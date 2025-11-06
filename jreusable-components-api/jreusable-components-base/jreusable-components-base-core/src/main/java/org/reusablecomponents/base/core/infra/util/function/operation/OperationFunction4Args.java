package org.reusablecomponents.base.core.infra.util.function.operation;

import java.util.function.Function;

import org.apache.commons.lang3.function.TriFunction;

/**
 * Represents a function that accepts four arguments and produces a result.
 * This is a four-arity extension to the functional interfaces {@link Function}
 * and {@link java.util.function.BiFunction BiFunction} from the JDK.
 *
 * @param <In1> the type of the first argument to the function
 * @param <In2> the type of the second argument to the function
 * @param <In3> the type of the third argument to the function
 * @param <In4> the type of the fourth argument to the function
 * @param <R>   the type of the result of the function
 *
 * @see TriFunction
 */
@FunctionalInterface
public non-sealed interface OperationFunction4Args<In1, In2, In3, In4, Out> extends OperationFunction {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    Out apply(In1 in1, In2 in2, In3 in3, In4 in4);
}