package org.reusablecomponents.base.core.application.command.entity.function.delete_by_id;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A function that deletes an entity by its id.
 * 
 * <p>
 * This function is used in the <code>DeleteByIdCommand</code> to perform the
 * delete operation.
 * <p>
 * 
 * @param <DeleteIdIn>  The input id type for the delete by id operation
 * @param <DeleteIdOut> The output type for the delete by id operation, like
 *                      void, Boolean, or a wrap type like Mono<Void> or
 *                      Mono<Boolean>
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0
 * 
 * @see DeleteByIdCommand
 */
@FunctionalInterface
public non-sealed interface DeleteByIdFunction<DeleteIdIn, DeleteIdOut>
                extends CommandFunction, OperationFunction2Args<DeleteIdIn, Object[], DeleteIdOut> {
}