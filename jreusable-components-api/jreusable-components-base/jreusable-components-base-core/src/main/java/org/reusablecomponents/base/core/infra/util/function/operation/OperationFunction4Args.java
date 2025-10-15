package org.reusablecomponents.base.core.infra.util.function.operation;

import java.util.function.Function;

import org.apache.commons.lang3.function.TriFunction;

/**
 * Represents a function that accepts four arguments and produces a result.
 * This is a four-arity extension to the functional interfaces {@link Function}
 * and {@link java.util.function.BiFunction BiFunction} from the JDK.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <V> the type of the third argument to the function
 * @param <W> the type of the fourth argument to the function
 * @param <R> the type of the result of the function
 *
 * @see TriFunction
 */
@FunctionalInterface
public non-sealed interface OperationFunction4Args<T, U, V, W, R> extends OperationFunction {

    R apply(T t, U u, V v, W w);

    default <S> OperationFunction4Args<T, U, V, W, S> andThen(Function<? super R, S> after) {
        return (t, u, v, w) -> after.apply(apply(t, u, v, w));
    }
}