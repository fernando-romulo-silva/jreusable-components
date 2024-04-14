package org.reusablecomponent.core.infra.messaging.flow;

import org.reusablecomponent.core.infra.messaging.InterfacePublisherSerice;
import org.reusablecomponent.core.infra.messaging.event.Event;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public final class JavaReactPublisherSerice implements InterfacePublisherSerice {

    private final EventPublisher eventPublisher = new EventPublisher();
    
    public JavaReactPublisherSerice(@NotNull final EventSubscriber eventSubscriber) {
	eventPublisher.subscribe(eventSubscriber);
    }
    
    /**
     *
     */
    @Override
    public void publish(final Event event) {
	
	try (eventPublisher) {
	    
	    eventPublisher.submit(event);
	    
	} catch(final Exception ex) {
	    throw new IllegalStateException(ex);
	}
    }
}
