package org.reusablecomponent.rest.rest.query.base;

/**
 * @param <Entity>
 * @param <Id>
 * @param <OneResult>
 * @param <MultipleResult>
 * @param <ExistsResult>
 * @param <CountResult>
 */
public abstract class AbstractEntityQueryBaseHttpController <QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult, HttpResponseVoid, HttpResponseOne, HttpResponseMultiple> {
    
    
    // TODO convert to functions, like command and query
    /**
     * @param multipleResult
     * @return
     */
    protected abstract HttpResponseMultiple createResponseGetMultiple(final MultipleResult multipleResult);
    
    /**
     * @param oneResult
     * @return
     */
    protected abstract HttpResponseOne createResponseGetOne(final OneResult oneResult);
    
    /**
     * @param existsResult
     * @return
     */
    protected abstract HttpResponseVoid createResponseHead(final ExistsResult existsResult);
    
    /**
     * @param queryIdIn
     * @return
     */
    protected abstract ExistsResult existsById(final QueryIdIn queryIdIn);
    
    /**
     * @param <Response>
     * @param exists
     * @return
     */
    protected abstract ExistsResult createExistsResult(final CountResult countResult); 

}
