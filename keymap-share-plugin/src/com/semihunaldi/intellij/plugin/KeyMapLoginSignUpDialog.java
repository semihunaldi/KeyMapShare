package com.semihunaldi.intellij.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.semihunaldi.intellij.plugin.view.KeyMapLoginSignUpUI;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

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

    private KeyMapLoginSignUpUI keyMapLoginSignUpUI = new KeyMapLoginSignUpUI(this,project);

    @Nullable
    @Override
    protected JComponent createCenterPanel()
    {
        return keyMapLoginSignUpUI.getContentPane();
    }
}
