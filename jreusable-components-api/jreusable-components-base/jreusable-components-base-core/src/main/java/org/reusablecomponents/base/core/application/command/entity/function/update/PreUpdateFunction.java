package org.reusablecomponents.base.core.application.command.entity.function.update;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
* 
*/
@FunctionalInterface
public non-sealed interface PreUpdateFunction<UpdateEntityIn>
        extends CommandFunction, OperationFunction2Args<UpdateEntityIn, Object[], UpdateEntityIn> {
}