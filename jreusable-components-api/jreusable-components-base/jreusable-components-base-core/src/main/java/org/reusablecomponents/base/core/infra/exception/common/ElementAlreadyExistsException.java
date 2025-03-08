package org.reusablecomponents.base.core.infra.exception.common;

import static org.reusablecomponents.base.core.infra.constants.ExceptionMessages.ELEMENT_ALREADY_EXITS_EXCEPTION_MSG;

import org.reusablecomponents.base.translation.InterfaceI18nService;

/**
 * Element already exists exception.
 * 
 * @author Fernando Romulo da Silva
 */
public class ElementAlreadyExistsException extends BaseApplicationException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new ElementAlreadyExistsException exception.
	 * 
	 * @param code   The message code
	 * @param i18n   The message translation service
	 * @param params The parameters used on message
	 */
	protected ElementAlreadyExistsException(
			final String code,
			final InterfaceI18nService i18n,
			final Object... params) {
		super(
				code,
				i18n,
				params);
	}

	/**
	 * Constructs a new ElementAlreadyExistsException exception.
	 * 
	 * @param code   The message code
	 * @param i18n   The message translation service
	 * @param ex     The exception's cause
	 * @param params The parameters used on message
	 */
	protected ElementAlreadyExistsException(
			final String code,
			final InterfaceI18nService i18n,
			final Throwable ex,
			final Object... params) {
		super(
				code,
				i18n,
				ex,
				params);
	}

	/**
	 * Constructs a new ElementAlreadyExistsException exception.
	 * 
	 * @param clazz  Class element
	 * @param i18n   The message translation service
	 * @param ex     The exception's cause
	 * @param object The object in exception
	 */
	public <T> ElementAlreadyExistsException(
			final InterfaceI18nService i18n,
			final Throwable ex,
			final Object object) {
		super(
				ELEMENT_ALREADY_EXITS_EXCEPTION_MSG,
				i18n,
				ex,
				object);
	}

}
