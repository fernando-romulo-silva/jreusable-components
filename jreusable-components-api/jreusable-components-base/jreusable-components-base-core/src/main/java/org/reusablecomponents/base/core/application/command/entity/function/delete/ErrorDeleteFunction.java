package org.reusablecomponents.base.core.application.command.entity.function.delete;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction3Args;

/**
 * A functional interface representing an error function for an entity delete
 * operation. This function is executed when an error occurs during the delete
 * operation and can be used to perform any necessary operations or
 * transformations on the exception or the entity involved in the delete
 * operation.
 * 
 * @param <DeleteEntityIn> The type of the input used to identify and delete
 *                         the entity.
 * @param <Object[]>       The type of the additional arguments that can be
 *                         passed to the function, if needed.
 * 
 * @return A {@link BaseException} that represents the error that occurred
 *         during the delete operation.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 * 
 */
@FunctionalInterface
public non-sealed interface ErrorDeleteFunction<DeleteEntityIn>
        extends CommandFunction,
        OperationFunction3Args<BaseException, DeleteEntityIn, Object[], BaseException> {
}