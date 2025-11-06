package org.reusablecomponents.base.core.application.command.function.operation.save;

import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction3Args;

/**
* 
*/
@FunctionalInterface
public non-sealed interface ErrorSaveFunction<SaveEntityIn>
		extends OperationFunction3Args<BaseException, SaveEntityIn, Object[], BaseException> {
}