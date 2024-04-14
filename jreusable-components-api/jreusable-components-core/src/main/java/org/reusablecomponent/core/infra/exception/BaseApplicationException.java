package org.reusablecomponent.core.infra.exception;

import java.util.Objects;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.reusablecomponent.core.infra.i18n.InterfaceI18nService;

/**
 * Base applicaton exception.
 * 
 * @author Fernando Romulo da Silva
 */
public abstract class BaseApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new BaseApplicationException exception with the specified detail message.
     * 
     * @param msg    The detail message
     * @param params The parameters used on message
     */
    protected BaseApplicationException(final String msg, final Object... params) {
	super(getFinalMessage(msg, null, params));
    }

    /**
     * Constructs a new runtime exception with the specified detail message and cause.
     * 
     * @param msg    The detail message
     * @param ex     The cause
     * @param params The parameters used on message
     */
    protected BaseApplicationException(final String msg, final Throwable ex, final Object... params) {
	super(getFinalMessage(msg, null, params), ex);
    }
    
    /**
     * Constructs a new BaseApplicationException exception with the specified detail message.
     * 
     * @param msg    The detail message
     * @param i18n   The msg translation function
     * @param params The parameters used on message
     */
    protected BaseApplicationException(final String msg, final InterfaceI18nService i18n, final Object... params) {
	super(getFinalMessage(msg, i18n, params));
    }

    /**
     * Constructs a new runtime exception with the specified detail message and cause.
     * 
     * @param msg    The detail message
     * @param i18n   The msg translation function
     * @param ex     The cause
     * @param params The parameters used on message
     */
    protected BaseApplicationException(final String msg, final InterfaceI18nService i18n, final Throwable ex, final Object... params) {
	super(getFinalMessage(msg, i18n, params), ex);
    }
    
    
    /**
     * @param msg
     * @param i18n
     * @param params
     * @return
     */
    private static String getFinalMessage(final String msg, final InterfaceI18nService i18n, final Object... params) {

	if (StringUtils.containsNone(msg, '{', '}') || Objects.isNull(i18n)) {
	    return msg;
	}

	final var code = RegExUtils.replaceAll(msg, "[{}]", "");

	return i18n.translate(code, params);
    }

}
