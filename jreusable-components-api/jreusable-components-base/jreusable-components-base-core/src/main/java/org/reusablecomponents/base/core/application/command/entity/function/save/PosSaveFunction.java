package org.reusablecomponents.base.core.application.command.entity.function.save;

import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
* 
*/
@FunctionalInterface
public non-sealed interface PosSaveFunction<SaveEntityOut>
		extends OperationFunction2Args<SaveEntityOut, Object[], SaveEntityOut> {
}