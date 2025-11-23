package org.reusablecomponents.base.core.application.query.entity.pagination.function.find_one_sorted;

import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction3Args;

@FunctionalInterface
public non-sealed interface ErrorFindOneSortedFunction<BaseException, Sort>
        extends OperationFunction3Args<BaseException, Sort, Object[], BaseException> {
}
