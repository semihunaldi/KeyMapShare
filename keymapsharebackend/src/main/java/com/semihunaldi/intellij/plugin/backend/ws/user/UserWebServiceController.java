package com.semihunaldi.intellij.plugin.backend.ws.user;

import com.semihunaldi.intellij.plugin.backend.services.user.UserService;
import com.semihunaldi.intellij.plugin.backend.ws.BaseWebServiceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserWebServiceController extends BaseWebServiceController implements UserWebService
{
    @Autowired
    private UserService userService;

}
