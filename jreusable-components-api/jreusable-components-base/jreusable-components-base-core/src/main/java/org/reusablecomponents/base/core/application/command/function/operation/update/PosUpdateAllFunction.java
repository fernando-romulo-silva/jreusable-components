package org.reusablecomponents.base.core.application.command.function.operation.update;

import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
* 
*/
@FunctionalInterface
public non-sealed interface PosUpdateAllFunction<UpdateEntitiesOut>
        extends OperationFunction2Args<UpdateEntitiesOut, Object[], UpdateEntitiesOut> {
}