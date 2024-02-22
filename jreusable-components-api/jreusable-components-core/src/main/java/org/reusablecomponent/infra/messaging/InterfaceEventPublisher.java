package org.reusablecomponent.infra.messaging;

import org.reusablecomponent.infra.messaging.event.Event;

/**
 * 
 */
public interface InterfaceEventPublisher<T> {
    
    /**
     * @param msg
     */
    T publish(final Event event);
    
    /**
     * @param <T>
     * @param object
     * @param converter
     */
//    default <T>  void send(final T object, final Function<T, String> converter) {
//	
//	try {
//	    
//	    final var msg = converter.apply(object);
	
//	    send(msg);
	    
//	} catch (final Exception ex) {
//	    
//	}
//    }
    
//    default <T>  void send(final T object, final InterfaceOperationEvent operation) {
//	
//	try {
//	    
//	    final var msg = converter.apply(object);
//	
//	    send(msg, operation);
//	    
//	} catch (final Exception ex) {
//	    
//	}
//    }    
    
}
