package org.reusablecomponents.base.core.application.command.entity.function.delete_by_id_all;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A function that deletes entities by their ids.
 * 
 * <p>
 * This function is used to delete multiple entities based on their ids. It
 * takes an input of type <code>DeleteIdsIn</code>, which represents the ids to
 * be deleted, and returns a result of type <code>DeleteIdsOut</code>, which
 * represents the outcome of the delete operation.
 * 
 * @param <DeleteIdsIn>  The input type for the delete operation, typically a
 *                       collection of ids or a wrapper around it.
 * @param <DeleteIdsOut> The output type for the delete operation, typically a
 *                       boolean indicating success or failure, or a count of
 *                       deleted entities.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0
 */
@FunctionalInterface
public non-sealed interface DeleteByIdsFunction<DeleteIdsIn, DeleteIdsOut>
                extends CommandFunction, OperationFunction2Args<DeleteIdsIn, Object[], DeleteIdsOut> {
}