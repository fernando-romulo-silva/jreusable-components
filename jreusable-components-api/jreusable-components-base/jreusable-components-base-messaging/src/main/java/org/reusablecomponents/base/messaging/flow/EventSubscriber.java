package org.reusablecomponents.base.messaging.flow;

import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

import org.reusablecomponents.base.messaging.InterfaceConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

class EventSubscriber implements Subscriber<String> {
   
    private static final Logger LOGGER = LoggerFactory.getLogger(EventSubscriber.class);
    
    private Subscription subscription;
    
    private InterfaceConsumerService eventConsumer;
    
    void setConsumer(@NotNull final InterfaceConsumerService eventConsumer) {
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
    public void onNext(final String event) {
	LOGGER.info("event #{}" , event);
	eventConsumer.consume(event);
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
