package org.reusablecomponent.core.infra.messaging;

import org.reusablecomponent.core.infra.messaging.event.Event;

/**
 * 
 */
public interface InterfaceReaderService {

    /**
     * @param event
     */
    void read(final Event event);
}
