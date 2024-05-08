package org.application_example.infra;

import org.reusablecomponent.core.infra.messaging.InterfacePublisherSerice;
import org.reusablecomponent.core.infra.messaging.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DummyPublisherSerice implements InterfacePublisherSerice {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DummyPublisherSerice.class);

    @Override
    public void publish(final Event event) {
	LOGGER.debug("Publish event: {}", event.getWhat().data());
    }
}
