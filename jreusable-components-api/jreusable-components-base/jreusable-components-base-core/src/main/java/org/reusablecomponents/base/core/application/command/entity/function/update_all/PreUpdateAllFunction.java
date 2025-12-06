package org.reusablecomponents.base.core.application.command.entity.function.update_all;

import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
* 
*/
@FunctionalInterface
public non-sealed interface PreUpdateAllFunction<UpdateEntitiesIn>
        extends OperationFunction2Args<UpdateEntitiesIn, Object[], UpdateEntitiesIn> {
}