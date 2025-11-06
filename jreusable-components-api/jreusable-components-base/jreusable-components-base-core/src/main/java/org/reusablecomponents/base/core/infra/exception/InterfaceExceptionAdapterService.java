package org.reusablecomponents.base.core.infra.exception;

import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.translation.InterfaceI18nService;

import jakarta.validation.constraints.NotNull;

/**
 * This service is responsible to converter persistence mechanism exceptions to
 * JReusable Components exceptions.
 */
@FunctionalInterface
public interface InterfaceExceptionAdapterService {

    /**
     * This method execute the conversion.
     * 
     * @param ex          The exception that needs to convert.
     * @param i18nService Translator message service.
     * @param directives  Params to help to convert
     * @return A new exception, a <code>BaseApplicationException</code> descendent.
     */
    BaseException convert(
            @NotNull final Exception ex,
            @NotNull final InterfaceI18nService i18nService,
            @NotNull final Object... directives);
}
