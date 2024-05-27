package org.reusablecomponent.rest.rest.query.entity.base;

import org.reusablecomponent.core.application.query.entity.nonpaged.InterfaceEntityQueryFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EntityQueryBaseHttpController<QueryIdIn, ExistsResult, OneResult, HttpResponseVoid, HttpResponseOne> 
	implements InterfaceEntityQueryBaseController<QueryIdIn, HttpResponseVoid, HttpResponseOne> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityQueryBaseHttpController.class);
    
    private final InterfaceEntityQueryFacade<? , ?, QueryIdIn, OneResult, ?, ?, ExistsResult> entityQueryFacade;
    
    protected final Function<OneResult, HttpResponseOne> createResponseGetOneFunction;
    
    protected final Function<ExistsResult, HttpResponseVoid> createResponseHeadOneFunction;
    
    
    protected EntityQueryBaseHttpController(
		    final InterfaceEntityQueryFacade<?, ?, QueryIdIn, OneResult, ?, ?, ExistsResult> entityQueryFacade, 
		    final Function<OneResult, HttpResponseOne> createResponseGetOneFunction,
		    final Function<ExistsResult, HttpResponseVoid> createResponseHeadOneFunction) {
	super();
	this.entityQueryFacade = entityQueryFacade;
	this.createResponseGetOneFunction = createResponseGetOneFunction;
	this.createResponseHeadOneFunction = createResponseHeadOneFunction;
    }

    // ------------------------------------------------------------------------
    
    protected QueryIdIn preGet(final QueryIdIn queryIdIn) {
	return queryIdIn;
    }
   
    /**
     * {@inheritDoc}
     */
    @Override    
    public HttpResponseOne get(final QueryIdIn queryIdIn, final HttpServletRequest request, final HttpServletResponse response) {
	
	LOGGER.debug("Geting entity by '{}'", queryIdIn);
	
	final var finalQueryIdIn = preGet(queryIdIn);
	
	final var result = entityQueryFacade.findBy(finalQueryIdIn);
	
	final var finalResult = createResponseGetOneFunction.apply(result);
	
	LOGGER.debug("Got entity by '{}', result '{}'", queryIdIn, finalResult);
	
	return finalResult;
    }
    
    
    // ------------------------------------------------------------------------
    
    protected QueryIdIn preHead(final QueryIdIn queryIdIn) {
	return queryIdIn;
    }    
    
    /**
     * {@inheritDoc}
     */
    @Override    
    public HttpResponseVoid head(final QueryIdIn queryIdIn, final HttpServletRequest request, final HttpServletResponse response) {
	
	LOGGER.debug("Check entity by '{}'", queryIdIn);
	
	final var finalQueryIdIn = preHead(queryIdIn);
	
	final var result = entityQueryFacade.existsBy(finalQueryIdIn);
	
	final var finalResult = createResponseHeadOneFunction.apply(result);
	
	LOGGER.debug("Checked entity by '{}', result '{}'", queryIdIn, finalResult);
	
	return finalResult;
    }
}
