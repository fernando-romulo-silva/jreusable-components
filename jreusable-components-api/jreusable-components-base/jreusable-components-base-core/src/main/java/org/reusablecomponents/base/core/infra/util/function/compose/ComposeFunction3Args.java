package org.reusablecomponents.base.core.infra.util.function.compose;

import org.apache.commons.lang3.function.TriFunction;

/**
 * Especialized function used for create composed functions, it always return an
 * updated object of the type <code>T</code>
 * 
 * @param <T> The function input one data type
 * @param <Q> The function input two data type
 */
@FunctionalInterface
public non-sealed interface ComposeFunction3Args<T, Q> extends TriFunction<T, Q, Object[], T>, ComposeFunction {

}