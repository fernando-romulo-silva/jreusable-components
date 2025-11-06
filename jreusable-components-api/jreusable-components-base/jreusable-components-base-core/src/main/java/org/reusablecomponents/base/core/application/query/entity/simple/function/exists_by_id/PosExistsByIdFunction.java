package org.reusablecomponents.base.core.application.query.entity.simple.function.exists_by_id;

import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

@FunctionalInterface
public non-sealed interface PosExistsByIdFunction<ExistsResult>
                extends OperationFunction2Args<ExistsResult, Object[], ExistsResult> {

}
