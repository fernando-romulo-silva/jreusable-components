package org.reusablecomponent.messaging.flow;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.reusablecomponent.messaging.InterfacePublisherSerice;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public final class JavaReactPublisherSerice implements InterfacePublisherSerice {

    private final EventPublisher eventPublisher = new EventPublisher();
    
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(10); 
    
    public JavaReactPublisherSerice(@NotNull JavaReactReaderService javaReactReaderService) {
	eventPublisher.subscribe(javaReactReaderService.eventSubscriber);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void publish(final String event) {
	
	try (eventPublisher) {
	
	    EXECUTOR.submit(() -> eventPublisher.submit(event));
	    
	} catch(final Exception ex) {
	    throw new IllegalStateException(ex);
	}
    }
}
