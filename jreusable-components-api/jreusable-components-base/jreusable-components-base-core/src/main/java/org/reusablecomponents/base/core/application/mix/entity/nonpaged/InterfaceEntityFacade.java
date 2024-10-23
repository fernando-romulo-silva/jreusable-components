package org.reusablecomponents.base.core.application.mix.entity.nonpaged;

import org.reusablecomponents.base.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.InterfaceEntityQueryFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;

/**
 * @param <Entity>
 * @param <Id>
 * @param <OneResultCommand>
 * @param <OneResultQuery>
 * @param <MultipleResult>
 * @param <CountResult>
 * @param <ExistsResult>
 * @param <VoidResult>
 */
public interface InterfaceEntityFacade<Entity extends AbstractEntity<Id>, Id, // basic
        // ------------ command
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
        DeleteIdsIn, DeleteIdsOut, // delete entities by id
        // ------------ query
        QueryIdIn, // by id arg
        OneResult, // One result type
        MultipleResult, // multiple result type
        CountResult, // count result type
        ExistsResult> // boolean result type
        // command
        extends InterfaceEntityCommandFacade<Entity, Id, // default
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
                DeleteIdsIn, DeleteIdsOut>, // delete entities by id
        // query
        InterfaceEntityQueryFacade<Entity, Id, // default
                QueryIdIn, // by id arg
                OneResult, // One result type
                MultipleResult, // multiple result type
                CountResult, // count result type
                ExistsResult> { // boolean result type

    /**
     * {@inheritDoc}
     */
    @Override
    default SaveEntityOut save(final SaveEntityIn saveEntityIn, final Object... directives) {
        return getEntityCommandFacade().save(saveEntityIn, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default SaveEntitiesOut saveAll(SaveEntitiesIn saveEntitiesIn, final Object... directives) {
        return getEntityCommandFacade().saveAll(saveEntitiesIn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default UpdateEntityOut update(final UpdateEntityIn updateEntityIn, final Object... directives) {
        return getEntityCommandFacade().update(updateEntityIn, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default UpdateEntitiesOut updateAll(final UpdateEntitiesIn updateEntitiesIn, final Object... directives) {
        return getEntityCommandFacade().updateAll(updateEntitiesIn, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default DeleteEntityOut delete(final DeleteEntityIn deleteEntityIn, final Object... directives) {
        return getEntityCommandFacade().delete(deleteEntityIn, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default DeleteEntitiesOut deleteAll(final DeleteEntitiesIn deleteEntitiesIn, final Object... directives) {
        return getEntityCommandFacade().deleteAll(deleteEntitiesIn, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default DeleteIdOut deleteBy(final DeleteIdIn deleteIdIn, final Object... directives) {
        return getEntityCommandFacade().deleteBy(deleteIdIn, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default DeleteIdsOut deleteAllBy(final DeleteIdsIn deleteIdsIn, final Object... directives) {
        return getEntityCommandFacade().deleteAllBy(deleteIdsIn, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default OneResult findBy(final QueryIdIn queryIdIn, final Object... directives) {
        return getEntityQueryFacade().findBy(queryIdIn, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default MultipleResult findAll(final Object... directives) {
        return getEntityQueryFacade().findAll(directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default ExistsResult existsBy(final QueryIdIn queryIdIn) {
        return getEntityQueryFacade().existsBy(queryIdIn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default CountResult countAll() {
        return getEntityQueryFacade().countAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default ExistsResult existsAll() {
        return getEntityQueryFacade().existsAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Class<Entity> getEntityClazz() {
        return getEntityCommandFacade().getEntityClazz();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Class<Id> getIdClazz() {
        return getEntityCommandFacade().getIdClazz();
    }

    InterfaceEntityCommandFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> getEntityCommandFacade();

    InterfaceEntityQueryFacade<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> getEntityQueryFacade();

}
