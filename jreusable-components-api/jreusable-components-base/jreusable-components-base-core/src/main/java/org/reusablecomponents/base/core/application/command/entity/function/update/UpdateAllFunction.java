package org.reusablecomponents.base.core.application.command.entity.function.update;

import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
* 
*/
@FunctionalInterface
public non-sealed interface UpdateAllFunction<UpdateEntitiesIn, UpdateEntitiesOut>
        extends OperationFunction2Args<UpdateEntitiesIn, Object[], UpdateEntitiesOut> {
}