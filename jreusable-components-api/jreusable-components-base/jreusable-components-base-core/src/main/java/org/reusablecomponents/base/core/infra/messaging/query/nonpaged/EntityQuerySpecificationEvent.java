package org.reusablecomponents.base.core.infra.messaging.query.nonpaged;

import static org.reusablecomponents.base.messaging.operation.QueryOperation.COUNT_BY_SPECIFICATION;
import static org.reusablecomponents.base.messaging.operation.QueryOperation.EXISTS_BY_SPECIFICATION;
import static org.reusablecomponents.base.messaging.operation.QueryOperation.FIND_ENTITIES_BY_SPECIFICATION;
import static org.reusablecomponents.base.messaging.operation.QueryOperation.FIND_ENTITY_BY_SPECIFICATION;

import java.util.Objects;

import org.reusablecomponents.base.core.infra.messaging.base.EntiyBaseEvent;
import org.reusablecomponents.base.core.infra.messaging.base.EntiyBaseEventBuilder;

import com.google.common.base.Supplier;

/**
 * 
 */
public class EntityQuerySpecificationEvent<OneResult, MultipleResult, CountResult, ExistsResult, Specification>
        extends EntiyBaseEvent {

    /**
     * Default constructor
     * 
     * @param builder Object attribute constructor.
     */
    protected EntityQuerySpecificationEvent(final EntiyBaseEventBuilder builder) {
        super(builder);
    }

    /**
     * Create a supplier function (deferred execution) that converts a
     * {@code Specification} object to String to show in logs, the default is the
     * <code>java.util.Objects.toString</code>
     * 
     * @param specification The object to transform
     * 
     * @return A Supplier object
     */
    protected Supplier<String> convertSpecificationToPublishDataIn(final Specification specification) {
        return () -> Objects.toString(specification);
    }

    /**
     * Create a supplier function (deferred execution) that converts a
     * {@code MultipleResult} object to String to show in logs, the default is the
     * <code>java.util.Objects.toString</code>
     * 
     * @param multipleResult The entity group to transform
     * 
     * @return A Supplier object
     */
    protected Supplier<String> convertMultipleResultToPublishDataOut(final MultipleResult multipleResult) {
        return () -> Objects.toString(multipleResult);
    }

    protected void findByPublishEvennt(
            final Specification finalSpecification,
            final MultipleResult finalMultipleResult) {

        final var dataInSupplier = convertSpecificationToPublishDataIn(finalSpecification);
        final var dataIn = dataInSupplier.get();

        final var dataOutSupplier = convertMultipleResultToPublishDataOut(finalMultipleResult);
        final var dataOut = dataOutSupplier.get();

        publishEvent(dataIn, dataOut, FIND_ENTITIES_BY_SPECIFICATION);
    }

    /**
     * Create a supplier function (deferred execution) that converts a
     * {@code OneResult} object to String to show in logs, the default is the
     * <code>java.util.Objects.toString</code>
     * 
     * @param oneResult The entity group to transform
     * 
     * @return A Supplier object
     */
    protected Supplier<String> convertOneResultToPublishDataOut(final OneResult oneResult) {
        return () -> Objects.toString(oneResult);
    }

    protected void findOneByPublishEvent(final Specification finalSpecification, final OneResult finalOneResult) {

        final var dataInSupplier = convertSpecificationToPublishDataIn(finalSpecification);
        final var dataIn = dataInSupplier.get();

        final var dataOutSupplier = convertOneResultToPublishDataOut(finalOneResult);
        final var dataOut = dataOutSupplier.get();

        publishEvent(dataIn, dataOut, FIND_ENTITY_BY_SPECIFICATION);
    }

    /**
     * Create a supplier function (deferred execution) that converts a
     * {@code ExistsResult} object to String to show in logs, the default is the
     * <code>java.util.Objects.toString</code>
     * 
     * @param resultFinal The entity group to transform
     * 
     * @return A Supplier object
     */
    protected Supplier<String> convertExistsResultToPublishDataOut(final ExistsResult resultFinal) {
        return () -> Objects.toString(resultFinal);
    }

    /**
     * Create a supplier function (deferred execution) that converts a
     * {@code CountResult} object to String to show in logs, the default is the
     * <code>java.util.Objects.toString</code>
     * 
     * @param countResult The entity to transform
     * @return A Supplier object
     */
    protected Supplier<String> convertCountResultToPublishDataOut(final CountResult countResult) {
        return () -> Objects.toString(countResult);
    }

    /**
     * 
     * @param finalSpecification
     * @param finalResult
     */
    protected void existsByPublishEvent(final Specification finalSpecification, final ExistsResult finalResult) {

        final var dataInSupplier = convertSpecificationToPublishDataIn(finalSpecification);
        final var dataIn = dataInSupplier.get();

        final var dataOutSupplier = convertExistsResultToPublishDataOut(finalResult);
        final var dataOut = dataOutSupplier.get();

        publishEvent(dataIn, dataOut, EXISTS_BY_SPECIFICATION);
    }

    /**
     * 
     * @param finalSpecification
     * @param finalCountResult
     */
    protected void countPublishEvent(final Specification finalSpecification, final CountResult finalCountResult) {

        final var dataInSupplier = convertSpecificationToPublishDataIn(finalSpecification);
        final var dataIn = dataInSupplier.get();

        final var dataOutSupplier = convertCountResultToPublishDataOut(finalCountResult);
        final var dataOut = dataOutSupplier.get();

        publishEvent(dataIn, dataOut, COUNT_BY_SPECIFICATION);
    }
}
