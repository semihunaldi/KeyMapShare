package com.semihunaldi.intellij.plugin.backend.model.user;

import com.google.common.collect.Sets;
import com.semihunaldi.intellij.plugin.backend.model.SimpleAbstractEntity;
import com.semihunaldi.intellij.plugin.backend.model.keymap.KeyMap;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Data
@Table(name = "T_USER")
@DynamicUpdate
@EqualsAndHashCode(callSuper = true, of="")
@Where(clause = "DELETED = '0'")
public class User extends SimpleAbstractEntity
{
    private String email;

    private String userName;

    private String password;

    private String gitHubId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @Where(clause = "DELETED = '0'")
    private Set<KeyMap> keyMaps = Sets.newHashSet();
}
