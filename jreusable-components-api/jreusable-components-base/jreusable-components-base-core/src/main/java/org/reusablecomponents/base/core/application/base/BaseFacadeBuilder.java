package org.reusablecomponents.base.core.application.base;

import static java.util.Objects.nonNull;

import java.util.function.Consumer;

import org.reusablecomponents.base.core.infra.exception.DefaultExceptionAdapterService;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.security.DefaultSecurityService;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;
import org.reusablecomponents.base.translation.JavaSEI18nService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The <code>EntiyBaseFacade</code> builder's class.
 */
public class BaseFacadeBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseFacadeBuilder.class);

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
     * Exception adapter service, in case of null, the
     * <code>DefaultExceptionAdapterService</code> will be used.
     */
    public InterfaceExceptionAdapterService exceptionAdapterService;

    /**
     * Default constructor
     * 
     * @param function Consumer function
     */
    public BaseFacadeBuilder(final Consumer<? extends BaseFacadeBuilder> function) {
        LOGGER.debug("Constructing BaseFacadeBuilder");

        @SuppressWarnings("unchecked")
        final var finalFunction = (Consumer<BaseFacadeBuilder>) function;

        finalFunction.accept(this);

        i18nService = nonNull(i18nService)
                ? i18nService
                : new JavaSEI18nService();

        securityService = nonNull(securityService)
                ? securityService
                : new DefaultSecurityService();

        exceptionAdapterService = nonNull(exceptionAdapterService)
                ? exceptionAdapterService
                : new DefaultExceptionAdapterService();

        LOGGER.debug("BaseFacadeBuilder constructed");
    }
}
