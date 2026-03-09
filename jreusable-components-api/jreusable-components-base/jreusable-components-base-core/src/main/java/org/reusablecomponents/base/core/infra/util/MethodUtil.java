package org.reusablecomponents.base.core.infra.util;

import static org.reusablecomponents.base.core.infra.constants.ExceptionMessages.NULL_POINTER_EXCEPTION_MSG;

import java.util.Objects;

import org.reusablecomponents.base.translation.InterfaceI18nService;

/**
 * Utility class for method validations.
 */
public class MethodUtil {

    /**
     * Private constructor to prevent instantiation.
     */
    private MethodUtil() {
        throw new UnsupportedOperationException("You can't instanciate this class, it is utility class");
    }

    /**
     * Check if an object is null, if so, throw a NullPointerException with a
     * translated message.
     * 
     * @param object      The object to check.
     * @param objectName  The name of the object.
     * @param i18nService The internationalization service.
     */
    public static void checkObjectIsNull(
            final Object object,
            final String objectName,
            final InterfaceI18nService i18nService) {
        if (Objects.isNull(object)) {
            throw new NullPointerException(i18nService.translate(NULL_POINTER_EXCEPTION_MSG, objectName));
        }
    }

    /**
     * Check if the internationalization service is null, if so, throw a
     * NullPointerException.
     * 
     * @param i18nService The internationalization service to check.
     */
    public static void checkI18nServiceIsNull(final InterfaceI18nService i18nService) {
        if (Objects.isNull(i18nService)) {
            throw new NullPointerException("The object 'i18nService' cannot be null");
        }
    }
}
