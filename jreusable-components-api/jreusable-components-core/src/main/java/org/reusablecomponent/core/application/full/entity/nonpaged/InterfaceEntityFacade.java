package org.reusablecomponent.core.application.full.entity.nonpaged;

import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.application.query.entity.nonpaged.InterfaceEntityQueryFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

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
		// results
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
				// args
				QueryIdIn, // by id arg
				// results
				OneResult,  // One result type
				MultipleResult, // multiple result type 
				CountResult, // count result type
				ExistsResult> { // boolean result type

    
    @Override
    default SaveEntityOut save(final SaveEntityIn saveEntityIn) {
	return getEntityCommandFacade().save(saveEntityIn);
    }
    
    @Override
    default SaveEntitiesOut saveAll(SaveEntitiesIn saveEntitiesIn) {
	return getEntityCommandFacade().saveAll(saveEntitiesIn);
    }
    
    InterfaceEntityCommandFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> getEntityCommandFacade();
}
