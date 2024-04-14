package org.reusablecomponent.rest.rest.query;

import org.reusablecomponent.core.application.query.entity.paged.InterfaceEntityQueryPaginationSpecificationFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.rest.infra.openapi.annotations.method.EntityRestGetOpenApi;
import org.reusablecomponent.rest.rest.query.base.AbstractEntityQueryBaseHttpController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @param <Entity>
 * @param <Id>
 * @param <OneResult>
 * @param <MultiplePagedResult>
 * @param <CountResult>
 * @param <ExistsResult>
 * @param <Pageable>
 * @param <Sort>
 * @param <Specification>
 */
public abstract class AbstractEntityQueryPaginationSpecificationHttp <Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, CountResult, ExistsResult, Response, Pageable, Sort, Specification> 
	extends AbstractEntityQueryBaseHttpController<Id, OneResult, MultiplePagedResult, CountResult, ExistsResult, Response> {
    
    protected InterfaceEntityQueryPaginationSpecificationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification> entityQueryFacade;
    
    /**
     * @param <Response>
     * @param request
     * @param response
     * @return
     */
    @EntityRestGetOpenApi
    public Response getAll(final Pageable pageable, final Specification specification, final HttpServletRequest request, final HttpServletResponse response) {

	response.setStatus(200);
	
	 if (isHttpHead(request)) {
	     
	     entityQueryFacade.findBy(pageable, specification, request.getParameterMap());
	     
	     final var result = createExistsResult(null);
	     return createResponseHead(result);
	     
	 } else {
	     
	     final var result = entityQueryFacade.findBy(null, null, request.getParameterMap());
	     return createResponseGetMultiple(result);
	 }
    }
    
    @Override
    public ExistsResult existsById(final Id id) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public OneResult findById(final Id id) {
	// TODO Auto-generated method stub
	return null;
    }

}
