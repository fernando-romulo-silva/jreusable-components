package org.reusablecomponents.base.core.application.command.function.operation.delete;

import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction3Args;

/**
* 
*/
@FunctionalInterface
public non-sealed interface ErrorDeleteFunction<DeleteEntityIn>
        extends OperationFunction3Args<BaseException, DeleteEntityIn, Object[], BaseException> {
}