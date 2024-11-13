package org.reusablecomponents.base.core.application.query.entity.paged;

import org.reusablecomponents.base.core.application.base.InterfaceEntityBaseFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;

/**
 * Interface responsible for establishing contracts to retrieve objects, using
 * pagination.
 * 
 * @param <Entity>              The facade entity type
 * @param <Id>                  The facade entity id type
 * 
 * @param <OneResult>           The one-result type
 * @param <MultiplePagedResult> The multiple-result type
 * @param <Pageable>            The query result controll
 * 
 * @param <Sort>                The query result order
 */
public non-sealed interface InterfaceEntityQueryPaginationFacade<Entity extends AbstractEntity<Id>, Id, //
    OneResult, //
    MultiplePagedResult, //
    Pageable, //
    Sort> //
    extends InterfaceEntityBaseFacade<Entity, Id> {

  /**
   * Find and retrieve all objects using pagination
   * 
   * @param pageable   Object {@code Pageable} used to controll the query's result
   * @param directives Params used to configure the query's result
   * 
   * @return Return a {@code MultipleResult} object
   */
  MultiplePagedResult findAll(final Pageable pageable, final Object... directives);

  /**
   * Find and retrieve the first {@code OneResult}
   * 
   * @param sort       Object {@code Sort} used to order the query
   * @param directives Params used to configure the query's result
   * 
   * @return Return a {@code OneResult} object
   */
  OneResult findOne(final Sort sort, final Object... directives);
}
