package org.reusablecomponents.base.core.application.command.entity.function.delete;

import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/*
* 
*/
@FunctionalInterface
public non-sealed interface PreDeleteAllFunction<DeleteEntitiesIn>
        extends OperationFunction2Args<DeleteEntitiesIn, Object[], DeleteEntitiesIn> {
}