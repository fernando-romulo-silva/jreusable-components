package org.reusablecomponents.core.application.mix.entity.nonpaged;

import org.reusablecomponents.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponents.core.application.query.entity.nonpaged.InterfaceEntityQuerySpecificationFacade;
import org.reusablecomponents.core.domain.AbstractEntity;

public interface InterfaceEntitySpecificationFacade<Entity extends AbstractEntity<Id>, Id,
		// ------------ command
		// save
		SaveEntityIn, SaveEntityOut, // save a entity
		SaveEntitiesIn, SaveEntitiesOut, // save entities
		// update
		UpdateEntityIn, UpdateEntityOut, // update a entity
		UpdateEntitiesIn, UpdateEntitiesOut, // update entities
		// delete
		DeleteEntityIn, DeleteEntityOut, // delete a entity
		DeleteEntitiesIn, DeleteEntitiesOut, // delete entities
		// delete by id
		DeleteIdIn, DeleteIdOut, // delete a entity by id
		DeleteIdsIn, DeleteIdsOut, // delete entities by id
		// ------------ query
		OneResult, // One result type
		MultiplePagedResult, // multiple result type
		CountResult, // count result type
		ExistsResult, // exists result type
		Specification> // query specification (parameters, filters, orders, etc)
		extends
		InterfaceEntityCommandFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut>,
		InterfaceEntityQuerySpecificationFacade<Entity, Id, OneResult, MultiplePagedResult, CountResult, ExistsResult, Specification> {

    
    InterfaceEntityCommandFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> getEntityCommandFacade();
    
    InterfaceEntityQuerySpecificationFacade<Entity, Id, OneResult, MultiplePagedResult, CountResult, ExistsResult, Specification> getEntityQueryFacade();
    
}
