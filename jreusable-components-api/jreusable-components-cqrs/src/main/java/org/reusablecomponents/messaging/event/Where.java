package org.reusablecomponents.messaging.event;

import java.util.Objects;

/**
 * Represents the location where an event occurred.
 */
public record Where(String machine, String application, String version, String build, String descriptor) {

	/**
	 * Constructor with validation.
	 *
	 * @param machine     The machine where the event occurred.
	 * @param application The application where the event occurred.
	 * @param version     The version of the application.
	 * @param build       The build of the application.
	 * @param descriptor  The descriptor of the location.
	 */
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

		if (Objects.isNull(build)) {
			throw new IllegalArgumentException("The parameter 'build' cannot be null");
		}

		if (Objects.isNull(descriptor)) {
			throw new IllegalArgumentException("The parameter 'descriptor' cannot be null");
		}
	}
}
