package org.reusablecomponent.core.infra.messaging.flow;

import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

import org.reusablecomponent.core.infra.messaging.InterfaceReaderService;
import org.reusablecomponent.core.infra.messaging.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

class EventSubscriber implements Subscriber<Event> {
   
    private static final Logger LOGGER = LoggerFactory.getLogger(EventSubscriber.class);
    
    private Subscription subscription;
    
    private InterfaceReaderService eventConsumer;
    
    void setConsumer(@NotNull final InterfaceReaderService eventConsumer) {
	this.eventConsumer = eventConsumer;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void onSubscribe(final Subscription subscription) {
	this.subscription = subscription;
	subscription.request(1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onNext(final Event event) {
	LOGGER.info("event #{}" , event);
	eventConsumer.read(event);
        subscription.request(1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onError(final Throwable throwable) {
	LOGGER.error("There is an error in event sending:{}" , getRootCauseMessage(throwable));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onComplete() {
	LOGGER.info("event has ended");
    }
}
