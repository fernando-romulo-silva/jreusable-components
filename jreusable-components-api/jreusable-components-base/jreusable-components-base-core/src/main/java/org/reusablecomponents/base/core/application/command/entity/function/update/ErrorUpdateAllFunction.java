package org.reusablecomponents.base.core.application.command.entity.function.update;

import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction3Args;

/**
* 
*/
@FunctionalInterface
public non-sealed interface ErrorUpdateAllFunction<UpdateEntitiesIn>
        extends OperationFunction3Args<BaseException, UpdateEntitiesIn, Object[], BaseException> {
}