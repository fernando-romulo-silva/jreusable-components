package org.reusablecomponents.base.core.application.command.entity.function.delete_all;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A functional interface representing a post delete all function for entities.
 * This function is executed after deleting a list of entities and can be used
 * to perform any necessary operations or transformations on the result of the
 * delete all operation after it has been executed.
 *
 * @param <DeleteEntitiesOut> The type of the result of the delete all
 *                            operation.
 * @param <Object[]>          The type of the additional arguments that can be
 *                            passed to the function, if needed.
 * 
 * @return The result of the delete all operation after any post-delete-all
 *         operations have been applied.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface PosDeleteAllFunction<DeleteEntitiesOut>
        extends CommandFunction, OperationFunction2Args<DeleteEntitiesOut, Object[], DeleteEntitiesOut> {

    /**
     * {@inheritDoc}
     */
    @Override
    default String getName() {
        return "PosDeleteAllFunction";
    }
}