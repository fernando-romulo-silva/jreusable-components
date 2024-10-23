package org.reusablecomponents.base.core.application.base;

import static java.util.Objects.nonNull;

import java.util.function.Consumer;

import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.core.infra.exception.common.GenericException;
import org.reusablecomponents.base.security.DefaultSecurityService;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;
import org.reusablecomponents.base.translation.JavaSEI18nService;

/**
 * The <code>EntiyBaseFacade</code> builder's class.
 */
public class EntiyBaseFacadeBuilder {

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

    /**
     * Default constructor
     * 
     * @param function Consumer function
     */
    public EntiyBaseFacadeBuilder(final Consumer<? extends EntiyBaseFacadeBuilder> function) {

        @SuppressWarnings("unchecked")
        final var finalFunction = (Consumer<EntiyBaseFacadeBuilder>) function;

        finalFunction.accept(this);

        i18nService = nonNull(i18nService) ? i18nService : new JavaSEI18nService();

        securityService = nonNull(securityService) ? securityService : new DefaultSecurityService();

        exceptionAdapterService = nonNull(exceptionAdapterService)
                ? exceptionAdapterService
                : (paramException, paramI18nService, directives) -> new GenericException(paramException);

    }
}
