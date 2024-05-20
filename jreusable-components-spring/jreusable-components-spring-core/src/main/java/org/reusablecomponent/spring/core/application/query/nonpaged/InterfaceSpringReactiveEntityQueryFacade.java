package org.reusablecomponent.spring.core.application.query.nonpaged;


import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

import org.reactivestreams.Publisher;
import org.reusablecomponent.core.application.query.entity.nonpaged.InterfaceEntityQueryFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * @param <Entity>
 * @param <Id>
 */
public interface InterfaceSpringReactiveEntityQueryFacade<Entity extends AbstractEntity<Id>, Id> 
	extends InterfaceEntityQueryFacade<Entity, Id,
		//
		Publisher<Id>, // by id arg
		// results
		Mono<Entity>, // One result
		Flux<Entity>, // multiple result
		Mono<Long>, // count result
		Mono<Boolean>> { // exists result
    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable
    @Transactional(readOnly = true, propagation = SUPPORTS)
    Mono<Entity> findBy(final Publisher<Id> id, final Object... directives);
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable
    @Transactional(readOnly = true, propagation = SUPPORTS)
    Flux<Entity> findAll(final Object... directives);

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable
    @Transactional(readOnly = true, propagation = SUPPORTS)
    Mono<Boolean> existsBy(final Publisher<Id> id);
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable
    @Transactional(readOnly = true, propagation = SUPPORTS)
    Mono<Long> countAll();
}
