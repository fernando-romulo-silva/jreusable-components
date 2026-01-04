package org.reusablecomponents.base.core.application.command.entity.function.save;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
* 
*/
@FunctionalInterface
public non-sealed interface SaveFunction<SaveEntityIn, SaveEntityOut>
		extends CommandFunction, OperationFunction2Args<SaveEntityIn, Object[], SaveEntityOut> {
}