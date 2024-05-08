package org.reusablecomponent.core.infra.messaging;

import org.reusablecomponent.core.infra.messaging.event.Event;
import org.reusablecomponent.core.infra.messaging.flow.JavaReactPublisherSerice;

/**
 * This is the service in charge of publishing the event, it can be JMS, RabbitMQ, Kafka, etc. <br />
 * It is strongly recommended to implement it as an ASYNC operation and capture possible exceptions from the messaging mechanism because it can impact the execution thread. <br />
 * For example, please check the {@link JavaReactPublisherSerice} implementation.
 */
@FunctionalInterface
public interface InterfacePublisherSerice {
    
    /**
     * Publish the event on a messaging mechanism.
     * 
     * @param event The event object.
     */
    void publish(final Event event);
}
