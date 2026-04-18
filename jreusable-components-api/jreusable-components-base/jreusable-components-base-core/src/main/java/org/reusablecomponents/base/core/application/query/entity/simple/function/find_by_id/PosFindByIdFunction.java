package org.reusablecomponents.base.core.application.query.entity.simple.function.find_by_id;

import org.reusablecomponents.base.core.application.query.QueryFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A functional interface representing a post-processing function for a "find by
 * ID" query operation.
 * This function is intended to be used after executing a "find by ID" query,
 * allowing for any necessary transformations or validations on the query result
 * and additional arguments.
 * 
 * 
 * @param <OneResult> the type of the query result
 * @param <Object[]>  The type of the additional arguments that may be used
 */
@FunctionalInterface
public non-sealed interface PosFindByIdFunction<OneResult>
        extends OperationFunction2Args<OneResult, Object[], OneResult>, QueryFunction {

    @Override
    default String getName() {
        return "PosFindByIdFunction";
    }
}
