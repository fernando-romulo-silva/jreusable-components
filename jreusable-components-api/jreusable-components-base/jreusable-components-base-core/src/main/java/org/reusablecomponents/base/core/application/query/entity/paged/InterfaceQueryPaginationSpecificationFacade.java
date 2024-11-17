package org.reusablecomponents.base.core.application.query.entity.paged;

import org.reusablecomponents.base.core.application.base.InterfaceBaseFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;

/**
 * Interface responsible for establishing contracts to retrieve objects, using
 * pagination and specification.
 * 
 * @param <Entity>              The facade entity type
 * @param <Id>                  The facade entity id type
 * 
 * @param <OneResult>           The one-result type
 * @param <MultiplePagedResult> The multiple-result type
 * @param <Pageable>            The query result controll
 * 
 * @param <Sort>                The query result order
 * @param <Specification>       The specification is a strutucte used to filter
 *                              queries
 */
public non-sealed interface InterfaceQueryPaginationSpecificationFacade<Entity extends AbstractEntity<Id>, Id, //
        OneResult, //
        MultiplePagedResult, //
        Pageable, //
        Sort, //
        Specification> //
        // Base
        extends InterfaceBaseFacade<Entity, Id> {

    /**
     * Find and retrieve entities filtered by specification using pagination
     * 
     * @param pageable      Object {@code Pageable} used to controll the query's
     *                      result
     * @param specification Object {@code Specification} used to filter query's
     *                      result
     * @param directives    Params used to configure the query's result
     * 
     * @return Return a {@code MultipleResult} object
     */
    MultiplePagedResult findBy(final Pageable pageable, final Specification specification, final Object... directives);

    /**
     * * Find and retrieve one entity filtered by specification using pagination
     * 
     * @param sort          Object {@code Sort} used to order the query
     * @param specification Object {@code Pageable} used to filter query's result
     * @param directives    Objects used to configure the query's result
     * 
     * @return Return a {@code OneResult} object
     */
    OneResult findOneBy(final Sort sort, final Specification specification, final Object... directives);
}