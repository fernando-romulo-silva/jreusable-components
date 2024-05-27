package org.reusablecomponent.core.application.command.entity;

import org.reusablecomponent.core.application.base.InterfaceEntityBaseFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

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
     * @param entity
     * @return
     */
    SaveEntityOut save(final SaveEntityIn saveEntityIn);

    /**
     * @param entities
     * @return
     */
    SaveEntitiesOut saveAll(final SaveEntitiesIn saveEntitiesIn);

    // --------------------------------------------------------------------------------

    /**
     * @param entity
     * @return
     */
    UpdateEntityOut update(final UpdateEntityIn updateEntityIn);

    /**
     * @param entities
     * @return
     */
    UpdateEntitiesOut updateAll(final UpdateEntitiesIn updateEntitiesIn);

    // --------------------------------------------------------------------------------

    /**
     * @param entity
     * @return
     */
    DeleteEntityOut delete(final DeleteEntityIn deleteEntityIn);

    /**
     * @param entities
     */
    DeleteEntitiesOut deleteAll(final DeleteEntitiesIn deleteEntitiesIn);

    /**
     * @param id
     */
    DeleteIdOut deleteBy(final DeleteIdIn deleteIdIn);

    /**
     * @param ids
     */
    DeleteIdsOut deleteAllBy(final DeleteIdsIn deleteIdsIn);

}