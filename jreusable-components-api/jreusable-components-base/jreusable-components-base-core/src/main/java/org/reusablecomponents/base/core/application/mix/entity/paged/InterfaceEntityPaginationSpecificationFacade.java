package org.reusablecomponents.base.core.application.mix.entity.paged;

import org.reusablecomponents.base.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponents.base.core.application.query.entity.paged.InterfaceEntityQueryPaginationSpecificationFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;

public interface InterfaceEntityPaginationSpecificationFacade<Entity extends AbstractEntity<Id>, Id, // basic
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
        OneResult, MultiplePagedResult,
        // Pagination
        Pageable, Sort,
        // Specification
        Specification>
        // commands
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
        InterfaceEntityQueryPaginationSpecificationFacade<Entity, Id, // basic
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
    default MultiplePagedResult findBy(final Pageable pageable, final Specification specification,
            final Object... directives) {
        return getEntityQueryPaginationSpecificationFacade().findBy(pageable, specification, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default OneResult findOneBy(final Specification specification, final Sort sort, final Object... directives) {
        return getEntityQueryPaginationSpecificationFacade().findOneBy(specification, sort, directives);
    }

    InterfaceEntityCommandFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> getEntityCommandFacade();

    InterfaceEntityQueryPaginationSpecificationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification> getEntityQueryPaginationSpecificationFacade();
}
