package org.reusablecomponents.base.core.application.command.entity.function.delete_by_id_all;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A functional interface representing a post-function for a delete by IDs
 * operation. This function is executed after the delete by IDs operation has
 * been performed and can be used to perform any necessary operations or
 * transformations on the result of the delete by IDs operation or any relevant
 * information about the operation.
 * 
 * @param <DeleteIdsOut> The type of the output result of the delete by IDs
 *                       operation, which can be a confirmation of deletion, the
 *                       deleted IDs, or any relevant information about the
 *                       operation.
 * @param <Object[]>     The type of the additional arguments that can be
 *                       passed to the function, if needed.
 * 
 * @return The result of the post-function operations, which can be a modified
 *         version of the delete by IDs operation result or any relevant
 *         information about the operation.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface PosDeleteByIdsFunction<DeleteIdsOut>
        extends CommandFunction, OperationFunction2Args<DeleteIdsOut, Object[], DeleteIdsOut> {

    /**
     * {@inheritDoc}
     */
    @Override
    default String getName() {
        return "ErrorDeleteByIdsFunction";
    }
}