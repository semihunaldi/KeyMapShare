package com.semihunaldi.intellij.plugin.backend.dao.keymap;

import com.semihunaldi.intellij.plugin.backend.model.keymap.KeyMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeyMapRepository extends JpaRepository<KeyMap,String>
{
    @Query("select keyMap from KeyMap keyMap where keyMap.user.id=:userId and keyMap.deleted='0'")
    public List<KeyMap> findKeyMapsOfUser(@Param("userId") String userId);

    @Query("select keyMap from KeyMap keyMap where keyMap.user.id=:userId and keyMap.name=:name and keyMap.deleted='0'")
    public KeyMap findKeyMapByNameAndUserId(@Param("name") String name, @Param("userId") String userId);
}
