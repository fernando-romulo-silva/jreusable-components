package org.reusablecomponent.core.application.query.entity.paged;

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
public interface InterfaceEntityQueryPaginationSpecificationFacade<Entity extends AbstractEntity<Id>, Id, // basic
		// results
		OneResult, 
		MultiplePagedResult, 
		// Pagination
		Pageable, Sort, 
		// Specification
		Specification> 
	// Base
	extends InterfaceEntityBaseFacade<Entity, Id> {

    /**
     * @param pageable
     * @param specification
     * @param directives
     * @return
     */
    MultiplePagedResult findBy(final Pageable pageable, final Specification specification, final Object... directives);

    /**
     * @param specification
     * @return
     */
    OneResult findOneBy(final Specification specification, final Sort sort);
}