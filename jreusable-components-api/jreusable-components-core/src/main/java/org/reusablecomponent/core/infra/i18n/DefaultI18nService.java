package org.reusablecomponent.core.infra.i18n;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang3.stream.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultI18nService implements InterfaceI18nService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultI18nService.class);
    
    @Override
    public String translate(final String code, final Object... params) {

	final var paramsString = Streams.of(params)
		.map(obj -> nonNull(obj) ? obj.toString() : EMPTY)
		.collect(joining(","));
	
	LOGGER.debug("Translate msg code '{}', params '{}'", code, paramsString);

	final var locale = Locale.getDefault();
	final var resourceBundle = ResourceBundle.getBundle("messages", locale);

	final var txt = resourceBundle.getString(code);

	final var msg = MessageFormat.format(txt, params);

	LOGGER.debug("Translated msg '{}' with code '{}', params '{}', and locale '{}'", msg, code, paramsString, locale);
	
	return msg;
    }
}
