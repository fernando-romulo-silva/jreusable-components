package org.reusablecomponents.base.core.application.empty;

import org.reusablecomponents.base.core.application.base.EntiyBaseFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionTranslatorService;
import org.reusablecomponents.base.messaging.InterfaceEventPublisherSerice;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;

public non-sealed class SimpleEntiyBaseFacade<Entity extends AbstractEntity<Id>, Id> extends EntiyBaseFacade<Entity, Id> {

    public SimpleEntiyBaseFacade(
		    final InterfaceEventPublisherSerice publisherService, 
		    final InterfaceI18nService i18nService, 
		    final InterfaceSecurityService securityService,
		    final InterfaceExceptionTranslatorService exceptionTranslatorService) {
	
	super(publisherService, i18nService, securityService, exceptionTranslatorService);
    }

    public SimpleEntiyBaseFacade() {
	super();
    }
}
