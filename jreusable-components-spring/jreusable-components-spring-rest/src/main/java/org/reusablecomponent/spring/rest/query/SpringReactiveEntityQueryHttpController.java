package org.reusablecomponent.spring.rest.query;


import java.util.Objects;

import org.reactivestreams.Publisher;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.rest.rest.query.entity.nonpaged.AbstractEntityQueryHttpController;
import org.reusablecomponent.spring.core.application.query.nonpaged.InterfaceSpringReactiveEntityQueryFacade;
import org.reusablecomponent.spring.core.infra.logging.Loggable;
import org.springframework.http.ResponseEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @param <Entity>
 * @param <Id>
 */
@Loggable
public class SpringReactiveEntityQueryHttpController <Entity extends AbstractEntity<Id>, Id>  
	extends AbstractEntityQueryHttpController<Entity, Id, //
				Publisher<Id>, // by id arg
				// results
				Mono<Entity>, // One result
				Flux<Entity>, // multiple result
				Mono<Long>, // count result
				Mono<Boolean>, // exists result
				ResponseEntity<Mono<Void>>,
				ResponseEntity<Mono<Entity>>, 
				ResponseEntity<Flux<Entity>>>
		implements InterfaceSpringReactiveEntityQueryHttpController<Entity, Id> {
    
    /**
     * @param entityQueryFacade
     */
    protected SpringReactiveEntityQueryHttpController(final InterfaceSpringReactiveEntityQueryFacade<Entity, Id> entityQueryFacade) {
	super(entityQueryFacade);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Mono<Boolean> existsById(final Publisher<Id> queryIdIn) {
	return entityQueryFacade.existsBy(queryIdIn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Mono<Boolean> createExistsResult(final Mono<Long> countResult) {
	
	return countResult.map(exists -> {

	    if (Objects.isNull(exists) || exists.longValue() <= 0) {
		return Boolean.FALSE;
	    }

	    return Boolean.TRUE;
	});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ResponseEntity<Flux<Entity>> createResponseGetMultiple(final Flux<Entity> multipleResult) {
	return ResponseEntity.ok(multipleResult);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ResponseEntity<Mono<Entity>> createResponseGetOne(final Mono<Entity> oneResult) {
	return ResponseEntity.ok(oneResult);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ResponseEntity<Mono<Void>> createResponseHead(final Mono<Boolean> existsResult) {
	
	// TODO avoid block
	final var exists = existsResult.blockOptional().orElse(Boolean.FALSE);
	
	if (exists) {
	    return ResponseEntity.ok(Mono.<Void>empty());
	} else {
	    return ResponseEntity.notFound().build();
	}
    }
}
