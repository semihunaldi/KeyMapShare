package com.semihunaldi.intellij.plugin.restmodel;

public class AuthenticationRequest
{
    private String email;
    private String password;
    private String gitHubId;

    public AuthenticationRequest(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public AuthenticationRequest()
    {
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getGitHubId()
    {
        return gitHubId;
    }

    public void setGitHubId(String gitHubId)
    {
        this.gitHubId = gitHubId;
    }
}
