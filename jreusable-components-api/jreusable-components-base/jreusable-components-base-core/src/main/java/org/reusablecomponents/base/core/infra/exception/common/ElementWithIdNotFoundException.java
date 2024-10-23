package org.reusablecomponents.base.core.infra.exception.common;

import static org.reusablecomponents.base.core.infra.constants.ExceptionMessages.ELEMENT_WITH_ID_NOT_FOUND_MSG;

import java.text.NumberFormat;

import org.reusablecomponents.base.translation.InterfaceI18nService;

/**
 * Element not found on search by Id.
 * 
 * @author Fernando Romulo da Silva
 */
public class ElementWithIdNotFoundException extends BaseApplicationException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ElementWithIdNotFoundException exception and create detail
     * message regard of parameters. </br>
     * 
     * @param <T>         The class type
     * @param clazz       Class element
     * @param i18nService The msg translation service
     * @param object      The object not found
     */
    public <T> ElementWithIdNotFoundException(
            final Class<T> clazz,
            final InterfaceI18nService i18nService,
            final Exception ex,
            final Object id) {
        super(
                ELEMENT_WITH_ID_NOT_FOUND_MSG,
                i18nService,
                ex,
                new Object[] { formatNumber(id), clazz.getSimpleName() });
    }

    private static Object formatNumber(final Object id) {

        if (id instanceof Number number) {

            final var format = NumberFormat.getInstance();
            format.setGroupingUsed(false);

            return format.format(number);
        }

        return id;
    }
}
