package org.reusablecomponents.base.core.application.command.entity.function.update;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
* 
*/
@FunctionalInterface
public non-sealed interface PosUpdateFunction<UpdateEntityOut>
        extends CommandFunction, OperationFunction2Args<UpdateEntityOut, Object[], UpdateEntityOut> {
}