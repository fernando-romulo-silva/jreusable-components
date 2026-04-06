package org.reusablecomponents.base.core.application.query.entity.simple.function.find_all;

import org.reusablecomponents.base.core.application.query.QueryFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction1Args;
import org.reusablecomponents.base.core.application.query.entity.simple.QueryFacade;

/**
 * Function that executes the find all operation in the
 * {@link QueryFacade#findAll(Object...) findAll} method
 * 
 * @param <MultipleResult> The multiple-result type, like List<Entity>,
 *                         Iterable<Entity>, or a wrap type like
 *                         Mono<List<Entity>>
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0
 * 
 * @see QueryFacade#findAll(Object...)
 */
@FunctionalInterface
public non-sealed interface FindAllFunction<MultipleResult>
                extends OperationFunction1Args<Object[], MultipleResult>, QueryFunction {

}
