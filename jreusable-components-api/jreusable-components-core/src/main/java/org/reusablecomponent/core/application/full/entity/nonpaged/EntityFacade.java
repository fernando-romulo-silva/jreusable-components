package org.reusablecomponent.core.application.full.entity.nonpaged;

import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.application.full.entity.AbstractEntityCommonFacade;
import org.reusablecomponent.core.application.query.entity.nonpaged.InterfaceEntityQueryFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 * @param <Specification>
 */
public class EntityFacade<Entity extends AbstractEntity<Id>, Id, // basic
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
		QueryIdIn, // by id arg
		// results
		OneResult, // One result
		MultipleResult, // multiple result
		CountResult, // count result
		ExistsResult> // exists result
		// Base class
		extends AbstractEntityCommonFacade<Entity, Id, // basic
				//
				SaveEntityIn, SaveEntityOut, // save a entity
				SaveEntitiesIn, SaveEntitiesOut, // save entities

				UpdateEntityIn, UpdateEntityOut, // update a entity
				UpdateEntitiesIn, UpdateEntitiesOut, // update entities

				DeleteEntityIn, DeleteEntityOut, // delete a entity
				DeleteEntitiesIn, DeleteEntitiesOut, // delete entities

				DeleteIdIn, DeleteIdOut, // delete a entity by id
				DeleteIdsIn, DeleteIdsOut>  // delete entities by id
                // Base interface
		implements InterfaceEntityFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> {

    protected final InterfaceEntityQueryFacade<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> entityQueryFacade;

    /**
     * @param entityCommandFacade
     * @param entityQueryFacade
     */
    protected EntityFacade( //
		    @NotNull final InterfaceEntityCommandFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> entityCommandFacade, 
		    @NotNull final InterfaceEntityQueryFacade<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> entityQueryFacade) {
	super(entityCommandFacade);
	this.entityQueryFacade = entityQueryFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OneResult findBy(final QueryIdIn queryIdIn, final Object... directives) {
	return entityQueryFacade.findBy(queryIdIn, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MultipleResult findAll(final Object... directives) {
	return entityQueryFacade.findAll(directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExistsResult existsBy(final QueryIdIn queryIdIn) {
	return entityQueryFacade.existsBy(queryIdIn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountResult countAll() {
	return entityQueryFacade.countAll();
    }

    @Override
    public InterfaceEntityCommandFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> getEntityCommandFacade() {
	// TODO Auto-generated method stub
	return entityCommandFacade;
    }
}
