package org.reusablecomponents.base.core.application.command.entity.function.update_all;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction3Args;

/**
* 
*/
@FunctionalInterface
public non-sealed interface ErrorUpdateAllFunction<UpdateEntitiesIn>
        extends CommandFunction, OperationFunction3Args<BaseException, UpdateEntitiesIn, Object[], BaseException> {
}