package org.reusablecomponents.core.application.query.entity.paged;

import org.reusablecomponents.core.application.base.InterfaceEntityBaseFacade;
import org.reusablecomponents.core.domain.AbstractEntity;

/**
 * @param <Entity>
 * @param <Id>
 * @param <OneResult>
 * @param <MultiplePagedResult>
 * @param <Pageable>
 * @param <Sort>
 */
public non-sealed interface InterfaceEntityQueryPaginationFacade<Entity extends AbstractEntity<Id>, Id, // basic
		OneResult, // one result type
		MultiplePagedResult, // multiple result type
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
