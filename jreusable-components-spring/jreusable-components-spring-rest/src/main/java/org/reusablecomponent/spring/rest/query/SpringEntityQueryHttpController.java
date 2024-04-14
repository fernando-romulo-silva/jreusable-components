package org.reusablecomponent.spring.rest.query;


import static java.lang.Boolean.TRUE;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.reusablecomponent.core.application.query.entity.nonpaged.InterfaceEntityQueryFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.rest.rest.query.AbstractEntityQueryHttpController;
import org.reusablecomponent.spring.core.infra.logging.Loggable;
import org.springframework.http.ResponseEntity;

/**
 * @param <Entity>
 * @param <Id>
 */
@Loggable
public class SpringEntityQueryHttpController <Entity extends AbstractEntity<Id>, Id> 
	extends AbstractEntityQueryHttpController<Entity, Id, Optional<Entity>, Iterable<Entity>, Long, Boolean, ResponseEntity<?>> 
        implements InterfaceSpringEntityQueryHttpController<Entity, Id>   {
    
    /**
     * @param entityFacade
     */
    protected SpringEntityQueryHttpController(final InterfaceEntityQueryFacade<Entity, Id, Optional<Entity>, Iterable<Entity>, Long, Boolean> entityFacade) {
	super(entityFacade);
    }
    
    // ==== HTTP Methods ====================================================================================================================
    
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public ResponseEntity<Entity> get(@PathVariable final Id id, final HttpServletRequest request, final HttpServletResponse response) {
//        return (ResponseEntity<Entity>) super.get(id, request, response);
//    }
//    
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public final ResponseEntity<List<Entity>> getAll(final HttpServletRequest request, final HttpServletResponse response) {
//        return (ResponseEntity<List<Entity>>)super.getAll(request, response);
//    }
    
    
    // ==== Utils Methods ====================================================================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    protected final ResponseEntity<List<Entity>> createResponseGetMultiple(final Iterable<Entity> entities) {
	return ResponseEntity.ok(StreamSupport.stream(entities.spliterator(), false).toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final ResponseEntity<Entity> createResponseGetOne(final Optional<Entity> oneResult) {
	return ResponseEntity.ok(oneResult.get());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected final ResponseEntity<Void> createResponseHead(final Boolean exists) {
	
	if (Objects.equals(TRUE, exists)) {
	   return ResponseEntity.ok().build();
	}
	
	return ResponseEntity.notFound().build();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected final Boolean createExistsResult(final Long exists) {
	// TODO Auto-generated method stub
	return null;
    }
}
