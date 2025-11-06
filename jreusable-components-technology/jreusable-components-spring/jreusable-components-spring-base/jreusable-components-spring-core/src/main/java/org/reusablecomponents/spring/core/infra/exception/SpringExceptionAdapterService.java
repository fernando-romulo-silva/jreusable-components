package org.reusablecomponents.spring.core.infra.exception;

import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.translation.InterfaceI18nService;
import org.springframework.stereotype.Component;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;

/**
 * 
 */
@Component
public class SpringExceptionAdapterService implements InterfaceExceptionAdapterService {

    /**
     * {@inheritDoc}
     */
    public BaseException convert(
            final Exception ex,
            final InterfaceI18nService i18nService,
            final Object... directives) {

        return null;
    }

}
