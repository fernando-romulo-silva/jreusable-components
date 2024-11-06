package org.reusablecomponents.cqrs.command;

import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.SAVE_ENTITY;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.UPDATE_ENTITIES;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.UPDATE_ENTITY;

import java.util.Objects;
import java.util.function.Supplier;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.cqrs.base.EntiyBaseEvent;
import org.reusablecomponents.cqrs.base.EntiyBaseEventBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public class EntityCommandEvent< // generics
        // default
        Entity extends AbstractEntity<Id>, Id, // basic
        // save
        SaveEntityIn, SaveEntityOut, //
        SaveEntitiesIn, SaveEntitiesOut, //
        // update
        UpdateEntityIn, UpdateEntityOut, //
        UpdateEntitiesIn, UpdateEntitiesOut, //
        // delete
        DeleteEntityIn, DeleteEntityOut, //
        DeleteEntitiesIn, DeleteEntitiesOut, //
        // delete by id
        DeleteIdIn, DeleteIdOut, //
        DeleteIdsIn, DeleteIdsOut> extends EntiyBaseEvent {

    protected EntityCommandEvent(final EntiyBaseEventBuilder builder) {
        super(builder);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityCommandEvent.class);

    /**
     * Create a supplier function (deferred execution) that converts a
     * {@code SaveEntityIn} object to String to show in logs, the default is the
     * <code>java.util.Objects.toString</code>
     * 
     * @param saveEntityIn The entity to transform
     * 
     * @return A Supplier object
     */
    @NotNull
    protected Supplier<String> convertSaveEntityInToPublishDataIn(final SaveEntityIn saveEntityIn) {
        return () -> Objects.toString(saveEntityIn);
    }

    /**
     * Create a supplier function (deferred execution) that converts a
     * {@code SaveEntityOut} object to String to show in logs, the default is the
     * <code>java.util.Objects.toString</code>
     * 
     * @param saveEntityOut The entity to transform
     * 
     * @return A Supplier object
     */
    @NotNull
    protected Supplier<String> convertSaveEntityOutToPublishDataOut(final SaveEntityOut saveEntityOut) {
        return () -> Objects.toString(saveEntityOut);
    }

    /**
     * Publish save event to message service
     * 
     * @param finalSaveEntityIn  The object you want to save on the persistence
     *                           mechanism
     * 
     * @param finalSaveEntityOut The save command result
     * 
     * @param directives         Objects used to configure the save operation
     */
    protected void publishSaveEvent(
            final SaveEntityIn finalSaveEntityIn,
            final SaveEntityOut finalSaveEntityOut,
            final Object... directives) {

        if (!isPublishEvents(directives)) {
            LOGGER.debug(
                    "Publishing save event was avoided, finalSaveEntityIn '{}', finalSaveEntityOut '{}', and directives '{}'",
                    finalSaveEntityIn, finalSaveEntityOut, directives);
            return;
        }

        LOGGER.debug("Publishing save event, finalSaveEntityIn '{}', finalSaveEntityOut '{}', and directives '{}'",
                finalSaveEntityIn, finalSaveEntityOut, directives);

        try {
            final var dataInSupplier = convertSaveEntityInToPublishDataIn(finalSaveEntityIn);
            final var dataIn = dataInSupplier.get();

            final var dataOutSupplier = convertSaveEntityOutToPublishDataOut(finalSaveEntityOut);
            final var dataOut = dataOutSupplier.get();

            publishEvent(dataIn, dataOut, SAVE_ENTITY);

            LOGGER.debug("The save event was published, dataIn '{}' and dataOut '{}'", dataIn, dataOut);

        } catch (final Exception ex) {
            LOGGER.error("The save event was not published", ExceptionUtils.getRootCause(ex));
        }
    }

    // --------------------------------------------------------

    /**
     * Create a supplier function (deferred execution) that converts a
     * {@code SaveEntitiesIn} object to String to show in logs, the default is the
     * <code>java.util.Objects.toString</code>
     * 
     * @param saveEntitiesIn The entity to transform
     * 
     * @return A Supplier object
     */
    protected Supplier<String> convertSaveEntitiesInToPublishDataIn(final SaveEntitiesIn saveEntitiesIn) {
        return () -> Objects.toString(saveEntitiesIn);
    }

    /**
     * Create a supplier function (deferred execution) that converts a
     * {@code SaveEntitiesOut} object to String to show in logs, the default is the
     * <code>java.util.Objects.toString</code>
     * 
     * @param SaveEntitiesOut The entity to transform
     * 
     * @return A Supplier object
     */
    protected Supplier<String> convertSaveEntitiesOutToPublishDataOut(final SaveEntitiesOut saveEntitiesOut) {
        return () -> Objects.toString(saveEntitiesOut);
    }

    /**
     * Publish save all event to message service
     * 
     * @param finalSaveEntitiesIn  The objects you want to save on the persistence
     *                             mechanism
     * 
     * @param finalSaveEntitiesOut The save command result
     * 
     * @param directives           Objects used to configure the save operation
     */
    protected void publishSaveAllEvent(
            final SaveEntitiesIn finalSaveEntitiesIn,
            final SaveEntitiesOut finalSaveEntitiesOut,
            final Object... directives) {

        if (!isPublishEvents(directives)) {
            LOGGER.debug(
                    "Publishing save all event was avoided, finalSaveEntitiesIn '{}', finalSaveEntitiesOut '{}', and directives '{}'",
                    finalSaveEntitiesIn, finalSaveEntitiesOut, directives);
            return;
        }

        LOGGER.debug(
                "Publishing save all event, finalSaveEntitiesIn '{}', finalSaveEntitiesOut '{}', and directives '{}'",
                finalSaveEntitiesIn, finalSaveEntitiesOut, directives);

        try {
            final var dataInSupplier = convertSaveEntitiesInToPublishDataIn(finalSaveEntitiesIn);
            final var dataIn = dataInSupplier.get();

            final var dataOutSupplier = convertSaveEntitiesOutToPublishDataOut(finalSaveEntitiesOut);
            final var dataOut = dataOutSupplier.get();

            publishEvent(dataIn, dataOut, SAVE_ENTITY);

            LOGGER.debug("The save all event was published, dataIn '{}' and dataOut '{}'", dataIn, dataOut);
        } catch (final Exception ex) {
            LOGGER.error("The save all event was not published", ExceptionUtils.getRootCause(ex));
        }
    }

    // --------------------------------------------------------

    /**
     * Create a supplier function (deferred execution) that converts a
     * {@code UpdateEntityIn} object to String to show in logs, the default is the
     * <code>java.util.Objects.toString</code>
     * 
     * @param updateEntityIn The entity to transform
     * 
     * @return A Supplier object
     */
    protected Supplier<String> convertUpdateEntityInToPublishDataIn(final UpdateEntityIn updateEntityIn) {
        return () -> Objects.toString(updateEntityIn);
    }

    /**
     * Create a supplier function (deferred execution) that converts a
     * {@code UpdateEntityOut} object to String to show in logs, the default is the
     * <code>java.util.Objects.toString</code>
     * 
     * @param updateEntityOut The entity to transform
     * 
     * @return A Supplier object
     */
    protected Supplier<String> convertUpdateEntityOutToPublishDataOut(final UpdateEntityOut updateEntityOut) {
        return () -> Objects.toString(updateEntityOut);
    }

    /**
     * Publish update event to message service
     * 
     * @param finalUpdateEntityIn  The objects you want to update on the persistence
     *                             mechanism
     * 
     * @param finalUpdateEntityOut The update command result
     * 
     * @param directives           Objects used to configure the update operation
     */
    protected void publishUpdateEvent(
            final UpdateEntityIn finalUpdateEntityIn,
            final UpdateEntityOut finalUpdateEntityOut,
            final Object... directives) {

        if (!isPublishEvents(directives)) {
            LOGGER.debug(
                    "Publishing update event was avoided, finalUpdateEntityIn '{}', finalUpdateEntityOut '{}', and directives '{}'",
                    finalUpdateEntityIn, finalUpdateEntityOut, directives);
            return;
        }

        LOGGER.debug(
                "Publishing update event, finalUpdateEntityIn '{}', finalUpdateEntityOut '{}', and directives '{}'",
                finalUpdateEntityIn, finalUpdateEntityOut, directives);

        try {
            final var dataInSupplier = convertUpdateEntityInToPublishDataIn(finalUpdateEntityIn);
            final var dataIn = dataInSupplier.get();

            final var dataOutSupplier = convertUpdateEntityOutToPublishDataOut(finalUpdateEntityOut);
            final var dataOut = dataOutSupplier.get();

            publishEvent(dataIn, dataOut, UPDATE_ENTITY);

            LOGGER.debug("The update event was published, dataIn '{}' and dataOut '{}'", dataIn, dataOut);
        } catch (final Exception ex) {
            LOGGER.error("The update event was not published", ExceptionUtils.getRootCause(ex));
        }
    }

    // --------------------------------------------------------

    /**
     * Create a supplier function (deferred execution) that converts a
     * {@code UpdateEntitiesIn} object to String to show in logs, the default is the
     * <code>java.util.Objects.toString</code>
     * 
     * @param updateEntitiesIn The group of entities to transform
     * 
     * @return A Supplier object
     */
    protected Supplier<String> convertUpdateEntitiesInToPublishDataIn(final UpdateEntitiesIn updateEntitiesIn) {
        return () -> Objects.toString(updateEntitiesIn);
    }

    /**
     * Create a supplier function (deferred execution) that converts a
     * {@code UpdateEntitiesOut} object to String to show in logs, the default is
     * the <code>java.util.Objects.toString</code>
     * 
     * @param updateEntitiesOut The group of entities to transform
     * 
     * @return A Supplier object
     */
    protected Supplier<String> convertUpdateEntitiesOutToPublishDataOut(final UpdateEntitiesOut updateEntitiesOut) {
        return () -> Objects.toString(updateEntitiesOut);
    }

    /**
     * Publish update all event to message service
     * 
     * @param finalUpdateEntitiesIn  The objects you want to update on the
     *                               persistence mechanism
     * 
     * @param finalUpdateEntitiesOut The update command result
     * 
     * @param directives             Objects used to configure the save operation
     */
    protected void publishUpdateAllEvent(
            final UpdateEntitiesIn finalUpdateEntitiesIn,
            final UpdateEntitiesOut finalUpdateEntitiesOut,
            final Object... directives) {

        if (!isPublishEvents(directives)) {
            LOGGER.debug(
                    "Publishing update all event was avoided, finalUpdateEntitiesIn '{}', finalUpdateEntitiesOut '{}', and directives '{}'",
                    finalUpdateEntitiesIn, finalUpdateEntitiesOut, directives);
            return;
        }

        LOGGER.debug(
                "Publishing update all event, finalUpdateEntitiesIn '{}', finalUpdateEntitiesOut '{}', and directives '{}'",
                finalUpdateEntitiesIn,
                finalUpdateEntitiesOut,
                directives);

        try {
            final var dataInSupplier = convertUpdateEntitiesInToPublishDataIn(finalUpdateEntitiesIn);
            final var dataIn = dataInSupplier.get();

            final var dataOutSupplier = convertUpdateEntitiesOutToPublishDataOut(finalUpdateEntitiesOut);
            final var dataOut = dataOutSupplier.get();

            publishEvent(dataIn, dataOut, UPDATE_ENTITIES);

            LOGGER.debug("The update all event was published, dataIn '{}' and dataOut '{}'", dataIn, dataOut);

        } catch (final Exception ex) {
            LOGGER.error("The update all event was not published", ExceptionUtils.getRootCause(ex));
        }
    }
}
