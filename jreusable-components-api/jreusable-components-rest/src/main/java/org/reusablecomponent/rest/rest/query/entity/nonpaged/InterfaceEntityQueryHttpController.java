package org.reusablecomponent.rest.rest.query.entity.nonpaged;

import org.reusablecomponent.rest.infra.openapi.annotations.method.EntityRestGetOpenApi;
import org.reusablecomponent.rest.rest.query.entity.base.InterfaceEntityQueryBaseController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface InterfaceEntityQueryHttpController<QueryIdIn, HttpResponseVoid, HttpResponseOne, HttpResponseMultiple>
	extends InterfaceEntityQueryBaseController<QueryIdIn, HttpResponseVoid, HttpResponseOne> {
    
    /**
     * @param request
     * @param response
     * @return
     */
    @EntityRestGetOpenApi
    HttpResponseMultiple getAll(final HttpServletRequest request, final HttpServletResponse response);
    
    /**
     * @param request
     * @param response
     * @return
     */
    @EntityRestGetOpenApi
    HttpResponseVoid headAll(final HttpServletRequest request, final HttpServletResponse response);

}