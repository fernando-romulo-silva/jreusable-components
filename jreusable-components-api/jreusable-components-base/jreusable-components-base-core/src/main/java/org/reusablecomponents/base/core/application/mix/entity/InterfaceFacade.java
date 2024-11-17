package org.reusablecomponents.base.core.application.mix.entity;

import org.reusablecomponents.base.core.application.command.entity.InterfaceCommandFacade;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.InterfaceQueryFacade;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.InterfaceQuerySpecificationFacade;
import org.reusablecomponents.base.core.application.query.entity.paged.InterfaceQueryPaginationFacade;
import org.reusablecomponents.base.core.application.query.entity.paged.InterfaceQueryPaginationSpecificationFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;

public interface InterfaceFacade<Entity extends AbstractEntity<Id>, Id, // basic
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

        MultiplePagedResult, // multiple result type
        // Pagination
        Pageable, // pageable type
        Sort, // sort type
        Specification>

        // non paged
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
        //
        InterfaceQueryFacade<Entity, Id, // default
                QueryIdIn, // by id arg
                OneResult, // One result type
                MultipleResult, // multiple result type
                CountResult, // count result type
                ExistsResult>, // boolean result type
        //
        InterfaceQuerySpecificationFacade<Entity, Id, //
                OneResult, //
                MultipleResult, //
                CountResult, //
                ExistsResult, //
                Specification>, //
        //
        InterfaceQueryPaginationFacade<Entity, Id, // basic
                OneResult, // one result type
                MultiplePagedResult, // multiple result type
                Pageable, // pageable type
                Sort>, // sort type
        //
        InterfaceQueryPaginationSpecificationFacade<Entity, Id, // basic
                OneResult, // oneResult
                MultiplePagedResult, // multiple paged
                Pageable, Sort, // pagination
                Specification> { // specification

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
    default MultipleResult findAll(final Object... directives) {
        return getEntityQueryFacade().findAll(directives);
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
    default OneResult findById(final QueryIdIn queryIdIn, final Object... directives) {
        return getEntityQueryFacade().findById(queryIdIn, directives);
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
    default MultiplePagedResult findBy(
            final Pageable pageable,
            final Specification specification,
            final Object... directives) {
        return getEntityQueryPaginationSpecificationFacade().findBy(pageable, specification, directives);
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
    default OneResult findOneBySpec(final Specification specification, final Object... directives) {
        return getEntityQuerySpecificationFacade().findOneBySpec(specification, directives);
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
    default ExistsResult existsById(final QueryIdIn queryIdIn) {
        return getEntityQueryFacade().existsById(queryIdIn);
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
    default ExistsResult existsBySpec(final Specification specification) {
        return getEntityQuerySpecificationFacade().existsBySpec(specification);
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
    default CountResult countBySpec(final Specification specification) {
        return getEntityQuerySpecificationFacade().countBySpec(specification);
    }

    // ---------------------------------------------------------------------

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

    InterfaceQueryPaginationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort> getEntityQueryPaginationFacade();

    InterfaceQueryPaginationSpecificationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification> getEntityQueryPaginationSpecificationFacade();

}