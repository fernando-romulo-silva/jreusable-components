package org.reusablecomponents.base.core.application.command.entity;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.DELETE_BY_ID;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.DELETE_BY_IDS;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.DELETE_ENTITIES;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.DELETE_ENTITY;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.SAVE_ENTITIES;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.SAVE_ENTITY;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.UPDATE_ENTITIES;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.UPDATE_ENTITY;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.reusablecomponents.base.core.application.base.BaseFacade;
import org.reusablecomponents.base.core.application.base.FacadeBiFunction;
import org.reusablecomponents.base.core.application.base.FacadeTriFunction;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default <code>InterfaceEntityCommandFacade</code>'s implementation.
 */
public non-sealed class CommandFacade< // generics
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
		extends BaseFacade<Entity, Id>
		// Interface command facade
		implements InterfaceCommandFacade<Entity, Id, // basic

				SaveEntityIn, SaveEntityOut, // entity
				SaveEntitiesIn, SaveEntitiesOut, // entities

				UpdateEntityIn, UpdateEntityOut, // entity
				UpdateEntitiesIn, UpdateEntitiesOut, // entities

				DeleteEntityIn, DeleteEntityOut, // entity
				DeleteEntitiesIn, DeleteEntitiesOut, // entities

				DeleteIdIn, DeleteIdOut, // id
				DeleteIdsIn, DeleteIdsOut> { // ids

	private static final String NON_NULL_GROUP_OF_ENTITIES_MSG = "Please pass a non-null group of %s entities";

	private static final String NON_NULL_DIRECTIVES_MSG = "Please pass a non-null directives";

	private static final String NON_NULL_ENTITY_MSG = "Please pass a non-null %s entity";

	private static final Logger LOGGER = LoggerFactory.getLogger(CommandFacade.class);

	/**
	 * Functions executed in sequence in the {@link #preSave(Object, Object...)
	 * preSave} method
	 */
	protected final List<FacadeBiFunction<SaveEntityIn>> savePreFunctions = new ArrayList<>();

	/**
	 * Functions executed in sequence in the {@link #posSave(Object, Object...)
	 * posSave} method
	 */
	protected final List<FacadeBiFunction<SaveEntityOut>> savePosFunctions = new ArrayList<>();

	/**
	 * Functions executed in sequence in the
	 * {@link #errorSave(Object, Object, Object...) errorSave} method
	 */
	protected final List<FacadeTriFunction<Exception, SaveEntityIn>> saveErrorFunctions = new ArrayList<>();

	/**
	 * Function that executes the save operation in the
	 * {@link #save(Object, Object...) save} method
	 */
	protected final BiFunction<SaveEntityIn, Object[], SaveEntityOut> saveFunction;

	/**
	 * Functions executed in sequence in the {@link #preSaveAll(Object, Object...)
	 * preSaveAll} method
	 */
	protected final List<FacadeBiFunction<SaveEntitiesIn>> saveAllPreFunctions = new ArrayList<>();

	/**
	 * Functions executed in sequence in the {@link #posSaveAll(Object, Object...)
	 * posSaveAll} method
	 */
	protected final List<FacadeBiFunction<SaveEntitiesOut>> saveAllPosFunctions = new ArrayList<>();

	/**
	 * Functions executed in sequence in the
	 * {@link #errorSaveAll(Object, Object, Object...) errorSaveAll} method
	 */
	protected final List<FacadeTriFunction<Exception, SaveEntitiesIn>> saveAllErrorFunctions = new ArrayList<>();

	/**
	 * Function that executes the save all (bunch save) operation in the
	 * {@link #saveAll(Object, Object...) saveAll} method
	 */
	protected final BiFunction<SaveEntitiesIn, Object[], SaveEntitiesOut> saveAllFunction;

	/**
	 * Functions executed in sequence in the {@link #preUpdate(Object, Object...)
	 * preUpdate} method
	 */
	protected final List<FacadeBiFunction<UpdateEntityIn>> updatePreFunctions = new ArrayList<>();

	/**
	 * Functions executed in sequence in the {@link #posUpdate(Object, Object...)
	 * posUpdate} method
	 */
	protected final List<FacadeBiFunction<UpdateEntityOut>> updatePosFunctions = new ArrayList<>();

	/**
	 * Functions executed in sequence in the
	 * {@link #errorUpdate(Object, Object, Object...) errorUpdate} method
	 */
	protected final List<FacadeTriFunction<Exception, UpdateEntityIn>> updateErrorFunctions = new ArrayList<>();

	/**
	 * Function that executes the update operation in the
	 * {@link #update(Object, Object...) update} method
	 */
	protected final BiFunction<UpdateEntityIn, Object[], UpdateEntityOut> updateFunction;

	/**
	 * Functions executed in sequence in the {@link #preUpdateAll(Object, Object...)
	 * preUpdateAll} method
	 */
	protected final List<FacadeBiFunction<UpdateEntitiesIn>> updateAllPreFunctions = new ArrayList<>();

	/**
	 * Functions executed in sequence in the {@link #posUpdateAll(Object, Object...)
	 * posUpdateAll} method
	 */
	protected final List<FacadeBiFunction<UpdateEntitiesOut>> updateAllPosFunctions = new ArrayList<>();

	/**
	 * Functions executed in sequence in the
	 * {@link #errorUpdateAll(Object, Object, Object...) errorUpdateAll} method
	 */
	protected final List<FacadeTriFunction<Exception, UpdateEntitiesIn>> updateAllErrorFunctions = new ArrayList<>();

	/**
	 * Function that executes the update all (bunch update) operation in the
	 * {@link #updateAll(Object, Object...) updateAll} method
	 */
	protected final BiFunction<UpdateEntitiesIn, Object[], UpdateEntitiesOut> updateAllFunction;

	/**
	 * Function that executes the delete operation in the
	 * {@link #delete(Object, Object...) delete} method
	 */
	protected final BiFunction<DeleteEntityIn, Object[], DeleteEntityOut> deleteFunction;

	/**
	 * Function that executes the delete all (bunch delete) operation in the
	 * {@link #deleteAll(Object, Object...) deleteAll} method
	 */
	protected final BiFunction<DeleteEntitiesIn, Object[], DeleteEntitiesOut> deleteAllFunction;

	/**
	 * Function that executes the delete by id operation in the
	 * {@link #deleteBy(Object, Object...) deleteBy} method
	 */
	protected final BiFunction<DeleteIdIn, Object[], DeleteIdOut> deleteByIdFunction;

	/**
	 * Function that executes the delete all by id (bunch delete by id) operation in
	 * the {@link #deleteAllBy(Object, Object...) deleteAllBy} method
	 */
	protected final BiFunction<DeleteIdsIn, Object[], DeleteIdsOut> deleteAllByIdFunction;

	/**
	 * Default constructior
	 * 
	 * @param builder Object in charge to construct this one
	 */
	protected CommandFacade(
			final CommandFacadeBuilder<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> builder) {
		super(builder);

		this.saveFunction = builder.saveFunction;
		this.saveAllFunction = builder.saveAllFunction;

		this.updateFunction = builder.updateFunction;
		this.updateAllFunction = builder.updateAllFunction;

		this.deleteFunction = builder.deleteFunction;
		this.deleteAllFunction = builder.deleteAllFunction;

		this.deleteByIdFunction = builder.deleteByIdFunction;
		this.deleteAllByIdFunction = builder.deleteAllByIdFunction;
	}

	/**
	 * Method executed in {@link #save(Object, Object...) save} method before the
	 * {@link #saveFunction saveFunction}, use it to configure, change, etc. the
	 * saveEntityIn object. <br />
	 * 
	 * This method execute {@link #savePreFunctions savePreFunctions} in sequence
	 * 
	 * @param saveEntityIn The object you want to save on the persistence mechanism
	 * @param directives   Objects used to configure the save operation
	 * 
	 * @return A {@code SaveEntityIn} object
	 */
	protected SaveEntityIn preSave(final SaveEntityIn saveEntityIn, final Object... directives) {
		LOGGER.debug("Default preSave, saveEntityIn {}, directives {} ", saveEntityIn, directives);

		final var saveEntityInResult = execute("preSave", saveEntityIn, savePreFunctions, directives);

		LOGGER.debug("Default preSave, saveEntityInResult {}, directives {} ", saveEntityInResult, directives);
		return saveEntityInResult;
	}

	/**
	 * Method executed in {@link #save(Object, Object...) save} method after
	 * {@link #saveFunction saveFunction}, use it to configure, change, etc. the
	 * output. <br />
	 * 
	 * This method execute {@link #savePosFunctions savePosFunctions} in sequence
	 * 
	 * @param saveEntityOut The object you saved on the persistence mechanism
	 * @param directives    Objects used to configure the save operation
	 * 
	 * @return A {@code SaveEntityOut} object
	 */
	protected SaveEntityOut posSave(final SaveEntityOut saveEntityOut, final Object... directives) {
		LOGGER.debug("Default posSave, saveEntityOut {}, directives {} ", saveEntityOut, directives);

		final var saveEntityOutResult = execute("posSave", saveEntityOut, savePosFunctions, directives);

		LOGGER.debug("Default posSave, saveEntityOutResult {}, directives {} ", saveEntityOutResult, directives);
		return saveEntityOutResult;
	}

	/**
	 * Method executed in {@link #save(Object, Object...) save} method to handle
	 * {@link #saveFunction saveFunction} errors. <br />
	 * 
	 * This method execute {@link #saveErrorFunctions saveErrorFunctions} in
	 * sequence
	 * 
	 * @param saveEntityIn The object you tried to save on the persistence mechanism
	 * @param exception    Exception thrown by save operation
	 * @param directives   Objects used to configure the save operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorSave(
			final SaveEntityIn saveEntityIn,
			final Exception exception,
			final Object... directives) {
		LOGGER.debug("Default errorSave, saveEntityIn {}, exception {}, directives {} ",
				saveEntityIn,
				exception,
				directives);

		final var exceptionResult = execute(
				"errorSave",
				exception,
				saveEntityIn,
				saveErrorFunctions,
				directives);

		LOGGER.debug("Default errorSave, saveEntityIn {}, exceptionResult {}, directives {} ",
				saveEntityIn,
				exceptionResult,
				directives);
		return exceptionResult;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SaveEntityOut save(final SaveEntityIn saveEntityIn, final Object... directives) {
		LOGGER.debug("Default save, saveEntityIn {}, directives {} ", saveEntityIn, directives);

		checkNotNull(saveEntityIn, NON_NULL_ENTITY_MSG, getEntityClazz().getSimpleName());
		checkNotNull(directives, NON_NULL_DIRECTIVES_MSG);

		final var saveEntityOut = execute(
				saveEntityIn, SAVE_ENTITY, this::preSave,
				this::posSave, saveFunction::apply, this::errorSave, directives);

		LOGGER.debug("Default save, saveEntityOut {}, directives {} ", saveEntityOut, directives);
		return saveEntityOut;
	}

	/**
	 * Method executed in {@link #saveAll(Object, Object...) saveAll} method before
	 * the {@link #saveAllFunction saveAllFunction}, use it to configure, change,
	 * etc. the input.
	 * 
	 * @param saveEntitiesIn The objects you want to save on the persistence
	 *                       mechanism
	 * @param directives     Objects used to configure the saveAll operation
	 * 
	 * @return A {@code SaveEntitiesIn} object
	 */
	protected SaveEntitiesIn preSaveAll(final SaveEntitiesIn saveEntitiesIn, final Object... directives) {
		LOGGER.debug("Default preSaveAll, saveEntiesIn {}, directives {} ", saveEntitiesIn, directives);

		final var saveEntiesInResult = execute("preSaveAll", saveEntitiesIn, saveAllPreFunctions, directives);

		LOGGER.debug("Default preSaveAll, saveEntityInResult {}, directives {} ", saveEntiesInResult, directives);
		return saveEntiesInResult;
	}

	/**
	 * Method executed in {@link #saveAll(Object, Object...) saveAll} method after
	 * {@link #saveAllFunction saveAllFunction}, use it to configure, change, etc.
	 * the output.
	 * 
	 * @param saveEntitiesOut The objects you saved on the persistence mechanism
	 * 
	 * @param directives      Objects used to configure the saveAll operation
	 * 
	 * @return A {@code SaveEntitiesOut} object
	 */
	protected SaveEntitiesOut posSaveAll(final SaveEntitiesOut saveEntitiesOut, final Object... directives) {
		LOGGER.debug("Default posSaveAll, saveEntiesOut {}, directives {} ", saveEntitiesOut, directives);

		final var saveEntitiesOutResult = execute(
				"posSaveAll",
				saveEntitiesOut,
				saveAllPosFunctions,
				directives);

		LOGGER.debug("Default posSaveAll, saveEntitiesOutResult {}, directives {} ", saveEntitiesOutResult, directives);
		return saveEntitiesOut;
	}

	/**
	 * Method executed in {@link #saveAll(Object, Object...) saveAll} method to
	 * handle {@link #saveAllFunction saveAllFunction} errors.
	 * 
	 * @param saveEntitiesIn The objects you tried to save on the persistence
	 *                       mechanism
	 * @param exception      Exception thrown by save operation
	 * @param directives     Objects used to configure the save operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorSaveAll(
			final SaveEntitiesIn saveEntitiesIn,
			final Exception exception,
			final Object... directives) {
		LOGGER.debug(
				"Default errorSaveAll, saveEntitiesIn {}, exception {}, directives {} ",
				saveEntitiesIn, getRootCause(exception), directives);

		final var exceptionResult = execute(
				"errorSaveAll", exception, saveEntitiesIn, saveAllErrorFunctions, directives);

		LOGGER.debug("Default errorSave, saveEntitiesIn {}, exceptionResult {}, directives {} ",
				saveEntitiesIn, exceptionResult, directives);
		return exceptionResult;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SaveEntitiesOut saveAll(final SaveEntitiesIn saveEntitiesIn, final Object... directives) {
		LOGGER.debug("Default saveAll, saveEntitiesIn {}, directives {} ", saveEntitiesIn, directives);

		checkNotNull(saveEntitiesIn, NON_NULL_GROUP_OF_ENTITIES_MSG, getEntityClazz().getSimpleName());
		checkNotNull(directives, NON_NULL_DIRECTIVES_MSG);

		final var saveEntitiesOut = execute(
				saveEntitiesIn, SAVE_ENTITIES, this::preSaveAll,
				this::posSaveAll, saveAllFunction::apply, this::errorSaveAll, directives);

		LOGGER.debug("Default saveAll, saveEntitiesOut {}, directives {} ", saveEntitiesOut, directives);
		return saveEntitiesOut;
	}

	/**
	 * Method executed in {@link #update(Object, Object...) update} method before
	 * the {@link #updateFunction updateFunction}, use it to configure, change, etc.
	 * the input.
	 * 
	 * @param updateEntityIn The object you want to update on the persistence
	 *                       mechanism
	 * @param directives     Objects used to configure the update operation
	 * 
	 * @return A {@code UpdateEntityIn} object
	 */
	protected UpdateEntityIn preUpdate(final UpdateEntityIn updateEntityIn, final Object... directives) {
		LOGGER.debug("Default preUpdate, updateEntityIn {}, directives {} ", updateEntityIn, directives);

		final var updateEntityInResult = execute("preUpdate", updateEntityIn, updatePreFunctions, directives);

		LOGGER.debug("Default preUpdate, updateEntityInResult {}, directives {} ", updateEntityInResult, directives);
		return updateEntityInResult;
	}

	/**
	 * Method executed in {@link #update(Object, Object...) update} method after
	 * {@link #updateFunction updateFunction}, use it to configure, change, etc.
	 * the output.
	 * 
	 * @param updateEntityOut The object you updated on the persistence mechanism
	 * @param directives      Objects used to configure the update operation
	 * 
	 * @return A {@code UpdateEntityOut} object
	 */
	protected UpdateEntityOut posUpdate(final UpdateEntityOut updateEntityOut, final Object... directives) {
		LOGGER.debug("Default preUpdate, updateEntityOut {}, directives {} ", updateEntityOut, directives);

		final var updateEntityOutResult = execute(
				"posUpdate",
				updateEntityOut,
				updatePosFunctions,
				directives);

		LOGGER.debug("Default posUpdate, updateEntityOutResult {}, directives {} ", updateEntityOutResult, directives);
		return updateEntityOutResult;
	}

	/**
	 * Method executed in {@link #update(Object, Object...) update} method to handle
	 * {@link #updateFunction updateFunction} errors.
	 * 
	 * @param updateEntityIn The object you tried to update on the persistence
	 *                       mechanism
	 * @param exception      Exception thrown by update operation
	 * @param directives     Objects used to configure the update operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorUpdate(
			final UpdateEntityIn updateEntityIn,
			final Exception exception,
			final Object... directives) {
		LOGGER.debug("Default errorUpdate, updateEntityIn {}, exception {}, directives {} ",
				updateEntityIn, getRootCause(exception), directives);

		final var exceptionResult = execute(
				"errorUpdate",
				exception,
				updateEntityIn,
				updateErrorFunctions,
				directives);

		LOGGER.debug("Default errorUpdate, updateEntityIn {}, exceptionResult {}, directives {} ",
				updateEntityIn,
				exceptionResult,
				directives);
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UpdateEntityOut update(final UpdateEntityIn updateEntityIn, final Object... directives) {
		LOGGER.debug("Default update, updateEntityIn {}, directives {} ", updateEntityIn, directives);

		final var updateEntityOut = execute(
				updateEntityIn, UPDATE_ENTITY, this::preUpdate,
				this::posUpdate, updateFunction::apply, this::errorUpdate, directives);

		LOGGER.debug("Default update, updateEntityOut {}, directives {} ", updateEntityOut, directives);
		return updateEntityOut;
	}

	/**
	 * Method executed in {@link #updateAll(Object, Object...) updateAll} method
	 * before the {@link #updateAllFunction updateAllFunction}, use it to change
	 * values.
	 * 
	 * @param updateEntitiesIn The objects you want to save on the persistence
	 *                         mechanism
	 * @param directives       Objects used to configure the updateAll operation
	 * 
	 * @return A {@code UpdateEntitiesIn} object
	 */
	protected UpdateEntitiesIn preUpdateAll(final UpdateEntitiesIn updateEntitiesIn, final Object... directives) {
		LOGGER.debug("Default preUpdateAll, updateEntityIn {}, directives {} ", updateEntitiesIn, directives);

		final var updateEntiesInResult = execute(
				"preUpdateAll",
				updateEntitiesIn,
				updateAllPreFunctions,
				directives);

		LOGGER.debug("Default preUpdateAll, updateEntiesInResult {}, directives {} ", updateEntiesInResult, directives);
		return updateEntiesInResult;
	}

	/**
	 * Method executed in {@link #updateAll(Object, Object...) updateAll} method
	 * after {@link #updateAllFunction updateAllFunction}, use it to configure,
	 * change, etc. the output.
	 * 
	 * @param updateEntitiesOut The objects you updated on the persistence mechanism
	 * @param directives        Objects used to configure the updateAll operation
	 * 
	 * @return A {@code SaveEntitiesOut} object
	 */
	protected UpdateEntitiesOut posUpdateAll(final UpdateEntitiesOut updateEntitiesOut, final Object... directives) {
		LOGGER.debug("Default posUpdateAll, updateEntitiesOut {}, directives {} ", updateEntitiesOut, directives);

		final var updateEntitiesOutResult = execute(
				"posUpdateAll",
				updateEntitiesOut,
				updateAllPosFunctions,
				directives);

		LOGGER.debug(
				"Default posUpdateAll, updateEntitiesOutResult {}, directives {} ",
				updateEntitiesOutResult, directives);
		return updateEntitiesOutResult;
	}

	/**
	 * Method executed in {@link #updateAll(Object, Object...) updateAll} method to
	 * handle {@link #updateAllFunction updateAllFunction} errors.
	 * 
	 * @param updateEntitiesIn The objects you tried to update all on the
	 *                         persistence mechanism
	 * @param exception        Exception thrown by update all operation
	 * @param directives       Objects used to configure the update all operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorUpdateAll(
			final UpdateEntitiesIn updateEntitiesIn,
			final Exception exception,
			final Object... directives) {
		LOGGER.debug("Default errorUpdateAll, updateEntitiesIn {}, exception {}, directives {} ",
				updateEntitiesIn, getRootCause(exception), directives);

		final var exceptionResult = execute(
				"errorUpdateAll",
				exception,
				updateEntitiesIn,
				updateAllErrorFunctions,
				directives);

		LOGGER.debug("Default errorUpdateAll, updateEntitiesIn {}, exceptionResult {}, directives {} ",
				updateEntitiesIn,
				exceptionResult,
				directives);
		return exceptionResult;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UpdateEntitiesOut updateAll(final UpdateEntitiesIn updateEntitiesIn, final Object... directives) {
		LOGGER.debug("Default updateAll, updateEntityIn {}, directives {} ", updateEntitiesIn, directives);

		final var updateEntitiesOut = execute(
				updateEntitiesIn, UPDATE_ENTITIES, this::preUpdateAll,
				this::posUpdateAll, updateAllFunction::apply, this::errorUpdateAll, directives);

		LOGGER.debug("Default updateAll, updateEntitiesOut {}, directives {} ", updateEntitiesOut, directives);
		return updateEntitiesOut;
	}

	// ---------------------------------------------------------------------------------------------

	/**
	 * Method executed in {@link #delete(Object, Object...) delete} method before
	 * the {@link #deleteFunction deleteFunction}, use it to configure, change, etc.
	 * the input.
	 * 
	 * @param deleteEntityIn The object you want to delete on the persistence
	 *                       mechanism
	 * @param directives     Objects used to configure the delete operation
	 * 
	 * @return A {@code DeleteEntityIn} object
	 */
	protected DeleteEntityIn preDelete(final DeleteEntityIn deleteEntityIn, final Object... directives) {
		LOGGER.debug("Default preDelete, deleteEntityIn {}, directives {} ", deleteEntityIn, directives);
		return deleteEntityIn;
	}

	/**
	 * Method executed in {@link #delete(Object, Object...) delete} method after
	 * {@link #deleteFunction deleteFunction}, use it to configure, change, etc.
	 * the output.
	 * 
	 * @param deleteEntityOut The object you deleted on the persistence mechanism
	 * @param directives      Objects used to configure the delete operation
	 * 
	 * @return A {@code DeleteEntityOut} object
	 */
	protected DeleteEntityOut posDelete(final DeleteEntityOut deleteEntityOut, final Object... directives) {
		LOGGER.debug("Default posDelete, deleteEntityOut {}, directives {} ", deleteEntityOut, directives);
		return deleteEntityOut;
	}

	/**
	 * Method executed in {@link #delete(Object, Object...) delete} method to handle
	 * {@link #deleteFunction deleteFunction} errors.
	 * 
	 * @param deleteEntityIn The object you tried to delete on the persistence
	 *                       mechanism
	 * @param exception      Exception thrown by delete operation
	 * @param directives     Objects used to configure the delete operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorDelete(
			final DeleteEntityIn deleteEntityIn,
			final Exception exception,
			final Object... directives) {
		LOGGER.debug("Default errorDelete, deleteEntityIn {}, exception {}, directives {}",
				deleteEntityIn, getRootCause(exception), directives);
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteEntityOut delete(final DeleteEntityIn deleteEntityIn, final Object... directives) {
		LOGGER.debug("Default delete, deleteEntityIn {}, directives {}", deleteEntityIn, directives);

		final var deleteEntityOut = execute(
				deleteEntityIn,
				DELETE_ENTITY,
				this::preDelete,
				this::posDelete,
				deleteFunction::apply,
				this::errorDelete,
				directives);

		LOGGER.debug("Default delete, deleteEntityOut {}, directives {}", deleteEntityOut, directives);
		return deleteEntityOut;
	}

	/**
	 * Method executed in {@link #deleteAll(Object, Object...) deleteAll} method
	 * before the {@link #deleteAllFunction deleteAllFunction}, use it to change
	 * values.
	 * 
	 * @param deleteEntitiesIn The objects you want to delete on the persistence
	 *                         mechanism
	 * @param directives       Objects used to configure the deleteAll operation
	 * 
	 * @return A {@code DeleteEntitiesIn} object
	 */
	protected DeleteEntitiesIn preDeleteAll(final DeleteEntitiesIn deleteEntitiesIn, final Object... directives) {
		LOGGER.debug("Default preDeleteAll, deleteEntityIn {}, directives {}", deleteEntitiesIn, directives);
		return deleteEntitiesIn;
	}

	/**
	 * Method executed in {@link #deleteAll(Object, Object...) deleteAll} method
	 * after {@link #deleteAllFunction deleteAllFunction}, use it to configure,
	 * change, etc. the output.
	 * 
	 * @param deleteEntitiesOut The objects you deleted on the persistence mechanism
	 * @param directives        Objects used to configure the deleteAll operation
	 * 
	 * @return A {@code SaveEntitiesOut} object
	 */
	protected DeleteEntitiesOut posDeleteAll(final DeleteEntitiesOut deleteEntitiesOut, final Object... directives) {
		LOGGER.debug("Default posDeleteAll, deleteEntitiesOut {}, directives {}", deleteEntitiesOut, directives);
		return deleteEntitiesOut;
	}

	/**
	 * Method executed in {@link #deleteAll(Object, Object...) deleteAll} method to
	 * handle {@link #deleteAllFunction deleteAllFunction} errors.
	 * 
	 * @param deleteEntitiesIn The objects you tried to delete on the persistence
	 *                         mechanism
	 * @param exception        Exception thrown by delete operation
	 * @param directives       Objects used to configure the delete operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorDeleteAll(
			final DeleteEntitiesIn deleteEntitiesIn,
			final Exception exception,
			final Object... directives) {
		LOGGER.debug("Default errorDeleteAll, deleteEntitiesIn {}, exception {}, directives {} ",
				deleteEntitiesIn, getRootCause(exception), directives);
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteEntitiesOut deleteAll(final DeleteEntitiesIn deleteEntitiesIn, final Object... directives) {
		LOGGER.debug("Default deleteAll, deleteEntitiesIn {}, directives {} ", deleteEntitiesIn, directives);

		final var deleteEntitiesOut = execute(
				deleteEntitiesIn, DELETE_ENTITIES, this::preDeleteAll,
				this::posDeleteAll, deleteAllFunction::apply, this::errorDeleteAll, directives);

		LOGGER.debug("Default deleteAll, deleteEntitiesOut {}, directives {} ", deleteEntitiesOut, directives);
		return deleteEntitiesOut;
	}

	/**
	 * Method executed in {@link #deleteBy(Object, Object...) deleteBy} method
	 * before the {@link #deleteByIdFunction deleteByIdFunction}, use it to change
	 * values.
	 * 
	 * @param deleteIdIn The object you tried to delete by on the persistence
	 *                   mechanism
	 * @param directives Objects used to configure the delete by operation
	 * 
	 * @return A {@code DeleteIdIn} object
	 */
	protected DeleteIdIn preDeleteBy(final DeleteIdIn deleteIdIn, final Object... directives) {
		LOGGER.debug("Default preDeleteBy, deleteIdIn {}, directives {} ", deleteIdIn, directives);
		return deleteIdIn;
	}

	/**
	 * Method executed in {@link #deleteBy(Object, Object...) deleteBy} method after
	 * {@link #deleteByIdFunction deleteByIdFunction}, use it to configure, change,
	 * etc. the input.
	 * 
	 * @param deleteIdOut The object's id you deleted on the persistence mechanism
	 * @param directives  Objects used to configure the delete operation
	 * 
	 * @return A {@code DeleteIdOut} object
	 */
	protected DeleteIdOut posDeleteBy(final DeleteIdOut deleteIdOut, final Object... directives) {
		LOGGER.debug("Default posDeleteBy, deleteIdOut {}, directives {} ", deleteIdOut, directives);
		return deleteIdOut;
	}

	/**
	 * Method executed in {@link #deleteBy(Object, Object...) deleteBy} method to
	 * handle {@link #deleteByIdFunction deleteByIdFunction} errors.
	 * 
	 * @param deleteIdIn The object's id you tried to delete on the persistence
	 *                   mechanism
	 * @param exception  Exception thrown by delete by id operation
	 * @param directives Objects used to configure the delete operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorDeleteBy(
			final DeleteIdIn deleteIdIn,
			final Exception exception,
			final Object... directives) {
		LOGGER.debug("Default errorDeleteBy, deleteIdIn {}, exception {}, directives {} ",
				deleteIdIn, getRootCause(exception), directives);
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteIdOut deleteBy(final DeleteIdIn deleteIdIn, final Object... directives) {
		LOGGER.debug("Default deleteBy, deleteIdIn {}, directives {} ", deleteIdIn, directives);

		final var deleteIdOut = execute(
				deleteIdIn, DELETE_BY_ID, this::preDeleteBy, this::posDeleteBy,
				deleteByIdFunction::apply, this::errorDeleteBy, directives);

		LOGGER.debug("Default deleteBy, deleteIdOut {}, directives {} ", deleteIdOut, directives);
		return deleteIdOut;
	}

	/**
	 * Method executed in {@link #deleteAllBy(Object, Object...) deleteAllBy} method
	 * before the {@link #deleteAllByIdFunction deleteAllByIdFunction}, use it to
	 * configure, change, etc. the input.
	 * 
	 * @param deleteIdsIn The object's ids you want to deleteBy on the persistence
	 *                    mechanism
	 * @param directives  Objects used to configure the deleteBy operation
	 * 
	 * @return A {@code DeleteIdsIn} object
	 */
	protected DeleteIdsIn preDeleteAllBy(final DeleteIdsIn deleteIdsIn, final Object... directives) {
		LOGGER.debug("Default preDeleteAllBy, deleteIdsIn {}, directives {} ", deleteIdsIn, directives);
		return deleteIdsIn;
	}

	/**
	 * Method executed in {@link #deleteAllBy(Object, Object...) deleteAllBy} method
	 * after {@link #deleteAllByIdFunction deleteAllByIdFunction}, use it to change
	 * values.
	 * 
	 * @param deleteIdsOut The object's ids you deleted on the persistence mechanism
	 * @param directives   Objects used to configure the delete operation
	 * 
	 * @return A {@code DeleteIdsOut} object
	 */
	protected DeleteIdsOut posDeleteAllBy(final DeleteIdsOut deleteIdsOut, final Object... directives) {
		LOGGER.debug("Default posDeleteAllBy, deleteIdsOut {}, directives {} ", deleteIdsOut, directives);
		return deleteIdsOut;
	}

	/**
	 * Method executed in {@link #deleteAllBy(Object, Object...) deleteAllBy} method
	 * to handle {@link #deleteAllByIdFunction deleteAllByIdFunction} errors.
	 * 
	 * @param deleteIdsIn The object's ids you tried to delete on the persistence
	 *                    mechanism
	 * @param exception   Exception thrown by delete by id operation
	 * @param directives  Objects used to configure the delete operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorDeleteAllBy(
			final DeleteIdsIn deleteIdsIn,
			final Exception exception,
			final Object... directives) {
		LOGGER.debug("Default errorDeleteAllBy, deleteIdsIn {}, exception {}, directives {} ",
				deleteIdsIn, getRootCause(exception), directives);
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteIdsOut deleteAllBy(final DeleteIdsIn deleteIdsIn, final Object... directives) {
		LOGGER.debug("Default deleteAllBy, deleteIdsIn {}, directives {} ", deleteIdsIn, directives);

		final var deleteIdsOut = execute(
				deleteIdsIn, DELETE_BY_IDS, this::preDeleteAllBy, this::posDeleteAllBy,
				deleteAllByIdFunction::apply, this::errorDeleteAllBy, directives);

		LOGGER.debug("Default deleteAllBy, deleteIdsOut {}, directives {} ", deleteIdsOut, directives);
		return deleteIdsOut;
	}
}