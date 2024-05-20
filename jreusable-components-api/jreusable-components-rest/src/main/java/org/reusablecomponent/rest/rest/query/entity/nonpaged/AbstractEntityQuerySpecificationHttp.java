package org.reusablecomponent.rest.rest.query.entity.nonpaged;

import org.reusablecomponent.core.application.query.entity.nonpaged.InterfaceEntityQuerySpecificationFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.rest.rest.query.base.AbstractEntityQueryBaseHttpController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * @param <Entity>
 * @param <Id>
 * @param <Specification>
 */
public abstract class AbstractEntityQuerySpecificationHttp <Entity extends AbstractEntity<Id>, Id,
		QueryIdIn,
		// results
		OneResult, // One result type
		MultiplePagedResult, // multiple result type
		CountResult, // count result type
		ExistsResult, // exists result type
		// specification
		Specification, // query specification
		HttpResponse> // http Response

	extends AbstractEntityQueryBaseHttpController<QueryIdIn, OneResult, MultiplePagedResult, CountResult, ExistsResult, HttpResponse> implements InterfaceEntityQuerySpecificationHttp<Entity, Id, QueryIdIn, OneResult, MultiplePagedResult, CountResult, ExistsResult, Specification, HttpResponse> {
    
    protected InterfaceEntityQuerySpecificationFacade<Entity, Id, OneResult, MultiplePagedResult, CountResult, ExistsResult, Specification> entityQuerySpecificationFacade;

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpResponse getBy(final Specification specification, final HttpServletRequest request, final HttpServletResponse response) {

	final var result = entityQuerySpecificationFacade.findBy(specification, request.getParameterMap());
	response.setStatus(200);
	
	return createResponseGetMultiple(result);
    }
}
