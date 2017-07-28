package com.semihunaldi.intellij.plugin.backend.dao.user.impl;

import com.semihunaldi.intellij.plugin.backend.dao.user.UserRepositoryCustom;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional(readOnly = true)
public class UserRepositoryCustomImpl implements UserRepositoryCustom
{
    @PersistenceContext
    @Setter
    private EntityManager entityManager;
}
