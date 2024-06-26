package org.reusablecomponents.rest.rest.query.entity.base;

import org.reusablecomponents.rest.infra.openapi.annotations.method.EntityRestGetByIdOpenApi;
import org.reusablecomponents.rest.infra.openapi.annotations.method.EntityRestHeadByIdOpenApi;
import org.reusablecomponents.rest.rest.base.InterfaceEntityBaseHttpController;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public interface InterfaceEntityQueryBaseController<QueryIdIn, HttpResponseVoid, HttpResponseOne> extends InterfaceEntityBaseHttpController {

    @EntityRestGetByIdOpenApi
    HttpResponseOne get(
		    @Parameter(description = "The entity id's", example = "1000")
		    final QueryIdIn queryIdIn, 
		    final HttpServletRequest request,
		    final HttpServletResponse response);
    
    @EntityRestHeadByIdOpenApi
    HttpResponseVoid head(
		    @Parameter(description = "The entity id's", example = "1000")
		    final QueryIdIn queryIdIn, 
		    final HttpServletRequest request,
		    final HttpServletResponse response);
}