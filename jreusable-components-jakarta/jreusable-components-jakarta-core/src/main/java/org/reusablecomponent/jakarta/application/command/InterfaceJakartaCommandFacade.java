package org.reusablecomponent.jakarta.application.command;

import static jakarta.transaction.Transactional.TxType.REQUIRED;

import java.util.List;

import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

import jakarta.transaction.Transactional;

public interface InterfaceJakartaCommandFacade<Entity extends AbstractEntity<Id>, Id>
		//
		extends InterfaceEntityCommandFacade<Entity, Id, Entity, List<Entity>, Void> {

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = REQUIRED)
    Entity save(final Entity entity);

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = REQUIRED)
    List<Entity> saveAll(final Iterable<Entity> entities);
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = REQUIRED)
    Entity update(final Entity entity);

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = REQUIRED)
    Entity update(final Id id, final Entity entity);

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = REQUIRED)
    List<Entity> updateAll(final Iterable<Entity> entities);

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = REQUIRED)
    Void delete(final Entity entity);

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = REQUIRED)
    Void deleteAll(final Iterable<Entity> entities);

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = REQUIRED)
    Void deleteBy(final Id id);

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = REQUIRED)
    Void deleteAllBy(final Iterable<Id> ids);

}
