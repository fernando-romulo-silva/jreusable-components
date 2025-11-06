package org.reusablecomponents.base.core.application.command.function.operation.save;

import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

@FunctionalInterface
public non-sealed interface PreSaveAllFunction<SaveEntitiesIn>
		extends OperationFunction2Args<SaveEntitiesIn, Object[], SaveEntitiesIn> {
}