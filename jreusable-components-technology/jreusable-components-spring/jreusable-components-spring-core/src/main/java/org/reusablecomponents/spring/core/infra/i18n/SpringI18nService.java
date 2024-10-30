package org.reusablecomponents.spring.core.infra.i18n;

import org.reusablecomponents.base.translation.InterfaceI18nService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SpringI18nService implements InterfaceI18nService {

    private final MessageSource messageSource;
    
    protected SpringI18nService(final MessageSource messageSource) {
	super();
	this.messageSource = messageSource;
    }

    @Override
    public String translate(final String code, final Object... params) {

	final var locale = LocaleContextHolder.getLocale();

	return messageSource.getMessage(code, params, locale);
    }
}
