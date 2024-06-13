package org.reusablecomponents.rest.rest.query.entity.nonpaged;

import org.reusablecomponents.core.application.query.entity.nonpaged.InterfaceEntityQuerySpecificationFacade;
import org.reusablecomponents.core.domain.AbstractEntity;
import org.reusablecomponents.rest.rest.query.entity.base.EntityQueryBaseHttpController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * @param <Entity>
 * @param <Id>
 * @param <Specification>
 */
public class EntityQuerySpecificationHttp <Entity extends AbstractEntity<Id>, Id,
		QueryIdIn,
		// results
		OneResult, // One result type
		MultipleResult, // multiple result type
		CountResult, // count result type
		ExistsResult, // exists result type
		// specification
		Specification, // query specification
		HttpResponseVoid, HttpResponseOne, HttpResponseMultiple> // http Response
		//
		extends EntityQueryBaseHttpController<QueryIdIn, ExistsResult, OneResult, HttpResponseVoid, HttpResponseOne>
		implements InterfaceEntityQuerySpecificationHttp<QueryIdIn, HttpResponseVoid, HttpResponseOne, HttpResponseMultiple, Specification> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityQuerySpecificationHttp.class);
    
    protected final InterfaceEntityQuerySpecificationFacade<Entity, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification> entityQuerySpecificationFacade;
    
    protected final Function<MultipleResult, HttpResponseMultiple> createResponseGetMultipleFunction;
    

    protected EntityQuerySpecificationHttp(final EntityQuerySpecificationHttpBuilder<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult, Specification, HttpResponseVoid, HttpResponseOne, HttpResponseMultiple> builder) {
	
	super(builder.entityQueryFacade, builder.createResponseGetOneFunction, builder.createResponseHeadFunction);
	
	this.createResponseGetMultipleFunction = builder.createResponseGetMultipleFunction;
	this.entityQuerySpecificationFacade = builder.entityQuerySpecificationFacade;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public HttpResponseMultiple getBy(final Specification specification, final HttpServletRequest request, final HttpServletResponse response) {

	final var directives = request.getParameterMap();
	
	LOGGER.debug("Getting entities by '{}', directives '{}'", specification, directives);
	
	final var findByResult = entityQuerySpecificationFacade.findBy(specification, directives);
	
	final var finalResult = createResponseGetMultipleFunction.apply(findByResult);
	
	LOGGER.debug("Got entities by '{}', directives '{}'", specification, directives);
	
	return finalResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpResponseVoid headBy(final Specification specification, final HttpServletRequest request, final HttpServletResponse response) {
	
	LOGGER.debug("Check if there are any entities by '{}'", specification);
	
	final var existsResult = entityQuerySpecificationFacade.existsBy(specification);
	
	final var finalResult = createResponseHeadFunction.apply(existsResult);
	
	LOGGER.debug("Checked if there are any entities by '{}', result '{}'", specification, finalResult);
	
	return finalResult;
    }
}
