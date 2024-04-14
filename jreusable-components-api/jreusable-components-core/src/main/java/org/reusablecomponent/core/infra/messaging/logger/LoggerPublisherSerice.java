package org.reusablecomponent.core.infra.messaging.logger;

import org.reusablecomponent.core.infra.messaging.InterfacePublisherSerice;
import org.reusablecomponent.core.infra.messaging.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerPublisherSerice implements InterfacePublisherSerice {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerPublisherSerice.class);

    @Override
    public void publish(final Event event) {
	LOGGER.info("Publish event: {}", event);
    }
}
