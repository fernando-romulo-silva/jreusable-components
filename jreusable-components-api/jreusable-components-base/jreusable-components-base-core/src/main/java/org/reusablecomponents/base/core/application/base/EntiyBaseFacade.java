package org.reusablecomponents.base.core.application.base;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static java.util.Optional.ofNullable;
import static org.reusablecomponents.base.core.infra.messages.SystemMessages.NULL_POINTER_EXCEPTION_MSG;

import java.util.Optional;
import java.util.function.Supplier;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.reusablecomponents.base.core.application.command.entity.EntityCommandFacade;
import org.reusablecomponents.base.core.application.empty.SimpleEntiyBaseFacade;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.EntityQueryFacade;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.EntityQuerySpecificationFacade;
import org.reusablecomponents.base.core.application.query.entity.paged.EntityQueryPaginationFacade;
import org.reusablecomponents.base.core.application.query.entity.paged.EntityQueryPaginationSpecificationFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.messaging.InterfaceEventPublisherSerice;
import org.reusablecomponents.base.messaging.event.Event;
import org.reusablecomponents.base.messaging.event.What;
import org.reusablecomponents.base.messaging.event.When;
import org.reusablecomponents.base.messaging.event.Where;
import org.reusablecomponents.base.messaging.event.Who;
import org.reusablecomponents.base.messaging.event.Why;
import org.reusablecomponents.base.messaging.operation.InterfaceOperation;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.TypeToken;

import jakarta.validation.constraints.NotNull;

/**
 * The <code>InterfaceEntityBaseFacade</code> common implementation using
 * event-driven architecture.
 * 
 * @param <Entity> The facade entity type
 * @param <Id>     The facade entity id type
 */
