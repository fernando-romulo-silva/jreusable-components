package org.reusablecomponents.base.core.application.command.entity.function.delete;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
* 
*/
@FunctionalInterface
public non-sealed interface DeleteFunction<DeleteEntityIn, DeleteEntityOut>
        extends CommandFunction, OperationFunction2Args<DeleteEntityIn, Object[], DeleteEntityOut> {

}