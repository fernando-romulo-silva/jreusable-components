package org.reusablecomponent.core.application.query.entity.nonpaged;

import org.reusablecomponent.core.application.base.InterfaceEntityBaseFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

/**
 * @param <Entity>
 * @param <Id>
 * @param <QueryIdIn>
 * @param <Directives>
 * @param <OneResult>
 * @param <MultipleResult>
 * @param <CountResult>
 * @param <ExistsResult>
 */
public interface InterfaceEntityQueryFacade
				<Entity extends AbstractEntity<Id>, Id,
				 QueryIdIn,
				 Directives,
				 OneResult, 
				 MultipleResult, 
				 CountResult, 
				 ExistsResult> //

extends InterfaceEntityBaseFacade<Entity, Id> {
    
    /**
     * @param queryIdIn
     * @param directives
     * @return
     */
    OneResult findBy(final QueryIdIn queryIdIn, final Directives directives);
    
    /**
     * @param directives
     * @return
     */
    MultipleResult findAll(final Directives directives);
    
    /**
     * @param id
     * @return
     */
    OneResult findBy(final QueryIdIn queryIdIn);
    
    /**
     * @param directives
     * @return
     */
    MultipleResult findAll();    

    /**
     * @param id
     * @return
     */
    ExistsResult existsBy(final QueryIdIn queryIdIn);
    
    /**
     *
     * @return
     */
    CountResult count();
}