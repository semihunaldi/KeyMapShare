package com.semihunaldi.intellij.plugin.backend.util;

import com.semihunaldi.intellij.plugin.backend.model.SimpleAbstractEntity;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by semih on 24.04.2017.
 */

@Component
public class EntityCommonFieldInterceptor extends EmptyInterceptor
{
    private final static Logger log = LoggerFactory.getLogger(EntityCommonFieldInterceptor.class);

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
    {
        updateCommonFieldsOnInsert(entity, state, propertyNames);
        return true;
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types)
    {
        updateCommonFieldsOnInsert(entity, currentState, propertyNames);
        return true;
    }

    private void updateCommonFieldsOnInsert(Object p_entity, Object[] p_state, String[] p_propertyNames)
    {
        try
        {
            if (p_entity instanceof SimpleAbstractEntity)
            {
                SimpleAbstractEntity abstractEntity = (SimpleAbstractEntity) p_entity;
                if(abstractEntity.getCreateTime() == null)
                {
                    abstractEntity.setCreateTime(new Date());
                    setPropertyState(p_state, p_propertyNames, "createTime", abstractEntity.getCreateTime());
                }
            }
        }
        catch (Exception e)
        {
            log.error("Failed to set common fields.", e);
            throw new RuntimeException(e);
        }
    }

    private void setPropertyState(Object[] propertyStates, String[] propertyNames, String propertyName, Object propertyState)
    {
        for (int i = 0; i < propertyNames.length; i++)
        {
            if (propertyName.equals(propertyNames[i]))
            {
                propertyStates[i] = propertyState;
                return;
            }
        }
    }
}
