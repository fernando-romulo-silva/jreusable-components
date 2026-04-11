package org.reusablecomponents.base.core.application.command.entity.function.save_all;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A functional interface representing a post-save operation for saving multiple
 * entities. This function is executed after the actual save operation and can
 * be used to perform any necessary transformations or validations on the output
 * data.
 * 
 * @param <SaveEntitiesOut> The type of the output data for the save operation,
 *                          typically a collection of entities.
 * @param <Object[]>        The type of the additional arguments that can be
 *                          passed to the function, which can be used for
 *                          various purposes such as providing context or
 *                          additional data needed for the post-save operation.
 * 
 * @return The result of the post-save operation, which can be the transformed
 *         output data.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface PosSaveAllFunction<SaveEntitiesOut>
		extends CommandFunction, OperationFunction2Args<SaveEntitiesOut, Object[], SaveEntitiesOut> {
}