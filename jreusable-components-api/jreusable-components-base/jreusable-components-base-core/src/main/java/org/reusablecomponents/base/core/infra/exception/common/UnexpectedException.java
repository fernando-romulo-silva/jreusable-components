package org.reusablecomponents.base.core.infra.exception.common;

import static org.reusablecomponents.base.core.infra.constants.ExceptionMessages.UNEXPECTED_ERROR_MSG;

import org.reusablecomponents.base.translation.InterfaceI18nService;

/**
 * 
 */
public final class UnexpectedException extends BaseApplicationException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new UnexpectedException exception and create detail
     * message regard of parameters. </br>
     * 
     * @param i18n The msg translation function
     * @param ex   The exception's cause
     */
    public UnexpectedException(
            final InterfaceI18nService i18n,
            final Exception ex) {
        super(UNEXPECTED_ERROR_MSG, i18n, ex);
    }
}
