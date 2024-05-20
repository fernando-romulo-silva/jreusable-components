package org.reusablecomponent.rest.rest.query.entity.nonpaged;

import org.reusablecomponent.core.application.query.entity.nonpaged.InterfaceEntityQueryFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.rest.rest.query.base.AbstractEntityQueryBaseHttpController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 * @param <OneResult>
 * @param <MultipleResult>
 * @param <CountResult>
 * @param <ExistsResult>
 */
public abstract class AbstractEntityQueryHttpController<Entity extends AbstractEntity<Id>, Id, //
		// args
		QueryIdIn, // by id arg
		// results
		OneResult, // One result type
		MultipleResult, // multiple result type
		CountResult, // count result type
		ExistsResult, //
		HttpResponseVoid, HttpResponseOne, HttpResponseMultiple>
		//
		extends AbstractEntityQueryBaseHttpController<QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult, HttpResponseVoid, HttpResponseOne, HttpResponseMultiple>
		implements InterfaceEntityQueryHttpController<QueryIdIn, HttpResponseVoid, HttpResponseOne, HttpResponseMultiple> {

    protected final InterfaceEntityQueryFacade<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> entityQueryFacade;

    /**
     * @param entityQueryFacade
     */
    protected AbstractEntityQueryHttpController(@NotNull final InterfaceEntityQueryFacade<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> entityQueryFacade) {
	super();
	this.entityQueryFacade = entityQueryFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpResponseOne get(final QueryIdIn id, final HttpServletRequest request, final HttpServletResponse response) {

	response.setStatus(200);

	final var result = entityQueryFacade.findBy(id);
	return createResponseGetOne(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpResponseMultiple getAll(final HttpServletRequest request, final HttpServletResponse response) {

	response.setStatus(200);

	final var result = entityQueryFacade.findAll(request.getParameterMap());
	return createResponseGetMultiple(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpResponseVoid head(final QueryIdIn id, final HttpServletRequest request, final HttpServletResponse response) {

	response.setStatus(200);
	
	final var result = entityQueryFacade.existsBy(id);
	return createResponseHead(result);
    }

    /**
     * {@inheritDoc}
     */
    public HttpResponseVoid headAll(final HttpServletRequest request, final HttpServletResponse response) {

	response.setStatus(200);
	
	final var result = createExistsResult(entityQueryFacade.countAll());
	return createResponseHead(result);
    }
}
