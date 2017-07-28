package com.semihunaldi.intellij.plugin;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.keymap.Keymap;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class KeyMapShareAction extends AnAction
{
    @Override
    public void actionPerformed(AnActionEvent event)
    {
        try
        {
            KeyMapShareDialog keyMapShareDialog = new KeyMapShareDialog(event.getProject());
            keyMapShareDialog.createCenterPanel();
            keyMapShareDialog.show();
        }
        catch (Exception e)
        {
            Notification notification = new Notification("keyMapShareError","Keymap Share",e.getMessage(), NotificationType.ERROR);
            notification.notify(event.getProject());
        }
    }

    private File getKeyMapFile(Keymap selectedKeymap)
    {
        String ideaConfigBasePath = System.getProperties().getProperty("idea.config.path");
        File ideaKeyMapsFolder = new File(ideaConfigBasePath.concat("/keymaps/"));
        if(ideaKeyMapsFolder.exists() && ideaKeyMapsFolder.isDirectory())
        {
            File selectedKeyMapFile = FileUtils.getFile(ideaKeyMapsFolder.getAbsolutePath().concat("/").concat(selectedKeymap.getName().concat(".xml")));
            if(selectedKeyMapFile.exists() && selectedKeyMapFile.isFile())
            {
                return selectedKeyMapFile;
            }
            else
            {
                throw new RuntimeException("Desired file can not be found on config path");
            }
        }
        else
        {
            throw new RuntimeException("IDEA Config path not found");
        }
    }
}
