package org.reusablecomponents.base.core.application.command.entity.function.update_all;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A function that updates multiple entities.
 * 
 * <p>
 * This function is used to update multiple entities in a single operation.
 * The input is a collection of entities to be updated, and the output is the
 * result of the update operation, which can be the updated entities or any
 * relevant information about the operation.
 * </p>
 * 
 * @param <UpdateEntitiesIn>  The type of the input collection of entities to be
 *                            updated.
 * @param <UpdateEntitiesOut> The type of the output result of the update
 *                            operation.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0
 */
@FunctionalInterface
public non-sealed interface UpdateAllFunction<UpdateEntitiesIn, UpdateEntitiesOut>
                extends CommandFunction, OperationFunction2Args<UpdateEntitiesIn, Object[], UpdateEntitiesOut> {
}