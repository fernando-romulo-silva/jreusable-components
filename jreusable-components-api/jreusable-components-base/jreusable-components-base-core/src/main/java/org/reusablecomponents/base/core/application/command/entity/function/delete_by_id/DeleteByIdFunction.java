package org.reusablecomponents.base.core.application.command.entity.function.delete_by_id;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * This function is used to delete an entity by its ID.
 * The output is the result of the delete operation, which can be a confirmation
 * of deletion, the deleted id, or any relevant information about the operation.
 * 
 * @param <DeleteIdIn>  The type of the input used to identify and delete
 *                      the entity.
 * @param <DeleteIdOut> The type of the output result of the delete
 *                      operation.
 * @param <Object[]>    The type of the additional arguments that can be
 *                      passed to the function.
 * 
 * @return The result of the delete operation, which can be a confirmation of
 *         deletion, the deleted id, or any relevant information about the
 *         operation.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface DeleteByIdFunction<DeleteIdIn, DeleteIdOut>
        extends CommandFunction, OperationFunction2Args<DeleteIdIn, Object[], DeleteIdOut> {

    /**
     * {@inheritDoc}
     */
    @Override
    default String getName() {
        return "DeleteByIdFunction";
    }

}