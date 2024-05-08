package org.reusablecomponent.core.infra.messaging.flow;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.reusablecomponent.core.infra.messaging.InterfacePublisherSerice;
import org.reusablecomponent.core.infra.messaging.event.Event;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public final class JavaReactPublisherSerice implements InterfacePublisherSerice {

    private final EventPublisher eventPublisher = new EventPublisher();
    
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(10); 
    
    public JavaReactPublisherSerice(@NotNull final EventSubscriber eventSubscriber) {
	eventPublisher.subscribe(eventSubscriber);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void publish(final Event event) {
	
	try (eventPublisher) {
	
	    EXECUTOR.submit(() -> eventPublisher.submit(event));
	    
	} catch(final Exception ex) {
	    throw new IllegalStateException(ex);
	}
    }
}
