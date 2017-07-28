package com.semihunaldi.intellij.plugin.backend.services.user;

import com.semihunaldi.intellij.plugin.backend.dao.user.UserRepository;
import com.semihunaldi.intellij.plugin.backend.services.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl extends BaseServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;
}
