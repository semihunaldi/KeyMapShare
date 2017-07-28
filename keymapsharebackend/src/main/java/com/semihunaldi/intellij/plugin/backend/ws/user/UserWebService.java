package com.semihunaldi.intellij.plugin.backend.ws.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(path = "/api/user")
public interface UserWebService
{
    @RequestMapping(path = "/getString", method = RequestMethod.GET)
    String getString();
}
