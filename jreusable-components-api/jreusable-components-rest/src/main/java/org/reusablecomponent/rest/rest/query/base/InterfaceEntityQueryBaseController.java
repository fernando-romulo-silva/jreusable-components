package org.reusablecomponent.rest.rest.query.base;

import org.reusablecomponent.rest.infra.openapi.annotations.method.EntityRestGetByIdOpenApi;
import org.reusablecomponent.rest.rest.base.InterfaceEntityBaseHttpController;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @param <Entity>
 * @param <Id>
 * @param <OneResult>
 * @param <MultipleResult>
 * @param <CountResult>
 * @param <ExistsResult>
 */
public interface InterfaceEntityQueryBaseController<Id, HttpResponse> extends InterfaceEntityBaseHttpController {

    /**
     * @param <Response>
     * @param id
     * @param request
     * @param response
     * @return
     */
    @EntityRestGetByIdOpenApi
    HttpResponse get(
		    @Parameter(description = "The entity id's", example = "1000")
		    final Id id, 
		    final HttpServletRequest request,
		    final HttpServletResponse response);
    
}