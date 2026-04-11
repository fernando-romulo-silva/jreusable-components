package org.reusablecomponents.base.core.application.command.entity.function.save_all;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A functional interface representing a pre-save operation for saving multiple
 * entities. This function is executed before the actual save operation and can
 * be used to perform any necessary transformations or validations on the input
 * data.
 *
 * @param <SaveEntitiesIn> The type of the input data for the save operation,
 *                         typically a collection of entities.
 * @param <Object[]>       The type of the additional arguments that can be
 *                         passed to the function, if needed.
 * 
 * @return The transformed input data that will be used for the save operation.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface PreSaveAllFunction<SaveEntitiesIn>
		extends CommandFunction, OperationFunction2Args<SaveEntitiesIn, Object[], SaveEntitiesIn> {
}