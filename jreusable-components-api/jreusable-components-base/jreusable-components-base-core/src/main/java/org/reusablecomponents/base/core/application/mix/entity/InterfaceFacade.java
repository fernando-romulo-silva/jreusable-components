package org.reusablecomponents.base.core.application.mix.entity;

import org.reusablecomponents.base.core.application.command.entity.InterfaceCommandFacade;
import org.reusablecomponents.base.core.application.query.entity.pagination.InterfaceQueryPaginationFacade;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.InterfaceQuerySpecificationPaginationFacade;
import org.reusablecomponents.base.core.application.query.entity.simple.InterfaceQueryFacade;
import org.reusablecomponents.base.core.application.query.entity.specification.InterfaceQuerySpecificationFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;

public interface InterfaceFacade<Entity extends AbstractEntity<Id>, Id, // basic
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
		OneResult, // One result type
		MultipleResult, // multiple result type
		CountResult, // count result type
		ExistsResult, // boolean result type

		MultiplePagedResult, // multiple result type
		// Pagination
		Specification, // specification
		Pageable, // pageable type
		Sort> // sort type

		// non paged
		extends InterfaceCommandFacade<Entity, Id, // default
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
		//
		InterfaceQueryFacade<Entity, Id, // default
				QueryIdIn, // by id arg
				OneResult, // One result type
				MultipleResult, // multiple result type
				CountResult, // count result type
				ExistsResult>, // boolean result type
		//
		InterfaceQuerySpecificationFacade<Entity, Id, //
				OneResult, //
				MultipleResult, //
				CountResult, //
				ExistsResult, //
				Specification>, //
		//
		InterfaceQueryPaginationFacade<Entity, Id, // basic
				OneResult, // one result type
				MultiplePagedResult, // multiple result type
				Pageable, // pageable type
				Sort>, // sort type
		//
		InterfaceQuerySpecificationPaginationFacade<Entity, Id, // basic
				OneResult, // oneResult
				MultiplePagedResult, // multiple paged
				Specification, // specification
				Pageable, Sort> { // pagination

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
	default MultipleResult findAll(final Object... directives) {
		return getEntityQueryFacade().findAll(directives);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default MultiplePagedResult findAllPaged(final Pageable pageable, final Object... directives) {
		return getEntityQueryPaginationFacade().findAllPaged(pageable, directives);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default OneResult findById(final QueryIdIn queryIdIn, final Object... directives) {
		return getEntityQueryFacade().findById(queryIdIn, directives);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default MultipleResult findBySpecification(final Specification specification, final Object... directives) {
		return getEntityQuerySpecificationFacade().findBySpecification(specification, directives);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default MultiplePagedResult findByPaginationPaged(
			final Specification specification,
			final Pageable pageable,
			final Object... directives) {
		return getEntityQueryPaginationSpecificationFacade().findByPaginationPaged(specification, pageable, directives);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default OneResult findOneSorted(final Sort sort, final Object... directives) {
		return getEntityQueryPaginationFacade().findOneSorted(sort, directives);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default OneResult findOneBySpecification(final Specification specification, final Object... directives) {
		return getEntityQuerySpecificationFacade().findOneBySpecification(specification, directives);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default OneResult findOneByPaginationSorted(final Specification specification, final Sort sort,
			final Object... directives) {
		return getEntityQueryPaginationSpecificationFacade().findOneByPaginationSorted(specification, sort, directives);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default ExistsResult existsById(final QueryIdIn queryIdIn, final Object... directives) {
		return getEntityQueryFacade().existsById(queryIdIn, directives);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default ExistsResult existsAll(final Object... directives) {
		return getEntityQueryFacade().existsAll(directives);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default ExistsResult existsBySpecification(final Specification specification, final Object... directives) {
		return getEntityQuerySpecificationFacade().existsBySpecification(specification, directives);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default CountResult countAll(final Object... directives) {
		return getEntityQueryFacade().countAll(directives);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default CountResult countBySpecification(final Specification specification, final Object... directives) {
		return getEntityQuerySpecificationFacade().countBySpecification(specification, directives);
	}

	// ---------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Class<Entity> getEntityClazz() {
		return getEntityCommandFacade().getEntityClazz();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Class<Id> getIdClazz() {
		return getEntityCommandFacade().getIdClazz();
	}

	InterfaceCommandFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> getEntityCommandFacade();

	InterfaceQueryFacade<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> getEntityQueryFacade();

	InterfaceQuerySpecificationFacade<Entity, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification> getEntityQuerySpecificationFacade();

	InterfaceQueryPaginationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort> getEntityQueryPaginationFacade();

	InterfaceQuerySpecificationPaginationFacade<Entity, Id, OneResult, MultiplePagedResult, Specification, Pageable, Sort> getEntityQueryPaginationSpecificationFacade();

}