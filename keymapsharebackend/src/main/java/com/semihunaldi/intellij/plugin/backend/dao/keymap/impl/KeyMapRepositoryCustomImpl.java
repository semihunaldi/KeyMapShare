package com.semihunaldi.intellij.plugin.backend.dao.keymap.impl;

import com.semihunaldi.intellij.plugin.backend.dao.keymap.KeyMapRepositoryCustom;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional(readOnly = true)
public class KeyMapRepositoryCustomImpl implements KeyMapRepositoryCustom
{
    @PersistenceContext
    @Setter
    private EntityManager entityManager;
}
