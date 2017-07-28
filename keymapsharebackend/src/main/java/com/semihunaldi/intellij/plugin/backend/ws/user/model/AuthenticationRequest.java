package com.semihunaldi.intellij.plugin.backend.ws.user.model;

import lombok.Data;

@Data
public class AuthenticationRequest
{
    private String email;
    private String password;
    private String gitHubId;
}
