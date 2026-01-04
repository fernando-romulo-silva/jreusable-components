package org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_one_by_specification_sorted;

import org.reusablecomponents.base.core.application.query.QueryFunction;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction4Args;

@FunctionalInterface
public non-sealed interface ErrorFindOneBySpecificationSortedFunction<Specification, Sort>
                extends QueryFunction,
                OperationFunction4Args<BaseException, Specification, Sort, Object[], BaseException> {
}
