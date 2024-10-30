package org.reusablecomponents.base.core.infra.util;

import static org.reusablecomponents.base.core.infra.constants.ExceptionMessages.NULL_POINTER_EXCEPTION_MSG;

import java.util.function.Supplier;

import org.reusablecomponents.base.translation.InterfaceI18nService;

public class Functions {

    private Functions() {
        throw new UnsupportedOperationException("You can't instanciate this class");
    }

    /**
     * Create a supplier function to generate null pointer exception when it's
     * necessary.
     * 
     * @param parameters Parameters to show on message error
     * 
     * @return An object <code>Supplier<NullPointerException></code>
     */
    public static final Supplier<NullPointerException> createNullPointerException(
            final InterfaceI18nService i18nService,
            final String... parameters) {

        return () -> new NullPointerException(
                i18nService.translate(NULL_POINTER_EXCEPTION_MSG, (Object[]) parameters));
    }
}
