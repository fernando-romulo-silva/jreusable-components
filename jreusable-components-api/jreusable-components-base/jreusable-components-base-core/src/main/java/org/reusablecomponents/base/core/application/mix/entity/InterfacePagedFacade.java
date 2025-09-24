package org.reusablecomponents.base.core.application.mix.entity;

import org.reusablecomponents.base.core.application.command.entity.InterfaceCommandFacade;
import org.reusablecomponents.base.core.application.query.entity.pagination.InterfaceQueryPaginationFacade;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.InterfaceQueryPaginationSpecificationFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;

public interface InterfacePagedFacade<Entity extends AbstractEntity<Id>, Id, // basic
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
        // results
        OneResult, // one result type
        MultiplePagedResult, // multiple result type
        // Pagination
        Pageable, // pageable type
        Sort, // sort type
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
        InterfaceQueryPaginationFacade<Entity, Id, // basic
                OneResult, // one result type
                MultiplePagedResult, // multiple result type
                Pageable, // pageable type
                Sort>, // sort type

        InterfaceQueryPaginationSpecificationFacade<Entity, Id, // basic
                OneResult, // oneResult
                MultiplePagedResult, // multiple paged
                Pageable, Sort, // pagination
                Specification> { // specificatio

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
        return getEntityCommandFacade().delete(deleteEntityIn);
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
    default MultiplePagedResult findAll(final Pageable pageable, final Object... directives) {
        return getEntityQueryPaginationFacade().findAll(pageable, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default OneResult findOne(final Sort sort, final Object... directives) {
        return getEntityQueryPaginationFacade().findOne(sort, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default MultiplePagedResult findBySpec(final Pageable pageable, final Specification specification,
            final Object... directives) {
        return getEntityQueryPaginationSpecificationFacade().findBySpec(pageable, specification, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default OneResult findOneBy(final Sort sort, final Specification specification, final Object... directives) {
        return getEntityQueryPaginationSpecificationFacade().findOneBy(sort, specification, directives);
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

    InterfaceQueryPaginationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort> getEntityQueryPaginationFacade();

    InterfaceQueryPaginationSpecificationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification> getEntityQueryPaginationSpecificationFacade();

}
