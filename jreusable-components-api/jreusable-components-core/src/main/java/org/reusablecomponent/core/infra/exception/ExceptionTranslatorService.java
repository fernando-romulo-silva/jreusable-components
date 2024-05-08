package org.reusablecomponent.core.infra.exception;

import org.reusablecomponent.core.infra.i18n.InterfaceI18nService;

import jakarta.validation.constraints.NotNull;

/**
 * Exception translator, 
 * 
 * @param <In>
 * @param <Out>
 */
@FunctionalInterface
public interface ExceptionTranslatorService {

    /**
     * @param ex
     * @param I18nService
     * @return
     */
    RuntimeException translate(@NotNull final Exception ex, @NotNull final InterfaceI18nService I18nService);
}
