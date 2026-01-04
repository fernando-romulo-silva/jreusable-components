package org.reusablecomponents.base.core.application.query.entity.simple.function.find_all;

import org.reusablecomponents.base.core.application.query.QueryFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction1Args;

@FunctionalInterface
public non-sealed interface FindAllFunction<MultipleResult>
                extends OperationFunction1Args<Object[], MultipleResult>, QueryFunction {

}
