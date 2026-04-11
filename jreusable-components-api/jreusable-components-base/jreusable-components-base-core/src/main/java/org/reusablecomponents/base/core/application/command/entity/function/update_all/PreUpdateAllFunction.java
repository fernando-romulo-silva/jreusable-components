package org.reusablecomponents.base.core.application.command.entity.function.update_all;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A functional interface representing a pre-update operation for updating
 * multiple entities. This function is executed before the update operation
 * is performed and can be used to perform any necessary pre-processing
 * or validation on the input data.
 * 
 * @param <UpdateEntitiesIn> The type of the input data for the update
 *                           operation, typically a collection of entities.
 * @param <Object[]>         The type of the additional arguments that may be
 *                           needed for the update operation, such as context
 *                           information or related data.
 * 
 * @return The result of the pre-update operation, which may be the same as
 *         the input data or a modified version of it.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface PreUpdateAllFunction<UpdateEntitiesIn>
                extends CommandFunction, OperationFunction2Args<UpdateEntitiesIn, Object[], UpdateEntitiesIn> {
}