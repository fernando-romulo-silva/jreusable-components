package org.reusablecomponents.base.core.infra.messaging.query.nonpaged;

import static org.reusablecomponents.base.messaging.operation.QueryOperation.COUNT_ALL;
import static org.reusablecomponents.base.messaging.operation.QueryOperation.EXISTS_ALL;
import static org.reusablecomponents.base.messaging.operation.QueryOperation.EXISTS_BY_ID;
import static org.reusablecomponents.base.messaging.operation.QueryOperation.FIND_ALL_ENTITIES;
import static org.reusablecomponents.base.messaging.operation.QueryOperation.FIND_ENTITY_BY_ID;

import java.util.Objects;
import java.util.function.Supplier;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.reusablecomponents.base.core.infra.messaging.base.EntiyBaseEvent;
import org.reusablecomponents.base.core.infra.messaging.base.EntiyBaseEventBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityQueryEvent<QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult>
        extends EntiyBaseEvent {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityQueryEvent.class);

    /**
     * Default constructor
     * 
     * @param builder Object attribute constructor.
     */
    protected EntityQueryEvent(final EntiyBaseEventBuilder builder) {
        super(builder);
    }

    /**
     * Create a supplier function (deferred execution) that converts a
     * {@code MultipleResult} object to String to show in logs, the default is the
     * <code>java.util.Objects.toString</code>
     * 
     * @param multipleResult The entity to transform
     * 
     * @return A Supplier object
     */
    protected Supplier<String> convertMultipleResultToPublishDataOut(final MultipleResult multipleResult) {
        return () -> Objects.toString(multipleResult);
    }

    /**
     * Create a supplier function (deferred execution) that converts a
     * {@code OneResult} object to String to show in logs, the default is the
     * <code>java.util.Objects.toString</code>
     * 
     * @param oneResult The entity to transform
     * 
     * @return A Supplier object
     */
    protected Supplier<String> convertOneResultToPublishDataOut(final OneResult oneResult) {
        return () -> Objects.toString(oneResult);
    }

    /**
     * Create a supplier function (deferred execution) that converts a
     * {@code CountResult} object to String to show in logs, the default is the
     * <code>java.util.Objects.toString</code>
     * 
     * @param countResult The entity to transform
     * 
     * @return A Supplier object
     */
    protected Supplier<String> convertCountResultToPublishDataOut(final CountResult countResult) {
        return () -> Objects.toString(countResult);
    }

    /**
     * Create a supplier function (deferred execution) that converts a
     * {@code QueryIdIn} object to String to show in logs, the default is the
     * <code>java.util.Objects.toString</code>
     * 
     * @param queryIdIn The entity to transform
     * 
     * @return A Supplier object
     */
    protected Supplier<String> convertQueryIdInToPublishDataIn(final QueryIdIn queryIdIn) {
        return () -> Objects.toString(queryIdIn);
    }

    /**
     * Create a supplier function (deferred execution) that converts a
     * {@code Object...} object to String to show in logs, the default is the
     * <code>java.util.Objects.toString</code>
     * 
     * @param directives The entity to transform
     * 
     * @return A Supplier object
     */
    protected Supplier<String> convertDirectivesToPublishDataIn(final Object... directives) {
        return () -> Objects.toString(directives);
    }

    /**
     * Create a supplier function (deferred execution) that converts a
     * {@code ExistsResult} object to String to show in logs, the default is the
     * <code>java.util.Objects.toString</code>
     * 
     * @param resultFinal The entity to transform
     * 
     * @return A Supplier object
     */
    protected Supplier<String> convertExistsResultToPublishData(final ExistsResult resultFinal) {
        return () -> Objects.toString(resultFinal);
    }

    /**
     * Publish find All event to message service
     * 
     * @param finalMultipleResult The query result
     * 
     * @param directives          Objects used to configure the query's result
     */
    protected void findAllPublishEvent(final MultipleResult finalMultipleResult, final Object... directives) {

        if (!isPublishEvents(directives)) {
            LOGGER.debug("Publishing find all event was avoided, finalMultipleResult '{}' and directives '{}'",
                    finalMultipleResult, directives);
            return;
        }

        LOGGER.debug("Publishing find all event, finalMultipleResult '{}' and directives '{}'",
                finalMultipleResult, directives);

        try {
            final var dataInSupplier = convertDirectivesToPublishDataIn(directives);
            final var dataIn = dataInSupplier.get();

            final var dataOutSupplier = convertMultipleResultToPublishDataOut(finalMultipleResult);
            final var dataOut = dataOutSupplier.get();

            publishEvent(dataIn, dataOut, FIND_ALL_ENTITIES);

            LOGGER.debug("The find all was published, dataIn '{}' and dataOut '{}'", dataIn, dataOut);
        } catch (final Exception ex) {
            LOGGER.error("The find all event was not published", ExceptionUtils.getRootCause(ex));
        }
    }

    /**
     * Publish find by id event to message service
     * 
     * @param finalQueryIdIn The object id
     * 
     * @param finalOneResult The query result
     * 
     * @param directives     Objects used to configure the query's result
     */
    protected void findByPublishEvent(
            final QueryIdIn finalQueryIdIn,
            final OneResult finalOneResult,
            final Object... directives) {

        if (!isPublishEvents(directives)) {
            LOGGER.debug("Publish event for find by id finalQueryIdIn '{}', finalOneResult '{}', and directives '{}'",
                    finalQueryIdIn,
                    finalOneResult,
                    directives);
            return;
        }

        LOGGER.debug("Publish event for find by id finalQueryIdIn '{}', finalOneResult '{}', and directives '{}'",
                finalQueryIdIn,
                finalOneResult,
                directives);

        final var dataInSupplier = convertQueryIdInToPublishDataIn(finalQueryIdIn);
        final var dataOutSupplier = convertOneResultToPublishDataOut(finalOneResult);

        LOGGER.debug("Publish event for find by id dataIn '{}' and dataOut '{}', ", dataInSupplier, dataOutSupplier);

        publishEvent(dataInSupplier.get(), dataOutSupplier.get(), FIND_ENTITY_BY_ID);
    }

    /**
     * Publish exists by id event to message service
     * 
     * @param finalQueryIdIn    The object id
     * 
     * @param finalExistsResult The query result
     * 
     * @param directives        Objects used to configure the query's result
     */
    protected void existsByPublishEvent(
            final QueryIdIn finalQueryIdIn,
            final ExistsResult finalExistsResult,
            final Object... directives) {

        if (!isPublishEvents(directives)) {
            LOGGER.debug(
                    "Publish event for exists by id finalQueryIdIn '{}', finalExistsResult '{}', and directives '{}'",
                    finalQueryIdIn,
                    finalExistsResult,
                    directives);
            return;
        }

        LOGGER.debug("Publish event for exists by id finalQueryIdIn '{}', finalExistsResult '{}', and directives '{}'",
                finalQueryIdIn,
                finalExistsResult,
                directives);

        final var dataInSupplier = convertQueryIdInToPublishDataIn(finalQueryIdIn);
        final var dataOutSupplier = convertExistsResultToPublishData(finalExistsResult);

        LOGGER.debug("Publish event for exists by id, dataIn '{}' and dataOut '{}', ", dataInSupplier, dataOutSupplier);

        publishEvent(dataInSupplier.get(), dataOutSupplier.get(), EXISTS_BY_ID);
    }

    /**
     * Publish exists by id event to message service
     * 
     * @param finalCountResult  The object id
     * 
     * @param finalExistsResult The query result
     * 
     * @param directives        Objects used to configure the query's result
     */
    protected void countAllPublishEvent(
            final CountResult finalCountResult,
            final Object... directives) {

        if (!isPublishEvents(directives)) {
            LOGGER.debug(
                    "Publish event for count all, finalCountResult '{}' and directives '{}'",
                    finalCountResult,
                    directives);
            return;
        }

        LOGGER.debug("Publish event for count all, finalCountResult '{}' and directives '{}'",
                finalCountResult,
                directives);

        final var dataOutSupplier = convertCountResultToPublishDataOut(finalCountResult);
        publishEvent(StringUtils.EMPTY, dataOutSupplier.get(), COUNT_ALL);

        LOGGER.debug("Publish event for count all, dataIn '{}' and dataOut '{}', ",
                StringUtils.EMPTY,
                dataOutSupplier);
    }

    /**
     * Publish exists all event to message service
     * 
     * @param finalExistsResult The object id
     * 
     * @param finalExistsResult The query result
     * 
     * @param directives        Objects used to configure the query's result
     */
    protected void existsAllPublishEvent(
            final ExistsResult finalExistsResult,
            final Object... directives) {

        if (!isPublishEvents(directives)) {
            LOGGER.debug(
                    "Publish event for exists all, finalExistsResult '{}' and directives '{}'",
                    finalExistsResult,
                    directives);
            return;
        }

        LOGGER.debug("Publish event for exists all, finalExistsResult '{}' and directives '{}'",
                finalExistsResult,
                directives);

        final var dataOutSupplier = convertExistsResultToPublishData(finalExistsResult);
        publishEvent(StringUtils.EMPTY, dataOutSupplier.get(), EXISTS_ALL);

        LOGGER.debug("Publish event for exists all, dataIn '{}' and dataOut '{}', ",
                StringUtils.EMPTY,
                dataOutSupplier);
    }
}
