package org.reusablecomponent.core.infra.exception.common;

import org.reusablecomponent.core.infra.i18n.InterfaceI18nService;

/**
 * Element not found on search.
 * 
 * @author Fernando Romulo da Silva
 */
public class ElementNotFoundException extends BaseApplicationException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ElementNotFoundException exception and create detail message regard of parameters. </br>
     * For instance for Person object and msg equals to "id '10' and name 'Fernando'": </br>
     * "Person with id '10' and name 'Fernando' not found"
     * 
     * @param <T> The class type
     * @param cls Class element
     * @param msg The specific message
     */
    public <T> ElementNotFoundException(final Class<T> cls, final String msg) {
	super("{exception.elementNotFound}", new Object[] { cls.getSimpleName(), msg });
    }

    /**
     * Constructs a new BaseApplicationException exception with the specified detail message.
     * 
     * @param msg    The detail message
     * @param params The parameters used on message
     */
    protected ElementNotFoundException(final String msg, final Object... params) {
	super(msg, params);
    }

    /**
     * Constructs a new ElementNotFoundException exception and create detail message regard of parameters. </br>
     * For instance for Person object and msg equals to "id '10' and name 'Fernando'": </br>
     * "Person with id '10' and name 'Fernando' not found"
     * 
     * @param <T>  The class type
     * @param cls  Class element
     * @param i18nService The msg translation function
     * @param msg  The specific message
     */
    public <T> ElementNotFoundException(final Class<T> cls, final InterfaceI18nService i18nService, final String msg) {
	super("{exception.elementNotFound}", i18nService, new Object[] { cls.getSimpleName(), msg });
    }

    /**
     * Constructs a new BaseApplicationException exception with the specified detail message.
     * 
     * @param msg    The detail message
     * @param i18nService   The msg translation function
     * @param params The parameters used on message
     */
    protected ElementNotFoundException(final String msg, final InterfaceI18nService i18nService, final Object... params) {
	super(msg, i18nService, params);
    }
}
