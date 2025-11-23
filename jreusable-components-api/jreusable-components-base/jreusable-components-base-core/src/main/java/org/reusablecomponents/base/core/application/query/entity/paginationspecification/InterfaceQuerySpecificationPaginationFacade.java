package org.reusablecomponents.base.core.application.query.entity.paginationspecification;

import static org.reusablecomponents.base.core.infra.constants.ExceptionMessages.NULL_POINTER_EXCEPTION_MSG;

import org.reusablecomponents.base.core.application.base.InterfaceBaseFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;

import jakarta.validation.constraints.NotNull;

/**
 * Interface responsible for establishing contracts to retrieve objects, using
 * pagination and specification.
 * 
 * @param <Entity>              The facade entity type
 * @param <Id>                  The facade entity id type
 * 
 * @param <OneResult>           The one-result type
 * @param <MultiplePagedResult> The multiple-result type
 * @param <Pageable>            The query result controll
 * 
 * @param <Sort>                The query result order
 * @param <Specification>       The specification is a strutucte used to filter
 *                              queries
 */
public non-sealed interface InterfaceQuerySpecificationPaginationFacade<Entity extends AbstractEntity<Id>, Id, //
		OneResult, //
		MultiplePagedResult, //
		Specification, //
		Pageable, //
		Sort> //
		// Base
		extends InterfaceBaseFacade<Entity, Id> {

	/**
	 * Find and retrieve entities filtered by specification using pagination
	 * 
	 * @param pageable      Object {@code Pageable} used to controll the query's
	 *                      result
	 * @param specification Object {@code Specification} used to filter query's
	 *                      result
	 * @param directives    Params used to configure the query's result
	 * 
	 * @return Return a {@code MultipleResult} object
	 */
	MultiplePagedResult findByPaginationPaged(
			@NotNull(message = NULL_POINTER_EXCEPTION_MSG) final Specification specification,
			@NotNull(message = NULL_POINTER_EXCEPTION_MSG) final Pageable pageable,
			final Object... directives);

	/**
	 * Find and retrieve one entity filtered by specification using pagination
	 * 
	 * @param sort          Object {@code Sort} used to order the query
	 * @param specification Object {@code Pageable} used to filter query's result
	 * @param directives    Objects used to configure the query's result
	 * 
	 * @return Return a {@code OneResult} object
	 */
	OneResult findOneByPaginationSorted(
			@NotNull(message = NULL_POINTER_EXCEPTION_MSG) final Specification specification,
			@NotNull(message = NULL_POINTER_EXCEPTION_MSG) final Sort sort,
			final Object... directives);
}