package org.reusablecomponent.messaging.flow;

import java.util.concurrent.Flow.Subscription;

import org.reusablecomponent.messaging.InterfaceReaderService;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public class JavaReactReaderService implements InterfaceReaderService {

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
    public void read(final String event) {
    }
}