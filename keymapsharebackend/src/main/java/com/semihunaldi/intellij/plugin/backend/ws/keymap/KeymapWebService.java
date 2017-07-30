package com.semihunaldi.intellij.plugin.backend.ws.keymap;

import com.semihunaldi.intellij.plugin.backend.ws.BaseResult;
import com.semihunaldi.intellij.plugin.backend.ws.keymap.model.GetKeyMapHelper;
import com.semihunaldi.intellij.plugin.backend.ws.keymap.model.GetKeyMapRequest;
import com.semihunaldi.intellij.plugin.backend.ws.keymap.model.GetKeyMapsOfUserRequest;
import com.semihunaldi.intellij.plugin.backend.ws.keymap.model.KeyMapsOfUser;
import com.semihunaldi.intellij.plugin.backend.ws.keymap.model.UploadKeyMapRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(path = "/api/keymap")
public interface KeymapWebService
{
    @RequestMapping(value = "/getKeyMap", method = RequestMethod.GET)
    public GetKeyMapHelper getKeyMap(@RequestBody GetKeyMapRequest getKeyMapRequest);

    @RequestMapping(value = "/getKeyMapsOfUser", method = RequestMethod.GET)
    public KeyMapsOfUser getKeyMapsOfUser(@RequestBody GetKeyMapsOfUserRequest getKeyMapsOfUserRequest);

    @RequestMapping(value = "/uploadKeyMap", method = RequestMethod.POST)
    public BaseResult uploadKeyMap(@RequestBody UploadKeyMapRequest uploadKeyMapRequest);
}
