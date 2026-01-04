package org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_one_by_specification_sorted;

import org.reusablecomponents.base.core.application.query.QueryFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction3Args;

@FunctionalInterface
public non-sealed interface FindOneBySpecificationSortedFunction<Specification, Sort, OneResult>
                extends QueryFunction, OperationFunction3Args<Specification, Sort, Object[], OneResult> {
}
