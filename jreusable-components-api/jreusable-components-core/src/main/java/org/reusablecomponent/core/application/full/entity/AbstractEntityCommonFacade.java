package org.reusablecomponent.core.application.full.entity;

import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 * @param <OneResult>
 * @param <MultipleResult>
 * @param <VoidResult>
 * @param <ExistsResult>
 */
public class AbstractEntityCommonFacade<Entity extends AbstractEntity<Id>, Id, // basic
		// save
		SaveEntityIn, SaveEntityOut, // save a entity
		SaveEntitiesIn, SaveEntitiesOut, // save entities
		// update
		UpdateEntityIn, UpdateEntityOut, // update a entity
		UpdateEntitiesIn, UpdateEntitiesOut, // update entities
		// delete
		DeleteEntityIn, DeleteEntityOut, // delete a entity
		DeleteEntitiesIn, DeleteEntitiesOut, // delete entities
		// delete by id
		DeleteIdIn, DeleteIdOut, // delete a entity by id
		DeleteIdsIn, DeleteIdsOut> // delete entities by id

		// Interface command facade
		implements InterfaceEntityCommandFacade<Entity, Id, // basic
				// save
				SaveEntityIn, SaveEntityOut, // save a entity
				SaveEntitiesIn, SaveEntitiesOut, // save entities
				// update
				UpdateEntityIn, UpdateEntityOut, // update a entity
				UpdateEntitiesIn, UpdateEntitiesOut, // update entities
				// delete
				DeleteEntityIn, DeleteEntityOut, // delete a entity
				DeleteEntitiesIn, DeleteEntitiesOut, // delete entities
				// delete by id
				DeleteIdIn, DeleteIdOut, // delete a entity by id
				DeleteIdsIn, DeleteIdsOut> { // delete entities by id

    protected final InterfaceEntityCommandFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> entityCommandFacade;

    protected AbstractEntityCommonFacade(@NotNull final InterfaceEntityCommandFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> entityCommandFacade) {
	super();
	this.entityCommandFacade = entityCommandFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SaveEntityOut save(final SaveEntityIn saveEntityIn) {
	return entityCommandFacade.save(saveEntityIn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SaveEntitiesOut saveAll(final SaveEntitiesIn saveEntitiesIn) {
	return entityCommandFacade.saveAll(saveEntitiesIn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UpdateEntityOut update(final UpdateEntityIn updateEntityIn) {
	return entityCommandFacade.update(updateEntityIn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UpdateEntitiesOut updateAll(final UpdateEntitiesIn updateEntitiesIn) {
	return entityCommandFacade.updateAll(updateEntitiesIn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeleteEntityOut delete(final DeleteEntityIn deleteEntityIn) {
	return entityCommandFacade.delete(deleteEntityIn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeleteEntitiesOut deleteAll(final DeleteEntitiesIn deleteEntitiesIn) {
	return entityCommandFacade.deleteAll(deleteEntitiesIn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeleteIdOut deleteBy(final DeleteIdIn deleteIdIn) {
	return entityCommandFacade.deleteBy(deleteIdIn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeleteIdsOut deleteAllBy(DeleteIdsIn deleteIdsIn) {
	return entityCommandFacade.deleteAllBy(deleteIdsIn);
    }
}
