package org.reusablecomponents.base.core.application.command.entity.function.delete_by_id_all;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * This function is used to delete entities by their IDs.
 * The output is the result of the delete operation, which can be a confirmation
 * of deletion, the deleted ids, or any relevant information about the
 * operation.
 * 
 * @param <DeleteIdsIn>  The type of the input used to identify and delete
 *                       the entities.
 * @param <DeleteIdsOut> The type of the output result of the delete
 *                       operation.
 * @param <Object[]>     The type of the additional arguments that can be
 *                       passed to the function.
 * 
 * @return The result of the delete operation, which can be a confirmation of
 *         deletion, the deleted ids, or any relevant information about the
 *         operation.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface DeleteByIdsFunction<DeleteIdsIn, DeleteIdsOut>
        extends CommandFunction, OperationFunction2Args<DeleteIdsIn, Object[], DeleteIdsOut> {
    /**
     * {@inheritDoc}
     */
    @Override
    default String getName() {
        return "DeleteByIdsFunction";
    }
}