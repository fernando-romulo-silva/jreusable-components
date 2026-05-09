package org.reusablecomponents.base.core.application.query.entity.pagination;

import static org.reusablecomponents.base.core.infra.constants.ExceptionMessages.NULL_POINTER_EXCEPTION_MSG;

import org.reusablecomponents.base.core.application.base.InterfaceBaseFacade;
import org.reusablecomponents.base.core.domain.InterfaceEntity;

import jakarta.validation.constraints.NotNull;

/**
 * This interface is used to retrieve objects using pagination, and it is
 * intended to be used in the query layer of the application.
 * 
 * @param <Entity>              The facade entity type
 * @param <Id>                  The facade entity id type
 * 
 * @param <OneResult>           The one-result type
 * @param <MultiplePagedResult> The paged multiple-result type
 * @param <Pageable>            The query result controll
 * 
 * @param <Sort>                The query result order
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 * 
 * @see InterfaceBaseFacade
 */
public non-sealed interface InterfaceQueryPaginationFacade<Entity extends InterfaceEntity<Id>, Id, OneResult, MultiplePagedResult, Pageable, Sort>
        extends InterfaceBaseFacade<Entity, Id> {

    /**
     * Find and retrieve all objects using pagination
     * 
     * @param pageable   Object {@code Pageable} used to controll the query's result
     * @param directives Params used to configure the query's result
     * 
     * @return Return a {@code MultipleResult} object
     */
    MultiplePagedResult findAllPaged(
            @NotNull(message = NULL_POINTER_EXCEPTION_MSG) final Pageable pageable,
            final Object... directives);

    /**
     * Find and retrieve one object {@code OneResult} based on sort parameter
     * 
     * @param sort       Object {@code Sort} used to order the query
     * @param directives Params used to configure the query's result
     * 
     * @return Return a {@code OneResult} object
     */
    OneResult findOneSorted(
            @NotNull(message = NULL_POINTER_EXCEPTION_MSG) final Sort sort,
            final Object... directives);
}
