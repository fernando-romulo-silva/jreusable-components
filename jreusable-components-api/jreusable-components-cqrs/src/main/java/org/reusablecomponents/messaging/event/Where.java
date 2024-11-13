package org.reusablecomponents.messaging.event;

import java.util.Objects;

public record Where(String machine, String application, String version, String descriptor) {

	public Where {

		if (Objects.isNull(machine)) {
			throw new IllegalArgumentException("The parameter 'machine' cannot be null");
		}

		if (Objects.isNull(application)) {
			throw new IllegalArgumentException("The parameter 'application' cannot be null");
		}

		if (Objects.isNull(version)) {
			throw new IllegalArgumentException("The parameter 'version' cannot be null");
		}

		if (Objects.isNull(descriptor)) {
			throw new IllegalArgumentException("The parameter 'descriptor' cannot be null");
		}
	}
}
