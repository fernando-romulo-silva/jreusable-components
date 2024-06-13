package org.reusablecomponents.core.infra.exception.common;

import java.util.Objects;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.reusablecomponents.core.infra.i18n.InterfaceI18nService;

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
     * @param i18nService   The msg translation function
     * @param params The parameters used on message
     */
    protected BaseApplicationException(final String msg, final InterfaceI18nService i18nService, final Object... params) {
	super(getFinalMessage(msg, i18nService, params));
    }

    /**
     * Constructs a new runtime exception with the specified detail message and cause.
     * 
     * @param msg    The detail message
     * @param i18nService   The msg translation function
     * @param ex     The cause
     * @param params The parameters used on message
     */
    protected BaseApplicationException(final String msg, final InterfaceI18nService i18nService, final Throwable ex, final Object... params) {
	super(getFinalMessage(msg, i18nService, params), ex);
    }
    
    /**
     * @param msg
     * @param i18nService
     * @param params
     * @return
     */
    private static String getFinalMessage(final String msg, final InterfaceI18nService i18nService, final Object... params) {

	if (StringUtils.containsNone(msg, '{', '}') || Objects.isNull(i18nService)) {
	    return msg;
	}

	final var code = RegExUtils.replaceAll(msg, "[{}]", "");

	return i18nService.translate(code, params);
    }
}
