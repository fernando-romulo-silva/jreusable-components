package org.reusablecomponent.rest.rest.command;

import java.util.List;

import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.rest.infra.jsonpath.JsonPatch;
import org.reusablecomponent.rest.infra.openapi.annotations.CreateEntityRequestBody;
import org.reusablecomponent.rest.infra.openapi.annotations.UpdateEntityRequestBody;
import org.reusablecomponent.rest.infra.openapi.annotations.method.EntityRestDeleteOpenApi;
import org.reusablecomponent.rest.infra.openapi.annotations.method.EntityRestPatchOpenApi;
import org.reusablecomponent.rest.infra.openapi.annotations.method.OpenApiEntityRestPost;
import org.reusablecomponent.rest.infra.openapi.annotations.method.OpenApiEntityRestPut;
import org.reusablecomponent.rest.rest.base.InterfaceEntityBaseHttpController;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

/**
 * @param <Entity>
 * @param <Id>
 */
public interface InterfaceEntityCommandHttpController<Entity extends AbstractEntity<Id>, Id, HttpResponse> extends InterfaceEntityBaseHttpController {

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
		    final Entity entity, 
		    
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
		    final Id id, //
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
		    final Id id, //

		    @Valid //
		    @RequestBody //
		    @UpdateEntityRequestBody //
		    final Entity entity, //
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
		    final Id id, 
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