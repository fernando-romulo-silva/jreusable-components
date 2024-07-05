package org.reusablecomponents.base.messaging.flow;

import java.util.concurrent.Flow.Subscription;

import org.reusablecomponents.base.messaging.InterfaceConsumerService;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public class JavaReactReaderService implements InterfaceConsumerService {

    @SuppressWarnings("exports")
    public final EventSubscriber eventSubscriber = new EventSubscriber();

    public JavaReactReaderService(@NotNull final Subscription subscription) {
	super();
	eventSubscriber.onSubscribe(subscription);
	eventSubscriber.setConsumer(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void consume(final String event) {
    }
}
