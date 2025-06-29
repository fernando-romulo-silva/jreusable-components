package org.reusablecomponents.base.core.application.base;

import java.util.function.BiFunction;

/**
 * Especialized function used by <code>BaseFacade.executeFunctions</code> to
 * execute code in operations.
 * 
 * @param <T> The function input data type
 */
@FunctionalInterface
public non-sealed interface FacadeBiFunction<T> extends BiFunction<T, Object[], T>, BaseFunction {

}
