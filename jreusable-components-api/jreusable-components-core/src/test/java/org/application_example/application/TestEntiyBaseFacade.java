package org.application_example.application;

import org.application_example.domain.Department;
import org.reusablecomponent.core.application.empty.SimpleEntiyBaseFacade;
import org.reusablecomponent.core.infra.exception.InterfaceExceptionTranslatorService;
import org.reusablecomponent.core.infra.i18n.InterfaceI18nService;
import org.reusablecomponent.core.infra.security.InterfaceSecurityService;
import org.reusablecomponent.messaging.InterfacePublisherSerice;

import jakarta.annotation.Nullable;

public class TestEntiyBaseFacade extends SimpleEntiyBaseFacade<Department, String> {
	
    public TestEntiyBaseFacade(
		    @Nullable final InterfacePublisherSerice publisherService, 
		    @Nullable final InterfaceI18nService i18nService,
		    @Nullable final InterfaceSecurityService securityService,
		    @Nullable final InterfaceExceptionTranslatorService exceptionTranslatorService) {
	super(publisherService, i18nService, securityService, exceptionTranslatorService);
    }
    
    public TestEntiyBaseFacade() {
	super();
    }
}
