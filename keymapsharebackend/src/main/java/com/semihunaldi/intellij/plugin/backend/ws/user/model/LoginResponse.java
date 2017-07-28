package com.semihunaldi.intellij.plugin.backend.ws.user.model;

import com.semihunaldi.intellij.plugin.backend.ws.BaseResult;
import lombok.Data;

@Data
public class LoginResponse extends BaseResult
{
    private String email;
    private String gitHubId;
    private String token;
}
