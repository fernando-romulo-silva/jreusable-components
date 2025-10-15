package org.reusablecomponents.base.core.application.command.entity;

import java.util.List;

import org.reusablecomponents.base.core.application.base.BaseFacade;
import org.reusablecomponents.base.core.application.base.BaseFacadeBuilder;
import org.reusablecomponents.base.core.application.command.function.CommandSaveFunctions.*;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.util.function.compose.ComposeFunction3Args;
import org.reusablecomponents.base.core.infra.util.function.compose.ComposeFunction2Args;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public abstract sealed class AbstractCommandFacade< // generics
        // default
        Entity extends AbstractEntity<Id>, Id, // basic
        // save
        SaveEntityIn, SaveEntityOut, // entity
        SaveEntitiesIn, SaveEntitiesOut, // entities
        // update
        UpdateEntityIn, UpdateEntityOut, // entity
        UpdateEntitiesIn, UpdateEntitiesOut, // entities
        // delete
        DeleteEntityIn, DeleteEntityOut, // entity
        DeleteEntitiesIn, DeleteEntitiesOut, // entities
        // delete by id
        DeleteIdIn, DeleteIdOut, // id
        DeleteIdsIn, DeleteIdsOut> // ids
        // Base Facade
        extends BaseFacade<Entity, Id> permits CommandFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCommandFacade.class);

    /**
     * Default constructor
     * 
     * @param builder
     */
    protected AbstractCommandFacade(@NotNull final BaseFacadeBuilder builder) {
        super(builder);
    }

    /**
     * Method executed in {@link CommandFacade#save(Object, Object...) save} method
     * before the {@link CommandFacade#saveFunction saveFunction}, use it to
     * configure, change, etc. the saveEntityIn object. <br />
     * 
     * @param saveEntityIn The object you want to save on the persistence mechanism
     * @param directives   Objects used to configure the save operation
     * 
     * @return A {@code SaveEntityIn} object
     */
    protected final SaveEntityIn preSave(final SaveEntityIn saveEntityIn, final Object... directives) {
        LOGGER.debug("Executing default preSave, saveEntityIn {}, directives {} ", saveEntityIn, directives);

        final var saveEntityInFinal = compose(saveEntityIn, getSavePreFunctions(), directives);

        LOGGER.debug("Default preSave executed, saveEntityInFinal {}, directives {} ", saveEntityInFinal, directives);
        return saveEntityInFinal;
    }

    /**
     * Get functions executed in sequence in the {@link #preSave(Object, Object...)
     * preSave} method
     */
    protected List<PreSaveFunction<SaveEntityIn>> getSavePreFunctions() {
        return List.of();
    }

    /**
     * Method executed in {@link CommandFacade#save(Object, Object...) save} method
     * after {@link CommandFacade#saveFunction saveFunction}, use it to configure,
     * change, etc. the output. <br />
     * 
     * @param saveEntityOut The object you saved on the persistence mechanism
     * @param directives    Objects used to configure the save operation
     * 
     * @return A {@code SaveEntityOut} object
     */
    protected final SaveEntityOut posSave(final SaveEntityOut saveEntityOut, final Object... directives) {
        LOGGER.debug("Executing default posSave, saveEntityOut {}, directives {}", saveEntityOut, directives);

        final var saveEntityOutFinal = compose(saveEntityOut, getSavePosFunctions(), directives);

        LOGGER.debug("Default posSave executed, saveEntityOutFinal {}, directives {}", saveEntityOutFinal, directives);
        return saveEntityOutFinal;
    }

    /**
     * Get functions executed in sequence in the {@link #posSave(Object, Object...)
     * posSave} method
     */
    protected List<PosSaveFunction<SaveEntityOut>> getSavePosFunctions() {
        return List.of();
    }

    /**
     * Method executed in {@link CommandFacade#save(Object, Object...) save} method
     * to handle {@link CommandFacade#saveFunction saveFunction} errors. <br />
     * 
     * @param saveEntityIn The object you tried to save on the persistence mechanism
     * @param exception    Exception thrown by save operation
     * @param directives   Objects used to configure the save operation
     * 
     * @return The handled exception
     */
    protected final Exception errorSave(
            final SaveEntityIn saveEntityIn,
            final Exception exception,
            final Object... directives) {
        LOGGER.debug("Executing default errorSave, saveEntityIn {}, exception {}, directives {} ",
                saveEntityIn, exception, directives);

        final var finalException = compose(exception, saveEntityIn, getSaveErrorFunctions(), directives);

        LOGGER.debug("Default errorSave executed, saveEntityIn {}, finalException {}, directives {} ",
                saveEntityIn, finalException, directives);
        return finalException;
    }

    /**
     * Get functions executed in sequence in the
     * {@link #errorSave(Object, Object, Object...) errorSave} method
     */
    protected List<ErrorSaveFunction<Exception, SaveEntityIn>> getSaveErrorFunctions() {
        return List.of();
    }

    /**
     * Method executed in {@link CommandFacade#saveAll(Object, Object...) saveAll}
     * method before the {@link CommandFacade#saveAllFunction saveAllFunction}, use
     * it to configure, change, * etc. the input.
     * 
     * @param saveEntitiesIn The objects you want to save on the persistence
     *                       mechanism
     * @param directives     Objects used to configure the saveAll operation
     * 
     * @return A {@code SaveEntitiesIn} object
     */
    protected final SaveEntitiesIn preSaveAll(final SaveEntitiesIn saveEntitiesIn, final Object... directives) {
        LOGGER.debug("Executing default preSaveAll, saveEntiesIn {}, directives {}", saveEntitiesIn, directives);

        final var finalSaveEntiesIn = compose(saveEntitiesIn, getSaveAllPreFunctions(), directives);

        LOGGER.debug("Default preSaveAll executed, finalSaveEntiesIn {}, directives {}",
                finalSaveEntiesIn, directives);
        return finalSaveEntiesIn;
    }

    /**
     * Get functions executed in sequence in the
     * {@link #preSaveAll(Object, Object...) preSaveAll} method
     */
    protected List<PreSaveAllFunction<SaveEntitiesIn>> getSaveAllPreFunctions() {
        return List.of();
    }

    /**
     * Method executed in {@link CommandFacade#saveAll(Object, Object...) saveAll}
     * method after {@link CommandFacade#saveAllFunction saveAllFunction}, use it to
     * configure, change, etc. the output.
     * 
     * @param saveEntitiesOut The objects you saved on the persistence mechanism
     * 
     * @param directives      Objects used to configure the saveAll operation
     * 
     * @return A {@code SaveEntitiesOut} object
     */
    protected final SaveEntitiesOut posSaveAll(final SaveEntitiesOut saveEntitiesOut, final Object... directives) {
        LOGGER.debug("Executing default posSaveAll, saveEntiesOut {}, directives {} ", saveEntitiesOut, directives);

        final var finalSaveEntitiesOut = compose(saveEntitiesOut, getSaveAllPosFunctions(), directives);

        LOGGER.debug("Default posSaveAll executed, finalSaveEntitiesOut {}, directives {} ",
                finalSaveEntitiesOut, directives);
        return saveEntitiesOut;
    }

    /**
     * Get functions executed in sequence in the
     * {@link #posSaveAll(Object, Object...) posSaveAll} method
     */
    protected List<PosSaveAllFunction<SaveEntitiesOut>> getSaveAllPosFunctions() {
        return List.of();
    }

    /**
     * Method executed in {@link CommandFacade#saveAll(Object, Object...) saveAll}
     * method to handle {@link CommandFacade#saveAllFunction saveAllFunction}
     * errors.
     * 
     * @param saveEntitiesIn The objects you tried to save on the persistence
     *                       mechanism
     * @param exception      Exception thrown by save operation
     * @param directives     Objects used to configure the save operation
     * 
     * @return The handled exception
     */
    protected final Exception errorSaveAll(
            final SaveEntitiesIn saveEntitiesIn,
            final Exception exception,
            final Object... directives) {
        LOGGER.debug(
                "Executing default errorSaveAll, saveEntitiesIn {}, exception {}, directives {}",
                saveEntitiesIn, exception, directives);

        final var finalException = compose(exception, saveEntitiesIn, getSaveAllErrorFunctions(), directives);

        LOGGER.debug("Default errorSaveAll executed, saveEntitiesIn {}, finalException {}, directives {}",
                saveEntitiesIn, finalException, directives);
        return finalException;
    }

    /**
     * Get functions executed in sequence in the
     * {@link #errorSaveAll(Object, Object, Object...) errorSaveAll} method
     */
    protected List<ErrorSaveAllFunction<Exception, SaveEntitiesIn>> getSaveAllErrorFunctions() {
        return List.of();
    }

    /**
     * Method executed in {@link CommandFacade#update(Object, Object...) update}
     * method before the {@link CommandFacade#updateFunction updateFunction}, use it
     * to configure, change, etc. the input.
     * 
     * @param updateEntityIn The object you want to update on the persistence
     *                       mechanism
     * @param directives     Objects used to configure the update operation
     * 
     * @return A {@code UpdateEntityIn} object
     */
    protected final UpdateEntityIn preUpdate(final UpdateEntityIn updateEntityIn, final Object... directives) {
        LOGGER.debug("Executing default preUpdate, updateEntityIn {}, directives {}", updateEntityIn, directives);

        final var finalUpdateEntityIn = compose(updateEntityIn, getUpdatePreFunctions(), directives);

        LOGGER.debug("Default preUpdate executed, finalUpdateEntityIn {}, directives {}",
                finalUpdateEntityIn, directives);
        return finalUpdateEntityIn;
    }

    /**
     * Get functions executed in sequence in the
     * {@link #preUpdate(Object, Object...) preUpdate} method
     */
    protected List<ComposeFunction2Args<UpdateEntityIn>> getUpdatePreFunctions() {
        return List.of();
    }

    /**
     * Method executed in {@link CommandFacade#update(Object, Object...) update}
     * method after {@link CommandFacade#updateFunction updateFunction}, use it to
     * configure, change, etc. the output.
     * 
     * @param updateEntityOut The object you updated on the persistence mechanism
     * @param directives      Objects used to configure the update operation
     * 
     * @return A {@code UpdateEntityOut} object
     */
    protected final UpdateEntityOut posUpdate(final UpdateEntityOut updateEntityOut, final Object... directives) {
        LOGGER.debug("Executing default preUpdate, updateEntityOut {}, directives {} ", updateEntityOut, directives);

        final var finalUpdateEntityOut = compose(updateEntityOut, getUpdatePosFunctions(), directives);

        LOGGER.debug("Default posUpdate executed, finalUpdateEntityOut {}, directives {} ",
                finalUpdateEntityOut, directives);
        return finalUpdateEntityOut;
    }

    /**
     * Get functions executed in sequence in the
     * {@link #posUpdate(Object, Object...) posUpdate} method
     */
    protected List<ComposeFunction2Args<UpdateEntityOut>> getUpdatePosFunctions() {
        return List.of();
    }

    /**
     * Method executed in {@link CommandFacade#update(Object, Object...) update}
     * method to handle {@link CommandFacade#updateFunction updateFunction} errors.
     * 
     * @param updateEntityIn The object you tried to update on the persistence
     *                       mechanism
     * @param exception      Exception thrown by update operation
     * @param directives     Objects used to configure the update operation
     * 
     * @return The handled exception
     */
    protected final Exception errorUpdate(
            final UpdateEntityIn updateEntityIn,
            final Exception exception,
            final Object... directives) {
        LOGGER.debug("Executing default errorUpdate, updateEntityIn {}, exception {}, directives {} ",
                updateEntityIn, exception, directives);

        final var finalException = compose(exception, updateEntityIn, getUpdateErrorFunctions(), directives);

        LOGGER.debug("Default errorUpdate executed, updateEntityIn {}, finalException {}, directives {} ",
                updateEntityIn, finalException, directives);
        return finalException;
    }

    /**
     * Get functions executed in sequence in the
     * {@link #errorUpdate(Object, Object, Object...) errorUpdate} method
     */
    protected List<ComposeFunction3Args<Exception, UpdateEntityIn>> getUpdateErrorFunctions() {
        return List.of();
    }

    /**
     * Method executed in {@link CommandFacade#updateAll(Object, Object...)
     * updateAll} method before the {@link CommandFacade#updateAllFunction
     * updateAllFunction}, use it to change
     * values.
     * 
     * @param updateEntitiesIn The objects you want to save on the persistence
     *                         mechanism
     * @param directives       Objects used to configure the updateAll operation
     * 
     * @return A {@code UpdateEntitiesIn} object
     */
    protected final UpdateEntitiesIn preUpdateAll(final UpdateEntitiesIn updateEntitiesIn, final Object... directives) {
        LOGGER.debug("Executing default preUpdateAll, updateEntityIn {}, directives {}", updateEntitiesIn, directives);

        final var finalUpdateEntiesIn = compose(updateEntitiesIn, getUpdateAllPreFunctions(), directives);

        LOGGER.debug("Default preUpdateAll executed, finalUpdateEntiesIn {}, directives {}",
                finalUpdateEntiesIn, directives);
        return finalUpdateEntiesIn;
    }

    /**
     * Get functions executed in sequence in the
     * {@link #preUpdateAll(Object, Object...) preUpdateAll} method
     */
    protected List<ComposeFunction2Args<UpdateEntitiesIn>> getUpdateAllPreFunctions() {
        return List.of();
    }

    /**
     * Method executed in {@link CommandFacade#updateAll(Object, Object...)
     * updateAll} method after {@link CommandFacade#updateAllFunction
     * updateAllFunction}, use it to configure, change, etc. the output.
     * 
     * @param updateEntitiesOut The objects you updated on the persistence mechanism
     * @param directives        Objects used to configure the updateAll operation
     * 
     * @return A {@code SaveEntitiesOut} object
     */
    protected final UpdateEntitiesOut posUpdateAll(final UpdateEntitiesOut updateEntitiesOut,
            final Object... directives) {
        LOGGER.debug("Executing default posUpdateAll, updateEntitiesOut {}, directives {} ",
                updateEntitiesOut, directives);

        final var finalUpdateEntitiesOut = compose(updateEntitiesOut, getUpdateAllPosFunctions(), directives);

        LOGGER.debug(
                "Default posUpdateAll executed, finalUpdateEntitiesOut {}, directives {} ",
                finalUpdateEntitiesOut, directives);
        return finalUpdateEntitiesOut;
    }

    /**
     * Get functions executed in sequence in the
     * {@link #posUpdateAll(Object, Object...) posUpdateAll} method
     */
    protected List<ComposeFunction2Args<UpdateEntitiesOut>> getUpdateAllPosFunctions() {
        return List.of();
    }

    /**
     * Method executed in {@link CommandFacade#updateAll(Object, Object...)
     * updateAll} method to handle {@link CommandFacade#updateAllFunction
     * updateAllFunction} errors.
     * 
     * @param updateEntitiesIn The objects you tried to update all on the
     *                         persistence mechanism
     * @param exception        Exception thrown by update all operation
     * @param directives       Objects used to configure the update all operation
     * 
     * @return The handled exception
     */
    protected final Exception errorUpdateAll(
            final UpdateEntitiesIn updateEntitiesIn,
            final Exception exception,
            final Object... directives) {
        LOGGER.debug("Executing default errorUpdateAll, updateEntitiesIn {}, exception {}, directives {} ",
                updateEntitiesIn, exception, directives);

        final var finalException = compose(exception, updateEntitiesIn, getUpdateAllErrorFunctions(), directives);

        LOGGER.debug("Default errorUpdateAll executed, updateEntitiesIn {}, exceptionResult {}, directives {} ",
                updateEntitiesIn, finalException, directives);
        return finalException;
    }

    /**
     * Get functions executed in sequence in the
     * {@link #errorUpdateAll(Object, Object, Object...) errorUpdateAll} method
     */
    protected List<ComposeFunction3Args<Exception, UpdateEntitiesIn>> getUpdateAllErrorFunctions() {
        return List.of();
    }

    /**
     * Method executed in {@link CommandFacade#delete(Object, Object...) delete}
     * method before the {@link CommandFacade#deleteFunction deleteFunction}, use it
     * to configure, change, etc. the input.
     * 
     * @param deleteEntityIn The object you want to delete on the persistence
     *                       mechanism
     * @param directives     Objects used to configure the delete operation
     * 
     * @return A {@code DeleteEntityIn} object
     */
    protected final DeleteEntityIn preDelete(final DeleteEntityIn deleteEntityIn, final Object... directives) {
        LOGGER.debug("Executing default preDelete, deleteEntityIn {}, directives {} ", deleteEntityIn, directives);

        final var finalDeleteEntityIn = compose(deleteEntityIn, getDeletePreFunctions(), directives);

        LOGGER.debug("Default preSave executed, finalDeleteEntityIn {}, directives {} ",
                finalDeleteEntityIn, directives);
        return finalDeleteEntityIn;
    }

    /**
     * Get functions executed in sequence in the
     * {@link #preDelete(Object, Object...) preDelete} method
     */
    protected List<ComposeFunction2Args<DeleteEntityIn>> getDeletePreFunctions() {
        return List.of();
    }

    /**
     * Method executed in {@link CommandFacade#delete(Object, Object...) delete}
     * method after {@link CommandFacade#deleteFunction deleteFunction}, use it to
     * configure, change, etc. the output.
     * 
     * @param deleteEntityOut The object you deleted on the persistence mechanism
     * @param directives      Objects used to configure the delete operation
     * 
     * @return A {@code DeleteEntityOut} object
     */
    protected final DeleteEntityOut posDelete(final DeleteEntityOut deleteEntityOut, final Object... directives) {
        LOGGER.debug("Executing default posDelete, deleteEntityOut {}, directives {} ", deleteEntityOut, directives);

        final var finalDeleteEntityOut = compose(deleteEntityOut, getDeletePosFunctions(), directives);

        LOGGER.debug("Default posDelete executed, finalDeleteEntityOut {}, directives {} ",
                finalDeleteEntityOut, directives);
        return finalDeleteEntityOut;
    }

    /**
     * Get functions executed in sequence in the
     * {@link #posDelete(Object, Object...) posDelete} method
     */
    protected List<ComposeFunction2Args<DeleteEntityOut>> getDeletePosFunctions() {
        return List.of();
    }

    /**
     * Method executed in {@link CommandFacade#delete(Object, Object...) delete}
     * method to handle {@link CommandFacade#deleteFunction deleteFunction} errors.
     * 
     * @param deleteEntityIn The object you tried to delete on the persistence
     *                       mechanism
     * @param exception      Exception thrown by delete operation
     * @param directives     Objects used to configure the delete operation
     * 
     * @return The handled exception
     */
    protected final Exception errorDelete(
            final DeleteEntityIn deleteEntityIn,
            final Exception exception,
            final Object... directives) {
        LOGGER.debug("Executing default errorDelete, deleteEntityIn {}, exception {}, directives {}",
                deleteEntityIn, exception, directives);

        final var finalException = compose(exception, deleteEntityIn, getDeleteErrorFunctions(), directives);

        LOGGER.debug("Default errorDelete executed, deleteEntityIn {}, finalException {}, directives {} ",
                deleteEntityIn, finalException, directives);
        return finalException;
    }

    /**
     * Functions executed in sequence in the
     * {@link #errorDelete(Object, Object, Object...) errorDelete} method
     */
    protected List<ComposeFunction3Args<Exception, DeleteEntityIn>> getDeleteErrorFunctions() {
        return List.of();
    }

    /**
     * Method executed in {@link CommandFacade#deleteAll(Object, Object...)
     * deleteAll} method before the {@link CommandFacade#deleteAllFunction
     * deleteAllFunction}, use it to change values.
     * 
     * @param deleteEntitiesIn The objects you want to delete on the persistence
     *                         mechanism
     * @param directives       Objects used to configure the deleteAll operation
     * 
     * @return A {@code DeleteEntitiesIn} object
     */
    protected final DeleteEntitiesIn preDeleteAll(final DeleteEntitiesIn deleteEntitiesIn, final Object... directives) {
        LOGGER.debug("Default preDeleteAll, deleteEntityIn {}, directives {}", deleteEntitiesIn, directives);

        final var finalDeleteEntiesIn = compose(deleteEntitiesIn, getDeleteAllPreFunctions(), directives);

        LOGGER.debug("Default preDeleteAll executed, finalDeleteEntiesIn {}, directives {} ",
                finalDeleteEntiesIn, directives);
        return finalDeleteEntiesIn;
    }

    /**
     * Get functions executed in sequence in the
     * {@link #preDeleteAll(Object, Object...) preDeleteAll} method
     */
    protected List<ComposeFunction2Args<DeleteEntitiesIn>> getDeleteAllPreFunctions() {
        return List.of();
    }

    /**
     * Method executed in {@link CommandFacade#deleteAll(Object, Object...)
     * deleteAll} method after {@link CommandFacade#deleteAllFunction
     * deleteAllFunction}, use it to configure, change, etc. the output.
     * 
     * @param deleteEntitiesOut The objects you deleted on the persistence mechanism
     * @param directives        Objects used to configure the deleteAll operation
     * 
     * @return A {@code SaveEntitiesOut} object
     */
    protected final DeleteEntitiesOut posDeleteAll(
            final DeleteEntitiesOut deleteEntitiesOut,
            final Object... directives) {
        LOGGER.debug("Executing default posDeleteAll, deleteEntitiesOut {}, directives {}",
                deleteEntitiesOut, directives);

        final var finalDeleteEntiesOut = compose(deleteEntitiesOut, getDeleteAllPosFunctions(), directives);

        LOGGER.debug("Default posDeleteAll executed, finalDeleteEntiesOut {}, directives {} ",
                finalDeleteEntiesOut, directives);
        return finalDeleteEntiesOut;
    }

    /**
     * Get functions executed in sequence in the
     * {@link #posDeleteAll(Object, Object...)
     * posDeleteAll} method
     */
    protected List<ComposeFunction2Args<DeleteEntitiesOut>> getDeleteAllPosFunctions() {
        return List.of();
    }

    /**
     * Method executed in {@link CommandFacade#deleteAll(Object, Object...)
     * deleteAll} method to handle {@link CommandFacade#deleteAllFunction
     * deleteAllFunction} errors.
     * 
     * @param deleteEntitiesIn The objects you tried to delete on the persistence
     *                         mechanism
     * @param exception        Exception thrown by delete operation
     * @param directives       Objects used to configure the delete operation
     * 
     * @return The handled exception
     */
    protected final Exception errorDeleteAll(
            final DeleteEntitiesIn deleteEntitiesIn,
            final Exception exception,
            final Object... directives) {
        LOGGER.debug("Executing default errorDeleteAll, deleteEntitiesIn {}, exception {}, directives {} ",
                deleteEntitiesIn, exception, directives);

        final var finalException = compose(exception, deleteEntitiesIn, getDeleteAllErrorFunctions(), directives);

        LOGGER.debug("Default errorDeleteAll executed, deleteEntitiesIn {}, finalException {}, directives {} ",
                deleteEntitiesIn, finalException, directives);
        return finalException;
    }

    /**
     * Get functions executed in sequence in the
     * {@link #errorDeleteAll(Object, Object, Object...) errorDeleteAll} method
     */
    protected List<ComposeFunction3Args<Exception, DeleteEntitiesIn>> getDeleteAllErrorFunctions() {
        return List.of();
    }

    /**
     * Method executed in {@link CommandFacade#deleteBy(Object, Object...) deleteBy}
     * method before the {@link CommandFacade#deleteByIdFunction
     * deleteByIdFunction}, use it to change values.
     * 
     * @param deleteIdIn The object you tried to delete by on the persistence
     *                   mechanism
     * @param directives Objects used to configure the delete by operation
     * 
     * @return A {@code DeleteIdIn} object
     */
    protected final DeleteIdIn preDeleteBy(final DeleteIdIn deleteIdIn, final Object... directives) {
        LOGGER.debug("Executing default preDeleteBy, deleteIdIn {}, directives {} ", deleteIdIn, directives);

        final var finalDeleteIdIn = compose(deleteIdIn, getDeleteByIdPreFunctions(), directives);

        LOGGER.debug("Default preDeleteBy executed, finalDeleteIdIn {}, directives {} ", finalDeleteIdIn, directives);
        return finalDeleteIdIn;
    }

    /**
     * Get functions executed in sequence in the
     * {@link #preDeleteBy(Object, Object...) preDeleteBy} method
     */
    protected List<ComposeFunction2Args<DeleteIdIn>> getDeleteByIdPreFunctions() {
        return List.of();
    }

    /**
     * Method executed in {@link CommandFacade#deleteBy(Object, Object...) deleteBy}
     * method after {@link CommandFacade#deleteByIdFunction deleteByIdFunction}, use
     * it to configure, change, etc. the input.
     * 
     * @param deleteIdOut The object's id you deleted on the persistence mechanism
     * @param directives  Objects used to configure the delete operation
     * 
     * @return A {@code DeleteIdOut} object
     */
    protected final DeleteIdOut posDeleteBy(final DeleteIdOut deleteIdOut, final Object... directives) {
        LOGGER.debug("Executing default posDeleteBy, deleteIdOut {}, directives {}", deleteIdOut, directives);

        final var finalDeleteIdOut = compose(deleteIdOut, getDeleteByIdPosFunctions(), directives);

        LOGGER.debug("Default posDeleteBy executed, finalDeleteIdOut {}, directives {} ", finalDeleteIdOut, directives);
        return finalDeleteIdOut;
    }

    /**
     * Get functions executed in sequence in the
     * {@link #posDeleteBy(Object, Object...) posDeleteBy} method
     */
    protected List<ComposeFunction2Args<DeleteIdOut>> getDeleteByIdPosFunctions() {
        return List.of();
    }

    /**
     * Method executed in {@link CommandFacade#deleteBy(Object, Object...) deleteBy}
     * method to handle {@link CommandFacade#deleteByIdFunction deleteByIdFunction}
     * errors.
     * 
     * @param deleteIdIn The object's id you tried to delete on the persistence
     *                   mechanism
     * @param exception  Exception thrown by delete by id operation
     * @param directives Objects used to configure the delete operation
     * 
     * @return The handled exception
     */
    protected final Exception errorDeleteBy(
            final DeleteIdIn deleteIdIn,
            final Exception exception,
            final Object... directives) {
        LOGGER.debug("Executing default errorDeleteBy, deleteIdIn {}, exception {}, directives {}",
                deleteIdIn, exception, directives);

        final var finalException = compose(exception, deleteIdIn, getDeleteByIdErrorFunctions(), directives);

        LOGGER.debug("Default errorDelete executed, deleteEntityIn {}, finalException {}, directives {}",
                deleteIdIn, finalException, directives);
        return finalException;
    }

    /**
     * Functions executed in sequence in the
     * {@link #errorDeleteBy(Object, Object, Object...) errorDeleteBy} method
     */
    protected List<ComposeFunction3Args<Exception, DeleteIdIn>> getDeleteByIdErrorFunctions() {
        return List.of();
    }

    /**
     * Method executed in {@link CommandFacade#deleteAllBy(Object, Object...)
     * deleteAllBy} method before the {@link CommandFacade#deleteAllByIdFunction
     * deleteAllByIdFunction}, use it to configure, change, etc. the input.
     * 
     * @param deleteIdsIn The object's ids you want to deleteBy on the persistence
     *                    mechanism
     * @param directives  Objects used to configure the deleteBy operation
     * 
     * @return A {@code DeleteIdsIn} object
     */
    protected final DeleteIdsIn preDeleteAllBy(final DeleteIdsIn deleteIdsIn, final Object... directives) {
        LOGGER.debug("Executing default preDeleteAllBy, deleteIdsIn {}, directives {}", deleteIdsIn, directives);

        final var finalDeleteIdsIn = compose(deleteIdsIn, getDeleteAllByIdPreFunctions(), directives);

        LOGGER.debug("Default preDeleteAllBy executed, finalDeleteIdsIn {}, directives {}",
                finalDeleteIdsIn, directives);
        return finalDeleteIdsIn;
    }

    /**
     * Get functions executed in sequence in the
     * {@link #preDeleteAllBy(Object, Object...) preDeleteAllBy} method
     */
    protected List<ComposeFunction2Args<DeleteIdsIn>> getDeleteAllByIdPreFunctions() {
        return List.of();
    }

    /**
     * Method executed in {@link CommandFacade#deleteAllBy(Object, Object...)
     * deleteAllBy} method after {@link CommandFacade#deleteAllByIdFunction
     * deleteAllByIdFunction}, use it to change values.
     * 
     * @param deleteIdsOut The object's ids you deleted on the persistence mechanism
     * @param directives   Objects used to configure the delete operation
     * 
     * @return A {@code DeleteIdsOut} object
     */
    protected final DeleteIdsOut posDeleteAllBy(final DeleteIdsOut deleteIdsOut, final Object... directives) {
        LOGGER.debug("Executing default posDeleteAllBy, deleteIdsOut {}, directives {}", deleteIdsOut, directives);

        final var finalDeleteIdsIn = compose(deleteIdsOut, getDeleteAllByIdPosFunctions(), directives);

        LOGGER.debug("Default posDeleteAllBy executed, finalDeleteIdsIn {}, directives {}",
                finalDeleteIdsIn, directives);
        return deleteIdsOut;
    }

    /**
     * Get functions executed in sequence in the
     * {@link #posDeleteAllBy(Object, Object...) posDeleteAllBy} method
     */
    protected List<ComposeFunction2Args<DeleteIdsOut>> getDeleteAllByIdPosFunctions() {
        return List.of();
    }

    /**
     * Method executed in {@link CommandFacade#deleteAllBy(Object, Object...)
     * deleteAllBy} method to handle {@link CommandFacade#deleteAllByIdFunction
     * deleteAllByIdFunction} errors.
     * 
     * @param deleteIdsIn The object's ids you tried to delete on the persistence
     *                    mechanism
     * @param exception   Exception thrown by delete by id operation
     * @param directives  Objects used to configure the delete operation
     * 
     * @return The handled exception
     */
    protected final Exception errorDeleteAllBy(
            final DeleteIdsIn deleteIdsIn,
            final Exception exception,
            final Object... directives) {
        LOGGER.debug("Executing default errorDeleteAllBy, deleteIdsIn {}, exception {}, directives {}",
                deleteIdsIn, exception, directives);

        final var finalException = compose(
                exception, deleteIdsIn, getDeleteAllByIdErrorFunctions(), directives);

        LOGGER.debug("Default errorDeleteAllBy executed, deleteIdsIn {}, finalException {}, directives {}",
                deleteIdsIn, finalException, directives);
        return finalException;
    }

    /**
     * Get functions executed in sequence in the
     * {@link #errorDeleteAllBy(Object, Object, Object...) errorDeleteAllBy} method
     */
    protected List<ComposeFunction3Args<Exception, DeleteIdsIn>> getDeleteAllByIdErrorFunctions() {
        return List.of();
    }
}
