package org.reusablecomponents.messaging.event;

import java.util.Objects;

public record Where(String machine, String build, String application, String version) {

	public Where {

		if (Objects.isNull(machine)) {
			throw new IllegalArgumentException("The parameter 'machine' cannot be null");
		}

		if (Objects.isNull(build)) {
			throw new IllegalArgumentException("The parameter 'descriptor' cannot be null");
		}

		if (Objects.isNull(application)) {
			throw new IllegalArgumentException("The parameter 'application' cannot be null");
		}

		if (Objects.isNull(version)) {
			throw new IllegalArgumentException("The parameter 'version' cannot be null");
		}
	}
}
