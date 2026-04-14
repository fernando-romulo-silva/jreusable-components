package org.reusablecomponents.base.core.application.command.entity.function.delete_all;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction3Args;

/**
 * A functional interface representing an error function for a delete all
 * operation in a command facade. This function is used to handle errors that
 * may occur during the execution of the delete all operation.
 * 
 * @param <DeleteEntitiesIn> The type of the input used to identify and delete
 *                           the entities.
 * @param <Object[]>         The type of the additional arguments that can be
 *                           passed to the function, if needed.
 * 
 * @return A {@link BaseException} that represents the error that occurred
 *         during the delete all operation.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface ErrorDeleteAllFunction<DeleteEntitiesIn>
		extends CommandFunction, OperationFunction3Args<BaseException, DeleteEntitiesIn, Object[], BaseException> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	default String getName() {
		return "ErrorDeleteAllFunction";
	}
}