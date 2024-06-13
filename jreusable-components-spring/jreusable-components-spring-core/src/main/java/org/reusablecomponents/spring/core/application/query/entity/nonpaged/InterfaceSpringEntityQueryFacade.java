package org.reusablecomponents.spring.core.application.query.entity.nonpaged;


import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

import java.util.Optional;

import org.reusablecomponents.core.application.query.entity.nonpaged.InterfaceEntityQueryFacade;
import org.reusablecomponents.core.domain.AbstractEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;


/**
 * @param <Entity>
 * @param <Id>
 */
public interface InterfaceSpringEntityQueryFacade<Entity extends AbstractEntity<Id>, Id> 
	extends InterfaceEntityQueryFacade<Entity, Id,
		Id, // by id arg
		Optional<Entity>, // One result
		Iterable<Entity>, // multiple result
		Long, // count result
		Boolean> { // exists result
    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable
    @Transactional(readOnly = true, propagation = SUPPORTS)
    Optional<Entity> findBy(final Id id, final Object... directives);
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable
    @Transactional(readOnly = true, propagation = SUPPORTS)
    Iterable<Entity> findAll(final Object... directives);

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable
    @Transactional(readOnly = true, propagation = SUPPORTS)
    Boolean existsBy(final Id id);
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable
    @Transactional(readOnly = true, propagation = SUPPORTS)
    Long countAll();
}
