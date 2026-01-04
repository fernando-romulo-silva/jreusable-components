package org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_by_specification_paged;

import org.reusablecomponents.base.core.application.query.QueryFunction;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction4Args;

/**
* 
*/
@FunctionalInterface
public non-sealed interface ErrorFindBySpecificationPagedFunction<Specification, Pageable>
		extends QueryFunction, OperationFunction4Args<BaseException, Specification, Pageable, Object[], BaseException> {
}
