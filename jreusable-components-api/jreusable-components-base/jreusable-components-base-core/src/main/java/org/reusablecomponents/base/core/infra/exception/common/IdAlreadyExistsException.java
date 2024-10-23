package org.reusablecomponents.base.core.infra.exception.common;

import static org.apache.commons.lang3.ArrayUtils.addAll;
import static org.reusablecomponents.base.core.infra.constants.ExceptionMessages.ID_ALREADY_EXITS_EXCEPTION_MSG;

import org.reusablecomponents.base.translation.InterfaceI18nService;

/**
 * Id already exists exception.
 * 
 * @author Fernando Romulo da Silva
 */
public class IdAlreadyExistsException extends ElementConflictException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new IdAlreadyExistsException exception and create detail
     * message regard of parameters. </br>
     * 
     * @param <T>  The class type
     * @param cls  Class element
     * @param i18n The msg translation function
     * @param id   The id parameter
     */
    public <T> IdAlreadyExistsException(
            final Class<T> cls,
            final InterfaceI18nService i18n,
            final Object id) {
        super(
                ID_ALREADY_EXITS_EXCEPTION_MSG,
                i18n,
                addAll(new Object[] { cls.getSimpleName() }, id));
    }
}
