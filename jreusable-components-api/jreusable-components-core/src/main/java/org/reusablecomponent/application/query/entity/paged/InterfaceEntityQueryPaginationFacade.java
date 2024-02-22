package org.reusablecomponent.application.query.entity.paged;

import java.util.Map;

import org.reusablecomponent.application.base.InterfaceEntityBaseFacade;
import org.reusablecomponent.domain.AbstractEntity;


/**
 * @param <Entity>
 * @param <Id>
 * @param <OneResult>
 * @param <MultiplePagedResult>
 * @param <Pageable>
 * @param <Sort>
 */
public interface InterfaceEntityQueryPaginationFacade <Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, Pageable, Sort> 
	extends InterfaceEntityBaseFacade<Entity, Id> {

    /**
     * @param directives
     * @return
     */
    MultiplePagedResult findAll(final Pageable pageable, final Map<String, String[]> directives);
      
    /**
     * @param sort
     * @return
     */
    OneResult findFirst(final Sort sort);
}
