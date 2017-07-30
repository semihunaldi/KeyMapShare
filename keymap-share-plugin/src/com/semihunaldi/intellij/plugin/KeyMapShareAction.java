package com.semihunaldi.intellij.plugin;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.keymap.Keymap;
import com.semihunaldi.intellij.plugin.util.KeyMapConstants;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.prefs.Preferences;

public class KeyMapShareAction extends AnAction
{
    private Preferences prefs = Preferences.userNodeForPackage(this.getClass());

    @Override
    public void actionPerformed(AnActionEvent event)
    {
        try
        {
            //TODO for tests
            prefs.put(KeyMapConstants.USER_LOGGED_IN,Boolean.FALSE.toString());
            if(isUserLoggedIn())
            {
                KeyMapShareDialog keyMapShareDialog = new KeyMapShareDialog(event.getProject());
                keyMapShareDialog.createCenterPanel();
                keyMapShareDialog.show();
            }
            else
            {
                KeyMapLoginSignUpDialog keyMapLoginSignUpDialog = new KeyMapLoginSignUpDialog(event.getProject());
                keyMapLoginSignUpDialog.createCenterPanel();
                keyMapLoginSignUpDialog.show();
            }
        }
        catch (Exception e)
        {
            Notification notification = new Notification("keyMapShareError","Keymap Share",e.getMessage(), NotificationType.ERROR);
            notification.notify(event.getProject());
        }
    }

    private boolean isUserLoggedIn()
    {
        String userLoggedIn = prefs.get(KeyMapConstants.USER_LOGGED_IN,Boolean.FALSE.toString());
        if(userLoggedIn.equals(Boolean.TRUE.toString()))
        {
            return true;
        }
        return false;
    }
}
