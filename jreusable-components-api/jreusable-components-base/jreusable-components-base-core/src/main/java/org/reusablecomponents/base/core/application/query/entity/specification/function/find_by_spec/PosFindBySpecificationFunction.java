package org.reusablecomponents.base.core.application.query.entity.specification.function.find_by_spec;

import org.reusablecomponents.base.core.application.query.QueryFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

@FunctionalInterface
public non-sealed interface PosFindBySpecificationFunction<MultipleResult>
                extends QueryFunction, OperationFunction2Args<MultipleResult, Object[], MultipleResult> {

}
