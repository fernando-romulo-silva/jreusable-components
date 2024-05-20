package org.reusablecomponent.core.application.full.entity.paged;

import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.application.full.entity.AbstractEntityCommonFacade;
import org.reusablecomponent.core.application.query.entity.paged.InterfaceEntityQueryPaginationSpecificationFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 * @param <OneResultCommand>
 * @param <OneResultQuery>
 * @param <MultipleResult>
 * @param <MultiplePagedResult>
 * @param <CountResult>
 * @param <ExistsResult>
 * @param <VoidResult>
 * @param <Pageable>
 * @param <Sort>
 * @param <Specification>
 */
public class EntityPaginationSpecificationFacade<Entity extends AbstractEntity<Id>, Id, // basic
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
		extends AbstractEntityCommonFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut>
		implements InterfaceEntityPaginationSpecificationFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut, OneResult, MultiplePagedResult, Pageable, Sort, Specification> {

    protected final InterfaceEntityQueryPaginationSpecificationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification> entityQueryPaginationSpecificationFacade;

    /**
     * @param entityCommandFacade
     * @param entityQueryPaginationSpecificationFacade
     */
    protected EntityPaginationSpecificationFacade(
		    @NotNull final InterfaceEntityCommandFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> entityCommandFacade,
		    @NotNull final InterfaceEntityQueryPaginationSpecificationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification> entityQueryPaginationSpecificationFacade) {
	super(entityCommandFacade);
	this.entityQueryPaginationSpecificationFacade = entityQueryPaginationSpecificationFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MultiplePagedResult findBy(final Pageable pageable, final Specification specification, final Object... directives) {
	return entityQueryPaginationSpecificationFacade.findBy(pageable, specification, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OneResult findOneBy(final Specification specification, final Sort sort) {
	return entityQueryPaginationSpecificationFacade.findOneBy(specification, sort);
    }
}
