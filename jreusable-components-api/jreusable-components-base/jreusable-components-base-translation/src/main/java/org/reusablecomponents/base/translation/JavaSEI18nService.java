package org.reusablecomponents.base.translation;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.text.MessageFormat;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.stream.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JDK version of <code>InterfaceI18nService</code>. <br />
 * This version use the Java SE code to translate error messages.
 * 
 * @author Fernando Romulo da Silva
 */
public class JavaSEI18nService implements InterfaceI18nService {

	private static final Logger LOGGER = LoggerFactory.getLogger(JavaSEI18nService.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String translate(final String code, final Object... params) {

		final var paramsString = Streams.of(params)
				.map(obj -> nonNull(obj) ? obj.toString() : EMPTY)
				.collect(joining(","));

		if (StringUtils.containsNone(code, '{', '}')) {
			return code;
		}

		final var finalCode = RegExUtils.replaceAll(code, "[{}]", "");

		LOGGER.trace("Translate msg code '{}', finalCode '{}', params '{}'", code, finalCode, paramsString);

		final var locale = Locale.getDefault();
		final var resourceBundle = ResourceBundle.getBundle("messages", locale);

		final var txt = resourceBundle.getString(finalCode);

		final var msg = MessageFormat.format(txt, params);

		final var debugMsg = "Translated msg '{}' with code '{}', params '{}', and locale '{}'";

		LOGGER.trace(debugMsg, msg, code, paramsString, locale);

		return msg;
	}
}
