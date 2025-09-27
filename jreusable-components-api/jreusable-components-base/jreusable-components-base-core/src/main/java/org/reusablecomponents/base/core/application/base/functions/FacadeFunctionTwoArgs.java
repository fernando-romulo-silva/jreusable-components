package org.reusablecomponents.base.core.application.base.functions;

import org.apache.commons.lang3.function.TriFunction;

/**
 * Especialized function used by <code>BaseFacade.executeFunctions</code> to
 * execute code in operations.
 * 
 * @param <T> The function input one data type
 * @param <Q> The function input two data type
 */
@FunctionalInterface
public non-sealed interface FacadeFunctionTwoArgs<T, Q> extends TriFunction<T, Q, Object[], T>, BaseFunction {

}