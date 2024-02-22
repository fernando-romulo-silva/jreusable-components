package org.reusablecomponent.infra.exception;

import org.reusablecomponent.infra.i18n.I18nFuntion;

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
     * @param i18n The msg translation function
     * @param msg  The specific message
     */
    public <T> ElementNotFoundException(final Class<T> cls, final I18nFuntion i18n, final String msg) {
	super("{exception.elementNotFound}", i18n, new Object[] { cls.getSimpleName(), msg });
    }

    /**
     * Constructs a new BaseApplicationException exception with the specified detail message.
     * 
     * @param msg    The detail message
     * @param i18n   The msg translation function
     * @param params The parameters used on message
     */
    protected ElementNotFoundException(final String msg, final I18nFuntion i18n, final Object... params) {
	super(msg, i18n, params);
    }
}
