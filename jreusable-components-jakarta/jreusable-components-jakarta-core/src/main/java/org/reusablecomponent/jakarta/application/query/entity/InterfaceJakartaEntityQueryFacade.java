package org.reusablecomponent.jakarta.application.query.entity;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;

import java.util.Optional;
import java.util.stream.Stream;

import org.reusablecomponent.core.application.query.entity.nonpaged.InterfaceEntityQueryFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

import jakarta.transaction.Transactional;

/**
 * @param <Entity>
 * @param <Id>
 */
public interface InterfaceJakartaEntityQueryFacade<Entity extends AbstractEntity<Id>, Id>
		//
		extends InterfaceEntityQueryFacade<Entity, Id, // base
				Id, // by id arg
				Optional<Entity>, // One result
				Stream<Entity>, // multiple result
				Long, // count result
				Boolean> { // exists result
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = SUPPORTS)
    Optional<Entity> findBy(final Id id, final Object... directives);
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = SUPPORTS)
    Stream<Entity> findAll(final Object... directives);
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = SUPPORTS)
    Long countAll();
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = SUPPORTS)
    Boolean existsBy(final Id id);
}
