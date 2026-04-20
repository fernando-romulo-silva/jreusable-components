package org.reusablecomponents.base.core.infra.util.function.operation;

import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_by_specification_paged.ErrorFindBySpecificationPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_one_by_specification_sorted.ErrorFindOneBySpecificationSortedFunction;
import org.reusablecomponents.base.core.infra.util.function.QuadFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.custom.CustomOperation4Args;

/**
 * OperationFunction4Args is a functional interface that represents an operation
 * function with four arguments in the application.
 * 
 * It extends the QuadFunction interface and the OperationFunction interface,
 * providing additional methods for describing the operation, controlling its
 * execution, and handling exceptions.
 * 
 * This interface can be used as a base for creating specific operation function
 * implementations that require four arguments like find by specification paged
 * and find one by specification sorted operations.
 * 
 * @param <In1> The type of the first input to the function
 * @param <In2> The type of the second input to the function
 * @param <In3> The type of the third input to the function
 * @param <In4> The type of the fourth input to the function
 * @param <Out> The type of the result of the function
 * 
 * @see OperationFunction
 * @see QuadFunction
 */
public sealed interface OperationFunction4Args<In1, In2, In3, In4, Out>
                extends QuadFunction<In1, In2, In3, In4, Out>, OperationFunction
                permits CustomOperation4Args,
                ErrorFindOneBySpecificationSortedFunction, ErrorFindBySpecificationPagedFunction {

}