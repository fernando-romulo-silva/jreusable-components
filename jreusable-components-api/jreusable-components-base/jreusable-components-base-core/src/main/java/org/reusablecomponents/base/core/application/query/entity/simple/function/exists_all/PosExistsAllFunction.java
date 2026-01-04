package org.reusablecomponents.base.core.application.query.entity.simple.function.exists_all;

import org.reusablecomponents.base.core.application.query.QueryFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

public non-sealed interface PosExistsAllFunction<ExistsResult>
                extends OperationFunction2Args<ExistsResult, Object[], ExistsResult>, QueryFunction {

}
