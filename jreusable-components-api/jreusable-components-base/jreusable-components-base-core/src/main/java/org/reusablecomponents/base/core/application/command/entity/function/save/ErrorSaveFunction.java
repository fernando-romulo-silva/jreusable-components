package org.reusablecomponents.base.core.application.command.entity.function.save;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction3Args;

/**
 * A functional interface representing an error function for an entity save
 * operation. This function is executed when an error occurs during the save
 * operation and can be used to perform any necessary operations or
 * transformations on the exception or the entity involved in the save
 * operation.
 *
 * @param <SaveEntityIn> the type of the entity being saved
 * @param <Object[]>     the type of the additional arguments that may be needed
 * 
 * @return a {@link BaseException} that represents the error that occurred
 *         during the save operation
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
@FunctionalInterface
public non-sealed interface ErrorSaveFunction<SaveEntityIn>
		extends CommandFunction, OperationFunction3Args<BaseException, SaveEntityIn, Object[], BaseException> {
}