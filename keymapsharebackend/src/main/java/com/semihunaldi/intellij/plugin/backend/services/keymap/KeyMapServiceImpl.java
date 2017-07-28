package com.semihunaldi.intellij.plugin.backend.services.keymap;

import com.semihunaldi.intellij.plugin.backend.dao.keymap.KeyMapRepository;
import com.semihunaldi.intellij.plugin.backend.model.keymap.KeyMap;
import com.semihunaldi.intellij.plugin.backend.model.user.User;
import com.semihunaldi.intellij.plugin.backend.services.BaseServiceImpl;
import com.semihunaldi.intellij.plugin.backend.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KeyMapServiceImpl extends BaseServiceImpl implements KeyMapService
{
    @Autowired
    private KeyMapRepository keyMapRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<KeyMap> findUserKeyMaps(String email, String token)
    {
        User user = userService.findUserByEmail(email);
        checkToken(token,user);
        List<KeyMap> keyMapsOfUser = keyMapRepository.findKeyMapsOfUser(user.getId());
        return keyMapsOfUser;
    }

    @Override
    public void downloadKeyMap(String email, String name, String token)
    {
        User user = userService.findUserByEmail(email);
        checkToken(token,user);
        KeyMap keyMap = keyMapRepository.findKeyMapByNameAndUserId(name, user.getId());
        keyMap.getPath();
        //TODO
    }

    @Override
    public void uploadKeyMap(String token)
    {
        //TODO
    }

    private void checkToken(String token, User user)
    {
        if (!user.getLastToken().equals(token))
        {
            throw new RuntimeException("Please authenticate properly.");
        }
    }
}
