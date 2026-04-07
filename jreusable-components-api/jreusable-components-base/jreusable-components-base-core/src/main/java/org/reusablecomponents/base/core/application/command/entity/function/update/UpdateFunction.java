package org.reusablecomponents.base.core.application.command.entity.function.update;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A function that updates an entity.
 * 
 * <p>
 * This function is used to update an entity in the system. The input is the
 * entity to be updated, and the output is the result of the update operation,
 * which can be the updated entity or any relevant information about the
 * operation.
 * 
 * @param <UpdateEntityIn>  The type of the input entity to be updated.
 * @param <UpdateEntityOut> The type of the output result of the update
 *                          operation.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0
 */
@FunctionalInterface
public non-sealed interface UpdateFunction<UpdateEntityIn, UpdateEntityOut>
                extends CommandFunction, OperationFunction2Args<UpdateEntityIn, Object[], UpdateEntityOut> {
}