package com.semihunaldi.intellij.plugin.backend.ws.user;

import com.semihunaldi.intellij.plugin.backend.ws.user.model.AuthenticationRequest;
import com.semihunaldi.intellij.plugin.backend.ws.user.model.AuthenticationResponse;
import com.semihunaldi.intellij.plugin.backend.ws.user.model.LoginRequest;
import com.semihunaldi.intellij.plugin.backend.ws.user.model.LoginResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(path = "/api/user")
public interface UserWebService
{
    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    AuthenticationResponse signup(AuthenticationRequest authenticationRequest);

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    LoginResponse login(LoginRequest loginRequest);
}
