package org.reusablecomponents.base.core.application.command.entity.function.save_all;

import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction3Args;

/**
 * 
 */
@FunctionalInterface
public non-sealed interface ErrorSaveAllFunction<SaveEntitiesIn>
		extends OperationFunction3Args<BaseException, SaveEntitiesIn, Object[], BaseException> {
}