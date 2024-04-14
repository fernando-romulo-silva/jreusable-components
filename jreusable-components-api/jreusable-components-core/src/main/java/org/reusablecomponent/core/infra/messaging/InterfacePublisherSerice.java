package org.reusablecomponent.core.infra.messaging;

import org.reusablecomponent.core.infra.messaging.event.Event;

/**
 * 
 */
@FunctionalInterface
public interface InterfacePublisherSerice {
    
    /**
     * @param msg
     */
    void publish(final Event event);
    
    /**
     * @param event
     */
    default void publish(final String event) {
	
    }
}
