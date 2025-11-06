package org.reusablecomponents.base.core.infra.exception.common;

import static org.reusablecomponents.base.core.infra.constants.ExceptionMessages.ELEMENT_NOT_FOUND_EXCEPTION_MSG;

import org.reusablecomponents.base.translation.InterfaceI18nService;

/**
 * Element not found on search.
 * 
 * @author Fernando Romulo da Silva
 */
public class ElementNotFoundException extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ElementNotFoundException exception and create detail message
     * regard of parameters. </br>
     * For instance for Person object and msg equals to "id '10' and name
     * 'Fernando'": </br>
     * "Person with id '10' and name 'Fernando' not found"
     * 
     * @param <T>         The class type
     * @param clazz       Class element
     * @param i18nService The msg translation function
     * @param object      The object not found
     */
    public <T> ElementNotFoundException(
            final InterfaceI18nService i18nService,
            final Throwable ex,
            final Object object) {
        super(ELEMENT_NOT_FOUND_EXCEPTION_MSG, i18nService, ex, object);
    }
}
