package org.reusablecomponents.base.core.application.command.entity.function.delete_by_id_all;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
* 
*/
@FunctionalInterface
public non-sealed interface PosDeleteByIdsFunction<DeleteIdsOut>
        extends CommandFunction, OperationFunction2Args<DeleteIdsOut, Object[], DeleteIdsOut> {
}