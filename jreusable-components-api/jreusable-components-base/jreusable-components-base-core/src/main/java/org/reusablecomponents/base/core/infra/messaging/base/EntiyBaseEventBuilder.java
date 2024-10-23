package org.reusablecomponents.base.core.infra.messaging.base;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.function.Consumer;

import org.reusablecomponents.base.core.application.base.EntiyBaseFacadeBuilder;
import org.reusablecomponents.base.messaging.InterfaceEventPublisherSerice;

public class EntiyBaseEventBuilder extends EntiyBaseFacadeBuilder {

    /**
     * 
     * Message event service, in case of null, the
     * <code>LoggerPublisherSerice</code> will be used.
     */
    public InterfaceEventPublisherSerice<?> publisherService;

    /**
     * Default constructor
     * 
     * @param function Consumer function
     */
    public EntiyBaseEventBuilder(final Consumer<? extends EntiyBaseEventBuilder> function) {

        super(function);

        @SuppressWarnings("unchecked")
        final var finalFunction = (Consumer<EntiyBaseEventBuilder>) function;

        finalFunction.accept(this);

        checkNotNull(publisherService, "Please pass a non-null 'publisherService'");
        // new LoggerPublisherSerice()
    }
}