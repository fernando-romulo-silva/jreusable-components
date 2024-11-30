package org.reusablecomponents.cqrs.query.paged;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ENTITIES_BY_SPECIFICATION_PAGEABLE;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ENTITY_BY_SPECIFICATION_SORTED;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.reusablecomponents.messaging.event.DefaultEventStatus.SUCCESS;

import java.util.Objects;

import org.reusablecomponents.cqrs.base.EntiyBaseEvent;
import org.reusablecomponents.cqrs.base.EntiyBaseEventBuilder;

import com.google.common.base.Supplier;

/**
 * 
 */
public class EntityQueryPaginationSpecificationEvent<OneResult, MultiplePagedResult, Pageable, Sort, Specification>
        extends EntiyBaseEvent {

    /**
     * Default constructor
     * 
     * @param builder Object attribute constructor.
     */
    protected EntityQueryPaginationSpecificationEvent(final EntiyBaseEventBuilder builder) {
        super(builder);
    }

    /**
     * 
     * @param pageable
     * 
     * @param specification
     * 
     * @return
     */
    protected Supplier<String> convertPageableToPublishData(
            final Pageable pageable,
            final Specification specification) {

        return () -> Objects.toString(pageable).concat(SPACE).concat(Objects.toString(specification));
    }

    /**
     * 
     * @param multiplePagedResult
     * 
     * @return
     */
    protected Supplier<String> convertMultiplePagedResultToPublishData(final MultiplePagedResult multiplePagedResult) {
        return () -> Objects.toString(multiplePagedResult);
    }

    /**
     * 
     * @param finalPageable
     * 
     * @param finalSpecification
     * 
     * @param finalMultiplePagedResult
     */
    protected void findByPublishEvent(
            final Pageable finalPageable,
            final Specification finalSpecification,
            final MultiplePagedResult finalMultiplePagedResult) {

        final var dataInSupplier = convertPageableToPublishData(finalPageable, finalSpecification);
        final var dataIn = dataInSupplier.get();

        final var dataOutSupplier = convertMultiplePagedResultToPublishData(finalMultiplePagedResult);
        final var dataOut = dataOutSupplier.get();

        publishEvent(dataIn, dataOut, EMPTY, SUCCESS, FIND_ENTITIES_BY_SPECIFICATION_PAGEABLE);
    }

    // -----------------------------------------------------------------------------

    /**
     * 
     * @param specification
     * 
     * @param sort
     * 
     * @return
     */
    protected Supplier<String> convertSortToPublishData(final Specification specification, final Sort sort) {
        return () -> Objects.toString(specification).concat(SPACE).concat(Objects.toString(sort));
    }

    /**
     * 
     * @param oneResult
     * 
     * @return
     */
    protected Supplier<String> convertOneResultResultToPublishData(final OneResult oneResult) {
        return () -> Objects.toString(oneResult);
    }

    protected void findOneByPublishEvent(
            final Specification finalSpecification,
            final Sort finalSort,
            final OneResult finalOneResult) {

        final var dataInSupplier = convertSortToPublishData(finalSpecification, finalSort);
        final var dataIn = dataInSupplier.get();

        final var dataOutSupplier = convertOneResultResultToPublishData(finalOneResult);
        final var dataOut = dataOutSupplier.get();

        publishEvent(dataIn, dataOut, EMPTY, SUCCESS, FIND_ENTITY_BY_SPECIFICATION_SORTED);
    }
}
