package com.semihunaldi.intellij.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.keymap.Keymap;
import com.intellij.openapi.keymap.ex.KeymapManagerEx;
import com.thoughtworks.xstream.XStream;

public class KeyMapShareAction extends AnAction
{
    @Override
    public void actionPerformed(AnActionEvent event)
    {
        try
        {
            Keymap keymap = KeymapManagerEx.getInstanceEx().getAllKeymaps()[0];
            XStream xstream = new XStream();
//            String xml = xstream.toXML(keymap); //out of memory. need to find another solution
//            Keymap readKeyMap = (Keymap) xstream.fromXML(xml);
            System.out.println("done");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
