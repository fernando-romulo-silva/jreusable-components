package org.reusablecomponents.base.core.application.base.functions;

/**
 * Especialized function used by <code>BaseFacade.executeFunctions</code> to
 * execute code in operations.
 * 
 * @param <T> The function input one data type
 * @param <U> The function input two data type
 * @param <V> The function input three data type
 */
@FunctionalInterface
public non-sealed interface FacadeFunctionThreeArgs<T, U, V> extends BaseFunction {

    T apply(T t, U u, V v, Object[] directives);

}
