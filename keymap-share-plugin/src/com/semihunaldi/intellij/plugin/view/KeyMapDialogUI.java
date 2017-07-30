package com.semihunaldi.intellij.plugin.view;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.keymap.Keymap;
import com.intellij.openapi.keymap.ex.KeymapManagerEx;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBList;
import com.jgoodies.common.base.Preconditions;
import com.semihunaldi.intellij.plugin.KeyMapShareAction;
import com.semihunaldi.intellij.plugin.RestartDialog;
import com.semihunaldi.intellij.plugin.restmodel.BaseResult;
import com.semihunaldi.intellij.plugin.restmodel.GetKeyMapHelper;
import com.semihunaldi.intellij.plugin.restmodel.GetKeyMapRequest;
import com.semihunaldi.intellij.plugin.restmodel.GetKeyMapsOfUserRequest;
import com.semihunaldi.intellij.plugin.restmodel.KeyMapsOfUser;
import com.semihunaldi.intellij.plugin.restmodel.UploadKeyMapRequest;
import com.semihunaldi.intellij.plugin.util.KeyMapConstants;
import com.semihunaldi.intellij.plugin.util.KeyMapUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class KeyMapDialogUI extends JDialog
{
    private JPanel contentPane;
    private JTabbedPane tabbedPane;
    private JBList keymapListLocal;
    private JBList keymapListCloud;
    private JButton uploadSelectedButton;
    private JButton downloadSelectedButton;
    private JButton logOutButton;

    private Preferences prefs = Preferences.userNodeForPackage(KeyMapShareAction.class);

    private RestTemplate restTemplate = new RestTemplate();

    public KeyMapDialogUI(Project project, DialogWrapper dialogWrapper)
    {
        setContentPane(contentPane);
        setModal(true);
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        prepareLocalKeyMapsList(project);
        prepareCloudKeyMapsList(project);
        prepareUploadButtonListener(project, dialogWrapper);
        prepareDownloadButtonListener(project);
        prepareLogOutButtonListener(project, dialogWrapper);
    }

    private void prepareLogOutButtonListener(Project project, DialogWrapper dialogWrapper)
    {
        logOutButton.addActionListener(event ->
        {
            try
            {
                prefs.put(KeyMapConstants.USER_EMAIL, "");
                prefs.put(KeyMapConstants.USER_TOKEN, "");
                prefs.put(KeyMapConstants.USER_LOGGED_IN, Boolean.FALSE.toString());
                prefs.flush();
                dialogWrapper.close(0);
            }
            catch (Exception e)
            {
                Notification notification = new Notification("keyMapShareError", "Keymap Share", "Unexpected Error Occurred", NotificationType.ERROR);
                notification.notify(project);
            }
        });
    }

    private void prepareDownloadButtonListener(Project project)
    {
        downloadSelectedButton.addActionListener(event ->
        {
            try
            {
                String selectedKeyMapName = (String) keymapListCloud.getSelectedValue();
                Preconditions.checkArgument(StringUtils.isNotBlank(selectedKeyMapName), "Please select a keymap to download");
                String ideaConfigBasePath = System.getProperties().getProperty("idea.config.path");
                File ideaKeyMapsFolder = new File(ideaConfigBasePath.concat("/keymaps/"));
                File keymapToSave = new File(ideaKeyMapsFolder.getAbsolutePath().concat("/").concat(selectedKeyMapName).concat(".xml"));
                if (keymapToSave.exists())
                {
                    throw new IllegalArgumentException("Key Map already exists with name : " + selectedKeyMapName);
                }
                String email = prefs.get(KeyMapConstants.USER_EMAIL, "");
                String token = prefs.get(KeyMapConstants.USER_TOKEN, "");
                GetKeyMapRequest getKeyMapRequest = new GetKeyMapRequest(selectedKeyMapName, email, token);
                URI uri = URI.create(KeyMapConstants.GET_KEYMAP_WS);
                GetKeyMapHelper getKeyMapHelper = restTemplate.postForObject(uri, getKeyMapRequest, GetKeyMapHelper.class);
                if (getKeyMapHelper.getErrorCode() == 0)
                {
                    byte[] decodedXML = Base64.decode(getKeyMapHelper.getBase64EncodedXML());
                    FileUtils.writeByteArrayToFile(keymapToSave, decodedXML);
                    RestartDialog restartDialog = new RestartDialog(project);
                    restartDialog.createCenterPanel();
                    restartDialog.show();
                }
                else
                {
                    throw new RuntimeException(getKeyMapHelper.getErrorDescription());
                }
            }
            catch (Exception e)
            {
                Notification notification = new Notification("keyMapShareError", "Keymap Share", e.getMessage(), NotificationType.ERROR);
                notification.notify(project);
            }
        });
    }

    private void prepareUploadButtonListener(Project project, DialogWrapper dialogWrapper)
    {
        uploadSelectedButton.addActionListener(event ->
        {
            try
            {
                String selectedKeyMapName = (String) keymapListLocal.getSelectedValue();
                Preconditions.checkArgument(StringUtils.isNotBlank(selectedKeyMapName), "Please select a keymap to upload");
                File keyMapFile = KeyMapUtil.getKeyMapFile(selectedKeyMapName);
                InputStream targetStream = new FileInputStream(keyMapFile);
                byte[] fileByteArray = IOUtils.toByteArray(targetStream);
                String encodedBase64XML = Base64.encode(fileByteArray);
                String email = prefs.get(KeyMapConstants.USER_EMAIL, "");
                String token = prefs.get(KeyMapConstants.USER_TOKEN, "");
                UploadKeyMapRequest uploadKeyMapRequest = new UploadKeyMapRequest(selectedKeyMapName, email, encodedBase64XML, token);
                URI uri = URI.create(KeyMapConstants.UPLOAD_KEYMAP_WS);
                BaseResult baseResult = restTemplate.postForObject(uri, uploadKeyMapRequest, BaseResult.class);
                if (baseResult.getErrorCode() == 0)
                {
                    Notification notification = new Notification("keyMapShareUpload", "Keymap Share", "Keymap Successfully uploaded", NotificationType.INFORMATION);
                    notification.notify(project);
                    dialogWrapper.close(0);
                }
                else
                {
                    throw new RuntimeException(baseResult.getErrorDescription());
                }
            }
            catch (Exception e)
            {
                Notification notification = new Notification("keyMapShareError", "Keymap Share", e.getMessage(), NotificationType.ERROR);
                notification.notify(project);
            }
        });
    }

    private void prepareCloudKeyMapsList(Project project)
    {
        try
        {
            String email = prefs.get(KeyMapConstants.USER_EMAIL, "");
            String token = prefs.get(KeyMapConstants.USER_TOKEN, "");
            GetKeyMapsOfUserRequest getKeyMapsOfUserRequest = new GetKeyMapsOfUserRequest(email, token);
            URI uri = URI.create(KeyMapConstants.GET_KEYMAPSOFUSER_WS);
            KeyMapsOfUser keyMapsOfUser = restTemplate.postForObject(uri, getKeyMapsOfUserRequest, KeyMapsOfUser.class);
            if (keyMapsOfUser.getErrorCode() == 0)
            {
                keymapListCloud.setListData(keyMapsOfUser.getKeyMapNames().toArray());
                keymapListCloud.setVisible(true);
            }
            else
            {
                throw new RuntimeException(keyMapsOfUser.getErrorDescription());
            }
        }
        catch (Exception e)
        {
            Notification notification = new Notification("keyMapShareError", "Keymap Share", e.getMessage(), NotificationType.ERROR);
            notification.notify(project);
        }
    }

    private void prepareLocalKeyMapsList(Project project)
    {
        List<String> keymaps = findUserKeyMaps();
        keymapListLocal.setListData(keymaps.toArray());
        keymapListLocal.setVisible(true);
    }

    private List<String> findUserKeyMaps()
    {
        List<String> keymaps = new ArrayList<>();
        for (Keymap keymap : KeymapManagerEx.getInstanceEx().getAllKeymaps())
        {
            if (isUserKeyMap(keymap))
            {
                keymaps.add(keymap.getName());
            }
        }
        return keymaps;
    }

    private boolean isUserKeyMap(Keymap keymap)
    {
        if (keymap.getParent() != null && keymap.getParent().getName().equals("$default"))
        {
            return false;
        }
        else if (keymap.getParent() != null && !keymap.getParent().getName().equals("$default"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public JPanel getContentPane()
    {
        return contentPane;
    }

    public JTabbedPane getTabbedPane()
    {
        return tabbedPane;
    }

    public JBList getKeymapListLocal()
    {
        return keymapListLocal;
    }

    public JBList getKeymapListCloud()
    {
        return keymapListCloud;
    }

    public JButton getUploadSelectedButton()
    {
        return uploadSelectedButton;
    }

    public JButton getDownloadSelectedButton()
    {
        return downloadSelectedButton;
    }

    public JButton getLogOutButton()
    {
        return logOutButton;
    }
}
