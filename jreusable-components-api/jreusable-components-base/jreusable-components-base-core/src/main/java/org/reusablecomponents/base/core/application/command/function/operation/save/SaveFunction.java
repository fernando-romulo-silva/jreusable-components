package org.reusablecomponents.base.core.application.command.function.operation.save;

import java.util.function.Function;

import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
* 
*/
@FunctionalInterface
public non-sealed interface SaveFunction<SaveEntityIn, SaveEntityOut>
		extends OperationFunction2Args<SaveEntityIn, Object[], SaveEntityOut> {
}