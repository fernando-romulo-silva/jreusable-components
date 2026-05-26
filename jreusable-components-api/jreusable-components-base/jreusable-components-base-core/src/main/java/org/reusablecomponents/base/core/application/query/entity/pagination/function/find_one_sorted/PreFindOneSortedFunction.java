package org.reusablecomponents.base.core.application.query.entity.pagination.function.find_one_sorted;

import org.reusablecomponents.base.core.application.query.QueryFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

@FunctionalInterface
public non-sealed interface PreFindOneSortedFunction<Sort>
		extends QueryFunction, OperationFunction2Args<Sort, Object[], Sort> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	default String getName() {
		return "PreFindOneSortedFunction";
	}
}
