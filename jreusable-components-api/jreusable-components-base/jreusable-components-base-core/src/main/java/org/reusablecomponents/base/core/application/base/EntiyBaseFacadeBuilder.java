package org.reusablecomponents.base.core.application.base;

import static java.util.Objects.nonNull;

import java.util.function.Consumer;

import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.core.infra.exception.common.GenericException;
import org.reusablecomponents.base.messaging.InterfaceEventPublisherSerice;
import org.reusablecomponents.base.messaging.logger.LoggerPublisherSerice;
import org.reusablecomponents.base.security.DefaultSecurityService;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;
import org.reusablecomponents.base.translation.JavaSEI18nService;

public class EntiyBaseFacadeBuilder {

    /**
     * 
     * Message event service, in case of null, the
     * <code>LoggerPublisherSerice</code> will be used.
     */
    public InterfaceEventPublisherSerice<?> publisherService;

    /**
     * Security service, in case of null, the <code>DefaultSecurityService</code>
     * will be used.
     */
    public InterfaceSecurityService securityService;

    /**
     * Language translator service, in case of null, the
     * <code>JavaSEI18nService</code> will be used.
     */
    public InterfaceI18nService i18nService;

    /**
     * Exception adapter service, in case of null, a lambda function that throws
     * only the <code>GenericException</code>.
     */
    public InterfaceExceptionAdapterService exceptionAdapterService;

    public EntiyBaseFacadeBuilder(final Consumer<? extends EntiyBaseFacadeBuilder> function) {

        @SuppressWarnings("unchecked")
        final var finalFunction = (Consumer<EntiyBaseFacadeBuilder>) function;

        // load the functions
        finalFunction.accept(this);

        publisherService = nonNull(publisherService) ? publisherService : new LoggerPublisherSerice();

        i18nService = nonNull(i18nService) ? i18nService : new JavaSEI18nService();

        securityService = nonNull(securityService) ? securityService : new DefaultSecurityService();

        exceptionAdapterService = nonNull(exceptionAdapterService)
                ? exceptionAdapterService
                : (paramException, paramI18nService, directives) -> new GenericException(paramException);

        // checkNotNull(publisherService, "Please pass a non-null 'publisherService'");
        // checkNotNull(securityService, "Please pass a non-null 'securityService'");
        // checkNotNull(i18nService, "Please pass a non-null 'i18nService'");
        // checkNotNull(exceptionAdapterService, "Please pass a non-null
        // 'exceptionTranslatorService'");
    }
}
