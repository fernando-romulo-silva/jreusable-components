package org.reusablecomponents.base.core.application.command.entity.function.save_all;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save.SaveFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A functional interface representing a save operation for saving multiple
 * entities (bulk version of {@link SaveFunction}). This function is executed to
 * perform the actual save operation and can be used to persist the input data
 * to a database or any other storage
 * mechanism.
 * 
 * @param <SaveEntitiesIn>  The type of the input collection of entities to be
 *                          saved.
 * @param <SaveEntitiesOut> The type of the output result of the save operation.
 * 
 * @param <Object[]>        The type of the additional arguments that can be
 *                          passed to the function, which can be used for
 *                          various purposes such as providing context or
 *                          additional data needed for the save operation.
 * 
 * @return the result of the save operation after any save operations have
 *         been applied.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface SaveAllFunction<SaveEntitiesIn, SaveEntitiesOut>
		extends CommandFunction, OperationFunction2Args<SaveEntitiesIn, Object[], SaveEntitiesOut> {
}