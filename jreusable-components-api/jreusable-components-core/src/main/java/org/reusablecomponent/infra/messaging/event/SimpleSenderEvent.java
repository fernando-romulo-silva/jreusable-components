package org.reusablecomponent.infra.messaging.event;

import org.reusablecomponent.infra.messaging.InterfaceEventPublisher;

public class SimpleSenderEvent implements InterfaceEventPublisher<Void> {

    @Override
    public Void publish(final Event event) {
	
	return null;
    }
}
