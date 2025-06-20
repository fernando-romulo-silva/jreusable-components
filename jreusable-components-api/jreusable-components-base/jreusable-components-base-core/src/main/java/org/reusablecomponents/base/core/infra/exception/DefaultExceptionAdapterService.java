package org.reusablecomponents.base.core.infra.exception;

import org.reusablecomponents.base.core.infra.exception.common.BaseApplicationException;
import org.reusablecomponents.base.translation.InterfaceI18nService;

import jakarta.validation.constraints.NotNull;

/**
 * Default <code>InterfaceExceptionAdapterService</code>'s implementation.
 */
public class DefaultExceptionAdapterService implements InterfaceExceptionAdapterService {

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseApplicationException convert(
            @NotNull final Exception ex,
            @NotNull final InterfaceI18nService i18nService,
            @NotNull final Object... directives) {
        return new BaseApplicationException(ex);
    }
}
