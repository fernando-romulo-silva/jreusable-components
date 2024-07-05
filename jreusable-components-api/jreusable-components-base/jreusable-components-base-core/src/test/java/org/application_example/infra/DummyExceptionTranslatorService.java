package org.application_example.infra;

import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionTranslatorService;
import org.reusablecomponents.base.core.infra.exception.common.BaseApplicationException;
import org.reusablecomponents.base.core.infra.exception.common.GenericException;
import org.reusablecomponents.base.translation.InterfaceI18nService;

import jakarta.validation.constraints.NotNull;

public class DummyExceptionTranslatorService implements InterfaceExceptionTranslatorService {
    
    /**
     * {@inheritDoc}
     */
    @Override
    public BaseApplicationException translate(@NotNull Exception in, @NotNull InterfaceI18nService I18nService) {
	return new GenericException(in);
    }
}
