package com.semihunaldi.intellij.plugin.restmodel;

import java.util.List;

public class KeyMapsOfUser extends BaseResult
{
    private List<String> keyMapNames;



    public List<String> getKeyMapNames()
    {
        return keyMapNames;
    }

    public void setKeyMapNames(List<String> keyMapNames)
    {
        this.keyMapNames = keyMapNames;
    }
}
