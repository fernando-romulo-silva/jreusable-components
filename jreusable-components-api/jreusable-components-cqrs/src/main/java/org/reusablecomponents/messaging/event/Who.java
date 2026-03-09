package org.reusablecomponents.messaging.event;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.Objects;

/**
 * Represents the actor behind an event.
 */
public record Who(String realm, String login, String session) {

	/**
	 * Constructor with validation.
	 *
	 * @param realm   The realm of the actor.
	 * @param login   The login of the actor.
	 * @param session The session of the actor.
	 */
	public Who {
		realm = Objects.nonNull(realm) ? realm : EMPTY;
		session = Objects.nonNull(session) ? session : EMPTY;

		if (Objects.isNull(login)) {
			throw new IllegalArgumentException("The parameter 'login' cannot be null");
		}
	}

	/**
	 * Default constructor setting empty realm, login and session.
	 */
	public Who(String login) {
		this(null, login, null);
	}
}
