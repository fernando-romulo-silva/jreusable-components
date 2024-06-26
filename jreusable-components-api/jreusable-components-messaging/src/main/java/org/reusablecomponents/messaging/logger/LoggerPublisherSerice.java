package org.reusablecomponents.messaging.logger;

import org.reusablecomponents.messaging.InterfacePublisherSerice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public class LoggerPublisherSerice implements InterfacePublisherSerice {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerPublisherSerice.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void publish(final String event) {
	LOGGER.debug("Publish event [{}]", event);
    }
}
