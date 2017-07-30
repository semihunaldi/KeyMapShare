package com.semihunaldi.intellij.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.semihunaldi.intellij.plugin.view.KeyMapDialogUI;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by semih on 26.07.2017.
 */
public class KeyMapShareDialog extends DialogWrapper
{
    public KeyMapShareDialog(Project project)
    {
        super(project);
        this.project = project;
        init();
        setTitle("Keymap Share");
    }

    private Project project;

    private KeyMapDialogUI keyMapDialogUI = new KeyMapDialogUI(project,this);

    @Nullable
    @Override
    public JComponent createCenterPanel()
    {
        return keyMapDialogUI.getRootPane();
    }
}
