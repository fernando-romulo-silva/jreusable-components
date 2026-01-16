package org.reusablecomponents.messaging;

import java.util.concurrent.Future;

import org.reusablecomponents.messaging.event.Event;
import org.reusablecomponents.messaging.flow.JavaReactPublisherSerice;

/**
 * This is the service in charge of publishing the event from message mechanism,
 * it can be JMS, RabbitMQ, Kafka, etc. <br />
 * 
 * The <code>Event</code> object is the message's payload, therefore you have to
 * convert it to a message format before sending it.
 * 
 * It is strongly recommended to implement it as an ASYNC operation and capture
 * possible exceptions from the messaging mechanism because it can impact the
 * execution thread. <br />
 * For example, please check the {@link JavaReactPublisherSerice}
 * implementation.
 */

@FunctionalInterface
public interface InterfaceEventPublisherSerice<T> {

    /**
     * Publish a event.
     * 
     * @param event The event to publish.
     * @return A <code>Future</code> object
     */
    Future<T> publish(final Event event);
}
