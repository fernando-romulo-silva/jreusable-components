package org.reusablecomponents.base.core.application.query.entity.pagination.function.find_all_paged;

import org.reusablecomponents.base.core.application.query.QueryFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.QueryPaginationFacade;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction3Args;

/**
 * Function executed in
 * {@link QueryPaginationFacade#findAllPaged(Object, Object...)} method
 * to handle {@link QueryPaginationFacade#findAllPagedFunction}
 * errors.
 * 
 * @param BaseException the type of exception to be handled
 * @param Pageable      the type of pageable object used in the findAllPaged
 *                      function
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface ErrorFindAllPagedFunction<BaseException, Pageable>
		extends QueryFunction, OperationFunction3Args<BaseException, Pageable, Object[], BaseException> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	default String getName() {
		return "ErrorFindAllPagedFunction";
	}
}
