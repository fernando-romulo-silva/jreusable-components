package org.reusablecomponents.rest.rest.command;

import java.util.List;

import org.reusablecomponents.core.domain.AbstractEntity;
import org.reusablecomponents.rest.infra.jsonpath.JsonPatch;
import org.reusablecomponents.rest.infra.openapi.annotations.CreateEntityRequestBody;
import org.reusablecomponents.rest.infra.openapi.annotations.UpdateEntityRequestBody;
import org.reusablecomponents.rest.infra.openapi.annotations.method.EntityRestDeleteOpenApi;
import org.reusablecomponents.rest.infra.openapi.annotations.method.EntityRestPatchOpenApi;
import org.reusablecomponents.rest.infra.openapi.annotations.method.OpenApiEntityRestPost;
import org.reusablecomponents.rest.infra.openapi.annotations.method.OpenApiEntityRestPut;
import org.reusablecomponents.rest.rest.base.InterfaceEntityBaseHttpController;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

/**
 * @param <Entity>
 * @param <Id>
 */
public interface InterfaceEntityCommandHttpController<Entity extends AbstractEntity<Id>, Id, // basic
		QueryIdIn, 

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
	extends InterfaceEntityBaseHttpController {

    /**
     * @param <Response>
     * @param entity
     * @param request
     * @param response
     * @return
     */
    @OpenApiEntityRestPost
    HttpResponse post(
		    @Valid //
		    @RequestBody //
		    @CreateEntityRequestBody //
		    final SaveEntityIn saveEntityIn, 
		    
		    final HttpServletRequest request, 
		    final HttpServletResponse response);
    
    /**
     * Delete a entity
     * 
     * @param id The entity's id
     */
    @EntityRestDeleteOpenApi
    HttpResponse delete( //
		    //
		    @Parameter(description = "The entity id's", example = "1000") //
		    final DeleteIdIn deleteIdIn, //
		    //
		    final HttpServletRequest request, //
		    final HttpServletResponse response);

    /**
     * @param <Response>
     * @param id
     * @param entity
     * @param request
     * @param response
     * @return
     */
    @OpenApiEntityRestPut
    HttpResponse put( //
		    //
		    @Parameter(description = "The entity id's", example = "1000") //
		    final QueryIdIn id, //

		    @Valid //
		    @RequestBody //
		    @UpdateEntityRequestBody //
		    final UpdateEntityIn updateEntityIn, //
		    //
		    final HttpServletRequest request, //
		    final HttpServletResponse response);

    /**
     * @param <Response>
     * @param id
     * @param jsonPatchs
     * @param request
     * @param response
     * @return
     */
    @EntityRestPatchOpenApi
    HttpResponse patch(
		    //
		    @Parameter(description = "The entity type id's", example = "1000") //
		    final QueryIdIn id, 
		    //
		    @Parameter(description = "A json path structure", 
		    	       example = """
		     				 [
		     				   { 
		     				     "op":"replace",
		     				     "path":"name",
		     				     "value":"new name"
		     				   },
		     				   { 
		     				     "op":"remove",
		     				     "path":"name"
		     				   }		     				   
		     				 ]
		     				""") //
		    @RequestBody //
		    final List<JsonPatch> jsonPatchs, //
		    //
		    final HttpServletRequest request, //
		    final HttpServletResponse response);

}