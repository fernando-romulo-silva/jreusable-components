package org.reusablecomponents.base.core.infra.util.function.operation;

import java.util.function.Function;

import org.reusablecomponents.base.core.application.query.entity.simple.function.count_all.CountAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.count_all.PreCountAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_all.ExistsAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_all.PreExistsAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_all.FindAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_all.PreFindAllFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.custom.CustomOperation1Args;

/**
 * OperationFunction1Args is a functional interface that represents an operation
 * function with one argument in the application.
 * 
 * It extends the Function interface and the OperationFunction interface,
 * providing additional methods for describing the operation, controlling its
 * execution, and handling exceptions.
 * 
 * This interface can be used as a base for creating specific operation function
 * implementations that require one argument like findAll, countAll, existsAll,
 * and custom operations.
 * 
 * @param <In>  The type of the input to the function
 * @param <Out> The type of the result of the function
 * 
 * @see OperationFunction
 * @see Function
 */
public sealed interface OperationFunction1Args<In, Out> extends Function<In, Out>, OperationFunction
		permits CustomOperation1Args,
		FindAllFunction, PreFindAllFunction,
		CountAllFunction, PreCountAllFunction,
		ExistsAllFunction, PreExistsAllFunction {

}
