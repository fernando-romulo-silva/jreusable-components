package org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_by_specification_paged;

import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction3Args;

@FunctionalInterface
public non-sealed interface FindBySpecificationPagedFunction<Specification, Pageable, MultiplePagedResult>
        extends OperationFunction3Args<Specification, Pageable, Object[], MultiplePagedResult> {
}
