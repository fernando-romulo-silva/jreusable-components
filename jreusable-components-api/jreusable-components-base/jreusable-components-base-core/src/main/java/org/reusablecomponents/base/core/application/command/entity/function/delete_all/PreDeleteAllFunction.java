package org.reusablecomponents.base.core.application.command.entity.function.delete_all;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A functional interface representing a pre delete all function for entities.
 * This function is executed before deleting a list of entities and can be used
 * to perform any necessary operations or transformations on the input data for
 * the delete all operation before it is executed.
 *
 * @param <DeleteEntitiesIn> The type of the input data for the delete all
 *                           operation, such as a filter or criteria object.
 * @param <Object[]>         The type of the additional arguments that can be
 *                           passed to the function, if needed.
 * 
 * @return The transformed input data for the delete all operation after any
 *         pre-delete-all operations have been applied.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface PreDeleteAllFunction<DeleteEntitiesIn>
        extends CommandFunction, OperationFunction2Args<DeleteEntitiesIn, Object[], DeleteEntitiesIn> {

    /**
     * {@inheritDoc}
     */
    @Override
    default String getName() {
        return "PreDeleteAllFunction";
    }
}