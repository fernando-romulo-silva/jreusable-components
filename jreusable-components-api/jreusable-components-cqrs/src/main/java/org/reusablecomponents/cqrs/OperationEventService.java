package org.reusablecomponents.cqrs;

import static org.reusablecomponents.base.core.infra.util.MethodUtil.checkI18nServiceIsNull;
import static org.reusablecomponents.base.core.infra.util.MethodUtil.checkObjectIsNull;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;
import org.reusablecomponents.messaging.InterfaceEventPublisherSerice;
import org.reusablecomponents.messaging.event.Event;
import org.reusablecomponents.messaging.event.EventStatus;
import org.reusablecomponents.messaging.event.What;
import org.reusablecomponents.messaging.event.When;
import org.reusablecomponents.messaging.event.Where;
import org.reusablecomponents.messaging.event.Who;
import org.reusablecomponents.messaging.event.Why;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * Service to handle operation events.
 */
public class OperationEventService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OperationEventService.class);

	/**
	 * The event publisher service.
	 */
	protected final InterfaceEventPublisherSerice<?> publisherService;

	/**
	 * The security service.
	 */
	protected final InterfaceSecurityService securityService;

	/**
	 * The internationalization service.
	 */
	protected final InterfaceI18nService i18nService;

	/**
	 * Default constructor
	 * 
	 * @param newPublisherService The event publisher service.
	 * @param newI18nService      The internationalization service.
	 * @param newSecurityService  The security service.
	 */
	public OperationEventService(
			final InterfaceEventPublisherSerice<?> newPublisherService,
			final InterfaceI18nService newI18nService,
			final InterfaceSecurityService newSecurityService) {
		super();
		checkI18nServiceIsNull(newI18nService);
		checkObjectIsNull(newSecurityService, "securityService", newI18nService);
		checkObjectIsNull(newPublisherService, "publisherService", newI18nService);

		this.i18nService = newI18nService;
		this.securityService = newSecurityService;
		this.publisherService = newPublisherService;

		LOGGER.atDebug().log(
				"Constructed OperationEventService with publisherService '{}', i18nService '{}', securityService '{}'",
				newPublisherService.getClass().getSimpleName(),
				newI18nService.getClass().getSimpleName(),
				newSecurityService.getClass().getSimpleName());
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
			final EventStatus status,
			final OperationFunction operation) {
		LOGGER.atDebug().log("Creating event with operation '{}', origin '{}', status '{}', dataIn '{}', dataOut '{}'",
				operation, origin, status, dataIn, dataOut);

		checkObjectIsNull(operation, "operation", i18nService);
		checkObjectIsNull(status, "status", i18nService);

		final var user = securityService.getUserName();
		final var realm = securityService.getUserRealm();
		final var session = securityService.getSession();
		final var machineName = securityService.getMachineName();
		final var application = securityService.getApplication();
		final var version = securityService.getVersion();
		final var build = securityService.getBuild();
		final var descriptor = securityService.getDescriptor();

		final var event = new Event.Builder().with($ -> {
			$.status = status;
			$.origin = origin;
			$.what = new What(dataIn, dataOut);
			$.when = new When();
			$.where = new Where(machineName, application, version, build, descriptor);
			$.who = new Who(realm, user, session);
			$.why = new Why(operation.getName(), operation.getDescription());
		}).build();

		LOGGER.atDebug().log("Event '{}' created", event.getId());
		return event;
	}

	/**
	 * Publish the event on the messaging mechanism.
	 * 
	 * @param event The object the caller wants publish
	 */
	protected void publishEvent(final Event event) {
		checkObjectIsNull(event, "event", i18nService);

		LOGGER.atDebug().log("Publishing event '{}'", event.getId());
		try {
			publisherService.publish(event);
			LOGGER.atDebug().log("Published event '{}'", event.getId());
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
			final EventStatus status,
			final OperationFunction operation) {
		LOGGER.atDebug().log("Publishing event with operation '{}'", operation);

		checkObjectIsNull(operation, "operation", i18nService);
		checkObjectIsNull(status, "status", i18nService);

		final var event = createEvent(dataIn, dataOut, origin, status, operation);
		try {
			publisherService.publish(event);
			LOGGER.atDebug().log("Send event '{}' to publish with operation '{}'", event.getId(), operation);
		} catch (final Exception ex) {
			LOGGER.error("Event not published due to {}", ExceptionUtils.getRootCauseMessage(ex));
		}
	}

	@NotNull
	public final InterfaceEventPublisherSerice<?> getPublisherService() {
		return publisherService;
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
