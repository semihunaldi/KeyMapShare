package com.semihunaldi.intellij.plugin.restmodel;

public class BaseResult
{
    private Integer errorCode = 0 ;

    private String errorDescription = "Success";

    public BaseResult(Integer errorCode, String errorDescription)
    {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public BaseResult()
    {
    }

    public Integer getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getErrorDescription()
    {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription)
    {
        this.errorDescription = errorDescription;
    }
}
