package org.reusablecomponents.util;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;
import static org.reusablecomponents.base.core.infra.constants.ExceptionMessages.NULL_POINTER_EXCEPTION_MSG;
import static org.reusablecomponents.messaging.MessagingConst.JSON_LAYOUT;
import static org.reusablecomponents.base.core.infra.util.function.FunctionCommonUtils.createNullPointerException;

import org.apache.commons.lang3.StringUtils;
import org.reusablecomponents.messaging.event.Event;

public final class EventUtils {

	private EventUtils() {
		throw new UnsupportedOperationException("You can't instanciate this class, it is utility class");
	}

	/**
	 * Convert an event to string, the default format is JSON.
	 * You can override this method to produce string in another format.
	 * 
	 * @param event Object <code>Event</code>
	 * 
	 * @return A formatted string
	 */
	public static String prepareEventToPublisher(final Event event, final String layout) {

		var msg = StringUtils.deleteWhitespace(layout);

		// -----------------------------------------
		final var id = event.getId();
		final var status = event.getStatus();
		final var origin = event.getOrigin();

		msg = StringUtils.replaceEach(msg,
				new String[] { "${id}", "${status}", "${origin}" },
				new String[] { id, status.toString(), origin });

		// ------------------------------------------
		final var what = event.getWhat();
		final var dataIn = what.dataIn();
		final var dataOut = what.dataOut();

		msg = StringUtils.replaceEach(msg,
				new String[] { "${dataIn}", "${dataOut}" },
				new String[] { dataIn, dataOut });

		// ------------------------------------------
		final var when = event.getWhen();
		final var dateTime = ISO_DATE_TIME.format(when.dateTime());
		final var zoneId = when.zoneId().toString();

		msg = StringUtils.replaceEach(msg,
				new String[] { "${dateTime}", "${zoneId}" },
				new String[] { dateTime, zoneId });

		// ------------------------------------------
		final var where = event.getWhere();
		final var application = where.application();
		final var machine = where.machine();

		msg = StringUtils.replaceEach(msg,
				new String[] { "${application}", "${machine}" },
				new String[] { application, machine });

		// ------------------------------------------
		final var who = event.getWho();
		final var login = who.login();
		final var session = who.session();
		final var realm = who.realm();

		msg = StringUtils.replaceEach(msg,
				new String[] { "${login}", "${session}", "${realm}" },
				new String[] { login, session, realm });

		// ------------------------------------------
		final var why = event.getWhy();
		final var reason = why.reason();
		final var description = why.description();

		msg = StringUtils.replaceEach(msg,
				new String[] { "${reason}", "${description}" },
				new String[] { reason, description });

		return msg;
	}
}
