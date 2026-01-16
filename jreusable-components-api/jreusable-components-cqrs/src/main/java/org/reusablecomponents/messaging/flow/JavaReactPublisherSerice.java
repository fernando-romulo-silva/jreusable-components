package org.reusablecomponents.messaging.flow;

import static org.reusablecomponents.messaging.MessagingConst.JSON_LAYOUT;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.reusablecomponents.messaging.InterfaceEventPublisherSerice;
import org.reusablecomponents.messaging.event.Event;
import org.reusablecomponents.util.EventUtils;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public final class JavaReactPublisherSerice implements InterfaceEventPublisherSerice<Integer> {

    private final EventPublisher eventPublisher = new EventPublisher();

    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(10);

    public JavaReactPublisherSerice(@NotNull JavaReactReaderService javaReactReaderService) {
        eventPublisher.subscribe(javaReactReaderService.eventSubscriber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Future<Integer> publish(final Event event) {
        try (eventPublisher) {
            final var eventToPublish = EventUtils.prepareEventToPublisher(event, JSON_LAYOUT);
            return EXECUTOR.submit(() -> eventPublisher.submit(eventToPublish));
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
}
