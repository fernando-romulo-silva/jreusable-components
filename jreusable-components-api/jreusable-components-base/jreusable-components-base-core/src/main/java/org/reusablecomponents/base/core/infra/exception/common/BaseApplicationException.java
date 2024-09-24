package org.reusablecomponents.base.core.infra.exception.common;

import static org.apache.commons.lang3.StringUtils.endsWith;
import static org.apache.commons.lang3.StringUtils.startsWith;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.reusablecomponents.base.translation.InterfaceI18nService;

/**
 * Base applicaton exception for all errors.
 * 
 * @author Fernando Romulo da Silva
 */
public abstract class BaseApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new BaseApplicationException exception.
     * 
     * @param msg The exception's message
     */
    protected BaseApplicationException(final String msg) {
        super(msg);
    }

    /**
     * Constructs a new BaseApplicationException exception.
     * 
     * @param msg The exception's message
     * @param ex  The exception's cause
     */
    protected BaseApplicationException(final String msg, final Throwable ex) {
        super(msg, ex);
    }

    /**
     * Constructs a new BaseApplicationException exception.
     * 
     * @param code        The message code
     * @param i18nService The message translation service
     * @param params      The parameters used on message
     */
    protected BaseApplicationException(
            final String code,
            final InterfaceI18nService i18nService,
            final Object... params) {
        super(getFinalMessage(code, i18nService, params));
    }

    /**
     * Constructs a new BaseApplicationException exception.
     * 
     * @param code        The message code
     * @param i18nService The message translation service
     * @param ex          The exception's cause
     * @param params      The parameters used on message
     */
    protected BaseApplicationException(
            final String code,
            final InterfaceI18nService i18nService,
            final Throwable ex,
            final Object... params) {
        super(getFinalMessage(code, i18nService, params), ex);
    }

    /**
     * Check if is a message code or a real message, if it is a code (starts with
     * '{' and finishes with '}') translate it, if not jus return the param msg.
     * 
     * @param code        The msg or code to show
     * @param i18nService The translator service
     * @param params      The parameters used on message
     * @return A string message (translated or not)
     */
    private static String getFinalMessage(
            final String code,
            final InterfaceI18nService i18nService,
            final Object... params) {

        if (StringUtils.isBlank(code)) {
            return code;
        }

        // StringUtils.containsNone(msg, '{', '}')
        if (!(startsWith(code, "{") && endsWith(code, "}")) || Objects.isNull(i18nService)) {
            return code;
        }

        return i18nService.translate(code, params);
    }
}
