package org.reusablecomponents.base.core.application.command.entity.function.save_all;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A function that saves multiple entities.
 * 
 * <p>
 * This function is used to save multiple entities in a single operation.
 * The input is a collection of entities to be saved, and the output is the
 * result of the save operation, which can be the saved entities or any relevant
 * information about the operation.
 * 
 * @param <SaveEntitiesIn>  The type of the input collection of entities to be
 *                          saved.
 * @param <SaveEntitiesOut> The type of the output result of the save operation.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0
 */
@FunctionalInterface
public non-sealed interface SaveAllFunction<SaveEntitiesIn, SaveEntitiesOut>
		extends CommandFunction, OperationFunction2Args<SaveEntitiesIn, Object[], SaveEntitiesOut> {
}