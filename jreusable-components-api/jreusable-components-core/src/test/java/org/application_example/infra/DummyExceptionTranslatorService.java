package org.application_example.infra;

import org.reusablecomponent.core.infra.exception.ExceptionTranslatorService;
import org.reusablecomponent.core.infra.i18n.InterfaceI18nService;

import jakarta.validation.constraints.NotNull;

public class DummyExceptionTranslatorService implements ExceptionTranslatorService {
    
    /**
     * {@inheritDoc}
     */
    @Override
    public RuntimeException translate(@NotNull Exception in, @NotNull InterfaceI18nService I18nService) {
	return new RuntimeException(in);
    }
}
