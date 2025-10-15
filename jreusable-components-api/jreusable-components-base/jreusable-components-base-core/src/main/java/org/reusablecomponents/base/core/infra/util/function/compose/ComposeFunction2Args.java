package org.reusablecomponents.base.core.infra.util.function.compose;

import java.util.function.BiFunction;

/**
 * Especialized function used by <code>BaseFacade.executeFunctions</code> to
 * execute code in operations.
 * 
 * @param <T> The function input data type
 */
@FunctionalInterface
public non-sealed interface ComposeFunction2Args<T> extends BiFunction<T, Object[], T>, ComposeFunction {

}
