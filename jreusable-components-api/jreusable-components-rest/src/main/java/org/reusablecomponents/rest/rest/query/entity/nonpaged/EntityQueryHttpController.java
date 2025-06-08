package org.reusablecomponents.rest.rest.query.entity.nonpaged;

import org.reusablecomponents.base.core.application.query.entity.simple.InterfaceQueryFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.rest.rest.query.entity.base.EntityQueryBaseHttpController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;

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
public class EntityQueryHttpController<Entity extends AbstractEntity<Id>, Id, //
		QueryIdIn, // by id arg
		OneResult, // One result type
		MultipleResult, // multiple result type
		CountResult, // count result type
		ExistsResult, //
		HttpResponseVoid, HttpResponseOne, HttpResponseMultiple>
		//
		extends EntityQueryBaseHttpController<QueryIdIn, ExistsResult, OneResult, HttpResponseVoid, HttpResponseOne>
		implements
		InterfaceEntityQueryHttpController<QueryIdIn, HttpResponseVoid, HttpResponseOne, HttpResponseMultiple> {

	private static final Logger LOGGER = LoggerFactory.getLogger(EntityQueryHttpController.class);

	protected final InterfaceQueryFacade<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> entityQueryFacade;

	protected final Function<MultipleResult, HttpResponseMultiple> createResponseGetMultipleFunction;

	protected EntityQueryHttpController(
			final EntityQueryHttpControllerBuilder<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult, HttpResponseVoid, HttpResponseOne, HttpResponseMultiple> builder) {

		super(builder.entityQueryFacade, builder.createResponseGetOneFunction, builder.createResponseHeadFunction);

		this.createResponseGetMultipleFunction = builder.createResponseGetMultipleFunction;

		this.entityQueryFacade = builder.entityQueryFacade;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HttpResponseMultiple getAll(final HttpServletRequest request, final HttpServletResponse response) {

		final var directives = request.getParameterMap();

		LOGGER.debug("Getting all entities, directives '{}'", directives);

		final var result = entityQueryFacade.findAll(directives);

		final var finalResult = createResponseGetMultipleFunction.apply(result);

		LOGGER.debug("Got all entities, directives '{}'", directives);

		return finalResult;
	}

	/**
	 * {@inheritDoc}
	 */
	public HttpResponseVoid headAll(final HttpServletRequest request, final HttpServletResponse response) {

		LOGGER.debug("Check if there are any entities");

		final var result = entityQueryFacade.existsAll();

		final var finalResult = createResponseHeadFunction.apply(result);

		LOGGER.debug("Checked if there are any entities, result '{}'", finalResult);

		return finalResult;
	}
}
