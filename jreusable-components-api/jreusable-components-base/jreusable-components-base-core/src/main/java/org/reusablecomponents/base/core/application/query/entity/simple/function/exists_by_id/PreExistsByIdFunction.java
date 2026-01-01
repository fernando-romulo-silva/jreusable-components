package org.reusablecomponents.base.core.application.query.entity.simple.function.exists_by_id;

import org.reusablecomponents.base.core.application.query.entity.simple.QueryFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

@FunctionalInterface
public non-sealed interface PreExistsByIdFunction<ExistsIdIn>
        extends OperationFunction2Args<ExistsIdIn, Object[], ExistsIdIn>, QueryFunction {

}
