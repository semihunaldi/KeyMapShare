package com.semihunaldi.intellij.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.keymap.Keymap;
import com.intellij.openapi.keymap.ex.KeymapManagerEx;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamer;

public class KeyMapShareAction extends AnAction
{
    @Override
    public void actionPerformed(AnActionEvent event)
    {
        try
        {
            Keymap keymap = KeymapManagerEx.getInstanceEx().getAllKeymaps()[0];
            XStreamer xStreamer = new XStreamer();
            String xml = xStreamer.toXML(new XStream(),keymap);
            Keymap readKeyMap = (Keymap) xStreamer.fromXML(xml);
            System.out.println("done");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
