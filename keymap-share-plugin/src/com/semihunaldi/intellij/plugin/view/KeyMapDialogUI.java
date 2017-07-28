package com.semihunaldi.intellij.plugin.view;

import com.intellij.ui.components.JBList;

import javax.swing.*;

public class KeyMapDialogUI extends JDialog
{
    private JPanel contentPane;
    private JBList keymapList;
    private JScrollPane jScrollPane;

    public KeyMapDialogUI()
    {
        setContentPane(contentPane);
        setModal(true);
    }

    @Override
    public JPanel getContentPane()
    {
        return contentPane;
    }

    public JBList getKeymapList()
    {
        return keymapList;
    }
}
