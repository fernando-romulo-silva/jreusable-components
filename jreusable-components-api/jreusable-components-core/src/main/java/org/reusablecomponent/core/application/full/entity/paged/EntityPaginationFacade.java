package org.reusablecomponent.core.application.full.entity.paged;

import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.application.full.entity.AbstractEntityCommonFacade;
import org.reusablecomponent.core.application.query.entity.paged.InterfaceEntityQueryPaginationFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

/**
 * @param <Entity>
 * @param <Id>
 * @param <Page>
 * @param <Pageable>
 * @param <Specification>
 * @param <Sort>
 */
public class EntityPaginationFacade<Entity extends AbstractEntity<Id>, Id, // basic
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

		extends	AbstractEntityCommonFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut>
		implements InterfaceEntityPaginationFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut, OneResult, MultiplePagedResult, Pageable, Sort> {

    protected final InterfaceEntityQueryPaginationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort> entityQueryPaginationFacade;

    /**
     * @param entityCommandFacade
     * @param entityQueryPaginationFacade
     */
    protected EntityPaginationFacade(
		    final InterfaceEntityCommandFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> entityCommandFacade,
		    final InterfaceEntityQueryPaginationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort> entityQueryPaginationFacade) {

	super(entityCommandFacade);
	this.entityQueryPaginationFacade = entityQueryPaginationFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MultiplePagedResult findAll(final Pageable pageable, final Object... directives) {
	return entityQueryPaginationFacade.findAll(pageable, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OneResult findFirst(final Sort sort) {
	return entityQueryPaginationFacade.findFirst(sort);
    }
}
