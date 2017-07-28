package com.semihunaldi.intellij.plugin.backend.ws.keymap;

import com.semihunaldi.intellij.plugin.backend.services.keymap.KeyMapService;
import com.semihunaldi.intellij.plugin.backend.ws.BaseWebServiceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KeyMapWebServiceController extends BaseWebServiceController implements KeymapWebService
{
    @Autowired
    private KeyMapService keyMapService;

}
