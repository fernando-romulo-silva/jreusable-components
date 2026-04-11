package org.reusablecomponents.base.core.application.command.entity.function.delete;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * This function is used to delete an entity based on the provided input, which
 * can be the entity itself, its id, or any relevant information needed to
 * identify and delete the entity. The output is the result of the delete
 * operation, which can be a confirmation of deletion, the deleted entity, or
 * any relevant information about the operation.
 * 
 * @param <DeleteEntityIn>  The type of the input used to identify and delete
 *                          the entity.
 * @param <DeleteEntityOut> The type of the output result of the delete
 *                          operation.
 * @param <Object[]>        The type of the additional arguments that can be
 *                          passed to the function, if needed.
 * 
 * @return The result of the delete operation, which can be a confirmation of
 *         deletion, the deleted entity, or any relevant information about the
 *         operation.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface DeleteFunction<DeleteEntityIn, DeleteEntityOut>
                extends CommandFunction, OperationFunction2Args<DeleteEntityIn, Object[], DeleteEntityOut> {

}