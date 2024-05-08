package org.reusablecomponent.core.application.command.entity;

import org.reusablecomponent.core.application.base.InterfaceEntityBaseFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

/**
 * @param <Entity>
 * @param <Id>
 * @param <SaveEntityIn>
 * @param <SaveEntityOut>
 * @param <SaveEntitiesIn>
 * @param <SaveEntitiesOut>
 * @param <UpdateEntityIn>
 * @param <UpdateEntityOut>
 * @param <UpdateEntitiesIn>
 * @param <UpdateEntitiesOut>
 * @param <DeleteEntityIn>
 * @param <DeleteEntityOut>
 * @param <DeleteEntitisIn>
 * @param <DeleteEntitiesOut>
 * @param <DeleteIdIn>
 * @param <DeleteIdOut>
 * @param <DeleteIdsIn>
 * @param <DeleteIdsOut>
 */
public interface InterfaceEntityCommandFacade< // generics 
		// default
		Entity extends AbstractEntity<Id>, Id, // basic
		// save
		SaveEntityIn, SaveEntityOut, // save a entity
		SaveEntitiesIn, SaveEntitiesOut, // save entities
		// update
		UpdateEntityIn, UpdateEntityOut, // update a entity
		UpdateEntitiesIn, UpdateEntitiesOut, // update entities
		// delete entity
		DeleteEntityIn, DeleteEntityOut, // delete a entity
		DeleteEntitisIn, DeleteEntitiesOut, // delete entities
		// delete by id
		DeleteIdIn, DeleteIdOut, // delete a entity by id
		DeleteIdsIn, DeleteIdsOut> // delete entities by id
		// from base
		extends InterfaceEntityBaseFacade<Entity, Id> {

    // --------------------------------------------------------------------------------

    /**
     * @param entity
     * @return
     */
    SaveEntityOut save(final SaveEntityIn entity);

    /**
     * @param entities
     * @return
     */
    SaveEntitiesOut saveAll(final SaveEntitiesIn entities);

    // --------------------------------------------------------------------------------

    /**
     * @param entity
     * @return
     */
    UpdateEntityOut update(final UpdateEntityIn entity);

    /**
     * @param entities
     * @return
     */
    UpdateEntitiesOut updateAll(final UpdateEntitiesIn entities);

    // --------------------------------------------------------------------------------

    /**
     * @param entity
     * @return
     */
    DeleteEntityOut delete(final DeleteEntityIn entity);

    /**
     * @param entities
     */
    DeleteEntitiesOut deleteAll(final DeleteEntitisIn entities);

    /**
     * @param id
     */
    DeleteIdOut deleteBy(final DeleteIdIn id);

    /**
     * @param ids
     */
    DeleteIdsOut deleteAllBy(final DeleteIdsIn ids);

}