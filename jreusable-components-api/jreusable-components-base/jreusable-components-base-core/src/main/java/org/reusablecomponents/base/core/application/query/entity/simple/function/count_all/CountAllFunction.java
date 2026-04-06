package org.reusablecomponents.base.core.application.query.entity.simple.function.count_all;

import org.reusablecomponents.base.core.application.query.QueryFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction1Args;
import org.reusablecomponents.base.core.application.query.entity.simple.QueryFacade;

/**
 * Function that executes the count all operation in the
 * {@link QueryFacade#countAll(Object...) countAll} method.
 * 
 * @param <CountResult> The count-result type, like Long, Integer, or a wrap
 *                      type like Mono<Long>
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0
 */
@FunctionalInterface
public non-sealed interface CountAllFunction<CountResult>
        extends OperationFunction1Args<Object[], CountResult>, QueryFunction {
}
