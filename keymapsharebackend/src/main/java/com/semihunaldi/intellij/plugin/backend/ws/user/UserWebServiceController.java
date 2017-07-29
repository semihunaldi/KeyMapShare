package com.semihunaldi.intellij.plugin.backend.ws.user;

import com.semihunaldi.intellij.plugin.backend.model.user.User;
import com.semihunaldi.intellij.plugin.backend.services.user.UserService;
import com.semihunaldi.intellij.plugin.backend.ws.BaseWebServiceController;
import com.semihunaldi.intellij.plugin.backend.ws.user.model.AuthenticationRequest;
import com.semihunaldi.intellij.plugin.backend.ws.user.model.AuthenticationResponse;
import com.semihunaldi.intellij.plugin.backend.ws.user.model.LoginRequest;
import com.semihunaldi.intellij.plugin.backend.ws.user.model.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserWebServiceController extends BaseWebServiceController implements UserWebService
{
    @Autowired
    private UserService userService;

    @Override
    public AuthenticationResponse signup(@RequestBody AuthenticationRequest authenticationRequest)
    {
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        try
        {
            User user = userService.createUser(authenticationRequest.getEmail(), authenticationRequest.getPassword(), authenticationRequest.getGitHubId());
            authenticationResponse.setEmail(user.getEmail());
            authenticationResponse.setGitHubId(user.getGitHubId());
            authenticationResponse.setToken(user.getLastToken());
        }
        catch (Exception e)
        {
            authenticationResponse.setErrorCode(-1);
            authenticationResponse.setErrorDescription(e.getMessage());
        }
        return authenticationResponse;
    }

    @Override
    public LoginResponse login(@RequestBody LoginRequest loginRequest)
    {
        LoginResponse loginResponse = new LoginResponse();
        try
        {
            String token = userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
            loginResponse.setEmail(loginRequest.getEmail());
            loginResponse.setGitHubId(loginRequest.getGitHubId());
            loginResponse.setToken(token);
        }
        catch (Exception e)
        {
            loginResponse.setErrorCode(-1);
            loginResponse.setErrorDescription(e.getMessage());
        }
        return loginResponse;
    }
}
