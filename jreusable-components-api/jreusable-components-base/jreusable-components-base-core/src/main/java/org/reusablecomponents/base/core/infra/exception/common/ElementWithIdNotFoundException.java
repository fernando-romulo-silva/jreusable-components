package org.reusablecomponents.base.core.infra.exception.common;

import static org.reusablecomponents.base.core.infra.messages.SystemMessages.ELEMENT_WITH_ID_NOT_FOUND_MSG;

import java.text.NumberFormat;

import org.reusablecomponents.base.translation.InterfaceI18nService;

/**
 * 
 */
public class ElementWithIdNotFoundException extends BaseApplicationException {

    private static final long serialVersionUID = 1L;

    /**
     * @param <T>
     * @param cls
     * @param i18nService
     * @param id
     */
    public <T> ElementWithIdNotFoundException(
            final Class<T> cls,
            final InterfaceI18nService i18nService,
            final Object id) {
        super(
                ELEMENT_WITH_ID_NOT_FOUND_MSG,
                i18nService,
                new Object[] { cls.getSimpleName(), formatNumber(id) });
    }

    private static Object formatNumber(final Object id) {

        if (id instanceof Number number) {

            final var format = NumberFormat.getInstance();
            format.setGroupingUsed(false);

            return format.format(number);
        }

        return id;
    }
}
