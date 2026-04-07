package org.reusablecomponents.base.core.application.command.entity.function.save;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A function that saves an entity.
 * 
 * <p>
 * This function is used to save an entity. It takes the entity to be saved as
 * input and returns the saved entity as output.
 * <p>
 * 
 * @param <SaveEntityIn>  The type of the entity to be saved
 * @param <SaveEntityOut> The type of the saved entity
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0
 */
@FunctionalInterface
public non-sealed interface SaveFunction<SaveEntityIn, SaveEntityOut>
		extends CommandFunction, OperationFunction2Args<SaveEntityIn, Object[], SaveEntityOut> {
}