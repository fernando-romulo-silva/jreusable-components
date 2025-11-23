package org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_by_specification_paged;

import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

@FunctionalInterface
public non-sealed interface PosFindBySpecificationPagedFunction<MultiplePagedResult>
        extends OperationFunction2Args<MultiplePagedResult, Object[], MultiplePagedResult> {
}
