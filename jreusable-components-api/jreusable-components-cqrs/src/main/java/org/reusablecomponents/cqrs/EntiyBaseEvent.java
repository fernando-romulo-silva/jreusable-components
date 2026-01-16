package org.reusablecomponents.cqrs;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;
import static org.reusablecomponents.base.core.infra.constants.ExceptionMessages.NULL_POINTER_EXCEPTION_MSG;

import java.util.function.Function;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;
import org.reusablecomponents.messaging.InterfaceEventPublisherSerice;
import org.reusablecomponents.messaging.event.Event;
import org.reusablecomponents.messaging.event.InterfaceEventStatus;
import org.reusablecomponents.messaging.event.What;
import org.reusablecomponents.messaging.event.When;
import org.reusablecomponents.messaging.event.Where;
import org.reusablecomponents.messaging.event.Who;
import org.reusablecomponents.messaging.event.Why;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * Base class for envent
 */
public class EntiyBaseEvent {

	private static final Logger LOGGER = LoggerFactory.getLogger(EntiyBaseEvent.class);

	protected final InterfaceEventPublisherSerice<?> publisherService;

	protected final InterfaceSecurityService securityService;

	protected final InterfaceI18nService i18nService;

	protected final InterfaceExceptionAdapterService exceptionAdapterService;

	/**
	 * Default constructor
	 * 
	 * @param builder Object attribute constructor.
	 */
	protected EntiyBaseEvent(final EntiyBaseEventBuilder builder) {
		super();
		this.publisherService = builder.publisherService;
		this.i18nService = builder.i18nService;
		this.securityService = builder.securityService;
		this.exceptionAdapterService = builder.exceptionAdapterService;
	}

	/**
	 * Check if it should send action events based on its directives.
	 * 
	 * @param directives Specific configurations are needed to validate whether it
	 *                   sends events or not.
	 * 
	 * @return True if the caller wants to publish default events or false (default)
	 *         if not.
	 */
	public boolean isPublishEvents(final Object... directives) {
		return true;
	}

	/**
	 * Create an event to send it to a message service.
	 * 
	 * @param dataIn    The input data
	 * @param dataOut   The output data
	 * @param origin    The origin's message
	 * @param status    The operation status, for example failure, success, etc.
	 * @param operation The operation performed
	 * 
	 * @return A <code>Event</code> object
	 */
	public Event createEvent(
			final String dataIn,
			final String dataOut,
			final String origin,
			final InterfaceEventStatus status,
			final OperationFunction operation) {
		LOGGER.debug("Creating event with '{}' operation", operation);

		if (ObjectUtils.isEmpty(operation)) {
			throw new NullPointerException(i18nService.translate(NULL_POINTER_EXCEPTION_MSG, "operation"));
		}

		final var user = securityService.getUserName();
		final var realm = securityService.getUserRealm();
		final var session = securityService.getSession();
		final var application = securityService.getApplication();
		final var machineName = securityService.getMachineName();
		final var version = securityService.getVersion();
		final var descriptor = securityService.getDescriptor();

		final var event = new Event.Builder().with($ -> {
			$.status = status;
			$.origin = origin;
			$.what = new What(dataIn, dataOut);
			$.when = new When();
			$.where = new Where(machineName, application, version, descriptor);
			$.who = new Who(realm, user, session);
			$.why = new Why(operation.getName(), operation.getDescription());
		}).build();

		LOGGER.debug("Event '{}' created with operation '{}'", event.getId(), operation);
		return event;
	}

	/**
	 * Publish the event on the messaging mechanism.
	 * 
	 * @param event The object the caller wants publish
	 */
	protected void publishEvent(final Event event) {
		if (ObjectUtils.isEmpty(event)) {
			throw new NullPointerException(i18nService.translate(NULL_POINTER_EXCEPTION_MSG, "event"));
		}

		LOGGER.debug("Publishing event '{}'", event.getId());
		try {
			publisherService.publish(event);
			LOGGER.debug("Published event '{}'", event.getId());
		} catch (final Exception ex) {
			LOGGER.error("Event {} not published due to {}", event.getId(), ExceptionUtils.getRootCauseMessage(ex));
		}
	}

	/**
	 * Send the event to publish on the messaging mechanism.
	 * It's a async operation.
	 * 
	 * @param dataIn    The function that generates the input event data
	 * @param dataOut   The function that generates the output event data
	 * @param origin    The event id that triggers this event
	 * @param status    The operation status: failure, success, etc.
	 * @param operation The operation performed
	 */
	public void publishEvent(
			final String dataIn,
			final String dataOut,
			final String origin,
			final InterfaceEventStatus status,
			final OperationFunction operation) {
		LOGGER.debug("Publishing event with operation '{}'", operation);

		if (ObjectUtils.isEmpty(operation)) {
			throw new NullPointerException(i18nService.translate(NULL_POINTER_EXCEPTION_MSG, "operation"));
		}

		final var event = createEvent(dataIn, dataOut, origin, status, operation);
		try {
			publisherService.publish(event);
			LOGGER.debug("Send event '{}' to publish with operation '{}'", event.getId(), operation);
		} catch (final Exception ex) {
			LOGGER.error("Event not published due to {}", ExceptionUtils.getRootCauseMessage(ex));
		}
	}

	/**
	 * 
	 * @param <In>
	 * @param <Out>
	 * @param in
	 * @param out
	 * @param operation
	 * @param inToMsgFunction
	 * @param outToMsgFunction
	 * @param directives
	 */
	protected <In, Out> void publishCommandEvent(
			final In in,
			final Out out,
			final OperationFunction operation,
			final InterfaceEventStatus status,
			final Function<In, String> inToMsgFunction,
			final Function<Out, String> outToMsgFunction,
			final Object... directives) {

		if (!isPublishEvents(directives)) {
			LOGGER.debug(
					"Publishing {} event was avoided, in '{}', out '{}', and directives '{}'",
					operation.getName(), in, out, directives);
			return;
		}

		LOGGER.debug("Publishing {} event, in '{}', out '{}', and directives '{}'",
				operation.getName(), in, out, directives);

		try {
			final var dataIn = inToMsgFunction.apply(in);
			final var dataOut = outToMsgFunction.apply(out);

			publishEvent(dataIn, dataOut, EMPTY, status, operation);

			LOGGER.debug("The {} event was published, dataIn '{}' and dataOut '{}'",
					operation.getName(), dataIn, dataOut);

		} catch (final Exception ex) {
			LOGGER.error("The {} event was not published".formatted(operation.getName()), getRootCause(ex));
		}
	}

	/**
	 * publishCommandEvent(
	 * saveEntityIn,
	 * saveEntityOut,
	 * SaveOperation,
	 * object -> Objects.toString(object),
	 * object -> Objects.toString(object),
	 * directives
	 * );
	 */

	@NotNull
	public final InterfaceEventPublisherSerice<?> getPublisherService() {
		return publisherService;
	}

	@NotNull
	public InterfaceExceptionAdapterService getExceptionAdapterService() {
		return exceptionAdapterService;
	}

	@NotNull
	public InterfaceSecurityService getSecurityService() {
		return securityService;
	}

	@NotNull
	public InterfaceI18nService getI18nService() {
		return i18nService;
	}
}
