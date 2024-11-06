package org.application_example.infra;

import static java.util.stream.Collectors.joining;

import java.util.Objects;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.reusablecomponents.base.translation.InterfaceI18nService;

public class DummyI18nService implements InterfaceI18nService {

	@Override
	public String translate(final String code, final Object... params) {

		if (Objects.isNull(code)) {
			return StringUtils.EMPTY;
		}

		final var paramsString = Stream.of(params)
				.map(Object::toString)
				.collect(joining(","));

		return code.concat(" ").concat(paramsString);
	}
}
