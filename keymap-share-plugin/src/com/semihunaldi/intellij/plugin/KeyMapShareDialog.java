package com.semihunaldi.intellij.plugin;

import com.intellij.openapi.keymap.Keymap;
import com.intellij.openapi.keymap.ex.KeymapManagerEx;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.semihunaldi.intellij.plugin.view.KeyMapDialogUI;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by semih on 26.07.2017.
 */
public class KeyMapShareDialog extends DialogWrapper
{
    KeyMapShareDialog(Project project)
    {
        super(project);
        this.project = project;
        init();
        setTitle("Keymap Share");
    }

    private Project project;

    private KeyMapDialogUI keyMapDialogUI = new KeyMapDialogUI();

    @Nullable
    @Override
    protected JComponent createCenterPanel()
    {
        prepareKeyMapsList();
        return keyMapDialogUI.getRootPane();
    }

    private void prepareKeyMapsList()
    {
        List<String> keymaps = findUserKeyMaps();
        keyMapDialogUI.getKeymapList().setListData(keymaps.toArray());
        keyMapDialogUI.getKeymapList().setVisible(true);
    }

    private List<String> findUserKeyMaps()
    {
        List<String> keymaps = new ArrayList<>();
        for (Keymap keymap : KeymapManagerEx.getInstanceEx().getAllKeymaps())
        {
            if(isUserKeyMap(keymap))
            {
                keymaps.add(keymap.getName());
            }
        }
        return keymaps;
    }

    private boolean isUserKeyMap(Keymap keymap)
    {
        if(keymap.getParent()!= null && keymap.getParent().getName().equals("$default"))
        {
            return false;
        }
        else if(keymap.getParent()!= null && !keymap.getParent().getName().equals("$default"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
