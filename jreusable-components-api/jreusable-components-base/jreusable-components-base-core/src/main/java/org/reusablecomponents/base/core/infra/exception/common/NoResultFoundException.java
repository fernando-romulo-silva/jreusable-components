package org.reusablecomponents.base.core.infra.exception.common;

import static org.reusablecomponents.base.core.infra.constants.ExceptionMessages.NO_RESULT_EXCEPTION_MSG;

import org.reusablecomponents.base.translation.InterfaceI18nService;

/**
 * No result found exception.
 * 
 * @author Fernando Romulo da Silva
 */
public class NoResultFoundException extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new NoResultQueryException exception and create detail message
     * regard of parameters. </br>
     * 
     * @param <T>         The class type
     * @param clazz       Class element
     * @param i18nService The msg translation function
     */
    public <T> NoResultFoundException(
            final InterfaceI18nService i18nService,
            final Throwable ex) {
        super(NO_RESULT_EXCEPTION_MSG, i18nService, ex);
    }

}
