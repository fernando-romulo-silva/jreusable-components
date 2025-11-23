package org.reusablecomponents.base.core.application.query.entity.specification.function.exists_by_spec;

import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction3Args;

@FunctionalInterface
public non-sealed interface ErrorExistsBySpecificationFunction<Specification>
                extends OperationFunction3Args<BaseException, Specification, Object[], BaseException> {

}
