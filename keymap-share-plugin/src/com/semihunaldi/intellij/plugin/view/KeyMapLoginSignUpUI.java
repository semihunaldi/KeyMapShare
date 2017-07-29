package com.semihunaldi.intellij.plugin.view;

import javax.swing.*;

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

    public KeyMapLoginSignUpUI()
    {
        setContentPane(contentPane);
        setModal(true);
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
