package org.reusablecomponents.core.application.empty;

import org.reusablecomponents.core.application.base.EntiyBaseFacade;
import org.reusablecomponents.core.domain.AbstractEntity;
import org.reusablecomponents.core.infra.exception.InterfaceExceptionTranslatorService;
import org.reusablecomponents.core.infra.i18n.InterfaceI18nService;
import org.reusablecomponents.core.infra.security.InterfaceSecurityService;
import org.reusablecomponents.messaging.InterfacePublisherSerice;

public non-sealed class SimpleEntiyBaseFacade<Entity extends AbstractEntity<Id>, Id> extends EntiyBaseFacade<Entity, Id> {

    public SimpleEntiyBaseFacade(
		    final InterfacePublisherSerice publisherService, 
		    final InterfaceI18nService i18nService, 
		    final InterfaceSecurityService securityService,
		    final InterfaceExceptionTranslatorService exceptionTranslatorService) {
	
	super(publisherService, i18nService, securityService, exceptionTranslatorService);
    }

    public SimpleEntiyBaseFacade() {
	super();
    }
}
