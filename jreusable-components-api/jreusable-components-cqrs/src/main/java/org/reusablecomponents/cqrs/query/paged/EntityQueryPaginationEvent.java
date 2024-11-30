package org.reusablecomponents.cqrs.query.paged;

import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ALL_ENTITIES_PAGEABLE;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ENTITY_SORTED;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.reusablecomponents.messaging.event.DefaultEventStatus.SUCCESS;

import java.util.Objects;

import org.reusablecomponents.cqrs.base.EntiyBaseEvent;
import org.reusablecomponents.cqrs.base.EntiyBaseEventBuilder;

import com.google.common.base.Supplier;

/**
 * 
 */
public class EntityQueryPaginationEvent<OneResult, MultiplePagedResult, Pageable, Sort>
        extends EntiyBaseEvent {

    /**
     * Default constructor
     * 
     * @param builder Object attribute constructor.
     */
    protected EntityQueryPaginationEvent(final EntiyBaseEventBuilder builder) {
        super(builder);
    }

    /**
     * Create a supplier function (deferred execution) that converts a
     * {@code Pageable} object to String to show in logs, the default is the
     * <code>java.util.Objects.toString</code>
     * 
     * @param pageable The object to transform
     * 
     * @return A Supplier object
     */
    protected Supplier<String> convertPageableToPublishDataIn(final Pageable pageable) {
        return () -> Objects.toString(pageable);
    }

    /**
     * Create a supplier function (deferred execution) that converts a
     * {@code MultiplePagedResult} object to String to show in logs, the default is
     * the <code>java.util.Objects.toString</code>
     * 
     * @param multiplePagedResult The object to transform
     * 
     * @return A Supplier object
     */
    protected Supplier<String> convertMultiplePagedResultToPublishDataOut(
            final MultiplePagedResult multiplePagedResult) {
        return () -> Objects.toString(multiplePagedResult);
    }

    /**
     * 
     * @param finalPageable
     * @param finalMultiplePagedResult
     */
    protected void findAllPublishEvent(
            final Pageable finalPageable,
            final MultiplePagedResult finalMultiplePagedResult) {

        final var dataInSupplier = convertPageableToPublishDataIn(finalPageable);
        final var dataIn = dataInSupplier.get();

        final var dataOutSupplier = convertMultiplePagedResultToPublishDataOut(finalMultiplePagedResult);
        final var dataOut = dataOutSupplier.get();

        publishEvent(dataIn, dataOut, EMPTY, SUCCESS, FIND_ALL_ENTITIES_PAGEABLE);
    }

    protected Supplier<String> convertSortToPublishDataIn(final Sort sort) {
        return () -> Objects.toString(sort);
    }

    protected Supplier<String> convertOneResultResultToPublishDataOut(final OneResult oneResult) {
        return () -> Objects.toString(oneResult);
    }

    protected void findFirstPublishEvent(final Sort finalSort, final OneResult finalOneResult) {

        final var dataInSupplier = convertSortToPublishDataIn(finalSort);
        final var dataIn = dataInSupplier.get();

        final var dataOutSupplier = convertOneResultResultToPublishDataOut(finalOneResult);
        final var dataOut = dataOutSupplier.get();

        publishEvent(dataIn, dataOut, EMPTY, SUCCESS, FIND_ENTITY_SORTED);
    }
}
