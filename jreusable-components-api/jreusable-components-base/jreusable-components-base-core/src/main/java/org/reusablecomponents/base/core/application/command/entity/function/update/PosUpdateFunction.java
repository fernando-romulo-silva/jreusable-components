package org.reusablecomponents.base.core.application.command.entity.function.update;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A functional interface representing a post-update function for an entity.
 * This function is executed after updating an entity and can be used to perform
 * any necessary operations or transformations on the updated entity after it
 * has been persisted.
 *
 * @param <UpdateEntityOut> the type of the entity being updated
 * @param <Object[]>        the type of the additional arguments that may be
 *                          needed for post-update operations, such as context
 *                          information or related data
 * 
 * @return the updated entity after any post-update operations have been applied
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface PosUpdateFunction<UpdateEntityOut>
		extends CommandFunction, OperationFunction2Args<UpdateEntityOut, Object[], UpdateEntityOut> {
}