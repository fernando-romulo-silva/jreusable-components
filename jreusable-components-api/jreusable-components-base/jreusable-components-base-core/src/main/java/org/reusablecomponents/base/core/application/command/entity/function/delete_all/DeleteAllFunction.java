package org.reusablecomponents.base.core.application.command.entity.function.delete_all;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A function that deletes all entities of a given type.
 * 
 * @param <DeleteEntitiesIn>  The input type for the delete all operation, like
 *                            a filter or criteria object
 * @param <DeleteEntitiesOut> The output type for the delete all operation, like
 *                            the number of deleted entities or a status object
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0
 */
@FunctionalInterface
public non-sealed interface DeleteAllFunction<DeleteEntitiesIn, DeleteEntitiesOut>
                extends CommandFunction, OperationFunction2Args<DeleteEntitiesIn, Object[], DeleteEntitiesOut> {
}