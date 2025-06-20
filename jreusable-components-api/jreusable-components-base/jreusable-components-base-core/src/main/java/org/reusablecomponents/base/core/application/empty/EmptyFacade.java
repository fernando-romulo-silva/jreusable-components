package org.reusablecomponents.base.core.application.empty;

import org.reusablecomponents.base.core.application.base.BaseFacade;
import org.reusablecomponents.base.core.application.base.BaseFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;

public non-sealed class EmptyFacade<Entity extends AbstractEntity<Id>, Id>
		extends BaseFacade<Entity, Id> {

	public EmptyFacade(
			final InterfaceI18nService i18nService,
			final InterfaceSecurityService securityService,
			final InterfaceExceptionAdapterService exceptionTranslatorService) {

		super(new BaseFacadeBuilder($ -> {
			$.i18nService = i18nService;
			$.securityService = securityService;
			$.exceptionAdapterService = exceptionTranslatorService;
		}));
	}

	public EmptyFacade() {
		super(new BaseFacadeBuilder($ -> {
		}));
	}
}
