package org.reusablecomponents.base.core.application.command.entity.function.delete_all;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/*
* 
*/
@FunctionalInterface
public non-sealed interface DeleteAllFunction<DeleteEntitiesIn, DeleteEntitiesOut>
        extends CommandFunction, OperationFunction2Args<DeleteEntitiesIn, Object[], DeleteEntitiesOut> {
}