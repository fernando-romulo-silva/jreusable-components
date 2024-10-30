package org.reusablecomponents.base.core.application.query.entity.nonpaged;

import static org.reusablecomponents.base.core.infra.constants.ExceptionMessages.NULL_POINTER_EXCEPTION_MSG;

import org.reusablecomponents.base.core.application.base.InterfaceEntityBaseFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.common.BaseApplicationException;
import org.reusablecomponents.base.core.infra.exception.common.ElementWithIdNotFoundException;

import jakarta.validation.constraints.NotNull;

/**
 * Interface responsible for establishing contracts to retrieve objects, common
 * operations to all projects.
 * 
 * @param <Entity>         The facade entity type
 * @param <Id>             The facade entity id type
 * 
 * @param <QueryIdIn>      The entity Id used in the query
 * 
 * @param <OneResult>      The one-result type
 * @param <MultipleResult> The multiple-result type
 * @param <CountResult>    The count-result type
 * @param <ExistsResult>   The exist-result type
 */
public non-sealed interface InterfaceEntityQueryFacade<Entity extends AbstractEntity<Id>, Id, // basic
    QueryIdIn, //
    OneResult, //
    MultipleResult, //
    CountResult, //
    ExistsResult> //
    extends InterfaceEntityBaseFacade<Entity, Id> {

  /**
   * Find and retrieve a {@code OneResult} object by id
   * 
   * @param queryIdIn  The entity id
   * @param directives Params used to configure the query's result
   * 
   * @throws NullPointerException           If the parameter 'queryIdIn' is null
   * @throws ElementWithIdNotFoundException If you try to retrieve an entity that
   *                                        doesn't exist
   * @throws BaseApplicationException       If an unidentified error happened
   * 
   * @return Return a {@code OneResult} object
   */
  OneResult findBy(
      @NotNull(message = NULL_POINTER_EXCEPTION_MSG) final QueryIdIn queryIdIn,
      final Object... directives);

  /**
   * Find and retrieve all objects, be carefull with it.
   * 
   * @param directives Params used to configure the query's result
   * 
   * @throws NullPointerException     If the parameter 'queryIdIn' is null
   * @throws BaseApplicationException If an unidentified error happened
   * 
   * @return Return a {@code OneResult} object
   */
  MultipleResult findAll(final Object... directives);

  /**
   * Check if an entity exists with the provided ID.
   * 
   * @param queryIdIn The entity id
   * 
   * @throws NullPointerException     If the parameter 'queryIdIn' is null
   * 
   * @throws BaseApplicationException If an unidentified error happened
   * 
   * @return Return a {@code ExistsResult} object
   */
  ExistsResult existsBy(final QueryIdIn queryIdIn);

  /**
   * Check if there exists at least an entity.
   * 
   * @throws BaseApplicationException If an unidentified error happened
   * 
   * @return Return a {@code ExistsResult} object
   */
  ExistsResult existsAll();

  /**
   * Count how many entities there are.
   * 
   * @throws BaseApplicationException If an unidentified error happened
   * 
   * @return Return a {@code CountResult} object
   */
  CountResult countAll();
}