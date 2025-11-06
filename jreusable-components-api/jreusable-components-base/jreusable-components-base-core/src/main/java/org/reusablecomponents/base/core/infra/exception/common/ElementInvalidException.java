package org.reusablecomponents.base.core.infra.exception.common;

import static org.reusablecomponents.base.core.infra.constants.ExceptionMessages.INVALID_ELEMENT_EXCEPTION_MSG;

import org.reusablecomponents.base.translation.InterfaceI18nService;

/**
 * Element with invalid state/parameters.
 * 
 * @author Fernando Romulo da Silva
 */
public class ElementInvalidException extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ElementInvalidException exception.
     * 
     * @param msg The exception's message
     */
    protected ElementInvalidException(final String msg) {
        super(msg);
    }

    /**
     * Constructs a new ElementInvalidException exception.
     * 
     * @param clazz  Class element
     * @param i18n   The message translation service
     * @param ex     The exception's cause
     * @param params The parameters used on message
     */
    public ElementInvalidException(
            final InterfaceI18nService i18n,
            final Throwable ex,
            final Object object) {
        super(INVALID_ELEMENT_EXCEPTION_MSG, i18n, ex, object);
    }
}
