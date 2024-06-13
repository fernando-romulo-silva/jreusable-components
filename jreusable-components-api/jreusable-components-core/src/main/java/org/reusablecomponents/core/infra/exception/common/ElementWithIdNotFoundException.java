package org.reusablecomponents.core.infra.exception.common;

import java.text.NumberFormat;

import org.reusablecomponents.core.infra.i18n.InterfaceI18nService;

/**
 * 
 */
public class ElementWithIdNotFoundException extends ElementNotFoundException {

    private static final long serialVersionUID = 1L;

    /**
     * @param <T>
     * @param cls
     * @param id
     */
    public <T> ElementWithIdNotFoundException(final Class<T> cls, final Object id) {
	super("{exception.elementIdNotFound}", new Object[] { cls.getSimpleName(), formatNumber(id) });
    }
    
    /**
     * @param <T>
     * @param cls
     * @param i18nService
     * @param id
     */
    public <T> ElementWithIdNotFoundException(final Class<T> cls, final InterfaceI18nService i18nService, final Object id) {
	super("{exception.elementIdNotFound}", i18nService, new Object[] { cls.getSimpleName(), formatNumber(id) });
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
