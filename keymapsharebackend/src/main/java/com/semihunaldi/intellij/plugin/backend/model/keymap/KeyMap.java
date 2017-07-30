package com.semihunaldi.intellij.plugin.backend.model.keymap;

import com.semihunaldi.intellij.plugin.backend.model.SimpleAbstractEntity;
import com.semihunaldi.intellij.plugin.backend.model.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Data
@Table(name = "T_KEYMAP", uniqueConstraints = {@UniqueConstraint(name = "T_KEYMAP_NAME_UNIQUE_ID", columnNames = {"name","USER_ID"})})
@DynamicUpdate
@EqualsAndHashCode(callSuper = true, of="")
@Where(clause = "DELETED = '0'")
public class KeyMap extends SimpleAbstractEntity
{
    private String name;

    private String path;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
