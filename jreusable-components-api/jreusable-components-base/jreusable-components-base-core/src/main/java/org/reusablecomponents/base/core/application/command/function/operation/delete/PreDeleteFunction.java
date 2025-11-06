package org.reusablecomponents.base.core.application.command.function.operation.delete;

import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
* 
*/
@FunctionalInterface
public non-sealed interface PreDeleteFunction<DeleteEntityIn>
        extends OperationFunction2Args<DeleteEntityIn, Object[], DeleteEntityIn> {
}