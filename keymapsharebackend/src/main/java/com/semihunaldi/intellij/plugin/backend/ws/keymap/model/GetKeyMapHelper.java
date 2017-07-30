package com.semihunaldi.intellij.plugin.backend.ws.keymap.model;

import com.semihunaldi.intellij.plugin.backend.ws.BaseResult;
import lombok.Data;

@Data
public class GetKeyMapHelper extends BaseResult
{
    private String Base64EncodedXML;
}
