package org.reusablecomponents.base.core.application.command.entity;

import org.reusablecomponents.base.core.application.base.InterfaceEntityBaseFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.common.BaseApplicationException;
import org.reusablecomponents.base.core.infra.exception.common.ElementAlreadyExistsException;
import org.reusablecomponents.base.core.infra.exception.common.ElementInvalidException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public non-sealed interface InterfaceEntityCommandFacade<Entity extends AbstractEntity<Id>, Id, // basic
    // save
    SaveEntityIn, SaveEntityOut, // save a entity
    SaveEntitiesIn, SaveEntitiesOut, // save entities
    // update
    UpdateEntityIn, UpdateEntityOut, // update a entity
    UpdateEntitiesIn, UpdateEntitiesOut, // update entities
    // delete entity
    DeleteEntityIn, DeleteEntityOut, // delete a entity
    DeleteEntitiesIn, DeleteEntitiesOut, // delete entities
    // delete by id
    DeleteIdIn, DeleteIdOut, // delete a entity by id
    DeleteIdsIn, DeleteIdsOut> // delete entities by id
    // from base
    extends InterfaceEntityBaseFacade<Entity, Id> {

  // --------------------------------------------------------------------------------

  /**
   * Stores a {@code SaveEntityIn} object on the persistence mechanism and returns
   * a {@code SaveEntityOut} object, an updated object.
   * 
   * @param saveEntityIn The object you want to save on the persistence mechanism
   * @return The object you saved and updated with the persistence mechanism
   * @throws NullPointerException          if the parameter is null
   * @throws ElementAlreadyExistsException if you try store the entity with same
   *                                       {@code Id}
   * @throws ElementInvalidException       if the entity has constraints errors
   * @throws BaseApplicationException      if a generic error happened
   */
  @NotNull(message = NULL_POINTER_EXCEPTION_MSG)
  SaveEntityOut save(@NotNull(message = NULL_POINTER_EXCEPTION_MSG) final SaveEntityIn saveEntityIn);

  /**
   * Save a group of entities at the same time.
   * The collection version of {@link #save(E e) Save}.
   * 
   * @param saveEntitiesIn The objects you want to save on the persistence
   *                       mechanism
   * @return The object you saved and updated with the persistence mechanism
   * @throws NullPointerException          if the parameter is null
   * @throws ElementAlreadyExistsException if you try store an entity with same
   *                                       {@code Id}
   * @throws ElementInvalidException       if an entity has constraints errors
   * @throws BaseApplicationException      if a generic error happened
   */
  @NotNull(message = NULL_POINTER_EXCEPTION_MSG)
  SaveEntitiesOut saveAll(@NotNull(message = NULL_POINTER_EXCEPTION_MSG) final SaveEntitiesIn saveEntitiesIn);

  // --------------------------------------------------------------------------------

  /**
   * @param updateEntityIn
   * 
   * @return
   */
  UpdateEntityOut update(@NotNull final UpdateEntityIn updateEntityIn);

  /**
   * @param updateEntitiesIn
   * @return
   */
  UpdateEntitiesOut updateAll(final UpdateEntitiesIn updateEntitiesIn);

  // --------------------------------------------------------------------------------

  /**
   * @param deleteEntityIn
   * @return
   */
  DeleteEntityOut delete(@NotNull final DeleteEntityIn deleteEntityIn);

  /**
   * @param deleteEntitiesIn
   * @return
   */
  DeleteEntitiesOut deleteAll(@NotNull final DeleteEntitiesIn deleteEntitiesIn);

  /**
   * @param deleteIdIn
   * @return
   */
  DeleteIdOut deleteBy(@NotNull final DeleteIdIn deleteIdIn);

  /**
   * @param deleteIdsIn
   * @return
   */
  DeleteIdsOut deleteAllBy(@Valid final DeleteIdsIn deleteIdsIn);

}