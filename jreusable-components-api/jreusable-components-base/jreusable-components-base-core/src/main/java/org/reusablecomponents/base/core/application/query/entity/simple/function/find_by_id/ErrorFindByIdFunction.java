package org.reusablecomponents.base.core.application.query.entity.simple.function.find_by_id;

import org.reusablecomponents.base.core.application.query.QueryFunction;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction3Args;

/**
 * Function executed in {@link QueryFacade#findById(Object, Object...)} method
 * to handle {@link QueryFacade#findByIdFunction} errors.
 * 
 * @param <QueryIdIn> The input id type for the find by id and exists by id
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface ErrorFindByIdFunction<QueryIdIn>
		extends OperationFunction3Args<BaseException, QueryIdIn, Object[], BaseException>, QueryFunction {
}