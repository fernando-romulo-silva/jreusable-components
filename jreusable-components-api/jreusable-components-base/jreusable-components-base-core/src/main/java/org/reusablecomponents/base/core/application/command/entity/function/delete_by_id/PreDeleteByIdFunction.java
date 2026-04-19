package org.reusablecomponents.base.core.application.command.entity.function.delete_by_id;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A functional interface representing a pre-function for a delete by ID
 * operation. This function is executed before the delete by id operation is
 * performed and can be used to perform any necessary operations or
 * transformations on the input of the delete by id operation or any relevant
 * information about the operation.
 * 
 * @param <DeleteIdIn> The type of the input used to identify and delete
 *                     the entity.
 * @param <Object[]>   The type of the additional arguments that can be
 *                     passed to the function, if needed.
 * 
 * @return The result of the pre-function operations, which can be a modified
 *         version of the delete by id operation input or any relevant
 *         information about the operation.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface PreDeleteByIdFunction<DeleteIdIn>
        extends CommandFunction, OperationFunction2Args<DeleteIdIn, Object[], DeleteIdIn> {

    /**
     * {@inheritDoc}
     */
    @Override
    default String getName() {
        return "PreDeleteByIdFunction";
    }
}