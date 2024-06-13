package org.reusablecomponents.core.application.mix.entity.nonpaged;

import org.reusablecomponents.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponents.core.application.query.entity.nonpaged.InterfaceEntityQueryFacade;
import org.reusablecomponents.core.domain.AbstractEntity;

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
		OneResult,  // One result type
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
				OneResult,  // One result type
				MultipleResult, // multiple result type 
				CountResult, // count result type
				ExistsResult> { // boolean result type

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
    
    InterfaceEntityCommandFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> getEntityCommandFacade();
    
    InterfaceEntityQueryFacade<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> getEntityQueryFacade();
    
}
