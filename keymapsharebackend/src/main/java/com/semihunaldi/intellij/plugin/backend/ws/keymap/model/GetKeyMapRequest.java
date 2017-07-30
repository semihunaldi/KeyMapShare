package com.semihunaldi.intellij.plugin.backend.ws.keymap.model;

import lombok.Data;

@Data
public class GetKeyMapRequest
{
    private String keyMapName;
    private String email;
    private String token;
}
