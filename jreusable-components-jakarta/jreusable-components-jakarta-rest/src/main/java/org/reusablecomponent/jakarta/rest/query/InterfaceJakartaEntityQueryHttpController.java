package org.reusablecomponent.jakarta.rest.query;


import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.MediaType.APPLICATION_XML;

import org.reusablecomponent.domain.AbstractEntity;
import org.reusablecomponent.rest.query.InterfaceEntityQueryHttpController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * @param <Entity>
 * @param <Id>
 */
public interface InterfaceJakartaEntityQueryHttpController<Entity extends AbstractEntity<Id>, Id> 
	extends InterfaceEntityQueryHttpController<Id, Response> {

    /**
     * {@inheritDoc}
     */
    @GET
    @Path("/{id}")
    @Produces(value = {APPLICATION_JSON, APPLICATION_XML})
    Response get(@PathParam("id") final Id id, final HttpServletRequest request, final HttpServletResponse response);

    /**
     * {@inheritDoc}
     */
    @GET
    @Produces(value = {APPLICATION_JSON, APPLICATION_XML})
    Response getAll(final HttpServletRequest request, final HttpServletResponse response);

}