package org.reusablecomponents.base.core.application.mix.entity;

import org.reusablecomponents.base.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;

import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 * @param <OneResult>
 * @param <MultipleResult>
 * @param <VoidResult>
 * @param <ExistsResult>
 */
public abstract class AbstractEntityCommonFacade<Entity extends AbstractEntity<Id>, Id, // basic
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

    protected AbstractEntityCommonFacade(
            @NotNull final InterfaceEntityCommandFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> entityCommandFacade) {
        super();
        this.entityCommandFacade = entityCommandFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SaveEntityOut save(final SaveEntityIn saveEntityIn, final Object... directives) {
        return entityCommandFacade.save(saveEntityIn, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SaveEntitiesOut saveAll(final SaveEntitiesIn saveEntitiesIn, final Object... directives) {
        return entityCommandFacade.saveAll(saveEntitiesIn, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UpdateEntityOut update(final UpdateEntityIn updateEntityIn, final Object... directives) {
        return entityCommandFacade.update(updateEntityIn, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UpdateEntitiesOut updateAll(final UpdateEntitiesIn updateEntitiesIn, final Object... directives) {
        return entityCommandFacade.updateAll(updateEntitiesIn, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeleteEntityOut delete(final DeleteEntityIn deleteEntityIn, final Object... directives) {
        return entityCommandFacade.delete(deleteEntityIn, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeleteEntitiesOut deleteAll(final DeleteEntitiesIn deleteEntitiesIn, final Object... directives) {
        return entityCommandFacade.deleteAll(deleteEntitiesIn, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeleteIdOut deleteBy(final DeleteIdIn deleteIdIn, final Object... directives) {
        return entityCommandFacade.deleteBy(deleteIdIn, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeleteIdsOut deleteAllBy(DeleteIdsIn deleteIdsIn, final Object... directives) {
        return entityCommandFacade.deleteAllBy(deleteIdsIn, directives);
    }
}
