package org.reusablecomponent.infra.exception;

import org.reusablecomponent.infra.i18n.I18nFuntion;

/**
 * Element with invalid state/parameters.
 * 
 * @author Fernando Romulo da Silva
 */
public class ElementInvalidException extends BaseApplicationException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ConversionException exception with the specified detail message.
     * 
     * @param msg    The detail message
     * @param params The parameters used on message
     */
    public ElementInvalidException(final String msg, final Object... params) {
	super(msg, params);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and cause.
     * 
     * @param msg    The detail message
     * @param ex     The cause
     * @param params The parameters used on message
     */
    public ElementInvalidException(final String msg, final Throwable ex, final Object... params) {
	super(msg, ex, params);
    }

    /**
     * Constructs a new ConversionException exception with the specified detail message.
     * 
     * @param msg    The detail message
     * @param i18n   The msg translation function
     * @param params The parameters used on message
     */
    public ElementInvalidException(final String msg, final I18nFuntion i18n, final Object... params) {
	super(msg, i18n, params);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and cause.
     * 
     * @param msg    The detail message
     * @param i18n   The msg translation function
     * @param ex     The cause
     * @param params The parameters used on message
     */
    public ElementInvalidException(final String msg, final I18nFuntion i18n, final Throwable ex, final Object... params) {
	super(msg, ex, i18n, params);
    }
}
