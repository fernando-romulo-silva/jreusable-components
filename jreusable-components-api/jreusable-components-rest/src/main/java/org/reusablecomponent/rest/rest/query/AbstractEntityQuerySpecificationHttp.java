package org.reusablecomponent.rest.rest.query;

import java.util.Optional;

import org.apache.commons.collections4.IterableUtils;
import org.reusablecomponent.core.application.query.entity.nonpaged.InterfaceEntityQuerySpecificationFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.rest.infra.openapi.annotations.method.EntityRestGetOpenApi;
import org.reusablecomponent.rest.rest.query.base.AbstractEntityQueryBaseHttpController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * @param <Entity>
 * @param <Id>
 * @param <Specification>
 */
public abstract class AbstractEntityQuerySpecificationHttp <Entity extends AbstractEntity<Id>, Id, Specification, HttpResponse> 
	extends AbstractEntityQueryBaseHttpController<Id, Optional<Entity>, Iterable<Entity>, Long, Boolean, HttpResponse> {
    
    protected InterfaceEntityQuerySpecificationFacade<Entity, Id, Optional<Entity>, Iterable<Entity>, Long, Boolean, Specification> entityQuerySpecificationFacade;

    /**
     * @param <Response>
     * @param request
     * @param response
     * @return
     */
    @EntityRestGetOpenApi
    public HttpResponse getBy(final Specification specification, final HttpServletRequest request, final HttpServletResponse response) {

	final var result = entityQuerySpecificationFacade.findBy(specification, request.getParameterMap());
	response.setStatus(200);
	
	return createResponseGetMultiple(IterableUtils.toList(result));
    }
}
