package org.reusablecomponents.base.core.application.query.entity.simple.function.find_by_id;

import org.reusablecomponents.base.core.application.query.QueryFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A functional interface representing a pre-processing function for a "find by
 * ID" query operation.
 * This function is intended to be used before executing a "find by ID" query,
 * allowing for any necessary transformations or validations on the query ID and
 * additional arguments.
 * 
 * 
 * @param <QueryIdIn> the type of the query ID input
 */
@FunctionalInterface
public non-sealed interface PreFindByIdFunction<QueryIdIn>
        extends OperationFunction2Args<QueryIdIn, Object[], QueryIdIn>, QueryFunction {
    /**
     * {@inheritDoc}
     */
    @Override
    default String getName() {
        return "PreFindByIdFunction";
    }
}
