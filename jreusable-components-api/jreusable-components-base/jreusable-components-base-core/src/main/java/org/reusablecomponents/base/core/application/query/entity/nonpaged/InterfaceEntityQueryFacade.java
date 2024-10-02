package org.reusablecomponents.base.core.application.query.entity.nonpaged;

import org.reusablecomponents.base.core.application.base.InterfaceEntityBaseFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.common.BaseApplicationException;
import org.reusablecomponents.base.core.infra.exception.common.ElementWithIdNotFoundException;

/**
 * Interface responsible for establishing contracts to retrieve objects, common
 * operations to all projects.
 * 
 * @param <Entity>
 * @param <Id>
 * @param <QueryIdIn>
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
   * Find and retrieve a {@code OneResult} object by id
   * 
   * @param queryIdIn  The entity id
   * @param directives Params used to help to retrieve objects
   * 
   * @throws NullPointerException           If the parameter 'queryIdIn' is null
   * @throws ElementWithIdNotFoundException If you try to retrieve an entity that
   *                                        doesn't exist
   * @throws BaseApplicationException       If an unidentified error happened
   * 
   * @return Return a {@code OneResult} object
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