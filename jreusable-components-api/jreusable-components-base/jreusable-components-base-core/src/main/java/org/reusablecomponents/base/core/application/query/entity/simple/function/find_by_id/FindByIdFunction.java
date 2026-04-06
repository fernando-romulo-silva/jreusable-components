package org.reusablecomponents.base.core.application.query.entity.simple.function.find_by_id;

import org.reusablecomponents.base.core.application.query.QueryFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;
import org.reusablecomponents.base.core.application.query.entity.simple.QueryFacade;

/**
 * Function that executes the find by id operation in the
 * {@link QueryFacade#findById(Object, Object...) findById} method
 * 
 * @param <QueryIdIn> The input id type for the find by id operation
 * @param <OneResult> The one-result type, like the entity or wrap type like
 *                    Mono<Entity>
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0
 * 
 * @see QueryFacade#findById(Object, Object...)
 */
@FunctionalInterface
public non-sealed interface FindByIdFunction<QueryIdIn, OneResult>
        extends OperationFunction2Args<QueryIdIn, Object[], OneResult>, QueryFunction {
}
