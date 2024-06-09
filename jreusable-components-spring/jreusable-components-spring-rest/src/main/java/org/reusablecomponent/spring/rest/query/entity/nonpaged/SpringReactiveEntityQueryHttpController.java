package org.reusablecomponent.spring.rest.query.entity.nonpaged;


import org.reactivestreams.Publisher;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.rest.rest.query.entity.nonpaged.EntityQueryHttpController;
import org.reusablecomponent.rest.rest.query.entity.nonpaged.EntityQueryHttpControllerBuilder;
import org.reusablecomponent.spring.core.application.query.entity.nonpaged.InterfaceSpringReactiveEntityQueryFacade;
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
	extends EntityQueryHttpController<Entity, Id, //
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
    
    protected SpringReactiveEntityQueryHttpController(final InterfaceSpringReactiveEntityQueryFacade<Entity, Id> entityQueryFacade) {
	super(new EntityQueryHttpControllerBuilder<> ($ -> {
	    
	    $.createResponseGetMultipleFunction = (entities) ->  ResponseEntity.ok(entities);
	    $.createResponseGetOneFunction = (oneResult) -> ResponseEntity.ok(oneResult);
	    
	    $.createResponseHeadFunction = (existsResult) -> {
		final var exists = existsResult.blockOptional().orElse(Boolean.FALSE);

		if (exists) {
		    return ResponseEntity.ok(Mono.<Void>empty());
		} else {
		    return ResponseEntity.notFound().build();
		}
	    };
		
	    
	    $.entityQueryFacade = entityQueryFacade;
	}));
    }

}
