package com.semihunaldi.intellij.plugin.backend.ws.keymap.model;

import com.semihunaldi.intellij.plugin.backend.ws.BaseResult;
import lombok.Data;

import java.util.List;

@Data
public class KeyMapsOfUser extends BaseResult
{
    private List<String> keyMapNames;
}
