package org.reusablecomponent.rest.rest.query;

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
public abstract class AbstractEntityQueryHttpController <Entity extends AbstractEntity<Id>, Id,  OneResult, MultipleResult, CountResult, ExistsResult, HttpResponse> 
        extends AbstractEntityQueryBaseHttpController<Id, OneResult, MultipleResult, CountResult, ExistsResult, HttpResponse> 
	implements InterfaceEntityQueryHttpController<Id, HttpResponse> {
    
    protected final InterfaceEntityQueryFacade<Entity, Id, OneResult, MultipleResult, CountResult, ExistsResult> interfaceEntityQueryFacade;
    
    /**
     * @param entityQueryFacade
     */
    protected AbstractEntityQueryHttpController(@NotNull final InterfaceEntityQueryFacade<Entity, Id, OneResult, MultipleResult, CountResult, ExistsResult> entityQueryFacade) {
	super();
	this.interfaceEntityQueryFacade = entityQueryFacade;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public HttpResponse getAll(final HttpServletRequest request, final HttpServletResponse response) {

	response.setStatus(200);
	
	 if (isHttpHead(request)) {
	     
	     final var result = createExistsResult(interfaceEntityQueryFacade.count());
	     return createResponseHead(result);
	     
	 } else {
	     
	     final var result = interfaceEntityQueryFacade.findAll(request.getParameterMap());
	     return createResponseGetMultiple(result);
	 }
    }
    
   
    /**
     * {@inheritDoc}
     */
    @Override
    public final OneResult findById(final Id id) {
	return interfaceEntityQueryFacade.findBy(id);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final ExistsResult existsById(final Id id) {
        return interfaceEntityQueryFacade.existsBy(id);
    }
}
