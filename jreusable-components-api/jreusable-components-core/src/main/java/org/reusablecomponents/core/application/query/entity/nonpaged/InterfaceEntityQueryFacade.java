package org.reusablecomponents.core.application.query.entity.nonpaged;

import org.reusablecomponents.core.application.base.InterfaceEntityBaseFacade;
import org.reusablecomponents.core.domain.AbstractEntity;

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
public non-sealed interface InterfaceEntityQueryFacade<Entity extends AbstractEntity<Id>, Id, // basic
		QueryIdIn, // by id arg type
		OneResult, // One result type
		MultipleResult, // multiple result type
		CountResult, // count result type
		ExistsResult> // exists result type
		// interface basic
		extends InterfaceEntityBaseFacade<Entity, Id> {

    /**
     * @param queryIdIn
     * @param directives
     * @throws ElementWithIdNotFoundException
     * @return
     */
    OneResult findBy(final QueryIdIn queryIdIn, final Object... directives);

    /**
     * @param directives
     * @return
     */
    MultipleResult findAll(final Object... directives);

    /**
     * @param id
     * @return
     */
    ExistsResult existsBy(final QueryIdIn queryIdIn);
    
    /**
     * @return
     */
    ExistsResult existsAll();

    /**
     *
     * @return
     */
    CountResult countAll();
}