package org.reusablecomponents.base.core.infra.messaging.base;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static java.util.Optional.ofNullable;
import static org.reusablecomponents.base.core.infra.constants.ExceptionMessages.NULL_POINTER_EXCEPTION_MSG;
import static org.reusablecomponents.base.core.infra.util.Functions.createNullPointerException;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
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

import jakarta.validation.constraints.NotNull;

public class EntiyBaseEvent {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntiyBaseEvent.class);

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
     * @param operation The operation performed
     * 
     * @return A <code>Event</code> object
     */
    public Event createEvent(final String dataIn, final String dataOut, final InterfaceOperation operation) {

        LOGGER.debug("Creating event with '{}' operation", operation);

        ofNullable(operation)
                .orElseThrow(createNullPointerException(i18nService, "operation"));

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
     * @param event The object the caller wants publish
     */
    protected void publishEvent(final Event event) {

        final var finalEvent = Optional.of(event)
                .orElseThrow(
                        () -> new NullPointerException(i18nService.translate(NULL_POINTER_EXCEPTION_MSG, "event")));

        LOGGER.debug("Publishing event '{}'", finalEvent.getId());

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
     * Send the event to publish on the messaging mechanism.
     * It's a async operation.
     * 
     * @param dataIn    The function that generates the input event data
     * @param dataOut   The function that generates the output event data
     * @param operation The operation performed
     */
    public void publishEvent(
            final String dataIn,
            final String dataOut,
            final InterfaceOperation operation) {

        LOGGER.debug("Publishing event with operation '{}'", operation);

        final var finalOperation = Optional.of(operation).orElseThrow(
                () -> new NullPointerException(i18nService.translate(NULL_POINTER_EXCEPTION_MSG, "operation")));

        final var event = createEvent(dataIn, dataOut, finalOperation);

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
     * @return A formatted string
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

    @NotNull
    public final InterfaceEventPublisherSerice<?> getPublisherService() {
        return publisherService;
    }
}
