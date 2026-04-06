package org.reusablecomponents.base.core.application.query.entity.simple.function.exists_by_id;

import org.reusablecomponents.base.core.application.query.QueryFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

import org.reusablecomponents.base.core.application.query.entity.simple.QueryFacade;

/**
 * Function that executes the exists by id operation in the
 * {@link QueryFacade#existsById(Object, Object...) existsById} method
 * 
 * @param <QueryIdIn>    The input id type for the find by id and exists by id
 * @param <ExistsResult> The exist-result type, like Boolean or a wrap type like
 *                       Mono<Boolean>
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0
 * 
 * @see QueryFacade#existsById(Object, Object...)
 */
@FunctionalInterface
public non-sealed interface ExistsByIdFunction<QueryIdIn, ExistsResult>
        extends OperationFunction2Args<QueryIdIn, Object[], ExistsResult>, QueryFunction {
}
