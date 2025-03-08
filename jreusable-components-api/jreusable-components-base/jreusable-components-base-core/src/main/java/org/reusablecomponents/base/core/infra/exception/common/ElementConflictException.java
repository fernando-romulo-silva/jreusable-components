package org.reusablecomponents.base.core.infra.exception.common;

import static org.reusablecomponents.base.core.infra.constants.ExceptionMessages.ELEMENT_CONFLICT_EXCEPTION_MSG;

import org.reusablecomponents.base.translation.InterfaceI18nService;

/**
 * Element conflits exception, like delete a linked object.
 * 
 * @author Fernando Romulo da Silva
 */
public class ElementConflictException extends BaseApplicationException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ElementConflictException exception.
     * 
     * @param code   The message code
     * @param i18n   The message translation service
     * @param ex     The exception's cause
     * @param params The parameters used on message
     */
    protected ElementConflictException(
            final String code,
            final InterfaceI18nService i18n,
            final Throwable ex,
            final Object... params) {
        super(code, i18n, ex, params);
    }

    /**
     * Constructs a new ElementConflictException exception.
     * 
     * @param i18n   The message translation service
     * @param ex     The exception's cause
     * @param object The object in exception
     */
    public ElementConflictException(
            final InterfaceI18nService i18n,
            final Throwable ex,
            final Object object) {
        super(ELEMENT_CONFLICT_EXCEPTION_MSG,
                i18n,
                ex,
                object);
    }
}
