package org.reusablecomponent.core.application.query.entity.paged;

import java.util.Map;

import org.reusablecomponent.core.application.base.InterfaceEntityBaseFacade;
import org.reusablecomponent.core.domain.AbstractEntity;


/**
 * @param <Entity>
 * @param <Id>
 * @param <OneResult>
 * @param <ExistsResult>
 * @param <CountResult>
 * @param <MultiplePagedResult>
 * @param <Pageable>
 * @param <Sort>
 * @param <Specification>
 */
public interface InterfaceEntityQueryPaginationSpecificationFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification> 
	extends InterfaceEntityBaseFacade<Entity, Id> {

    /**
     * @param pageable
     * @param specification
     * @param directives
     * @return
     */
    MultiplePagedResult findBy(final Pageable pageable, final Specification specification, final Map<String, String[]> directives);

    /**
     * @param specification
     * @return
     */
    OneResult findBy(final Specification specification, final Sort sort);
}