public sealed class EntiyBaseFacade<Entity extends AbstractEntity<Id>, Id>
		implements InterfaceEntityBaseFacade<Entity, Id>
		permits SimpleEntiyBaseFacade, EntityCommandFacade, EntityQueryFacade,
		EntityQuerySpecificationFacade, EntityQueryPaginationFacade, EntityQueryPaginationSpecificationFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(EntiyBaseFacade.class);

	private static final String JSON_LAYOUT = """
			{
			   "id": "${id}",
			   "what": {
				"dataIn" : "${dataIn}",
				"dataOut" : "${dataOut}"
			   },
			   "when": {
				"dateTime" : "${dateTime}",
				"zoneId" : "${zoneId}"
			   },
			   "where": {
				"application" : "${application}",
				"machine" : "${machine}"
			   },
			   "who": {
				"login" : "${login}",
				"session" : "${session}",
				"realm" : "${realm}"
			   },
			   "why": {
			        "reason" : "${reason}",
			        "description" : "${description}"
			   }
			}""";

	// -------

	protected final InterfaceEventPublisherSerice<?> publisherService;

	protected final InterfaceSecurityService securityService;

	protected final InterfaceI18nService i18nService;

	protected final InterfaceExceptionAdapterService exceptionAdapterService;

	protected final Class<Entity> entityClazz;

	protected final Class<Id> idClazz;

	// ------

	/**
	 * Default constructor
	 * 
	 * @param builder Object attribute constructor.
	 */
	protected EntiyBaseFacade(final EntiyBaseFacadeBuilder builder) {
		super();

		this.entityClazz = retrieveEntityClazz();
		this.idClazz = retrieveIdClazz();

		this.publisherService = builder.publisherService;
		this.i18nService = builder.i18nService;
		this.securityService = builder.securityService;
		this.exceptionAdapterService = builder.exceptionAdapterService;
	}

	// ------

	/**
	 * Capture the entity class type
	 * 
	 * @return A <code>Class<Entity></code> object
	 */
	@SuppressWarnings("unchecked")
	private final Class<Entity> retrieveEntityClazz() {

		final var entityTypeToken = new TypeToken<Entity>(getClass()) {
			private static final long serialVersionUID = 1L;
		};

		return (Class<Entity>) entityTypeToken.getRawType();
	}

	/**
	 * Capture the entity id class type
	 * 
	 * @return A <code>Class<Id></code> object
	 */
	@SuppressWarnings("unchecked")
	private final Class<Id> retrieveIdClazz() {

		final var idTypeToken = new TypeToken<Id>(getClass()) {
			private static final long serialVersionUID = 1L;
		};

		return (Class<Id>) idTypeToken.getRawType();
	}

	/**
	 * Check if it should send action events based on its directives.
	 * 
	 * @param directives Specific configurations are needed to validate whether it
	 *                   sends events or not.
	 * 
	 * @return True (default) if the caller wants to publish default events or false
	 *         if not.
	 */
	protected boolean isPublishEvents(final Object... directives) {
		return true;
	}

	/**
	 * Create an event to send it to a message service.
	 * 
	 * @param dataIn    The input data
	 * @param dataOut   The output data
	 * @param operation The operation performed
	 * 
	 * @return A <code>Event</code> object
	 */
	protected Event createEvent(final String dataIn, final String dataOut, final InterfaceOperation operation) {

		LOGGER.debug("Creating event with '{}' operation", operation);

		ofNullable(operation)
				.orElseThrow(createNullPointerException("operation"));

		final var user = securityService.getUserName();
		final var realm = securityService.getUserRealm();
		final var session = securityService.getSession();
		final var application = securityService.getApplication();
		final var machineName = securityService.getMachineName();

		final var event = new Event.Builder().with($ -> {
			$.what = new What(dataIn, dataOut);
			$.when = new When();
			$.where = new Where(application, machineName);
			$.who = new Who(realm, user, session);
			$.why = new Why(operation.getName(), operation.getDescription());
		}).build();

		LOGGER.debug("Event '{}' created with operation '{}'", event.getId(), operation);

		return event;
	}

	/**
	 * Publish the event on the messaging mechanism.
	 * 
	 * @param event      The object the caller wants publish
	 * @param directives Specific configurations are needed to send the event
	 */
	protected void publishEvent(final Event event, final Object... directives) {

		final var finalEvent = Optional.of(event).orElseThrow(
				() -> new NullPointerException(i18nService.translate(NULL_POINTER_EXCEPTION_MSG, "event")));

		final var finalDirectives = Optional.of(directives).orElseThrow(
				() -> new NullPointerException(i18nService.translate(NULL_POINTER_EXCEPTION_MSG, "directives")));

		LOGGER.debug("Publishing event '{}'", finalEvent.getId());

		if (!isPublishEvents(finalDirectives)) {
			LOGGER.debug("Event publishing '{}' avoided", event.getId());
			return;
		}

		final var eventToPublish = prepareEventToPublisher(event);

		try {
			publisherService.publish(eventToPublish);
		} catch (final Exception ex) {
			LOGGER.error(ExceptionUtils.getRootCauseMessage(ex), ex);
			return;
		}

		LOGGER.debug("Published event '{}'", finalEvent.getId());
	}

	/**
	 * Publish the event on the messaging mechanism.
	 * 
	 * @param event      The string message supplier that produce the event the
	 *                   caller want to send
	 * @param directives Specific configurations are needed to send the event
	 */
	protected void publishEvent(final Supplier<String> event, final Object... directives) {

		final var finalEvent = Optional.of(event).orElseThrow(
				() -> new NullPointerException(i18nService.translate(NULL_POINTER_EXCEPTION_MSG, "event")));

		final var finalDirectives = Optional.of(directives).orElseThrow(
				() -> new NullPointerException(i18nService.translate(NULL_POINTER_EXCEPTION_MSG, "directives")));

		LOGGER.debug("Publishing event '{}'", event);

		if (!isPublishEvents(finalDirectives)) {
			LOGGER.debug("Event publishing {} avoided", event);
			return;
		}

		Validate.isTrue(!StringUtils.isBlank(finalEvent.get()), "The argument 'event' cannot be null or blank");

		try {
			publisherService.publish(finalEvent.get());
		} catch (final Exception ex) {
			LOGGER.error(ExceptionUtils.getRootCauseMessage(ex), ex);
		}

		LOGGER.debug("Published event '{}'", event);
	}

	/**
	 * Send the event to publish on the messaging mechanism.
	 * It's a async operation.
	 * 
	 * @param dataIn     The function that generates the input event data
	 * @param dataOut    The function that generates the output event data
	 * @param operation  The operation performed
	 * @param directives Specific configurations are needed to publish the
	 *                   event
	 */
	protected void publishEvent(
			final Supplier<String> dataIn,
			final Supplier<String> dataOut,
			final InterfaceOperation operation,
			final Object... directives) {

		LOGGER.debug("Publishing event with operation '{}'", operation);

		final var finalOperation = Optional.of(operation).orElseThrow(
				() -> new NullPointerException(i18nService.translate(NULL_POINTER_EXCEPTION_MSG, "operation")));

		final var finalDirectives = Optional.of(directives).orElseThrow(
				() -> new NullPointerException(i18nService.translate(NULL_POINTER_EXCEPTION_MSG, "directives")));

		if (!isPublishEvents(finalDirectives)) {
			LOGGER.debug("Event publishing with operation '{}' avoided", finalOperation);
			return;
		}

		final var event = createEvent(dataIn.get(), dataOut.get(), finalOperation);

		final var eventToPublish = prepareEventToPublisher(event);

		try {
			publisherService.publish(eventToPublish);
		} catch (final Exception ex) {
			LOGGER.error(ExceptionUtils.getRootCauseMessage(ex), ex);
			return;
		}

		LOGGER.debug("Send event '{}' to publish with operation '{}'", event.getId(), operation);
	}

	/**
	 * Convert an event to string, the default format is JSON.
	 * You can override this method to produce string in another format.
	 * 
	 * @param event Object <code>Event</code>
	 * 
	 * @return A string formatted
	 */
	protected String prepareEventToPublisher(final Event event) {

		var msg = StringUtils.deleteWhitespace(JSON_LAYOUT);

		// -----------------------------------------
		final var id = event.getId();
		msg = StringUtils.replace(msg, "${id}", id);

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

	// ------

	/**
	 * Create a supplier function to generate null point exception when it's
	 * necessary.
	 * 
	 * @param parameters Parameters to show on message error
	 * 
	 * @return An object <code>Supplier<NullPointerException></code>
	 */
	protected final Supplier<NullPointerException> createNullPointerException(final String... parameters) {
		return () -> new NullPointerException(
				i18nService.translate(NULL_POINTER_EXCEPTION_MSG, (Object[]) parameters));
	}

	// ------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Class<Id> getIdClazz() {
		return idClazz;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Class<Entity> getEntityClazz() {
		return entityClazz;
	}

	@NotNull
	public final InterfaceEventPublisherSerice<?> getPublisherService() {
		return publisherService;
	}

	@NotNull
	public final InterfaceI18nService getI18nService() {
		return i18nService;
	}

	@NotNull
	public final InterfaceSecurityService getSecurityService() {
		return securityService;
	}

	@NotNull
	public final InterfaceExceptionAdapterService getExceptionTranslatorService() {
		return exceptionAdapterService;
	}
}
