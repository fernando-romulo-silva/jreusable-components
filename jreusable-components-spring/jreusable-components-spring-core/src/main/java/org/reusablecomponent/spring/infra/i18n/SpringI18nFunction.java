package org.reusablecomponent.spring.infra.i18n;

import org.reusablecomponent.infra.i18n.I18nFuntion;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SpringI18nFunction implements I18nFuntion {

    private final MessageSource messageSource;
    
    protected SpringI18nFunction(final MessageSource messageSource) {
	super();
	this.messageSource = messageSource;
    }

    @Override
    public String translate(final String code, final Object... params) {

	final var locale = LocaleContextHolder.getLocale();

	return messageSource.getMessage(code, params, locale);
    }
}
