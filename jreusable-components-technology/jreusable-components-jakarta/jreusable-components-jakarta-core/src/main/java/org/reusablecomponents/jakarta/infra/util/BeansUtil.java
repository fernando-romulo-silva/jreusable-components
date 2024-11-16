package org.reusablecomponents.jakarta.infra.util;

import jakarta.enterprise.inject.spi.CDI;

public class BeansUtil {

    /**
     * Get a bean from its type.
     * 
     * @param <T>       The type
     * @param beanClass The class type
     * @return A bean retrieved
     */
    public static <T> T getBeanFrom(final Class<T> beanClass) {

        CDI<Object> cdi = CDI.current();

        return cdi.select(beanClass).stream().findAny().get();
    }
}
