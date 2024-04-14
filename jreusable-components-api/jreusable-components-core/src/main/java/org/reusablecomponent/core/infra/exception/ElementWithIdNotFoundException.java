package org.reusablecomponent.core.infra.exception;

import java.text.NumberFormat;

import org.reusablecomponent.core.infra.i18n.InterfaceI18nService;

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
     * @param i18n
     * @param id
     */
    public <T> ElementWithIdNotFoundException(final Class<T> cls, final InterfaceI18nService i18n, final Object id) {
	super("{exception.elementIdNotFound}", i18n, new Object[] { cls.getSimpleName(), formatNumber(id) });
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
