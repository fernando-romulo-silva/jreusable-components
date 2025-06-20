package org.reusablecomponents.base.core.application.base;

import java.util.function.BiFunction;

@FunctionalInterface
public interface FacadeBiFunction<T> extends BiFunction<T, Object[], T> {

    default boolean isActice() {
        return true;
    }

    default String getName() {
        return this.getClass().getSimpleName();
    }
}
