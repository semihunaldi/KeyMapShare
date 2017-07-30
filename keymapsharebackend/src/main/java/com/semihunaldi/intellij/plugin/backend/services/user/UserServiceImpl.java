package com.semihunaldi.intellij.plugin.backend.services.user;

import com.semihunaldi.intellij.plugin.backend.dao.user.UserRepository;
import com.semihunaldi.intellij.plugin.backend.model.user.User;
import com.semihunaldi.intellij.plugin.backend.services.BaseServiceImpl;
import com.semihunaldi.intellij.plugin.backend.util.Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;

@Component
public class UserServiceImpl extends BaseServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User createUser(String email, String password, String gitHubId)
    {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setGitHubId(gitHubId);
        try
        {
            String token = Util.generateToken();
            user.setLastToken(token);
            return this.userRepository.save(user);
        }
        catch (ConstraintViolationException cve)
        {
            throw new RuntimeException("User exists with email : " + email);
        }
        catch (DataIntegrityViolationException e)
        {
            throw new RuntimeException("User exists with email : " + email);
        }
    }

    @Override
    @Transactional
    public String authenticateUser(String email, String password)
    {
        if(StringUtils.isBlank(password))
        {
            throw new RuntimeException("Password can not be null");
        }
        User user = findUserByEmail(email);
        if(passwordEncoder.matches(password,user.getPassword()))
        {
            user.setLastToken(Util.generateToken());
            User savedUser = userRepository.save(user);
            return savedUser.getLastToken();
        }
        else
        {
            throw new RuntimeException("Email and password does not match");
        }
    }

    @Override
    public User findUserByEmail(String email)
    {
        User user = userRepository.findUserByEmail(email);
        if(user == null)
        {
            throw new RuntimeException("User not found with email : " + email);
        }
        return user;
    }
}
