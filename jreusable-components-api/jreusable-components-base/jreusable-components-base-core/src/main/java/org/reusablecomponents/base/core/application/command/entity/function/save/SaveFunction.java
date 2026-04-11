package org.reusablecomponents.base.core.application.command.entity.function.save;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A functional interface representing a save function for an entity. This
 * function is executed to perform the save operation for an entity and can be
 * used to perform any necessary operations or transformations on the entity
 * before it is saved, as well as to handle any additional arguments that may be
 * needed for the save operation, such as context information or related data.
 * 
 * @param <SaveEntityIn>  The type of the entity to be saved
 * @param <SaveEntityOut> The type of the saved entity
 * @param <Object[]>      The type of the additional arguments that may be
 *                        needed for the save operation, such as context
 *                        information or related data
 * 
 * @return the saved entity after any save operations have been applied
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0
 */
@FunctionalInterface
public non-sealed interface SaveFunction<SaveEntityIn, SaveEntityOut>
		extends CommandFunction, OperationFunction2Args<SaveEntityIn, Object[], SaveEntityOut> {
}