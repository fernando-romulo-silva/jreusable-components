package org.reusablecomponents.base.core.application.query.entity.simple.function.exists_all;

import org.reusablecomponents.base.core.application.query.QueryFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction1Args;

import org.reusablecomponents.base.core.application.query.entity.simple.QueryFacade;

/**
 * Function that executes the exists all operation in the
 * {@link QueryFacade#existsAll(Object...) existsAll} method
 * 
 * @param <ExistsResult> The exist-result type, like Boolean or a wrap type like
 *                       Mono<Boolean>
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0
 * 
 * @see QueryFacade#existsAll(Object...)
 */
@FunctionalInterface
public non-sealed interface ExistsAllFunction<ExistsResult>
        extends OperationFunction1Args<Object[], ExistsResult>, QueryFunction {

}
