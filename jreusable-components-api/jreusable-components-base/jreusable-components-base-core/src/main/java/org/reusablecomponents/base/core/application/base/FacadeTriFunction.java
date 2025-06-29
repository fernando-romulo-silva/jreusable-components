package org.reusablecomponents.base.core.application.base;

import org.apache.commons.lang3.function.TriFunction;

/**
 * Especialized function used by <code>BaseFacade.executeFunctions</code> to
 * execute code in operations.
 * 
 * @param <T> The function input one data type
 * @param <Q> The function input two data type
 */
@FunctionalInterface
public non-sealed interface FacadeTriFunction<T, Q> extends TriFunction<T, Q, Object[], T>, BaseFunction {

}