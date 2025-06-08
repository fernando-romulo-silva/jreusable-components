package org.reusablecomponents.base.core.application.query.entity.simple;

import static org.reusablecomponents.base.core.infra.constants.ExceptionMessages.NULL_POINTER_EXCEPTION_MSG;

import org.reusablecomponents.base.core.application.base.InterfaceBaseFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.common.BaseApplicationException;
import org.reusablecomponents.base.core.infra.exception.common.ElementWithIdNotFoundException;

import jakarta.validation.constraints.NotNull;

/**
 * Interface responsible for establishing contracts to retrieve objects, common
 * operations to all projects.
 * 
 * @param <Entity>         The facade entity type
 * @param <Id>             The facade entity id type
 * 
 * @param <QueryIdIn>      The entity Id used in the query
 * 
 * @param <OneResult>      The one-result type, like the entity or wrap type
 *                         like Mono<Entity>
 * @param <MultipleResult> The multiple-result type, like List<Entity>,
 *                         Iterable<Entity>, or a wrap type like
 * @param <CountResult>    The count-result type
 * @param <ExistsResult>   The exist-result type
 */
public non-sealed interface InterfaceQueryFacade<Entity extends AbstractEntity<Id>, Id, // basic
		QueryIdIn, //
		OneResult, //
		MultipleResult, //
		CountResult, //
		ExistsResult> //
		extends InterfaceBaseFacade<Entity, Id> {

	/**
	 * Find and retrieve a {@code OneResult} object by id
	 * 
	 * @param queryIdIn  The entity id
	 * @param directives Params used to configure the query
	 * 
	 * @throws ElementWithIdNotFoundException If you try to retrieve an entity that
	 *                                        doesn't exist
	 * @throws NullPointerException           If the parameter 'queryIdIn' is null
	 * @throws BaseApplicationException       If an unidentified error happened
	 * 
	 * @return Return a {@code OneResult} object
	 */
	OneResult findById(
			@NotNull(message = NULL_POINTER_EXCEPTION_MSG) final QueryIdIn queryIdIn,
			final Object... directives);

	/**
	 * Find and retrieve all objects, be carefull with it.
	 * 
	 * @param directives Params used to configure the query
	 * 
	 * @throws BaseApplicationException If an unidentified error happened
	 * 
	 * @return Return a {@code OneResult} object
	 */
	MultipleResult findAll(final Object... directives);

	/**
	 * Check if an entity exists with the provided ID.
	 * 
	 * @param queryIdIn  The entity id
	 * @param directives Params used to configure the query
	 * 
	 * @throws NullPointerException     If the parameter 'queryIdIn' is null
	 * @throws BaseApplicationException If an unidentified error happened
	 * 
	 * @return Return a {@code ExistsResult} object
	 */
	ExistsResult existsById(
			@NotNull(message = NULL_POINTER_EXCEPTION_MSG) final QueryIdIn queryIdIn,
			final Object... directives);

	/**
	 * Check if there exists at least an entity.
	 * 
	 * @param directives Params used to configure the query
	 * 
	 * @throws BaseApplicationException If an unidentified error happened
	 * 
	 * @return Return a {@code ExistsResult} object
	 */
	ExistsResult existsAll(final Object... directives);

	/**
	 * Count how many entities there are.
	 * 
	 * @param directives Params used to configure the query
	 * 
	 * @throws BaseApplicationException If an unidentified error happened
	 * 
	 * @return Return a {@code CountResult} object
	 */
	CountResult countAll(final Object... directives);
}