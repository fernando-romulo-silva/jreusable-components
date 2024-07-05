package org.reusablecomponents.base.messaging.logger;

import org.reusablecomponents.base.messaging.InterfaceEventPublisherSerice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public class LoggerPublisherSerice implements InterfaceEventPublisherSerice {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerPublisherSerice.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void publish(final String event) {
	LOGGER.debug("Publish event [{}]", event);
    }
}
