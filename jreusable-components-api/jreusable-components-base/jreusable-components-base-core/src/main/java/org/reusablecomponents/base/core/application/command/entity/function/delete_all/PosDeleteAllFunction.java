package org.reusablecomponents.base.core.application.command.entity.function.delete_all;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/*
* 
*/
@FunctionalInterface
public non-sealed interface PosDeleteAllFunction<DeleteEntitiesOut>
        extends CommandFunction, OperationFunction2Args<DeleteEntitiesOut, Object[], DeleteEntitiesOut> {
}