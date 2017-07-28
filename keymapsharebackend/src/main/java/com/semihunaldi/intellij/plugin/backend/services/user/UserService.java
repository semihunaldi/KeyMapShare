package com.semihunaldi.intellij.plugin.backend.services.user;

import com.semihunaldi.intellij.plugin.backend.model.user.User;
import com.semihunaldi.intellij.plugin.backend.services.BaseService;

public interface UserService extends BaseService
{
    User createUser(String email, String password, String gitHubId);

    String authenticateUser(String email, String password);

    User findUserByEmail(String email);
}
