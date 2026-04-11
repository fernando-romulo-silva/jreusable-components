package org.reusablecomponents.base.core.application.command.entity.function.update_all;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A functional interface representing the main operation for updating multiple
 * entities. This function is responsible for performing the actual update
 * operation on the collection of entities and returning the result of the
 * update, which may be a modified collection of entities or any other relevant
 * output.
 * 
 * @param <UpdateEntitiesIn>  The type of the input collection of entities to be
 *                            updated.
 * @param <UpdateEntitiesOut> The type of the output result of the update
 *                            operation.
 * @param <Object[]>          The type of the additional arguments that may be
 *                            needed for the update operation, such as context
 *                            information or related data.
 * 
 * @return The result of the update operation, which may be a modified
 *         collection of entities or any other relevant output.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface UpdateAllFunction<UpdateEntitiesIn, UpdateEntitiesOut>
        extends CommandFunction, OperationFunction2Args<UpdateEntitiesIn, Object[], UpdateEntitiesOut> {
}