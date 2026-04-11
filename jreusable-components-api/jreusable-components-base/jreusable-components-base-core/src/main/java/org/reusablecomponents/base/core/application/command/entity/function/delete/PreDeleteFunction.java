package org.reusablecomponents.base.core.application.command.entity.function.delete;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * PreDeleteFunction is a functional interface that represents a function to be
 * executed before deleting an entity. It takes an input of type DeleteEntityIn
 * and an array of Objects as arguments, and returns a value of type
 * DeleteEntityIn. This function can be used to perform any necessary operations
 * or validations before the actual deletion of the entity occurs.
 * 
 * @param <DeleteEntityIn> The type of the input used to identify and delete
 *                         the entity.
 * @param <Object[]>       The type of the additional arguments that can be
 *                         passed to the function, if needed.
 * 
 * @return The result of the pre-delete operation, which is typically
 *         the input entity or any relevant information about the operation.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface PreDeleteFunction<DeleteEntityIn>
        extends CommandFunction, OperationFunction2Args<DeleteEntityIn, Object[], DeleteEntityIn> {
}