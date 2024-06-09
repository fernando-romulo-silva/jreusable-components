package org.reusablecomponent.spring.rest.command;

import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.rest.infra.jsonpath.JsonPatch;
import org.reusablecomponent.rest.infra.jsonpath.JsonPatchOperation;
import org.reusablecomponent.rest.rest.command.EntityCommandHttpController;
import org.reusablecomponent.rest.rest.command.EntityCommandHttpControllerBuilder;
import org.reusablecomponent.spring.core.application.command.InterfaceSpringCommandFacade;
import org.reusablecomponent.spring.core.application.query.nonpaged.InterfaceSpringEntityQueryFacade;
import org.springframework.http.ResponseEntity;

/**
 * @param <Entity>
 * @param <Id>
 */
public class SpringEntityCommandHttpController<Entity extends AbstractEntity<Id>, Id> extends EntityCommandHttpController<Entity, Id, // basic
		Id, Entity, // 
		// save
		Entity, Entity, // save a entity
		Iterable<Entity>, Iterable<Entity>, // save entities
		// update
		Entity, Entity, // update a entity
		Iterable<Entity>, Iterable<Entity>, // update entities
		// delete
		Entity, Void, // delete a entity
		Iterable<Entity>, Void, // delete entities
		// delete by id
		Id, Void, // delete a entity by id
		Iterable<Id>, Void, // delete entities by id>
		ResponseEntity<?>> implements InterfaceSpringEntityCommandHttpController<Entity, Id> {

    protected SpringEntityCommandHttpController(
		    final InterfaceSpringEntityQueryFacade<Entity, Id> springEntityFacade,
		    final InterfaceSpringCommandFacade<Entity, Id> springCommandFacade) {
	
	super(new EntityCommandHttpControllerBuilder<>($ -> {
	    
	    $.applyPatchToJObject = (jsonPatchs, id) -> {
		
		final var entity = springEntityFacade.findBy(id).orElseThrow(null);
		
		final var toReplace = jsonPatchs.stream()
				.filter(jsonPatch -> Objects.equals(jsonPatch.op(), JsonPatchOperation.REPLACE))
				.collect(Collectors.toMap(JsonPatch::path, JsonPatch::value));
		
		toReplace.forEach((key, value) -> {
		    final var field = FieldUtils.getField(entity.getClass(), key, true);
		    try {
			FieldUtils.writeField(field, entity, value, true);
		    } catch (final IllegalAccessException ex) {
			throw new IllegalArgumentException(ex);
		    }
		});
		
		return entity;
	    };
	    
	    $.createResponseDelete = (voidValue) -> ResponseEntity.noContent().build();
	    $.createResponsePatch = (entity) -> ResponseEntity.noContent().build();
	    $.createResponsePut = (entity) -> ResponseEntity.noContent().build();
	    $.createResponsePost = (entity) -> ResponseEntity.ok(entity);

	    $.entityCommandFacade = springCommandFacade;
	}));
    }
}
