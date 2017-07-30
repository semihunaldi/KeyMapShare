package com.semihunaldi.intellij.plugin.restmodel;

public class GetKeyMapRequest
{
    private String keyMapName;
    private String email;
    private String token;

    public GetKeyMapRequest(String keyMapName, String email, String token)
    {
        this.keyMapName = keyMapName;
        this.email = email;
        this.token = token;
    }

    public GetKeyMapRequest()
    {
    }

    public String getKeyMapName()
    {
        return keyMapName;
    }

    public void setKeyMapName(String keyMapName)
    {
        this.keyMapName = keyMapName;
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
