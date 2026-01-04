package org.reusablecomponents.base.core.application.query.entity.simple.function.count_all;

import org.reusablecomponents.base.core.application.query.QueryFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction1Args;

@FunctionalInterface
public non-sealed interface PreCountAllFunction extends OperationFunction1Args<Object[], Object[]>, QueryFunction {

}
