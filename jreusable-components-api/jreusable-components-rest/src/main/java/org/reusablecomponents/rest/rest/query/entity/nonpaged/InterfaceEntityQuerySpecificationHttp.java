package org.reusablecomponents.rest.rest.query.entity.nonpaged;

import org.reusablecomponents.rest.infra.openapi.annotations.method.EntityRestGetOpenApi;
import org.reusablecomponents.rest.infra.openapi.annotations.method.EntityRestHeadOpenApi;
import org.reusablecomponents.rest.rest.query.entity.base.InterfaceEntityQueryBaseController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface InterfaceEntityQuerySpecificationHttp <QueryIdIn, HttpResponseVoid, HttpResponseOne, HttpResponseMultiple, Specification> 
	extends InterfaceEntityQueryBaseController<QueryIdIn, HttpResponseVoid, HttpResponseOne> {
    
    /**
     * @param specification
     * @param request
     * @param response
     * @return
     */
    @EntityRestGetOpenApi
    HttpResponseMultiple getBy(final Specification specification, final HttpServletRequest request, final HttpServletResponse response);
    
    /**
     * @param specification
     * @param request
     * @param response
     * @return
     */
    @EntityRestHeadOpenApi
    HttpResponseVoid headBy(final Specification specification, final HttpServletRequest request, final HttpServletResponse response);

}