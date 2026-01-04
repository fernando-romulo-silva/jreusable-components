package org.reusablecomponents.base.core.application.command.entity.function.delete_by_id;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
* 
*/
@FunctionalInterface
public non-sealed interface DeleteByIdFunction<DeleteIdIn, DeleteIdOut>
        extends CommandFunction, OperationFunction2Args<DeleteIdIn, Object[], DeleteIdOut> {
}