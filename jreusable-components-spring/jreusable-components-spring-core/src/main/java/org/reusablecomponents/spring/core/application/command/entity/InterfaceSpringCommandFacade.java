package org.reusablecomponents.spring.core.application.command.entity;


import org.reusablecomponents.base.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * @param <Entity>
 * @param <Id>
 */
public interface InterfaceSpringCommandFacade<Entity extends AbstractEntity<Id>, Id>
		//
		extends InterfaceEntityCommandFacade<Entity, Id,
				// save
				Entity, Entity, // save a entity
				Iterable<Entity>, Iterable<Entity>, // save entities
				// update
				Entity, Entity, // update a entity
				Iterable<Entity>, Iterable<Entity>, // update entities
				// delete entity
				Entity, Void, // delete a entity
				Iterable<Entity>, Void, // delete entities
				// delete by id
				Id, Void, // delete a entity by id
				Iterable<Id>, Void> { // delete entities by id
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    Entity save(final Entity entity);

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    Iterable<Entity> saveAll(final Iterable<Entity> entities);
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    Entity update(final Entity entity);

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    Iterable<Entity> updateAll(final Iterable<Entity> entities);

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    Void delete(final Entity entity);

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    Void deleteAll(final Iterable<Entity> entities);

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    Void deleteBy(final Id id);

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    Void deleteAllBy(final Iterable<Id> ids);
}
