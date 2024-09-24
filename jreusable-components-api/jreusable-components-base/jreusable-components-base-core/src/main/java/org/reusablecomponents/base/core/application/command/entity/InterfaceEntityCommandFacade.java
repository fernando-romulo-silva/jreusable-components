package org.reusablecomponents.base.core.application.command.entity;

import static org.reusablecomponents.base.core.infra.messages.SystemMessages.NULL_POINTER_EXCEPTION_MSG;

import org.reusablecomponents.base.core.application.base.InterfaceEntityBaseFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.common.BaseApplicationException;
import org.reusablecomponents.base.core.infra.exception.common.ElementAlreadyExistsException;
import org.reusablecomponents.base.core.infra.exception.common.ElementConflictException;
import org.reusablecomponents.base.core.infra.exception.common.ElementInvalidException;
import org.reusablecomponents.base.core.infra.exception.common.ElementNotFoundException;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Interface responsible for establishing contracts to create, update, and
 * delete entities, basic business operations to all projects.
 * 
 * 
 * @param <Entity>            The facade entity type
 * @param <Id>                The facade entity id type
 * 
 * @param <SaveEntityIn>      The save an entity operation input type
 * @param <SaveEntityOut>     The save an entity operation result type
 * @param <SaveEntitiesIn>    The save entities operation input type
 * @param <SaveEntitiesOut>   The save entities operation result type
 * 
 * @param <UpdateEntityIn>    The update an entity operation input type
 * @param <UpdateEntityOut>   The update an entity operation result type
 * @param <UpdateEntitiesIn>  The update entities operation input type
 * @param <UpdateEntitiesOut> The update entities operation result type
 * 
 * @param <DeleteEntityIn>    The delete an entity operation input type
 * @param <DeleteEntityOut>   The delete an entity operation result type
 * @param <DeleteEntitiesIn>  The delete entities operation input type
 * @param <DeleteEntitiesOut> The delete entities operation result type
 * 
 * @param <DeleteIdIn>        The delete an entity by id operation input type
 * @param <DeleteIdOut>       The delete an entity by id operation result type
 * @param <DeleteIdsIn>       The delete entities by ids operation input type
 * @param <DeleteIdsOut>      The delete entities by ids operation result type
 */
