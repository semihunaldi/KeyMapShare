package com.semihunaldi.intellij.plugin.backend.ws.keymap.model;

import lombok.Data;

@Data
public class GetKeyMapsOfUserRequest
{
    private String email;
    private String token;
}
