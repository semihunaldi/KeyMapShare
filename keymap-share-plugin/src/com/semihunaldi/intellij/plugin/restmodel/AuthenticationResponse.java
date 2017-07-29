package com.semihunaldi.intellij.plugin.restmodel;

public class AuthenticationResponse extends BaseResult
{
    private String email;
    private String gitHubId;
    private String token;

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getGitHubId()
    {
        return gitHubId;
    }

    public void setGitHubId(String gitHubId)
    {
        this.gitHubId = gitHubId;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }
}
