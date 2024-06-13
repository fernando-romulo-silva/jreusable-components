package org.reusablecomponents.rest.rest.command;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.reusablecomponents.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponents.core.domain.AbstractEntity;
import org.reusablecomponents.rest.infra.jsonpath.JsonPatch;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EntityCommandHttpController<Entity extends AbstractEntity<Id>, Id, // basic

		QueryIdIn, OneResult,
		// save
		SaveEntityIn, SaveEntityOut, // save a entity
		SaveEntitiesIn, SaveEntitiesOut, // save entities
		// update
		UpdateEntityIn, UpdateEntityOut, // update a entity
		UpdateEntitiesIn, UpdateEntitiesOut, // update entities
		// delete entity
		DeleteEntityIn, DeleteEntityOut, // delete a entity
		DeleteEntitiesIn, DeleteEntitiesOut, // delete entities
		// delete by id
		DeleteIdIn, DeleteIdOut, // delete a entity by id
		DeleteIdsIn, DeleteIdsOut, // delete entities by id
		HttpResponse> // httpResult
		
		implements InterfaceEntityCommandHttpController<Entity, Id, QueryIdIn, // save
				SaveEntityIn, SaveEntityOut, // save a entity
				SaveEntitiesIn, SaveEntitiesOut, // save entities
				// update
				UpdateEntityIn, UpdateEntityOut, // update a entity
				UpdateEntitiesIn, UpdateEntitiesOut, // update entities
				// delete entity
				DeleteEntityIn, DeleteEntityOut, // delete a entity
				DeleteEntitiesIn, DeleteEntitiesOut, // delete entities
				// delete by id
				DeleteIdIn, DeleteIdOut, // delete a entity by id
				DeleteIdsIn, DeleteIdsOut, // delete entities by id
				HttpResponse> { // httpResult
    
    protected final Function<SaveEntityOut, HttpResponse> createResponsePost;
    
    protected final Function<UpdateEntityOut, HttpResponse> createResponsePut;
    
    protected final Function<UpdateEntityOut, HttpResponse> createResponsePatch;
    
    protected final Function<DeleteIdOut, HttpResponse> createResponseDelete;
    
    protected final BiFunction<List<JsonPatch>, QueryIdIn, UpdateEntityIn> applyPatchToJObject;
    
    protected final InterfaceEntityCommandFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> entityCommandFacade;
    
    protected EntityCommandHttpController(final EntityCommandHttpControllerBuilder<Entity, Id, QueryIdIn, OneResult, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut, HttpResponse> builder) {
	//
	this.createResponsePost = builder.createResponsePost;
	this.createResponsePut = builder.createResponsePut;
	this.createResponsePatch = builder.createResponsePatch;
	this.createResponseDelete = builder.createResponseDelete;
	this.applyPatchToJObject = builder.applyPatchToJObject;
	//
	this.entityCommandFacade = builder.entityCommandFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpResponse post(final SaveEntityIn saveEntityIn, final HttpServletRequest request, final HttpServletResponse response) {

	final var saveResult = entityCommandFacade.save(saveEntityIn);
	
//	final var url = request.getRequestURI();
//	response.addHeader("Location", url + "/" + entityResult.getId());
	response.setStatus(201);
	
	return createResponsePost.apply(saveResult);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public HttpResponse delete(final DeleteIdIn deleteIdIn, final HttpServletRequest request, final HttpServletResponse response) {
	
	final var deleteResult = entityCommandFacade.deleteBy(deleteIdIn);

	response.setStatus(204);
	
	return createResponseDelete.apply(deleteResult);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public HttpResponse put(final QueryIdIn id, final UpdateEntityIn updateEntityIn, final HttpServletRequest request, final HttpServletResponse response) {
	
	final var updateResult = entityCommandFacade.update(updateEntityIn);

	response.setStatus(204);
	
	return createResponsePut.apply(updateResult);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public HttpResponse patch( 
		    final QueryIdIn id, // id
		    final List<JsonPatch> jsonPatchs, // fields
		    final HttpServletRequest request, // request http
		    final HttpServletResponse response) { // response http
	
	final var entityPatched = applyPatchToJObject.apply(jsonPatchs, id);
	
	final var updateResult = entityCommandFacade.update(entityPatched);
	
	response.setStatus(204);
	
	return createResponsePatch.apply(updateResult);
    }

    /**
     * @param jsonPatchs
     * @param targetObject
     * @return
     */
//    protected final UpdateEntityIn applyPatchToJObject(final List<JsonPatch> jsonPatchs, final OneResult targetObject) {
//
//	final var toReplace = jsonPatchs.stream()
//			.filter(jsonPatch -> jsonPatch.op().equals(REPLACE))
//			.collect(toMap(JsonPatch::path, JsonPatch::value));
//
//	toReplace.forEach((key, value) -> {
//	    final var field = FieldUtils.getField(targetObject.getClass(), key, true);
//	    try {
//		FieldUtils.writeField(field, targetObject, value, true);
//	    } catch (final IllegalAccessException ex) {
//		throw new IllegalArgumentException(ex);
//	    }
//	});
//
//	return getUpdateEntityIn(targetObject);
//    }
}
