package com.semihunaldi.intellij.plugin.backend.services.keymap;

import com.semihunaldi.intellij.plugin.backend.dao.keymap.KeyMapRepository;
import com.semihunaldi.intellij.plugin.backend.model.keymap.KeyMap;
import com.semihunaldi.intellij.plugin.backend.model.user.User;
import com.semihunaldi.intellij.plugin.backend.services.BaseServiceImpl;
import com.semihunaldi.intellij.plugin.backend.services.user.UserService;
import com.semihunaldi.intellij.plugin.backend.util.Util;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
    public byte[] downloadKeyMap(String email, String name, String token)
    {
        try
        {
            User user = userService.findUserByEmail(email);
            checkToken(token,user);
            KeyMap keyMap = keyMapRepository.findKeyMapByNameAndUserId(name, user.getId());
            File file = new File(keyMap.getPath());
            if(!file.exists() || !file.isFile())
            {
                throw new RuntimeException("Keymap can not be found");
            }
            InputStream targetStream = new FileInputStream(file);
            return IOUtils.toByteArray(targetStream);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error "+ e.getMessage());
        }
    }

    @Override
    @Transactional
    public void uploadKeyMap(String keyMapName, String email, String base64EncodedXML, String token)
    {
        try
        {
            User user = userService.findUserByEmail(email);
            checkToken(token,user);
            KeyMap keyMap = new KeyMap();
            keyMap.setName(keyMapName);
            keyMap.setUser(user);
            File file = new File(appProperties.getKeyMapsBasePath().concat("/").concat(keyMapName).concat(Util.getUUIDWithoutDashes(user.getId())).concat(".xml"));
            keyMap.setPath(file.getAbsolutePath());
            byte[] decodedXML = Base64.decode(base64EncodedXML);
            FileUtils.writeByteArrayToFile(file, decodedXML);
            keyMapRepository.save(keyMap);
        }
        catch (ConstraintViolationException cve)
        {
            throw new RuntimeException("Key map exists with keymap name : "+ keyMapName);
        }
        catch (DataIntegrityViolationException dive)
        {
            throw new RuntimeException("Key map exists with keymap name : "+ keyMapName);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error "+ e.getMessage());
        }
    }

    private void checkToken(String token, User user)
    {
        if (!user.getLastToken().equals(token))
        {
            throw new RuntimeException("Please authenticate properly.");
        }
    }
}
