package org.reusablecomponents.base.core.application.command.entity.function.update_all;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A functional interface representing a post-update operation for updating
 * multiple entities. This function is executed after the update operation
 * has been completed and can be used to perform any necessary post-processing
 * or additional operations on the updated entities.
 * 
 * @param <UpdateEntitiesOut> The type of the output data for the update
 *                            operation, typically a collection of updated
 *                            entities.
 * @param <Object[]>          The type of the additional arguments that may be
 *                            needed for the update operation, such as context
 *                            information or related data.
 * 
 * @return The result of the post-update operation, which may be the same as
 *         the input data or a modified version of it.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface PosUpdateAllFunction<UpdateEntitiesOut>
                extends CommandFunction, OperationFunction2Args<UpdateEntitiesOut, Object[], UpdateEntitiesOut> {
}