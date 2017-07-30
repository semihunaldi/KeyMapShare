package com.semihunaldi.intellij.plugin.backend.ws.keymap;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.semihunaldi.intellij.plugin.backend.model.keymap.KeyMap;
import com.semihunaldi.intellij.plugin.backend.services.keymap.KeyMapService;
import com.semihunaldi.intellij.plugin.backend.ws.BaseResult;
import com.semihunaldi.intellij.plugin.backend.ws.BaseWebServiceController;
import com.semihunaldi.intellij.plugin.backend.ws.keymap.model.GetKeyMapHelper;
import com.semihunaldi.intellij.plugin.backend.ws.keymap.model.GetKeyMapRequest;
import com.semihunaldi.intellij.plugin.backend.ws.keymap.model.GetKeyMapsOfUserRequest;
import com.semihunaldi.intellij.plugin.backend.ws.keymap.model.KeyMapsOfUser;
import com.semihunaldi.intellij.plugin.backend.ws.keymap.model.UploadKeyMapRequest;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KeyMapWebServiceController extends BaseWebServiceController implements KeymapWebService
{
    @Autowired
    private KeyMapService keyMapService;

    @Override
    public GetKeyMapHelper getKeyMap(@RequestBody GetKeyMapRequest getKeyMapRequest)
    {
        GetKeyMapHelper getKeyMapHelper = new GetKeyMapHelper();
        try
        {
            Preconditions.checkArgument(StringUtils.isNotBlank(getKeyMapRequest.getKeyMapName()),"keyMapName can not be null");
            Preconditions.checkArgument(StringUtils.isNotBlank(getKeyMapRequest.getEmail()),"email can not be null");
            Preconditions.checkArgument(StringUtils.isNotBlank(getKeyMapRequest.getToken()),"token can not be null");
            byte[] byteArray = keyMapService.downloadKeyMap(getKeyMapRequest.getEmail(),getKeyMapRequest.getKeyMapName(),getKeyMapRequest.getToken());
            getKeyMapHelper.setBase64EncodedXML(Base64.encode(byteArray));
        }
        catch (Exception e)
        {
            getKeyMapHelper.setErrorCode(-1);
            getKeyMapHelper.setErrorDescription(e.getMessage());
        }
        return getKeyMapHelper;
    }

    @Override
    public KeyMapsOfUser getKeyMapsOfUser(@RequestBody GetKeyMapsOfUserRequest getKeyMapsOfUserRequest)
    {
        KeyMapsOfUser keyMapsOfUser = new KeyMapsOfUser();
        try
        {
            Preconditions.checkArgument(StringUtils.isNotBlank(getKeyMapsOfUserRequest.getEmail()),"email can not be null");
            Preconditions.checkArgument(StringUtils.isNotBlank(getKeyMapsOfUserRequest.getToken()),"token can not be null");
            List<KeyMap> keyMaps = keyMapService.findUserKeyMaps(getKeyMapsOfUserRequest.getEmail(),getKeyMapsOfUserRequest.getToken());
            List<String> keyMapNames = Lists.newArrayList();
            for(KeyMap keyMap : keyMaps)
            {
                keyMapNames.add(keyMap.getName());
            }
            keyMapsOfUser.setKeyMapNames(keyMapNames);
        }
        catch (Exception e)
        {
            keyMapsOfUser.setErrorCode(-1);
            keyMapsOfUser.setErrorDescription(e.getMessage());
        }
        return keyMapsOfUser;
    }

    @Override
    public BaseResult uploadKeyMap(@RequestBody UploadKeyMapRequest uploadKeyMapRequest)
    {
        BaseResult baseResult = new BaseResult();
        try
        {
            Preconditions.checkArgument(StringUtils.isNotBlank(uploadKeyMapRequest.getKeyMapName()),"keyMapName can not be null");
            Preconditions.checkArgument(StringUtils.isNotBlank(uploadKeyMapRequest.getEmail()),"email can not be null");
            Preconditions.checkArgument(StringUtils.isNotBlank(uploadKeyMapRequest.getToken()),"token can not be null");
            Preconditions.checkArgument(StringUtils.isNotBlank(uploadKeyMapRequest.getBase64EncodedXML()),"base64EncodedXML can not be null");
            keyMapService.uploadKeyMap(uploadKeyMapRequest.getKeyMapName(),uploadKeyMapRequest.getEmail(),uploadKeyMapRequest.getBase64EncodedXML(),uploadKeyMapRequest.getToken());
        }
        catch (Exception e)
        {
            baseResult.setErrorCode(-1);
            baseResult.setErrorDescription(e.getMessage());
        }
        return baseResult;
    }
}
