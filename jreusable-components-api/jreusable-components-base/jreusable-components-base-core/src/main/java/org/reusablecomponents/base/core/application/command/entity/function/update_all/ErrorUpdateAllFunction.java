package org.reusablecomponents.base.core.application.command.entity.function.update_all;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction3Args;

/**
 * A functional interface representing an error handling operation for updating
 * multiple entities. This function is executed when an error occurs during the
 * update operation and can be used to perform any necessary error handling or
 * logging.
 * 
 * @param <UpdateEntitiesIn> The type of the input data for the update
 *                           operation, typically a collection of entities.
 * @param <Object[]>         The type of the additional arguments that may be
 *                           needed for the update operation, such as context
 *                           information or related data.
 * 
 * @return A {@link BaseException} that represents the error that occurred
 *         during the update operation.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface ErrorUpdateAllFunction<UpdateEntitiesIn>
                extends CommandFunction,
                OperationFunction3Args<BaseException, UpdateEntitiesIn, Object[], BaseException> {
}