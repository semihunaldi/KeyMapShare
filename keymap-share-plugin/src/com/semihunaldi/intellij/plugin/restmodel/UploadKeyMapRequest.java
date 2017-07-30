package com.semihunaldi.intellij.plugin.restmodel;

public class UploadKeyMapRequest
{
    private String keyMapName;
    private String email;
    private String base64EncodedXML;
    private String token;

    public UploadKeyMapRequest(String keyMapName, String email, String base64EncodedXML, String token)
    {
        this.keyMapName = keyMapName;
        this.email = email;
        this.base64EncodedXML = base64EncodedXML;
        this.token = token;
    }

    public UploadKeyMapRequest()
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

    public String getBase64EncodedXML()
    {
        return base64EncodedXML;
    }

    public void setBase64EncodedXML(String base64EncodedXML)
    {
        this.base64EncodedXML = base64EncodedXML;
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
