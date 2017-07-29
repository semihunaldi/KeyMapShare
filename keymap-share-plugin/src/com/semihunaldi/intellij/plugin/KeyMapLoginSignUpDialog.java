package com.semihunaldi.intellij.plugin;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.semihunaldi.intellij.plugin.restmodel.AuthenticationRequest;
import com.semihunaldi.intellij.plugin.restmodel.AuthenticationResponse;
import com.semihunaldi.intellij.plugin.restmodel.LoginRequest;
import com.semihunaldi.intellij.plugin.restmodel.LoginResponse;
import com.semihunaldi.intellij.plugin.util.EmailValidator;
import com.semihunaldi.intellij.plugin.util.KeyMapConstants;
import com.semihunaldi.intellij.plugin.view.KeyMapLoginSignUpUI;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.net.URI;
import java.util.prefs.Preferences;

public class KeyMapLoginSignUpDialog extends DialogWrapper
{
    protected KeyMapLoginSignUpDialog(Project project)
    {
        super(project);
        this.project = project;
        init();
        setTitle("Keymap Share");
    }

    private Project project;

    private KeyMapLoginSignUpUI keyMapLoginSignUpUI = new KeyMapLoginSignUpUI();

    private Preferences prefs = Preferences.userNodeForPackage(this.getClass());

    @Nullable
    @Override
    protected JComponent createCenterPanel()
    {
        prepareListeners();
        return keyMapLoginSignUpUI.getContentPane();
    }

    private void prepareListeners()
    {
        keyMapLoginSignUpUI.getLoginButton().addActionListener(actionEvent ->
        {
            try
            {
                String email = keyMapLoginSignUpUI.getLoginEmailField().getText();
                char[] password = keyMapLoginSignUpUI.getLoginPasswordField().getPassword();
                validate(email, password);
                URI uri = URI.create(KeyMapConstants.LOGIN_WS_URL);
                RestTemplate restTemplate = new RestTemplate();
                LoginRequest loginRequest = new LoginRequest(email, String.valueOf(password));
                LoginResponse loginResponse = restTemplate.postForObject(uri, loginRequest, LoginResponse.class);
                if(loginResponse.getErrorCode() == 0)
                {
                    prefs.put(KeyMapConstants.USER_EMAIL,loginResponse.getEmail());
                    prefs.put(KeyMapConstants.USER_TOKEN.concat(loginResponse.getEmail()),loginResponse.getToken());
                    prefs.put(KeyMapConstants.USER_LOGGED_IN,Boolean.TRUE.toString());
                    openMainDialog();
                }
                else
                {
                    throw new RuntimeException(loginResponse.getErrorDescription());
                }
            }
            catch (Exception e)
            {
                Notification notification = new Notification("keyMapShareLoginError","Keymap Share",e.getMessage(), NotificationType.ERROR);
                notification.notify(project);
            }
        });

        keyMapLoginSignUpUI.getSignupButton().addActionListener(e ->
        {
            try
            {
                String email = keyMapLoginSignUpUI.getSignupEmailField().getText();
                char[] password = keyMapLoginSignUpUI.getSignupPasswordField().getPassword();
                char[] confirmPassword = keyMapLoginSignUpUI.getSignupConfirmPasswordField().getPassword();
                validate(email,password);
                validate(email,confirmPassword);
                checkPasswordsSame(password,confirmPassword);
                URI uri = URI.create(KeyMapConstants.SIGNUP_WS_URL);
                RestTemplate restTemplate = new RestTemplate();
                AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, String.valueOf(password));
                AuthenticationResponse authenticationResponse = restTemplate.postForObject(uri, authenticationRequest, AuthenticationResponse.class);
                if(authenticationResponse.getErrorCode() == 0)
                {
                    prefs.put(KeyMapConstants.USER_EMAIL,authenticationResponse.getEmail());
                    prefs.put(KeyMapConstants.USER_TOKEN.concat(authenticationResponse.getEmail()),authenticationResponse.getToken());
                    prefs.put(KeyMapConstants.USER_LOGGED_IN,Boolean.TRUE.toString());
                    openMainDialog();
                }
                else
                {
                    throw new RuntimeException(authenticationResponse.getErrorDescription());
                }
            }
            catch (Exception e1)
            {
                Notification notification = new Notification("keyMapShareSignUpError","Keymap Share",e1.getMessage(), NotificationType.ERROR);
                notification.notify(project);
            }
        });
    }

    private void openMainDialog()
    {
        KeyMapShareDialog keyMapShareDialog = new KeyMapShareDialog(project);
        keyMapShareDialog.createCenterPanel();
        keyMapShareDialog.show();
        close(0);
    }

    private void checkPasswordsSame(char[] password, char[] confirmPassword)
    {
        if(!String.valueOf(password).equals(String.valueOf(confirmPassword)))
        {
            throw new IllegalArgumentException("Passwords does not match");
        }
    }

    private void validate(String email, char[] password)
    {
        if (StringUtils.isBlank(email))
        {
            throw new IllegalArgumentException("E-mail field can not be null");
        }
        if (password.length < 6)
        {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
        if(!new EmailValidator().validate(email))
        {
            throw new IllegalArgumentException("Email is not valid");
        }
    }
}
