package org.reusablecomponent.spring.rest.query;

import static java.lang.Boolean.TRUE;

import java.util.Objects;
import java.util.Optional;

import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.rest.rest.query.entity.nonpaged.EntityQueryHttpController;
import org.reusablecomponent.spring.core.application.query.nonpaged.InterfaceSpringEntityQueryFacade;
import org.reusablecomponent.spring.core.infra.logging.Loggable;
import org.springframework.http.ResponseEntity;

/**
 * @param <Entity>
 * @param <Id>
 */
@Loggable
public class SpringEntityQueryHttpController<Entity extends AbstractEntity<Id>, Id> extends EntityQueryHttpController<Entity, Id, //
		Id, // by id arg
		// results
		Optional<Entity>, // One result
		Iterable<Entity>, // multiple result
		Long, // count result
		Boolean, // exists result
		ResponseEntity<Void>,
		ResponseEntity<Entity>, 
		ResponseEntity<Iterable<Entity>>> implements InterfaceSpringEntityQueryHttpController<Entity, Id> {

    /**
     * @param entityQueryFacade
     */
    protected SpringEntityQueryHttpController(final InterfaceSpringEntityQueryFacade<Entity, Id> entityQueryFacade) {
	super(entityQueryFacade);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final ResponseEntity<Iterable<Entity>> createResponseGetMultiple(final Iterable<Entity> entities) {
	return ResponseEntity.ok(entities);
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
    protected final Boolean createExistsResult(final Long countResult) {

	if (Objects.isNull(countResult) || countResult.longValue() <= 0) {
	    return Boolean.FALSE;
	}

	return Boolean.TRUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Boolean existsById(final Id queryIdIn) {
	return entityQueryFacade.existsBy(queryIdIn);
    }
}
