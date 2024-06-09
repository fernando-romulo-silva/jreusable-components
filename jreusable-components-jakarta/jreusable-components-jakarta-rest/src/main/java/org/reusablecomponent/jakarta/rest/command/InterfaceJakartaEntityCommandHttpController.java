package org.reusablecomponent.jakarta.rest.command;


import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.MediaType.APPLICATION_XML;

import java.util.List;

import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.rest.infra.jsonpath.JsonPatch;
import org.reusablecomponent.rest.rest.command.InterfaceEntityCommandHttpController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * @param <Entity>
 * @param <Id>
 */
public interface InterfaceJakartaEntityCommandHttpController<Entity extends AbstractEntity<Id>, Id> 
	extends InterfaceEntityCommandHttpController<Entity, Id,  
				Id, // QueryIdIn
				// save
				Entity, Entity, // save a entity
				List<Entity>, List<Entity>, // save entities
				// update
				Entity, Entity, // update a entity
				List<Entity>, List<Entity>, // update entities
				// delete entity
				Entity, Void, // delete a entity
				List<Entity>, Void, // delete entities
				// delete by id
				Id, Void, // delete a entity by id
				List<Id>, Void, // delete entities by id	  
				Response> {

    /**
     * {@inheritDoc}
     */
    @POST
    @Produces(value = {APPLICATION_JSON, APPLICATION_XML})
    @Consumes(value = {APPLICATION_JSON, APPLICATION_XML})
    Response post(
		    final Entity entity, 
		    
		    final HttpServletRequest request, 
		    final HttpServletResponse response);

    /**
     * {@inheritDoc}
     */
    @PUT
    @Path("/{id}")
    @Produces(value = {APPLICATION_JSON, APPLICATION_XML})
    @Consumes(value = {APPLICATION_JSON, APPLICATION_XML})
    Response put(
		    @PathParam("id") 
		    final Id id, 
		    
		    final Entity entity, 
		    
		    final HttpServletRequest request, 
		    final HttpServletResponse response);

    /**
     * {@inheritDoc}
     */
    @PATCH
    @Path("/{id}")
    Response patch(
		    @PathParam("id") 
		    final Id id, 
		    
		    final List<JsonPatch> jsonPatchs,
		    final HttpServletRequest request, 
		    final HttpServletResponse response);

    /**
     * {@inheritDoc}
     */
    @DELETE
    @Path("/{id}")
    Response delete(
		    @PathParam("id") 
		    final Id id, 
		    
		    final HttpServletRequest request, 
		    final HttpServletResponse response);

}