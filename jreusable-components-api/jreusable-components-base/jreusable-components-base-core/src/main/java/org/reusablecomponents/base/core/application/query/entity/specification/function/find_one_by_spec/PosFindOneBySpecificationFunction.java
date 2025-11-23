package org.reusablecomponents.base.core.application.query.entity.specification.function.find_one_by_spec;

import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

@FunctionalInterface
public non-sealed interface PosFindOneBySpecificationFunction<OneResult>
                extends OperationFunction2Args<OneResult, Object[], OneResult> {

}
