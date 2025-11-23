package org.reusablecomponents.base.core.application.query.entity.pagination.function.find_all_paged;

import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

@FunctionalInterface
public non-sealed interface PreFindAllPagedFunction<Pageable>
        extends OperationFunction2Args<Pageable, Object[], Pageable> {
}
