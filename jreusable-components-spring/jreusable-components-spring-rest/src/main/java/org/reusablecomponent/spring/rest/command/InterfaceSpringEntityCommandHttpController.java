package org.reusablecomponent.spring.rest.command;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

import java.util.List;

import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.rest.infra.jsonpath.JsonPatch;
import org.reusablecomponent.rest.rest.command.InterfaceEntityCommandHttpController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @param <Entity>
 * @param <Id>
 */
public interface InterfaceSpringEntityCommandHttpController<Entity extends AbstractEntity<Id>, Id> 
		extends InterfaceEntityCommandHttpController<Entity, Id,
				Id,
				// save
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
				Iterable<Id>, Void, // delete entities by id
				ResponseEntity<?>> {

    /**
     * {@inheritDoc}
     */
    @ResponseStatus(CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = { TEXT_PLAIN_VALUE, APPLICATION_JSON_VALUE })
    ResponseEntity<?> post(
		    
		    @RequestBody 
		    final Entity entity, 
		    
		    final HttpServletRequest request, 
		    final HttpServletResponse response);

    /**
     * {@inheritDoc}
     */
    @ResponseStatus(NO_CONTENT)
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<?> put(
		    
		    @PathVariable 
		    final Id id, 
		    
		    @RequestBody 
		    final Entity entity, 
		    
		    final HttpServletRequest request, 
		    final HttpServletResponse response);

    /**
     * {@inheritDoc}
     */
    @ResponseStatus(NO_CONTENT)
    @PatchMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<?> patch(
		    
		    @PathVariable
		    final Id id, 
		    
		    @RequestBody
		    final List<JsonPatch> jsonPatchs,
		    
		    final HttpServletRequest request, 
		    final HttpServletResponse response);

    /**
     * {@inheritDoc}
     */
    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(
		    
		    @PathVariable 
		    final Id id, 
		    
		    final HttpServletRequest request, 
		    final HttpServletResponse response);

}