package org.reusablecomponents.base.core.application.command.entity.function.save;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A functional interface representing a post-save function for an entity.
 * This function is executed after saving an entity and can be used to perform
 * any necessary operations or transformations on the saved entity after it has
 * been persisted.
 *
 * @param <SaveEntityOut> the type of the entity being saved
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0
 */
@FunctionalInterface
public non-sealed interface PosSaveFunction<SaveEntityOut>
		extends CommandFunction, OperationFunction2Args<SaveEntityOut, Object[], SaveEntityOut> {
}