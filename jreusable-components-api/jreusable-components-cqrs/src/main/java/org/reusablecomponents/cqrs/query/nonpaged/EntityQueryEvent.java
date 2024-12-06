package org.reusablecomponents.cqrs.query.nonpaged;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.COUNT_ALL;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.EXISTS_ALL;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.EXISTS_BY_ID;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ALL_ENTITIES;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ENTITY_BY_ID;
import static org.reusablecomponents.messaging.event.DefaultEventStatus.SUCCESS;

import java.util.Objects;
import java.util.function.Supplier;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.reusablecomponents.cqrs.base.EntiyBaseEvent;
import org.reusablecomponents.cqrs.base.EntiyBaseEventBuilder;
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
    protected String convertMultipleResultToPublishDataOut(final MultipleResult multipleResult) {
        return Objects.toString(multipleResult);
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
    protected String convertOneResultToPublishDataOut(final OneResult oneResult) {
        return Objects.toString(oneResult);
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
    protected String convertCountResultToPublishDataOut(final CountResult countResult) {
        return Objects.toString(countResult);
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
    protected String convertQueryIdInToPublishDataIn(final QueryIdIn queryIdIn) {
        return Objects.toString(queryIdIn);
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
    protected String convertDirectivesToPublishDataIn(final MultipleResult multipleResult, final Object... directives) {
        return Objects.toString(multipleResult);
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
    protected String convertExistsResultToPublishData(final ExistsResult resultFinal) {
        return Objects.toString(resultFinal);
    }

    /**
     * Publish find All event to message service
     * 
     * @param multipleResult The query result
     * 
     * @param directives     Objects used to configure the query's result
     */
    protected void findAllPublishEvent(final MultipleResult multipleResult, final Object... directives) {

        if (!isPublishEvents(directives)) {
            LOGGER.debug("Publishing find all event was avoided, finalMultipleResult '{}' and directives '{}'",
                    multipleResult, directives);
            return;
        }

        LOGGER.debug("Publishing find all event, finalMultipleResult '{}' and directives '{}'",
                multipleResult, directives);

        try {
            final var dataIn = convertDirectivesToPublishDataIn(multipleResult, directives);
            final var dataOut = convertMultipleResultToPublishDataOut(multipleResult);

            publishEvent(dataIn, dataOut, EMPTY, SUCCESS, FIND_ALL_ENTITIES);

            LOGGER.debug("The find all was published, dataIn '{}' and dataOut '{}'", dataIn, dataOut);
        } catch (final Exception ex) {
            LOGGER.error("The find all event was not published", ExceptionUtils.getRootCause(ex));
        }
    }

    /**
     * Publish find by id event to message service
     * 
     * @param queryIdIn  The object id
     * 
     * @param oneResult  The query result
     * 
     * @param directives Objects used to configure the query's result
     */
    protected void findByPublishEvent(
            final QueryIdIn queryIdIn,
            final OneResult oneResult,
            final Object... directives) {

        publishCommandEvent(
                queryIdIn, oneResult, FIND_ENTITY_BY_ID,
                this::convertQueryIdInToPublishDataIn,
                this::convertOneResultToPublishDataOut, directives);
    }

    /**
     * Publish exists by id event to message service
     * 
     * @param queryIdIn    The object id
     * 
     * @param existsResult The query result
     * 
     * @param directives   Objects used to configure the query's result
     */
    protected void existsByPublishEvent(
            final QueryIdIn queryIdIn,
            final ExistsResult existsResult,
            final Object... directives) {

        publishCommandEvent(
                queryIdIn, existsResult, EXISTS_BY_ID,
                this::convertQueryIdInToPublishDataIn,
                this::convertExistsResultToPublishData, directives);
    }

    /**
     * Publish exists by id event to message service
     * 
     * @param countResult       The object id
     * 
     * @param finalExistsResult The query result
     * 
     * @param directives        Objects used to configure the query's result
     */
    protected void countAllPublishEvent(
            final CountResult countResult,
            final Object... directives) {

        if (!isPublishEvents(directives)) {
            LOGGER.debug(
                    "Publish event for count all, finalCountResult '{}' and directives '{}'",
                    countResult,
                    directives);
            return;
        }

        LOGGER.debug("Publish event for count all, finalCountResult '{}' and directives '{}'",
                countResult,
                directives);

        final var dataOut = convertCountResultToPublishDataOut(countResult);

        publishEvent(EMPTY, dataOut, EMPTY, SUCCESS, COUNT_ALL);

        LOGGER.debug("Publish event for count all, dataIn '{}' and dataOut '{}', ",
                StringUtils.EMPTY,
                dataOut);

        // publishCommandEvent(
        // countResult,
        // COUNT_ALL,
        // null,
        // null,
        // directives);
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

        final var dataOut = convertExistsResultToPublishData(finalExistsResult);

        publishEvent(EMPTY, dataOut, EMPTY, SUCCESS, EXISTS_ALL);

        LOGGER.debug("Publish event for exists all, dataIn '{}' and dataOut '{}', ",
                StringUtils.EMPTY, dataOut);
    }
}
