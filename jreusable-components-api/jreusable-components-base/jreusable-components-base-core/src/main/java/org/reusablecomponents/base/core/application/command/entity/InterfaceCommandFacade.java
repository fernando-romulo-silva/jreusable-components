package org.reusablecomponents.base.core.application.command.entity;

import static org.reusablecomponents.base.core.infra.constants.ExceptionMessages.NULL_POINTER_EXCEPTION_MSG;

import org.reusablecomponents.base.core.application.base.InterfaceBaseFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.common.BaseApplicationException;
import org.reusablecomponents.base.core.infra.exception.common.ElementAlreadyExistsException;
import org.reusablecomponents.base.core.infra.exception.common.ElementConflictException;
import org.reusablecomponents.base.core.infra.exception.common.ElementInvalidException;
import org.reusablecomponents.base.core.infra.exception.common.ElementNotFoundException;
import org.reusablecomponents.base.core.infra.exception.common.ElementWithIdNotFoundException;

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
public non-sealed interface InterfaceCommandFacade<Entity extends AbstractEntity<Id>, Id, // basic
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
                extends InterfaceBaseFacade<Entity, Id> {

        /**
         * Stores a {@code SaveEntityIn} object on the persistence mechanism and returns
         * a {@code SaveEntityOut} object, an updated object.
         * 
         * @param saveEntityIn The object you want to save on the persistence mechanism
         * 
         * @param directives   Objects used to configure the save operation
         * 
         * @return The object you saved and updated with the persistence mechanism
         * @throws NullPointerException          If the parameter is null
         * @throws ElementAlreadyExistsException If you try store the entity with same
         *                                       {@code Id}
         * @throws ElementInvalidException       If the entity has constraints errors
         * @throws BaseApplicationException      If an unidentified error happened
         * 
         * @return SaveEntityOut The persistence mechanism resulted in a save operation
         */
        @Valid
        @NotNull(message = NULL_POINTER_EXCEPTION_MSG)
        SaveEntityOut save(
                        @NotNull(message = NULL_POINTER_EXCEPTION_MSG) final SaveEntityIn saveEntityIn,
                        @Nullable final Object... directives);

        /**
         * Save a group of entities at the same time.
         * The batch version of {@link #save(E e) save method}.
         * 
         * @param saveEntitiesIn The objects you want to save on the persistence
         *                       mechanism
         * 
         * @param directives     Objects used to configure the save all action
         * 
         * @throws NullPointerException          If the parameter is null
         * @throws ElementAlreadyExistsException If you try store an entity with same
         *                                       {@code Id}
         * @throws ElementInvalidException       If an entity has constraints errors
         * @throws BaseApplicationException      If an unidentified error happened
         * 
         * @return An a collection of updated object with the persistence mechanism
         */
        @Valid
        @NotNull(message = NULL_POINTER_EXCEPTION_MSG)
        SaveEntitiesOut saveAll(
                        @NotNull(message = NULL_POINTER_EXCEPTION_MSG) final SaveEntitiesIn saveEntitiesIn,
                        @Nullable final Object... directives);

        /**
         * Update a {@code UpdateEntityIn} object on the persistence mechanism and
         * returns a {@code UpdateEntityOut} object, an updated object.
         * 
         * @param updateEntityIn The object you want to save on the persistence
         *                       mechanism
         * @param directives     Objects used to configure the save all action
         * 
         * @throws NullPointerException     If the parameter is null
         * @throws ElementNotFoundException If you try update an entity that doesn't
         *                                  exist
         * @throws ElementInvalidException  If the entity has constraints errors
         * @throws BaseApplicationException If an unidentified error happened
         * 
         * @return An updated object
         */
        @Valid
        @NotNull(message = NULL_POINTER_EXCEPTION_MSG)
        UpdateEntityOut update(
                        @NotNull(message = NULL_POINTER_EXCEPTION_MSG) final UpdateEntityIn updateEntityIn,
                        @Nullable final Object... directives);

        /**
         * Update a group of entities at the same time.
         * The batch version of {@link #update(E e) update method}.
         * 
         * @param updateEntitiesIn The objects you want to update on the persistence
         *                         mechanism
         * @param directives       Objects used to configure the update all action
         * 
         * @throws NullPointerException     If the parameter is null
         * @throws ElementNotFoundException If you try update an entity that doesn't
         *                                  exist
         * @throws ElementInvalidException  If an entity has constraints errors
         * @throws BaseApplicationException If an unidentified error happened
         * 
         * @return An a group of updated objects with the persistence mechanism
         */
        @Valid
        @NotNull(message = NULL_POINTER_EXCEPTION_MSG)
        UpdateEntitiesOut updateAll(
                        @NotNull(message = NULL_POINTER_EXCEPTION_MSG) final UpdateEntitiesIn updateEntitiesIn,
                        @Nullable final Object... directives);

        /**
         * Delete a {@code DeleteEntityIn} object on the persistence mechanism and
         * returns a {@code DeleteEntityOut} object, an deleted object.
         * 
         * @param deleteEntityIn The object you want to delete on the persistence
         *                       mechanism
         * @param directives     Objects used to configure the delete action
         * 
         * @throws NullPointerException     If the parameter is null
         * @throws ElementNotFoundException If you try delete an entity that doesn't
         *                                  exist
         * @throws ElementConflictException If the object has an unbroken connection
         *                                  with another one
         * @throws ElementInvalidException  If the entity has constraints errors
         * @throws BaseApplicationException If an unidentified error happened
         * 
         * @return An deleted object or status or even null.
         */
        @Valid
        @Nullable
        DeleteEntityOut delete(
                        @NotNull(message = NULL_POINTER_EXCEPTION_MSG) final DeleteEntityIn deleteEntityIn,
                        @Nullable final Object... directives);

        /**
         * Delete a group of entities at the same time.
         * The batch version of {@link #delete(E e) delete method}.
         * 
         * @param deleteEntitiesIn The object you want to delete on the persistence
         *                         mechanism
         * @param directives       Objects used to configure the delete all action
         * 
         * @throws NullPointerException     If the parameter is null
         * @throws ElementNotFoundException If you try delete an entity that doesn't
         *                                  exist
         * @throws ElementConflictException If the object has an unbroken connection
         *                                  with another one
         * @throws ElementInvalidException  If the entity has constraints errors
         * @throws BaseApplicationException If an unidentified error happened
         * 
         * @return An deleted object or status or even null.
         */
        @Valid
        @Nullable
        DeleteEntitiesOut deleteAll(
                        @NotNull(message = NULL_POINTER_EXCEPTION_MSG) final DeleteEntitiesIn deleteEntitiesIn,
                        @Nullable final Object... directives);

        /**
         * Delete an entity using a {@code DeleteIdIn} object on the persistence
         * mechanism and return a {@code DeleteIdOut} object, a status operation object.
         * 
         * @param deleteIdIn The entity id
         * @param directives Objects used to configure the delete by action
         * 
         * @throws NullPointerException           If the parameter is null
         * @throws ElementWithIdNotFoundException If you try update an entity that
         *                                        doesn't exist
         * @throws ElementConflictException       If the object has an unbroken
         *                                        connection
         *                                        with another one
         * @throws ElementInvalidException        If the entity has constraints errors
         * @throws BaseApplicationException       If an unidentified error happened
         * 
         * @return An deleted object or status or even null.
         */
        @Valid
        @Nullable
        DeleteIdOut deleteBy(
                        @NotNull(message = NULL_POINTER_EXCEPTION_MSG) final DeleteIdIn deleteIdIn,
                        @Nullable final Object... directives);

        /**
         * Delete a group of entities at the same time using its ids.
         * The batch version of {@link #deleteBy(Id id) delete method}.
         * 
         * @param DeleteIdsIn The entity ids
         * @param directives  Objects used to configure the delete by action
         * 
         * @throws NullPointerException           If the parameter is null
         * @throws ElementWithIdNotFoundException If you try delete an entity that
         *                                        doesn't exist
         * @throws ElementConflictException       If the object has an unbroken
         *                                        connection with another one
         * @throws ElementInvalidException        If the entity has constraints errors
         * @throws BaseApplicationException       If an unidentified error happened
         * 
         * @return An deleted object or status or even null.
         */
        @Valid
        @Nullable
        DeleteIdsOut deleteAllBy(
                        @NotNull(message = NULL_POINTER_EXCEPTION_MSG) @Valid final DeleteIdsIn deleteIdsIn,
                        @Nullable final Object... directives);

}