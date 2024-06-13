package org.reusablecomponents.core.infra.exception;

import org.reusablecomponents.core.infra.exception.common.BaseApplicationException;
import org.reusablecomponents.core.infra.i18n.InterfaceI18nService;

import jakarta.validation.constraints.NotNull;

/**
 * Exception translator, 
 * 
 * @param <In>
 * @param <Out>
 */
@FunctionalInterface
public interface InterfaceExceptionTranslatorService {

    /**
     * @param ex
     * @param I18nService
     * @return
     */
    BaseApplicationException translate(@NotNull final Exception ex, @NotNull final InterfaceI18nService I18nService);
}
