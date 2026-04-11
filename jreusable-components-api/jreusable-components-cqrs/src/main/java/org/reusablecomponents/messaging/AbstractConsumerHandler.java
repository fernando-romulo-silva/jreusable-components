package org.reusablecomponents.messaging;

import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract implementation of the {@link InterfaceConsumerService} that provides
 * a template method for consuming messages and handling exceptions.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
public abstract class AbstractConsumerHandler implements InterfaceConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractConsumerHandler.class);

    public final List<Class<? extends Exception>> requeableExceptions = new ArrayList<>();

    public final List<Class<? extends Exception>> droppableExceptions = new ArrayList<>();

    public final List<Class<? extends Exception>> deadLetterExceptions = new ArrayList<>();

    public final List<Class<? extends Exception>> detryableExceptions = new ArrayList<>();

    private final InterfaceConsumerService consumerService;

    protected AbstractConsumerHandler(final InterfaceConsumerService consumerService) {
        super();
        this.consumerService = consumerService;
    }

    public void consume(final String message) {

        LOGGER.atDebug().log("Sending messaging: {}", message);

        try {
            consumerService.consume(message);
        } catch (final Exception ex) {
            LOGGER.atDebug().log(format("Handle the exception on message: {}", message), getRootCause(ex));
            handleException(ex);
        }

        LOGGER.atDebug().log("Message sent: {}", message);
    }

    private void handleException(final Exception ex) {

        final var clazz = ex.getClass();

        if (requeableExceptions.contains(clazz)) {
            handleRequeableException(ex);
        }
    }

    protected abstract void handleRetryableException(final Exception ex);

    protected abstract void handleRequeableException(final Exception ex);

    protected abstract void handleDroppableException(final Exception ex);

    protected abstract void handleDeadLetterException(final Exception ex);

}