public non-sealed interface InterfaceEntityCommandFacade<Entity extends AbstractEntity<Id>, Id, // basic
    // save
    SaveEntityIn, SaveEntityOut, //
    SaveEntitiesIn, SaveEntitiesOut, //
    // update
    UpdateEntityIn, UpdateEntityOut, //
    UpdateEntitiesIn, UpdateEntitiesOut, //
    // delete entity
    DeleteEntityIn, DeleteEntityOut, //
    DeleteEntitiesIn, DeleteEntitiesOut, //
    // delete by id
    DeleteIdIn, DeleteIdOut, //
    DeleteIdsIn, DeleteIdsOut> //
    // from base
    extends InterfaceEntityBaseFacade<Entity, Id> {

  /**
   * Stores a {@code SaveEntityIn} object on the persistence mechanism and returns
   * a {@code SaveEntityOut} object, an updated object.
   * 
   * @param saveEntityIn  The object you want to save on the persistence mechanism
   * @param SaveEntityOut The persistence mechanism resulted in a save operation
   * 
   * @return The object you saved and updated with the persistence mechanism
   * @throws NullPointerException          If the parameter is null
   * @throws ElementAlreadyExistsException If you try store the entity with same
   *                                       {@code Id}
   * @throws ElementInvalidException       If the entity has constraints errors
   * @throws BaseApplicationException      If an unidentified error happened
   */
  @Valid
  @NotNull(message = NULL_POINTER_EXCEPTION_MSG)
  SaveEntityOut save(@NotNull(message = NULL_POINTER_EXCEPTION_MSG) final SaveEntityIn saveEntityIn);

  /**
   * Save a group of entities at the same time.
   * The collection version of {@link #save(E e) save method}.
   * 
   * @param saveEntitiesIn  The objects you want to save on the persistence
   *                        mechanism
   * @param SaveEntitiesOut The persistence mechanism resulted in a saveAll
   *                        operation
   * 
   * @return An a collection of updated object with the persistence mechanism
   * 
   * @throws NullPointerException          If the parameter is null
   * @throws ElementAlreadyExistsException If you try store an entity with same
   *                                       {@code Id}
   * @throws ElementInvalidException       If an entity has constraints errors
   * @throws BaseApplicationException      If an unidentified error happened
   */
  @Valid
  @NotNull(message = NULL_POINTER_EXCEPTION_MSG)
  SaveEntitiesOut saveAll(@NotNull(message = NULL_POINTER_EXCEPTION_MSG) final SaveEntitiesIn saveEntitiesIn);

  /**
   * Update a {@code UpdateEntityIn} object on the persistence mechanism and
   * returns a {@code UpdateEntityOut} object, an updated object.
   * 
   * @param updateEntityIn  The object you want to save on the persistence
   *                        mechanism
   * @param UpdateEntityOut The persistence mechanism resulted in a update
   *                        operation
   * 
   * @return An updated object
   * 
   * @throws NullPointerException     If the parameter is null
   * @throws ElementNotFoundException If you try update an entity that doesn't
   *                                  exist
   * @throws ElementInvalidException  If the entity has constraints errors
   * @throws BaseApplicationException If an unidentified error happened
   */
  @Valid
  @NotNull(message = NULL_POINTER_EXCEPTION_MSG)
  UpdateEntityOut update(@NotNull(message = NULL_POINTER_EXCEPTION_MSG) final UpdateEntityIn updateEntityIn);

  /**
   * Update a group of entities at the same time.
   * The collection version of {@link #update(E e) update method}.
   * 
   * @param UpdateEntitiesIn  The objects you want to update on the persistence
   *                          mechanism
   * @param UpdateEntitiesOut The persistence mechanism resulted in a updateAll
   *                          operation
   * 
   * @return An a group of updated objects with the persistence mechanism
   * 
   * @throws NullPointerException     If the parameter is null
   * @throws ElementNotFoundException If you try update an entity that doesn't
   *                                  exist
   * @throws ElementInvalidException  If an entity has constraints errors
   * @throws BaseApplicationException If an unidentified error happened
   */
  @Valid
  @NotNull(message = NULL_POINTER_EXCEPTION_MSG)
  UpdateEntitiesOut updateAll(@NotNull(message = NULL_POINTER_EXCEPTION_MSG) final UpdateEntitiesIn updateEntitiesIn);

  /**
   * Delete a {@code DeleteEntityIn} object on the persistence mechanism and
   * returns a {@code DeleteEntityOut} object, an deleted object.
   * 
   * @param DeleteEntityIn  The object you want to delete on the persistence
   *                        mechanism
   * @param DeleteEntityOut The persistence mechanism resulted in a delete
   *                        operation
   * 
   * @return An deleted object or status or even null.
   * 
   * @throws NullPointerException     If the parameter is null
   * @throws ElementNotFoundException If you try update an entity that doesn't
   *                                  exist
   * @throws ElementConflictException If the object has an unbroken connection
   *                                  with another one
   * @throws ElementInvalidException  If the entity has constraints errors
   * @throws BaseApplicationException If an unidentified error happened
   */
  @Valid
  @Nullable
  DeleteEntityOut delete(@NotNull(message = NULL_POINTER_EXCEPTION_MSG) final DeleteEntityIn deleteEntityIn);

  /**
   * Delete a group of entities at the same time.
   * The collection version of {@link #delete(E e) delete method}.
   * 
   * @param DeleteEntitiesIn  The object you want to delete on the persistence
   *                          mechanism
   * @param DeleteEntitiesOut The persistence mechanism resulted in a delete
   *                          operation
   * 
   * @return An deleted object or status or even null.
   * 
   * @throws NullPointerException     If the parameter is null
   * @throws ElementNotFoundException If you try update an entity that doesn't
   *                                  exist
   * @throws ElementConflictException If the object has an unbroken connection
   *                                  with another one
   * @throws ElementInvalidException  If the entity has constraints errors
   * @throws BaseApplicationException If an unidentified error happened
   */
  @Valid
  @Nullable
  DeleteEntitiesOut deleteAll(@NotNull(message = NULL_POINTER_EXCEPTION_MSG) final DeleteEntitiesIn deleteEntitiesIn);

  /**
   * @param deleteIdIn
   * @return
   */
  @Valid
  DeleteIdOut deleteBy(@NotNull(message = NULL_POINTER_EXCEPTION_MSG) final DeleteIdIn deleteIdIn);

  /**
   * @param deleteIdsIn
   * @return
   */
  @Valid
  DeleteIdsOut deleteAllBy(@NotNull(message = NULL_POINTER_EXCEPTION_MSG) @Valid final DeleteIdsIn deleteIdsIn);

}