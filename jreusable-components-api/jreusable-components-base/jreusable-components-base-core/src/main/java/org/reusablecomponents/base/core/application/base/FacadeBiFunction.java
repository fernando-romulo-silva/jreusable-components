package org.reusablecomponents.base.core.application.base;

import java.util.function.BiFunction;

/**
 * 
 * @param <T> The function data type
 */
@FunctionalInterface
public interface FacadeBiFunction<T> extends BiFunction<T, Object[], T> {

    /**
     * 
     * @return
     */
    default boolean isActice() {
        return true;
    }

    /**
     * 
     * @return
     */
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
