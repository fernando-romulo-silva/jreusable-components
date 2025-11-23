package org.reusablecomponents.base.core.application.query.entity.specification.function.count_by_spec;

import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

@FunctionalInterface
public non-sealed interface PreCountBySpecificationFunction<Specification>
                extends OperationFunction2Args<Specification, Object[], Specification> {
}
