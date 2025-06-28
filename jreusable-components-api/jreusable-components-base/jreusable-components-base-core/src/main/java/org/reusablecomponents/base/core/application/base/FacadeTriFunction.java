package org.reusablecomponents.base.core.application.base;

import org.apache.commons.lang3.function.TriFunction;

@FunctionalInterface
public interface FacadeTriFunction<T, Q> extends TriFunction<T, Q, Object[], T> {

    default boolean isActice() {
        return true;
    }

    default String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * 
     * @return
     */
    default boolean reTrowError() {
        return false;
    }
}