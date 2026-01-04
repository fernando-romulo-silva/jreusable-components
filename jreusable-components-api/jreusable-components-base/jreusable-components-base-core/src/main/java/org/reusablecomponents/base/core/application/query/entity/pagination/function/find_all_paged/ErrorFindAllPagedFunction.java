package org.reusablecomponents.base.core.application.query.entity.pagination.function.find_all_paged;

import org.reusablecomponents.base.core.application.query.QueryFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction3Args;

@FunctionalInterface
public non-sealed interface ErrorFindAllPagedFunction<BaseException, Pageable>
		extends QueryFunction, OperationFunction3Args<BaseException, Pageable, Object[], BaseException> {
}
