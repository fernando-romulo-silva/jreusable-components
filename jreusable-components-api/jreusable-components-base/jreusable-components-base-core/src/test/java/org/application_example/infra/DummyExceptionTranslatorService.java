package org.application_example.infra;

import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.core.infra.exception.common.BaseApplicationException;
import org.reusablecomponents.base.translation.InterfaceI18nService;

public class DummyExceptionTranslatorService implements InterfaceExceptionAdapterService {

    public static class GenericError extends BaseApplicationException {

        public GenericError() {
            super("Generic error");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseApplicationException convert(
            final Exception ex,
            final InterfaceI18nService i18nService,
            final Object... directives) {
        return new GenericError();
    }
}
