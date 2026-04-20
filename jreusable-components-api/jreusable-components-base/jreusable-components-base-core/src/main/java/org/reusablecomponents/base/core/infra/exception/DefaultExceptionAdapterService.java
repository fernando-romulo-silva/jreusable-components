package org.reusablecomponents.base.core.infra.exception;

import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.translation.InterfaceI18nService;

import jakarta.validation.constraints.NotNull;

/**
 * Default <code>InterfaceExceptionAdapterService</code>'s implementation.
 * This implementation simply wraps the given exception into a
 * <code>BaseException</code>.
 * 
 * @see InterfaceExceptionAdapterService
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
public class DefaultExceptionAdapterService implements InterfaceExceptionAdapterService {

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseException convert(
            @NotNull final Exception ex,
            @NotNull final InterfaceI18nService i18nService,
            @NotNull final Object... directives) {
        return new BaseException(ex);
    }
}
