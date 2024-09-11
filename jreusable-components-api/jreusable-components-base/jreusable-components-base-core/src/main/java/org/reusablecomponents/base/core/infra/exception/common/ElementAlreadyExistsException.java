package org.reusablecomponents.base.core.infra.exception.common;

import static org.apache.commons.lang3.ArrayUtils.addAll;
import static org.reusablecomponents.base.core.infra.messages.SystemMessages.ELEMENT_ALREADY_EXITS_EXCEPTION_MSG;

import org.reusablecomponents.base.translation.InterfaceI18nService;

/**
 * Element already exists exception.
 * 
 * @author Fernando Romulo da Silva
 */
public class ElementAlreadyExistsException extends ElementConflictException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ElementAlreadyExistsException exception and create detail
     * message regard of parameters. </br>
     * 
     * @param clazz Class element
     * @param msg   The specific message
     */
    public ElementAlreadyExistsException(final Class<?> clazz, final Object... params) {
        super(ELEMENT_ALREADY_EXITS_EXCEPTION_MSG, addAll(new Object[] { clazz.getSimpleName() }, params));
    }

    /**
     * Constructs a new ElementAlreadyExistsException exception and create detail
     * message regard of parameters. </br>
     * 
     * @param clazz Class element
     * @param i18n  The msg translation function
     * @param msg   The specific message
     */
    public ElementAlreadyExistsException(
            final Class<?> clazz,
            final InterfaceI18nService i18n,
            final Object... params) {
        super(
                ELEMENT_ALREADY_EXITS_EXCEPTION_MSG,
                i18n,
                addAll(new Object[] { clazz.getSimpleName() }, params));
    }
}
