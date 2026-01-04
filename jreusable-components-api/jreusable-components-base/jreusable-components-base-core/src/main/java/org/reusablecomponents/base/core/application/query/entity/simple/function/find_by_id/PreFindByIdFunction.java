package org.reusablecomponents.base.core.application.query.entity.simple.function.find_by_id;

import org.reusablecomponents.base.core.application.query.QueryFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

@FunctionalInterface
public non-sealed interface PreFindByIdFunction<QueryIdIn>
                extends OperationFunction2Args<QueryIdIn, Object[], QueryIdIn>, QueryFunction {
}
