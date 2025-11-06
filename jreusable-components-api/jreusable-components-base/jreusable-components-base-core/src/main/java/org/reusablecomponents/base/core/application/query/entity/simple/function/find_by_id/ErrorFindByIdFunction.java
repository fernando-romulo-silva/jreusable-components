package org.reusablecomponents.base.core.application.query.entity.simple.function.find_by_id;

import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction3Args;

/**
* 
*/
@FunctionalInterface
public non-sealed interface ErrorFindByIdFunction<QueryIdIn>
		extends OperationFunction3Args<BaseException, QueryIdIn, Object[], BaseException> {
}