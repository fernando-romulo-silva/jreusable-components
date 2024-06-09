package org.reusablecomponent.core.infra.exception.common;

import org.reusablecomponent.core.infra.i18n.InterfaceI18nService;

/**
 * Element conflits exception, like delete a linked object.
 * 
 * @author Fernando Romulo da Silva
 */
public class ElementConflictException extends BaseApplicationException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ElementConflictException exception with the specified detail message.
     * 
     * @param msg    The detail message
     * @param params The parameters used on message
     */
    public ElementConflictException(final String msg, final Object... params) {
	super(msg, params);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and cause.
     * 
     * @param msg    The detail message
     * @param ex     The cause
     * @param params The parameters used on message
     */
    public ElementConflictException(final String msg, final Throwable ex, final Object... params) {
	super(msg, ex, params);
    }
    
    /**
     * Constructs a new ElementConflictException exception with the specified detail message.
     * 
     * @param msg    The detail message
     * @param i18n   The msg translation function
     * @param params The parameters used on message
     */
    public ElementConflictException(final String msg, final InterfaceI18nService i18n, final Object... params) {
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
    public ElementConflictException(final String msg, final InterfaceI18nService i18n, final Throwable ex, final Object... params) {
	super(msg, i18n, ex, params);
    }
}
