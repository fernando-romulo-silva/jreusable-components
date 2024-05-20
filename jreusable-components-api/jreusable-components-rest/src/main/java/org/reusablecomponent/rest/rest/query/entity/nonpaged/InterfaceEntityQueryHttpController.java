package org.reusablecomponent.rest.rest.query.entity.nonpaged;

import org.reusablecomponent.rest.infra.openapi.annotations.method.EntityRestGetByIdOpenApi;
import org.reusablecomponent.rest.infra.openapi.annotations.method.EntityRestGetOpenApi;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @param <Id>
 * @param <HttpResponse>
 */
public interface InterfaceEntityQueryHttpController<QueryIdIn, HttpResponseVoid, HttpResponseOne, HttpResponseMultiple> {
    
    /**
     * @param <Response>
     * @param id
     * @param request
     * @param response
     * @return
     */
    @EntityRestGetByIdOpenApi
    HttpResponseOne get(
		    @Parameter(description = "The entity id's", example = "1000")
		    final QueryIdIn id, 
		    final HttpServletRequest request,
		    final HttpServletResponse response);
    
    /**
     * @param request
     * @param response
     * @return
     */
    @EntityRestGetOpenApi
    HttpResponseMultiple getAll(final HttpServletRequest request, final HttpServletResponse response);
    
    /**
     * @param <Response>
     * @param id
     * @param request
     * @param response
     * @return
     */
    @EntityRestGetByIdOpenApi
    HttpResponseVoid head(
		    @Parameter(description = "The entity id's", example = "1000")
		    final QueryIdIn id, 
		    final HttpServletRequest request,
		    final HttpServletResponse response);    
    
    /**
     * @param request
     * @param response
     * @return
     */
    @EntityRestGetOpenApi
    HttpResponseVoid headAll(final HttpServletRequest request, final HttpServletResponse response);

}