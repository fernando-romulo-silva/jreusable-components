package org.reusablecomponents.base.core.application.command.entity.function.delete;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction3Args;

/**
 * A function that handles errors during the delete operation of an entity.
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