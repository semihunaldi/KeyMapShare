package com.semihunaldi.intellij.plugin.util;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class KeyMapUtil
{
    public static File getKeyMapFile(String keyMapName)
    {
        String ideaConfigBasePath = System.getProperties().getProperty("idea.config.path");
        File ideaKeyMapsFolder = new File(ideaConfigBasePath.concat("/keymaps/"));
        if(ideaKeyMapsFolder.exists() && ideaKeyMapsFolder.isDirectory())
        {
            File selectedKeyMapFile = FileUtils.getFile(ideaKeyMapsFolder.getAbsolutePath().concat("/").concat(keyMapName.concat(".xml")));
            if(selectedKeyMapFile.exists() && selectedKeyMapFile.isFile())
            {
                return selectedKeyMapFile;
            }
            else
            {
                throw new RuntimeException("Desired file can not be found on config path");
            }
        }
        else
        {
            throw new RuntimeException("IDEA Config path not found");
        }
    }
}
