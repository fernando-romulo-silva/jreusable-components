package org.reusablecomponents.base.core.application.query.entity.specification.function.exists_by_spec;

import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

@FunctionalInterface
public non-sealed interface PosExistsBySpecificationFunction<ExistsResult>
                extends OperationFunction2Args<ExistsResult, Object[], ExistsResult> {
}
