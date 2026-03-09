package org.reusablecomponents.messaging.event;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.Objects;

/**
 * Represents the cause behind an event.
 */
public record Why(String reason, String description) {

	/**
	 * Constructor with validation.
	 *
	 * @param reason      The reason for the event.
	 * @param description The description of the reason.
	 */
	public Why {
		description = Objects.nonNull(description) ? description : EMPTY;

		if (Objects.isNull(reason)) {
			throw new IllegalArgumentException("The parameter 'reason' cannot be null");
		}
	}

	/**
	 * Default constructor setting empty reason and description.
	 */
	public Why(String reason) {
		this(reason, null);
	}
}
