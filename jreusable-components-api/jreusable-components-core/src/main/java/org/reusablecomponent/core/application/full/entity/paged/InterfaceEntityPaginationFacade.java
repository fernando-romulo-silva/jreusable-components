package org.reusablecomponent.core.application.full.entity.paged;

import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.application.query.entity.paged.InterfaceEntityQueryPaginationFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

public interface InterfaceEntityPaginationFacade<Entity extends AbstractEntity<Id>, Id, // basic
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
		Sort> // sort type
		// command
		extends	InterfaceEntityCommandFacade<Entity, Id, // default
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
		InterfaceEntityQueryPaginationFacade<Entity, Id, // basic
				OneResult, // one result type
				MultiplePagedResult, // multiple result type
				Pageable, // pageable type
				Sort> { // sort type

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
    default MultiplePagedResult findAll(final Pageable pageable, final Object... directives){
	return getEntityQueryPaginationFacade().findAll(pageable, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default OneResult findFirst(final Sort sort){
	return getEntityQueryPaginationFacade().findFirst(sort);
    }
    
    InterfaceEntityCommandFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> getEntityCommandFacade();
    
    InterfaceEntityQueryPaginationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort> getEntityQueryPaginationFacade();

}
