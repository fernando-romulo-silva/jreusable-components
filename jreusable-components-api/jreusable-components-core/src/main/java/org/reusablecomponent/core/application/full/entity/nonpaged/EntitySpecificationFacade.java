package org.reusablecomponent.core.application.full.entity.nonpaged;

import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.application.full.entity.AbstractEntityCommonFacade;
import org.reusablecomponent.core.application.query.entity.nonpaged.InterfaceEntityQuerySpecificationFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 * @param <Specification>
 */
public class EntitySpecificationFacade<Entity extends AbstractEntity<Id>, Id, // basic
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
		//
		extends	AbstractEntityCommonFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut>
		implements InterfaceEntitySpecificationFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut, OneResult, MultiplePagedResult, CountResult, ExistsResult, Specification> {
    
    protected final InterfaceEntityQuerySpecificationFacade<Entity, Id, OneResult, MultiplePagedResult, CountResult, ExistsResult, Specification> entityQueryFacade;

    /**
     * @param entityCommandFacade
     * @param entityQueryFacade
     */
    protected EntitySpecificationFacade(
		    @NotNull final InterfaceEntityCommandFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> entityCommandFacade, 
		    @NotNull final InterfaceEntityQuerySpecificationFacade<Entity, Id, OneResult, MultiplePagedResult, CountResult, ExistsResult, Specification> entityQueryFacade) {
	super(entityCommandFacade);
	this.entityQueryFacade = entityQueryFacade;
    }

    /**
     * {@inheritDoc}
     */    
    @Override
    public MultiplePagedResult findBy(final Specification specification, final Object... directives) {
	return entityQueryFacade.findBy(specification, directives);
    }

    /**
     * {@inheritDoc}
     */    
    @Override
    public OneResult findOneBy(final Specification specification, final Object... directives) {
	return entityQueryFacade.findOneBy(specification, directives);
    }

    /**
     * {@inheritDoc}
     */    
    @Override
    public ExistsResult existsBy(final Specification specification) {
	return entityQueryFacade.existsBy(specification);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountResult count(final Specification specification) {
	return entityQueryFacade.count(specification);
    }
}
