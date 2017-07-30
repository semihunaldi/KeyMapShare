package com.semihunaldi.intellij.plugin.restmodel;

public class GetKeyMapsOfUserRequest
{
    private String email;
    private String token;

    public GetKeyMapsOfUserRequest(String email, String token)
    {
        this.email = email;
        this.token = token;
    }

    public GetKeyMapsOfUserRequest()
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

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }
}
