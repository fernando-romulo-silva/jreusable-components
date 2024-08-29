package org.application_example.application;

import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.core.infra.exception.common.BaseApplicationException;
import org.reusablecomponents.base.translation.InterfaceI18nService;

public class ExceptionAdapterListService implements InterfaceExceptionAdapterService {

    @Override
    public BaseApplicationException convert(
            final Exception ex,
            final InterfaceI18nService i18nService,
            final Object... directives) {

        throw new UnsupportedOperationException("Unimplemented method 'convert'");
    }

}