package com.semihunaldi.intellij.plugin.backend.services.keymap;

import com.semihunaldi.intellij.plugin.backend.dao.keymap.KeyMapRepository;
import com.semihunaldi.intellij.plugin.backend.services.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KeyMapServiceImpl extends BaseServiceImpl implements KeyMapService
{
    @Autowired
    private KeyMapRepository keyMapRepository;
}
