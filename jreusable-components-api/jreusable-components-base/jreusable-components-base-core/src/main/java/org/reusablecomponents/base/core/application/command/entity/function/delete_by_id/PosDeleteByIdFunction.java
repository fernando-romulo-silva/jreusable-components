package org.reusablecomponents.base.core.application.command.entity.function.delete_by_id;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A functional interface representing a post-function for a delete by ID
 * operation. This function is executed after the delete by id operation has
 * been performed and can be used to perform any necessary operations or
 * transformations on the result of the delete by id operation or any relevant
 * information about the operation.
 * 
 * @param <DeleteIdOut> The type of the output result of the delete by id
 *                      operation, which can be a confirmation of deletion, the
 *                      deleted id, or any relevant information about the
 *                      operation.
 * @param <Object[]>    The type of the additional arguments that can be
 *                      passed to the function, if needed.
 * 
 * @return The result of the post-function operations, which can be a modified
 *         version of the delete by id operation result or any relevant
 *         information about the operation.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface PosDeleteByIdFunction<DeleteIdOut>
        extends CommandFunction, OperationFunction2Args<DeleteIdOut, Object[], DeleteIdOut> {

    /**
     * {@inheritDoc}
     */
    @Override
    default String getName() {
        return "PosDeleteByIdFunction";
    }
}