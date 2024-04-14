package org.reusablecomponent.spring.rest.command;

import java.util.Optional;

import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.rest.rest.command.AbstractEntityCommandHttpController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @param <Entity>
 * @param <Id>
 */
public class SpringEntityCommandHttpController<Entity extends AbstractEntity<Id>, Id> 
	extends AbstractEntityCommandHttpController<Entity, Id, Optional<Entity>, Iterable<Entity>, Void, ResponseEntity<?>>
	implements InterfaceSpringEntityCommandHttpController<Entity, Id> {

    /**
     * @param interfaceEntityCommandFacade
     */
    protected SpringEntityCommandHttpController(final InterfaceEntityCommandFacade<Entity, Id, Optional<Entity>, Iterable<Entity>, Void> interfaceEntityCommandFacade) {
	super(interfaceEntityCommandFacade);
    }

    // ==== HTTP Methods ====================================================================================================================
    
    /**
     * {@inheritDoc}
     */
//    @Override
//    public ResponseEntity<?> post(final Entity entity, final HttpServletRequest request, final HttpServletResponse response) {
//	return super.post(entity, request, response);
//    }
//    
//    @Override
//    public ResponseEntity<?> put(final Id id, Entity entity, HttpServletRequest request, HttpServletResponse response) {
//        return super.put(id, entity, request, response);
//    }
//    
//    @Override
//    public ResponseEntity<?> patch(
//		    final Id id, 
//		    final List<JsonPatch> jsonPatchs, //
//		    final HttpServletRequest request, //
//		    final HttpServletResponse response) {
//	return super.patch(id, jsonPatchs, request, response);
//    }
//    
//    @Override
//    public ResponseEntity<?> delete(final Id id, final HttpServletRequest request, final HttpServletResponse response) {
//        return super.delete(id, request, response);
//    }
    
    // ==== Utils Methods ====================================================================================================================
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Optional<Entity> findById(Id id) {
	// TODO Auto-generated method stub
	return Optional.empty();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Entity getEntityResult(final Optional<Entity> objectResult) {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ResponseEntity<?> createResponseDelete(final Id id) {
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ResponseEntity<?> createResponsePut(final Entity entity) {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ResponseEntity<?> createResponsePatch(final Entity entity) {
	// TODO Auto-generated method stub
	return null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected ResponseEntity<?> createResponsePost(final Entity entity) {
	return ResponseEntity.ok().build();
    }    
}
