package com.semihunaldi.intellij.plugin.backend.ws.user.model;

import lombok.Data;

@Data
public class LoginRequest
{
    private String email;
    private String password;
    private String gitHubId;
}
