package org.reusablecomponents.base.core.application.empty;

import org.reusablecomponents.base.core.application.base.EntiyBaseFacade;
import org.reusablecomponents.base.core.application.base.EntiyBaseFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;

public non-sealed class SimpleEntiyBaseFacade<Entity extends AbstractEntity<Id>, Id>
		extends EntiyBaseFacade<Entity, Id> {

	public SimpleEntiyBaseFacade(
			final InterfaceI18nService i18nService,
			final InterfaceSecurityService securityService,
			final InterfaceExceptionAdapterService exceptionTranslatorService) {

		super(new EntiyBaseFacadeBuilder($ -> {

			$.i18nService = i18nService;
			$.securityService = securityService;
			$.exceptionAdapterService = exceptionTranslatorService;
		}));
	}

	public SimpleEntiyBaseFacade() {
		super(new EntiyBaseFacadeBuilder($ -> {
		}));
	}
}
