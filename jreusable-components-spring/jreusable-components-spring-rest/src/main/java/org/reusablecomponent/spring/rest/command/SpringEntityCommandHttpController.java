package org.reusablecomponent.spring.rest.command;

import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.rest.rest.command.AbstractEntityCommandHttpController;
import org.reusablecomponent.spring.core.application.command.InterfaceSpringCommandFacade;
import org.springframework.http.ResponseEntity;

/**
 * @param <Entity>
 * @param <Id>
 */
public class SpringEntityCommandHttpController<Entity extends AbstractEntity<Id>, Id> extends AbstractEntityCommandHttpController<Entity, Id, // basic 				
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
		Iterable<Id>, Void, // delete entities by id>
		ResponseEntity<?>> 
	implements InterfaceSpringEntityCommandHttpController<Entity, Id> {

    /**
     * @param interfaceEntityCommandFacade
     */
    protected SpringEntityCommandHttpController(final InterfaceSpringCommandFacade<Entity, Id> interfaceEntityCommandFacade) {
	super(interfaceEntityCommandFacade);
    }
   
    /**
     * {@inheritDoc}
     */
    @Override
    protected ResponseEntity<Entity> createResponsePut(final Entity entity) {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ResponseEntity<Entity> createResponsePatch(final Entity entity) {
	// TODO Auto-generated method stub
	return null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected ResponseEntity<Entity> createResponsePost(final Entity entity) {
	return ResponseEntity.ok().build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Entity findById(final Id id) {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Entity getEntitySaveResult(final Entity saveEntityOut) {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Entity getEntityUpdateResult(final Entity saveEntityOut) {
	return saveEntityOut;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Entity getUpdateEntityIn(final Entity entity) {
	return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ResponseEntity<?> createResponseDelete(final Void deleteIdOut) {
	// TODO Auto-generated method stub
	return null;
    }    
}
