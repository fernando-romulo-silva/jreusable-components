package org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_one_by_specification_sorted;

import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

@FunctionalInterface
public non-sealed interface PosFindOneBySpecificationSortedFunction<OneResult>
        extends OperationFunction2Args<OneResult, Object[], OneResult> {

}
