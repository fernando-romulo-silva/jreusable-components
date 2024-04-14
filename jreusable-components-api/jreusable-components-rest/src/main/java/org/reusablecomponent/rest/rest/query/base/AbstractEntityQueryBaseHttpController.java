package org.reusablecomponent.rest.rest.query.base;

import org.apache.commons.lang3.StringUtils;
import org.reusablecomponent.rest.rest.base.AbstractEntityBaseHttpController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @param <Entity>
 * @param <Id>
 * @param <OneResult>
 * @param <MultipleResult>
 * @param <ExistsResult>
 * @param <CountResult>
 */
public abstract class AbstractEntityQueryBaseHttpController <Id, OneResult, MultipleResult, CountResult, ExistsResult, HttpResponse> 
	extends AbstractEntityBaseHttpController<Id, OneResult>
	implements InterfaceEntityQueryBaseController<Id, HttpResponse> {
    
    /**
     * {@inheritDoc}
     */
    @Override
    public HttpResponse get( //
		    final Id id,
		    final HttpServletRequest request,
		    final HttpServletResponse response) {

	response.setStatus(200);
	
	if (isHttpHead(request)) {
	    
	    final var result = existsById(id);
	    return createResponseHead(result);
	     
	 } else {
	     
	     final var result = findById(id);
	     return createResponseGetOne(result);
	 }
    }
    
    
    /**
     * @param request
     * @return
     */
    protected final boolean isHttpHead(final HttpServletRequest request) {
        return StringUtils.equalsIgnoreCase("HEAD", request.getMethod());
    }
    
    /**
     * @param multipleResult
     * @return
     */
    protected abstract HttpResponse createResponseGetMultiple(final MultipleResult multipleResult);
    
    /**
     * @param oneResult
     * @return
     */
    protected abstract HttpResponse createResponseGetOne(final OneResult oneResult);
    
    /**
     * @param existsResult
     * @return
     */
    protected abstract HttpResponse createResponseHead(final ExistsResult existsResult);
    
    /**
     * @param id
     * @return
     */
    protected abstract ExistsResult existsById(final Id id);
    
    /**
     * @param <Response>
     * @param exists
     * @return
     */
    protected abstract ExistsResult createExistsResult(final CountResult exists); 

}
