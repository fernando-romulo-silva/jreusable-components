package org.reusablecomponents.messaging;

/**
 * This is the service in charge of reading the event from message mechanism, it
 * can be JMS, RabbitMQ, Kafka, etc. <br />
 * For example, please check the {@link JavaReactReaderService} implementation.
 */
@FunctionalInterface
public interface InterfaceConsumerService {

    /**
     * Read a event.
     * 
     * @param event The event object.
     */
    void consume(final String event);
}