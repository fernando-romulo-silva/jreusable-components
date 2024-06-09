package org.reusablecomponent.core.application.empty;

import org.reusablecomponent.core.application.base.EntiyBaseFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.core.infra.exception.InterfaceExceptionTranslatorService;
import org.reusablecomponent.core.infra.i18n.InterfaceI18nService;
import org.reusablecomponent.core.infra.security.InterfaceSecurityService;
import org.reusablecomponent.messaging.InterfacePublisherSerice;

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
