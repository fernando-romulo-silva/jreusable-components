package org.reusablecomponents.base.core.application.empty;

import org.reusablecomponents.base.core.application.base.BaseFacade;
import org.reusablecomponents.base.core.application.base.BaseFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;

/**
 * Class design mainly create facade don't need to implement any method, just
 * need to extend this class and inject the services in the constructor, then
 * you can use the facade without any implementation.
 */
public non-sealed class EmptyFacade<Entity extends AbstractEntity<Id>, Id> extends BaseFacade<Entity, Id> {

	/**
	 * Constructor with parameters
	 * 
	 * @param i18nService             Language translator service
	 * @param securityService         Security service
	 * @param exceptionAdapterService Exception adapter service
	 */
	public EmptyFacade(
			final InterfaceI18nService i18nService,
			final InterfaceSecurityService securityService,
			final InterfaceExceptionAdapterService exceptionAdapterService) {

		super(new BaseFacadeBuilder($ -> {
			$.i18nService = i18nService;
			$.securityService = securityService;
			$.exceptionAdapterService = exceptionAdapterService;
		}));
	}

	/**
	 * Default constructor
	 */
	public EmptyFacade() {
		super(new BaseFacadeBuilder($ -> {
		}));
	}
}
