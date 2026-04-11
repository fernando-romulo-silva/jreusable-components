package org.reusablecomponents.base.core.application.command.entity.function.delete;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A function that handles the successful completion of the delete operation of
 * an entity.
 * This function is executed after deleting an entity and can be used to perform
 * any necessary operations or transformations on the deleted entity after it
 * has been removed.
 * 
 * @param <DeleteEntityOut> The type of the output result of the delete
 *                          operation.
 * @param <Object[]>        The type of the additional arguments that can be
 *                          passed to the function, if needed.
 * 
 * @return The result of the successful delete operation, which is typically
 *         the deleted entity or any relevant information about the operation.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 * 
 */
@FunctionalInterface
public non-sealed interface PosDeleteFunction<DeleteEntityOut>
                extends CommandFunction, OperationFunction2Args<DeleteEntityOut, Object[], DeleteEntityOut> {
}