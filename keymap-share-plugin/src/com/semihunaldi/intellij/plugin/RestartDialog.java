package com.semihunaldi.intellij.plugin;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.semihunaldi.intellij.plugin.view.RestartDialogUI;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RestartDialog extends DialogWrapper
{
    public RestartDialog(@Nullable Project project)
    {
        super(project);
        this.project = project;
        init();
        setTitle("Restart");
    }

    private Project project;

    private RestartDialogUI restartDialogUI = new RestartDialogUI();

    @Nullable
    @Override
    public JComponent createCenterPanel()
    {
        return restartDialogUI.getContentPane();
    }

    @Override
    protected void doOKAction()
    {
        ApplicationManager.getApplication().restart();
    }

    @Override
    public void doCancelAction()
    {
        super.doCancelAction();
    }
}
