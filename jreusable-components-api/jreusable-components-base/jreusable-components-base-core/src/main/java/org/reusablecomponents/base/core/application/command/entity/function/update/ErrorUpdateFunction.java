package org.reusablecomponents.base.core.application.command.entity.function.update;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction3Args;

/**
 * A functional interface representing an error handling operation for updating
 * an entity. This function is executed when an error occurs during the update
 * operation and can be used to perform any necessary error handling or logging.
 * 
 * @param <UpdateEntityIn> The type of the input data for the update operation,
 *                         typically the entity to be updated.
 * @param <Object[]>       The type of the additional arguments that may be
 *                         needed for error handling, such as context
 *                         information or error details.
 * 
 * @return A {@link BaseException} that represents the error that occurred
 *         during the update operation.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface ErrorUpdateFunction<UpdateEntityIn>
        extends CommandFunction,
        OperationFunction3Args<BaseException, UpdateEntityIn, Object[], BaseException> {
}