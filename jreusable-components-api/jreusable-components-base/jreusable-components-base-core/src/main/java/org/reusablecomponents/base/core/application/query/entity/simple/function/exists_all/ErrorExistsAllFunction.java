package org.reusablecomponents.base.core.application.query.entity.simple.function.exists_all;

import org.reusablecomponents.base.core.application.query.entity.simple.QueryFunction;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

@FunctionalInterface
public non-sealed interface ErrorExistsAllFunction
		extends OperationFunction2Args<BaseException, Object[], BaseException>, QueryFunction {

}
