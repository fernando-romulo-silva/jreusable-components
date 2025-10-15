package org.reusablecomponents.base.core.infra.util.function.compose;

/**
 * Especialized function used by <code>BaseFacade.executeFunctions</code> to
 * execute code in operations.
 * 
 * @param <T> The function input one data type
 * @param <U> The function input two data type
 * @param <V> The function input three data type
 */
@FunctionalInterface
public non-sealed interface ComposeFunction4Args<T, U, V> extends ComposeFunction {

    T apply(T t, U u, V v, Object[] directives);

}
