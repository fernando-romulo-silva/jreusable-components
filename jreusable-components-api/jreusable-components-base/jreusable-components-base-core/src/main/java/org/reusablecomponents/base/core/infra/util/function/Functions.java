package org.reusablecomponents.base.core.infra.util.function;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.joining;
import static org.reusablecomponents.base.core.infra.constants.ExceptionMessages.NULL_POINTER_EXCEPTION_MSG;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.reusablecomponents.base.core.application.base.functions.BaseFunction;
import org.reusablecomponents.base.core.application.base.functions.FacadeFunctionNoArgs;
import org.reusablecomponents.base.translation.InterfaceI18nService;

/**
 * Util class used to stores functions
 */
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

    /**
     * Create a supplier function to generate null pointer exception when it's
     * necessary.
     * 
     * @param parameter Parameters to show on message error
     * 
     * @return An object <code>Supplier<NullPointerException></code>
     */
    public static final Supplier<NullPointerException> createNullPointerException(final String parameter) {

        return () -> new NullPointerException("The object '".concat(parameter).concat("' cannot be null"));
    }
}
