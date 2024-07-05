package org.reusablecomponents.spring.rest.query.entity.nonpaged;

import static java.lang.Boolean.TRUE;

import java.util.Objects;
import java.util.Optional;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.rest.rest.query.entity.nonpaged.EntityQueryHttpController;
import org.reusablecomponents.rest.rest.query.entity.nonpaged.EntityQueryHttpControllerBuilder;
import org.reusablecomponents.spring.core.application.query.entity.nonpaged.InterfaceSpringEntityQueryFacade;
import org.springframework.http.ResponseEntity;

/**
 * @param <Entity>
 * @param <Id>
 */
public class SpringEntityQueryHttpController<Entity extends AbstractEntity<Id>, Id> 
	extends EntityQueryHttpController<Entity, Id, //
		Id, // by id arg
		// results
		Optional<Entity>, // One result
		Iterable<Entity>, // multiple result
		Long, // count result
		Boolean, // exists result
		ResponseEntity<Void>,
		ResponseEntity<Entity>, 
		ResponseEntity<Iterable<Entity>>> 
	implements InterfaceSpringEntityQueryHttpController<Entity, Id> {

    protected SpringEntityQueryHttpController(final InterfaceSpringEntityQueryFacade<Entity, Id> entityQueryFacade) {
	super(new EntityQueryHttpControllerBuilder<> ($ -> {
	    
	    $.createResponseGetMultipleFunction = (entities) ->  ResponseEntity.ok(entities);
	    $.createResponseGetOneFunction = (oneResult) -> ResponseEntity.ok(oneResult.get());
	    
	    $.createResponseHeadFunction = (exists) -> Objects.equals(TRUE, exists) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	    
	    $.entityQueryFacade = entityQueryFacade;
	}));
    }
}
