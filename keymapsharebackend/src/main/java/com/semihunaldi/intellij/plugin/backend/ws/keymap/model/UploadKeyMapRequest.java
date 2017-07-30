package com.semihunaldi.intellij.plugin.backend.ws.keymap.model;

import lombok.Data;

@Data
public class UploadKeyMapRequest
{
    private String keyMapName;
    private String email;
    private String base64EncodedXML;
    private String token;
}
