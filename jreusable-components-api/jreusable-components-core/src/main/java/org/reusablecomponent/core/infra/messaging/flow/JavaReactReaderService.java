package org.reusablecomponent.core.infra.messaging.flow;

import java.util.concurrent.Flow.Subscription;

import org.reusablecomponent.core.infra.messaging.InterfaceReaderService;
import org.reusablecomponent.core.infra.messaging.event.Event;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public class JavaReactReaderService implements InterfaceReaderService {

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
    public void read(final Event event) {
	
    }
}
