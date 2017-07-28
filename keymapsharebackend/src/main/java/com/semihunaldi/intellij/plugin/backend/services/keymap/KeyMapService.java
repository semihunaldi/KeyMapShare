package com.semihunaldi.intellij.plugin.backend.services.keymap;

import com.semihunaldi.intellij.plugin.backend.model.keymap.KeyMap;
import com.semihunaldi.intellij.plugin.backend.services.BaseService;

import java.util.List;

public interface KeyMapService extends BaseService
{
    List<KeyMap> findUserKeyMaps(String email, String token);

    void downloadKeyMap(String email, String name, String token);

    void uploadKeyMap(String token);
}
