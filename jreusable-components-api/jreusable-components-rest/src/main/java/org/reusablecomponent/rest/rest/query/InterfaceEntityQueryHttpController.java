package org.reusablecomponent.rest.rest.query;

import org.reusablecomponent.rest.infra.openapi.annotations.method.EntityRestGetOpenApi;
import org.reusablecomponent.rest.rest.query.base.InterfaceEntityQueryBaseController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @param <Id>
 * @param <HttpResponse>
 */
public interface InterfaceEntityQueryHttpController<Id, HttpResponse> extends InterfaceEntityQueryBaseController<Id, HttpResponse> {
    
    /**
     * @param request
     * @param response
     * @return
     */
    @EntityRestGetOpenApi
    HttpResponse getAll(final HttpServletRequest request, final HttpServletResponse response);

}