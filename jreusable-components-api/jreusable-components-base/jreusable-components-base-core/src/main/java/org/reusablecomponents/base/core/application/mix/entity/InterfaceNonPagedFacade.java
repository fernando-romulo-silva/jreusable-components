package org.reusablecomponents.base.core.application.mix.entity;

import org.reusablecomponents.base.core.application.command.entity.InterfaceCommandFacade;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.InterfaceQueryFacade;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.InterfaceQuerySpecificationFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;

/**
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
 * 
 * @param <OneResult>
 * @param <MultipleResult>
 * @param <CountResult>
 * @param <ExistsResult>
 * @param <Specification>
 */
public interface InterfaceNonPagedFacade<Entity extends AbstractEntity<Id>, Id, // basic
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
        ExistsResult, // boolean result type
        Specification>
        // command
        extends InterfaceCommandFacade<Entity, Id, // default
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
        InterfaceQueryFacade<Entity, Id, // default
                QueryIdIn, // by id arg
                OneResult, // One result type
                MultipleResult, // multiple result type
                CountResult, // count result type
                ExistsResult>, // boolean result type
        // query
        InterfaceQuerySpecificationFacade<Entity, Id, //
                OneResult, //
                MultipleResult, //
                CountResult, //
                ExistsResult, //
                Specification> {

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
    default OneResult findById(final QueryIdIn queryIdIn, final Object... directives) {
        return getEntityQueryFacade().findById(queryIdIn, directives);
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
    default ExistsResult existsById(final QueryIdIn queryIdIn) {
        return getEntityQueryFacade().existsById(queryIdIn);
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
    default MultipleResult findBySpec(final Specification specification, final Object... directives) {
        return getEntityQuerySpecificationFacade().findBySpec(specification, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default OneResult findOneBySpec(final Specification specification, final Object... directives) {
        return getEntityQuerySpecificationFacade().findOneBySpec(specification, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default ExistsResult existsBySpec(final Specification specification) {
        return getEntityQuerySpecificationFacade().existsBySpec(specification);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default CountResult countBySpec(final Specification specification) {
        return getEntityQuerySpecificationFacade().countBySpec(specification);
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

    InterfaceCommandFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> getEntityCommandFacade();

    InterfaceQueryFacade<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> getEntityQueryFacade();

    InterfaceQuerySpecificationFacade<Entity, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification> getEntityQuerySpecificationFacade();
}
