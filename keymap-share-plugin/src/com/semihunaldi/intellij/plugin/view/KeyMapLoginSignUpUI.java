package com.semihunaldi.intellij.plugin.view;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import com.semihunaldi.intellij.plugin.KeyMapLoginSignUpDialog;
import com.semihunaldi.intellij.plugin.KeyMapShareDialog;
import com.semihunaldi.intellij.plugin.restmodel.AuthenticationRequest;
import com.semihunaldi.intellij.plugin.restmodel.AuthenticationResponse;
import com.semihunaldi.intellij.plugin.restmodel.LoginRequest;
import com.semihunaldi.intellij.plugin.restmodel.LoginResponse;
import com.semihunaldi.intellij.plugin.util.EmailValidator;
import com.semihunaldi.intellij.plugin.util.KeyMapConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.net.URI;
import java.util.prefs.Preferences;

public class KeyMapLoginSignUpUI extends JDialog
{
    private JPanel contentPane;
    private JTabbedPane tabbedPane1;
    private JTextField loginEmailField;
    private JPasswordField loginPasswordField;
    private JButton loginButton;
    private JTextField signupEmailField;
    private JPasswordField signupPasswordField;
    private JPasswordField signupConfirmPasswordField;
    private JButton signupButton;

    private Preferences prefs = Preferences.userNodeForPackage(this.getClass());

    public KeyMapLoginSignUpUI(KeyMapLoginSignUpDialog keyMapLoginSignUpDialog, Project project)
    {
        setContentPane(contentPane);
        setModal(true);
        prepareListeners(project, keyMapLoginSignUpDialog);
    }

    private void prepareListeners(Project project, KeyMapLoginSignUpDialog keyMapLoginSignUpDialog)
    {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        loginButton.addActionListener(actionEvent ->
        {
            try
            {
                String email = loginEmailField.getText();
                char[] password = loginPasswordField.getPassword();
                validate(email, password);
                URI uri = URI.create(KeyMapConstants.LOGIN_WS_URL);
                LoginRequest loginRequest = new LoginRequest(email, String.valueOf(password));
                LoginResponse loginResponse = restTemplate.postForObject(uri, loginRequest, LoginResponse.class);
                if(loginResponse.getErrorCode() == 0)
                {
                    prefs.put(KeyMapConstants.USER_EMAIL,loginResponse.getEmail());
                    prefs.put(KeyMapConstants.USER_TOKEN,loginResponse.getToken());
                    prefs.put(KeyMapConstants.USER_LOGGED_IN,Boolean.TRUE.toString());
                    openMainDialog(project,keyMapLoginSignUpDialog);
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

        signupButton.addActionListener(e ->
        {
            try
            {
                String email = signupEmailField.getText();
                char[] password = signupPasswordField.getPassword();
                char[] confirmPassword = signupConfirmPasswordField.getPassword();
                validate(email,password);
                validate(email,confirmPassword);
                checkPasswordsSame(password,confirmPassword);
                URI uri = URI.create(KeyMapConstants.SIGNUP_WS_URL);
                AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, String.valueOf(password));
                AuthenticationResponse authenticationResponse = restTemplate.postForObject(uri, authenticationRequest, AuthenticationResponse.class);
                if(authenticationResponse.getErrorCode() == 0)
                {
                    prefs.put(KeyMapConstants.USER_EMAIL,authenticationResponse.getEmail());
                    prefs.put(KeyMapConstants.USER_TOKEN,authenticationResponse.getToken());
                    prefs.put(KeyMapConstants.USER_LOGGED_IN,Boolean.TRUE.toString());
                    openMainDialog(project,keyMapLoginSignUpDialog);
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

    private void openMainDialog(Project project, KeyMapLoginSignUpDialog keyMapLoginSignUpDialog)
    {
        KeyMapShareDialog keyMapShareDialog = new KeyMapShareDialog(project);
        keyMapShareDialog.createCenterPanel();
        keyMapShareDialog.show();
        keyMapLoginSignUpDialog.close(0);
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

    @Override
    public JPanel getContentPane()
    {
        return contentPane;
    }

    public JTabbedPane getTabbedPane1()
    {
        return tabbedPane1;
    }

    public JTextField getLoginEmailField()
    {
        return loginEmailField;
    }

    public JPasswordField getLoginPasswordField()
    {
        return loginPasswordField;
    }

    public JButton getLoginButton()
    {
        return loginButton;
    }

    public JTextField getSignupEmailField()
    {
        return signupEmailField;
    }

    public JPasswordField getSignupPasswordField()
    {
        return signupPasswordField;
    }

    public JPasswordField getSignupConfirmPasswordField()
    {
        return signupConfirmPasswordField;
    }

    public JButton getSignupButton()
    {
        return signupButton;
    }
}
