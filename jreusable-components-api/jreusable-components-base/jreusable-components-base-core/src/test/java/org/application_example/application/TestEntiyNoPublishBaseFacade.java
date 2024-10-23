package org.application_example.application;

import org.application_example.domain.Department;
import org.reusablecomponents.base.core.application.empty.SimpleEntiyBaseFacade;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.messaging.InterfaceEventPublisherSerice;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;

import jakarta.annotation.Nullable;

public class TestEntiyNoPublishBaseFacade extends SimpleEntiyBaseFacade<Department, String> {

    public TestEntiyNoPublishBaseFacade(
            @Nullable final InterfaceEventPublisherSerice<?> publisherService,
            @Nullable final InterfaceI18nService i18nService,
            @Nullable final InterfaceSecurityService securityService,
            @Nullable final InterfaceExceptionAdapterService exceptionTranslatorService) {
        super(publisherService, i18nService, securityService, exceptionTranslatorService);
    }

    public TestEntiyNoPublishBaseFacade() {
        super();
    }
}
