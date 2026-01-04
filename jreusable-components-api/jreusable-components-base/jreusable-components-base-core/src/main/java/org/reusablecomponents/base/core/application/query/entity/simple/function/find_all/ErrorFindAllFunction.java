package org.reusablecomponents.base.core.application.query.entity.simple.function.find_all;

import org.reusablecomponents.base.core.application.query.QueryFunction;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

public non-sealed interface ErrorFindAllFunction
                extends OperationFunction2Args<BaseException, Object[], BaseException>, QueryFunction {

}
