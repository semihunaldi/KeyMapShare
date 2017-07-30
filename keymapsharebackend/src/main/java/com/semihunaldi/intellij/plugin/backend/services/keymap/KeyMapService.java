package com.semihunaldi.intellij.plugin.backend.services.keymap;

import com.semihunaldi.intellij.plugin.backend.model.keymap.KeyMap;
import com.semihunaldi.intellij.plugin.backend.services.BaseService;

import java.util.List;

public interface KeyMapService extends BaseService
{
    List<KeyMap> findUserKeyMaps(String email, String token);

    byte[] downloadKeyMap(String email, String name, String token);

    void uploadKeyMap(String keyMapName, String email, String base64EncodedXML, String token);
}
