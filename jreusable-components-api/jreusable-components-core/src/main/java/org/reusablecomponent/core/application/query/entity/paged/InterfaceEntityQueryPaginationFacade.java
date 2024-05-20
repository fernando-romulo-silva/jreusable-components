package org.reusablecomponent.core.application.query.entity.paged;

import org.reusablecomponent.core.application.base.InterfaceEntityBaseFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

/**
 * @param <Entity>
 * @param <Id>
 * @param <OneResult>
 * @param <MultiplePagedResult>
 * @param <Pageable>
 * @param <Sort>
 */
public interface InterfaceEntityQueryPaginationFacade<Entity extends AbstractEntity<Id>, Id, // basic
		// results
		OneResult, // one result type
		MultiplePagedResult, // multiple result type
		// Pagination
		Pageable, // pageable type
		Sort> // sort type
		extends InterfaceEntityBaseFacade<Entity, Id> {

    /**
     * @param directives
     * @return
     */
    MultiplePagedResult findAll(final Pageable pageable, final Object... directives);

    /**
     * @param sort
     * @return
     */
    OneResult findFirst(final Sort sort);
}
