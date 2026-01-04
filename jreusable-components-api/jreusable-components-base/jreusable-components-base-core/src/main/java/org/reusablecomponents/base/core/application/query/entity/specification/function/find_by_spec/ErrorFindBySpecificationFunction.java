package org.reusablecomponents.base.core.application.query.entity.specification.function.find_by_spec;

import org.reusablecomponents.base.core.application.query.QueryFunction;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction3Args;

@FunctionalInterface
public non-sealed interface ErrorFindBySpecificationFunction<Specification>
		extends QueryFunction, OperationFunction3Args<BaseException, Specification, Object[], BaseException> {
}
