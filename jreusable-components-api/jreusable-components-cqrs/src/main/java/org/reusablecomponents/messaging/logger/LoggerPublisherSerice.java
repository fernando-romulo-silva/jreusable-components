package org.reusablecomponents.messaging.logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.reusablecomponents.messaging.InterfaceEventPublisherSerice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public class LoggerPublisherSerice implements InterfaceEventPublisherSerice<Void> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerPublisherSerice.class);

    private static final ExecutorService POOL_EXECUTOR_SERVICE = Executors.newFixedThreadPool(10);

    /**
     * {@inheritDoc}
     */
    @Override
    public Future<Void> publish(final String event) {

        return POOL_EXECUTOR_SERVICE.<Void>submit(() -> {
            LOGGER.debug("Publish event [{}]", event);
            return null;
        });
    }
}
