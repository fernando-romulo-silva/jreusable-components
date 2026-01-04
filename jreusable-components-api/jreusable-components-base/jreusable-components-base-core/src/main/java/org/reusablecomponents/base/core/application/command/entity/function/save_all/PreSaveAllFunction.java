package org.reusablecomponents.base.core.application.command.entity.function.save_all;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

@FunctionalInterface
public non-sealed interface PreSaveAllFunction<SaveEntitiesIn>
		extends CommandFunction, OperationFunction2Args<SaveEntitiesIn, Object[], SaveEntitiesIn> {
}