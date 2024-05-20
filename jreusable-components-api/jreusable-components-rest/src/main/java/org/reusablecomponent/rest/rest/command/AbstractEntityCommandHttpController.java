package org.reusablecomponent.rest.rest.command;

import static java.util.stream.Collectors.toMap;
import static org.reusablecomponent.rest.infra.jsonpath.JsonPatchOperation.REPLACE;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.rest.infra.jsonpath.JsonPatch;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;

public class AbstractEntityCommandHttpController<Entity extends AbstractEntity<Id>, Id, // basic

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

    protected BiFunction<QueryIdIn, Object[], OneResult> findByIdFunction;
    
    protected Function<SaveEntityOut, HttpResponse> createResponsePost;
    
    protected Function<UpdateEntityOut, HttpResponse> createResponsePut;
    
    protected Function<UpdateEntityOut, HttpResponse> createResponsePatch;
    
    protected Function<DeleteIdOut, HttpResponse> createResponseDelete;
    
    protected BiFunction<List<JsonPatch>, OneResult, UpdateEntityIn> applyPatchToJObject;
    
    protected final InterfaceEntityCommandFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> interfaceEntityCommandFacade;
    
    /**
     * @param interfaceEntityCommandFacade
     */
    protected AbstractEntityCommandHttpController(@NotNull final InterfaceEntityCommandFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> interfaceEntityCommandFacade) {
	super();
	this.interfaceEntityCommandFacade = interfaceEntityCommandFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpResponse post(final SaveEntityIn saveEntityIn, final HttpServletRequest request, final HttpServletResponse response) {

	final var saveResult = interfaceEntityCommandFacade.save(saveEntityIn);
	final var entityResult = getEntitySaveResult(saveResult);
	
	final var url = request.getRequestURI();
	response.addHeader("Location", url + "/" + entityResult.getId());
	response.setStatus(201);
	
	return createResponsePost.apply(saveResult);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public HttpResponse delete(final DeleteIdIn deleteIdIn, final HttpServletRequest request, final HttpServletResponse response) {
	
	response.setStatus(204);

	final var deleteResult = interfaceEntityCommandFacade.deleteBy(deleteIdIn);
	
	return createResponseDelete.apply(deleteResult);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public HttpResponse put(final QueryIdIn id, final UpdateEntityIn updateEntityIn, final HttpServletRequest request, final HttpServletResponse response) {
	
	final var updateResult = interfaceEntityCommandFacade.update(updateEntityIn);

	response.setStatus(204);
	
	return createResponsePut.apply(updateResult);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public HttpResponse patch(
		    final QueryIdIn id, 
		    final List<JsonPatch> jsonPatchs, //
		    final HttpServletRequest request, //
		    final HttpServletResponse response) {
	
	final var findByIdResult = findByIdFunction.apply(id, new Object[] {});
	
	final var entityPatched = applyPatchToJObject.apply(jsonPatchs, findByIdResult);
	
	final var updateResult = interfaceEntityCommandFacade.update(entityPatched);
	
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
    
    // ----------------    
    
    private Entity getEntitySaveResult(final SaveEntityOut saveEntityOut) {
	return null;
    }
    
    private UpdateEntityIn getUpdateEntityIn(final OneResult oneResult) {
	return null;
    }
    
    // ---------------- 
}
