package org.reusablecomponents.base.core.application.command.entity.function.update;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * Represents a function that performs an update operation on an entity. This
 * function takes an input entity to be updated, additional arguments that may
 * be needed for the update operation, and returns the updated entity or any
 * relevant information about the operation.
 *
 * @param <UpdateEntityIn>  The type of the input entity to be updated.
 * @param <UpdateEntityOut> The type of the output result of the update
 *                          operation.
 * @param <Object[]>        The type of the additional arguments that may be
 *                          needed for the update operation, such as context
 *                          information or related data.
 * 
 * @return the result of the update operation after any update operations have
 *         been applied.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface UpdateFunction<UpdateEntityIn, UpdateEntityOut>
                extends CommandFunction, OperationFunction2Args<UpdateEntityIn, Object[], UpdateEntityOut> {
}