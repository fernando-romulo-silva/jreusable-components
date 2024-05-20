package org.application_example.application;

import org.application_example.domain.Department;
import org.reusablecomponent.core.application.base.AbstractEntiyBaseFacade;
import org.reusablecomponent.core.infra.exception.ExceptionTranslatorService;
import org.reusablecomponent.core.infra.i18n.InterfaceI18nService;
import org.reusablecomponent.core.infra.security.InterfaceSecurityService;
import org.reusablecomponent.messaging.InterfacePublisherSerice;

import jakarta.annotation.Nullable;

public class TestEntiyBaseFacade extends AbstractEntiyBaseFacade<Department, String> {
	
    public TestEntiyBaseFacade(
		    @Nullable final InterfacePublisherSerice publisherService, 
		    @Nullable final InterfaceI18nService i18nService,
		    @Nullable final InterfaceSecurityService securityService,
		    @Nullable final ExceptionTranslatorService exceptionTranslatorService) {
	super(publisherService, i18nService, securityService, exceptionTranslatorService);
    }
    
    public TestEntiyBaseFacade() {
	super();
    }
}
