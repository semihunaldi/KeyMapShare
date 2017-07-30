package com.semihunaldi.intellij.plugin.view;

import javax.swing.*;

public class RestartDialogUI extends JDialog
{
    private JPanel contentPane;

    public RestartDialogUI()
    {
        setContentPane(contentPane);
        setModal(true);
    }

    @Override
    public JPanel getContentPane()
    {
        return contentPane;
    }
}
