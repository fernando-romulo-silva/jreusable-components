package org.reusablecomponents.base.core.application.command.entity.function.update;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A functional interface representing a pre-update operation for updating an
 * entity. This function is executed before the actual update operation and can
 * be used to perform any necessary transformations or validations on the input
 * data.
 * 
 * @param <UpdateEntityIn> The type of the input data for the update operation,
 *                         typically the entity to be updated.
 * @param <Object[]>       The type of the additional arguments that may be
 *                         needed for pre-update operations, such as context
 *                         information or related data.
 * 
 * @return the transformed input data after any pre-update operations have been
 *         applied
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface PreUpdateFunction<UpdateEntityIn>
        extends CommandFunction, OperationFunction2Args<UpdateEntityIn, Object[], UpdateEntityIn> {
}