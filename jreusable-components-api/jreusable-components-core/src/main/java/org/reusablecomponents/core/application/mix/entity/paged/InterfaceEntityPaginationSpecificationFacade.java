package org.reusablecomponents.core.application.mix.entity.paged;

import org.reusablecomponents.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponents.core.application.query.entity.paged.InterfaceEntityQueryPaginationSpecificationFacade;
import org.reusablecomponents.core.domain.AbstractEntity;

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
    default SaveEntityOut save(final SaveEntityIn saveEntityIn) {
	return getEntityCommandFacade().save(saveEntityIn);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default SaveEntitiesOut saveAll(SaveEntitiesIn saveEntitiesIn) {
	return getEntityCommandFacade().saveAll(saveEntitiesIn);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default UpdateEntityOut update(final UpdateEntityIn updateEntityIn) {
	return getEntityCommandFacade().update(updateEntityIn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default UpdateEntitiesOut updateAll(final UpdateEntitiesIn updateEntitiesIn) {
	return getEntityCommandFacade().updateAll(updateEntitiesIn);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default DeleteEntityOut delete(final DeleteEntityIn deleteEntityIn) {
	return getEntityCommandFacade().delete(deleteEntityIn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default DeleteEntitiesOut deleteAll(final DeleteEntitiesIn deleteEntitiesIn) {
	return getEntityCommandFacade().deleteAll(deleteEntitiesIn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default DeleteIdOut deleteBy(final DeleteIdIn deleteIdIn) {
	return getEntityCommandFacade().deleteBy(deleteIdIn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default DeleteIdsOut deleteAllBy(final DeleteIdsIn deleteIdsIn) {
	return getEntityCommandFacade().deleteAllBy(deleteIdsIn);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    default MultiplePagedResult findBy(final Pageable pageable, final Specification specification, final Object... directives) {
	return getEntityQueryPaginationSpecificationFacade().findBy(pageable, specification, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default OneResult findOneBy(final Specification specification, final Sort sort) {
	return getEntityQueryPaginationSpecificationFacade().findOneBy(specification, sort);
    }
    
    InterfaceEntityCommandFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> getEntityCommandFacade();
    
    InterfaceEntityQueryPaginationSpecificationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification> getEntityQueryPaginationSpecificationFacade();
}
