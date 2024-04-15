package org.reusablecomponent.core.application.base;

import org.reusablecomponent.core.domain.Department;
import org.reusablecomponent.core.infra.i18n.InterfaceI18nService;
import org.reusablecomponent.core.infra.messaging.InterfacePublisherSerice;
import org.reusablecomponent.core.infra.security.InterfaceSecurityService;

import jakarta.annotation.Nullable;

class TestEntiyBaseFacade extends AbstractEntiyBaseFacade<Department, String> {
	
    protected TestEntiyBaseFacade(
		    @Nullable final InterfacePublisherSerice publisherService, 
		    @Nullable final InterfaceI18nService i18nService,
		    @Nullable final InterfaceSecurityService securityService) {
	super(publisherService, i18nService, securityService);
    }
    
    protected TestEntiyBaseFacade() {
	super();
    }
}
