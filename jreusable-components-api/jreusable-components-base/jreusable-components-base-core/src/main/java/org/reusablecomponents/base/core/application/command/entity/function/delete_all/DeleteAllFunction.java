package org.reusablecomponents.base.core.application.command.entity.function.delete_all;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * This function is used to delete a list of entities based on the provided
 * input, which can be the entities themselves, their ids, or any relevant
 * information needed to identify and delete the entities. The output is the
 * result of the delete operation, which can be a confirmation of deletion, the
 * deleted entities, or any relevant information about the operation.
 * 
 * @param <DeleteEntitiesIn>  The input type for the delete all operation, like
 *                            a filter or criteria object
 * @param <DeleteEntitiesOut> The output type for the delete all operation, like
 *                            the number of deleted entities or a status object
 * @param <Object[]>          The type of the additional arguments that can be
 *                            passed to the function, if needed.
 * 
 * @return The result of the delete all operation, which can be a confirmation
 *         of deletion, the deleted entities, or any relevant information about
 *         the operation.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface DeleteAllFunction<DeleteEntitiesIn, DeleteEntitiesOut>
                extends CommandFunction, OperationFunction2Args<DeleteEntitiesIn, Object[], DeleteEntitiesOut> {
}