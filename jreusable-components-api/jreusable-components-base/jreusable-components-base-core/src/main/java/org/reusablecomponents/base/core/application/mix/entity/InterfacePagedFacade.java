package org.reusablecomponents.base.core.application.mix.entity;

import org.reusablecomponents.base.core.application.command.entity.InterfaceCommandFacade;
import org.reusablecomponents.base.core.application.query.entity.pagination.InterfaceQueryPaginationFacade;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.InterfaceQuerySpecificationPaginationFacade;
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
        Specification, // specification
        Pageable, // pageable type
        Sort> // sort type

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

        InterfaceQuerySpecificationPaginationFacade<Entity, Id, // basic
                OneResult, // oneResult
                MultiplePagedResult, // multiple paged
                Specification, // specification
                Pageable, Sort> { // pagination

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
    default MultiplePagedResult findAllPaged(final Pageable pageable, final Object... directives) {
        return getEntityQueryPaginationFacade().findAllPaged(pageable, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default OneResult findOneSorted(final Sort sort, final Object... directives) {
        return getEntityQueryPaginationFacade().findOneSorted(sort, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default MultiplePagedResult findByPaginationPaged(final Specification specification, final Pageable pageable,
            final Object... directives) {
        return getEntityQueryPaginationSpecificationFacade().findByPaginationPaged(specification, pageable, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default OneResult findOneByPaginationSorted(final Specification specification, final Sort sort,
            final Object... directives) {
        return getEntityQueryPaginationSpecificationFacade().findOneByPaginationSorted(specification, sort, directives);
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

    InterfaceQuerySpecificationPaginationFacade<Entity, Id, OneResult, MultiplePagedResult, Specification, Pageable, Sort> getEntityQueryPaginationSpecificationFacade();

}
