package org.reusablecomponents.messaging;

/**
 * This is the service in charge of publishing the event from message mechanism, it can be JMS, RabbitMQ, Kafka, etc. <br />
 * It is strongly recommended to implement it as an ASYNC operation and capture possible exceptions from the messaging mechanism because it can impact the execution thread. <br />
 * For example, please check the {@link JavaReactPublisherSerice} implementation.
 */
@FunctionalInterface
public interface InterfacePublisherSerice {
    
    /**
     * Publish a event.
     * 
     * @param eventString The event's string.
     */
    void publish(final String eventString);
}
