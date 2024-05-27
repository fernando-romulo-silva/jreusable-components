package org.reusablecomponent.rest.rest.command;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.rest.infra.jsonpath.JsonPatch;

public class EntityCommandHttpControllerBuilder<Entity extends AbstractEntity<Id>, Id, // basic
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
		HttpResponse> { // httpResult
    
    public Function<SaveEntityOut, HttpResponse> createResponsePost;
    
    public Function<UpdateEntityOut, HttpResponse> createResponsePut;
    
    public Function<UpdateEntityOut, HttpResponse> createResponsePatch;
    
    public Function<DeleteIdOut, HttpResponse> createResponseDelete;
    
    public BiFunction<List<JsonPatch>, QueryIdIn, UpdateEntityIn> applyPatchToJObject;
    
    public InterfaceEntityCommandFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> entityCommandFacade;

    public EntityCommandHttpControllerBuilder(
		    final Consumer<EntityCommandHttpControllerBuilder<Entity, Id, QueryIdIn, OneResult,
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
				    HttpResponse>> function) {
	super();
	
	// load the functions
	function.accept(this);

	checkNotNull(createResponsePost, "Please pass a non-null 'createResponsePost'");
	checkNotNull(createResponsePut, "Please pass a non-null 'createResponsePut'");
	checkNotNull(createResponsePatch, "Please pass a non-null 'createResponsePatch'");
	checkNotNull(createResponseDelete, "Please pass a non-null 'createResponseDelete'");
	checkNotNull(applyPatchToJObject, "Please pass a non-null 'applyPatchToJObject'");
	
	checkNotNull(entityCommandFacade, "Please pass a non-null 'entityCommandFacade'");
    }
    
}
