package org.reusablecomponents.base.core.application.query.entity.simple.function.count_all;

import org.reusablecomponents.base.core.application.query.entity.simple.QueryFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

@FunctionalInterface
public non-sealed interface PosCountAllFunction<CountResult>
        extends OperationFunction2Args<CountResult, Object[], CountResult>, QueryFunction {

}
