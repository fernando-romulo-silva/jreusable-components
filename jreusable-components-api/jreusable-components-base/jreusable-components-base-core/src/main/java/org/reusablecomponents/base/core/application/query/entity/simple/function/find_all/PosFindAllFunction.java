package org.reusablecomponents.base.core.application.query.entity.simple.function.find_all;

import org.reusablecomponents.base.core.application.query.QueryFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

@FunctionalInterface
public non-sealed interface PosFindAllFunction<MultipleResult>
                extends OperationFunction2Args<MultipleResult, Object[], MultipleResult>, QueryFunction {

}
