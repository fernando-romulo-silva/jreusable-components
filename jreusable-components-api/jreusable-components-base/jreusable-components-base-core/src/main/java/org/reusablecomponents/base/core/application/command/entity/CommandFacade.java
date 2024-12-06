package org.reusablecomponents.base.core.application.command.entity;

import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.DELETE_BY_ID;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.DELETE_BY_IDS;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.DELETE_ENTITIES;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.DELETE_ENTITY;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.SAVE_ENTITIES;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.SAVE_ENTITY;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.UPDATE_ENTITIES;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.UPDATE_ENTITY;

import java.util.function.BiFunction;

import org.reusablecomponents.base.core.application.base.BaseFacade;
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
		DeleteIdsIn, DeleteIdsOut> //

		// Base Facade
		extends
		BaseFacade<Entity, Id>
		// Interface command facade
		implements InterfaceCommandFacade<Entity, Id, // basic

				SaveEntityIn, SaveEntityOut, //
				SaveEntitiesIn, SaveEntitiesOut, //

				UpdateEntityIn, UpdateEntityOut, //
				UpdateEntitiesIn, UpdateEntitiesOut, //

				DeleteEntityIn, DeleteEntityOut, //
				DeleteEntitiesIn, DeleteEntitiesOut, //

				DeleteIdIn, DeleteIdOut, //
				DeleteIdsIn, DeleteIdsOut> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommandFacade.class);

	protected final BiFunction<SaveEntityIn, Object[], SaveEntityOut> saveFunction;
	protected final BiFunction<SaveEntitiesIn, Object[], SaveEntitiesOut> saveAllFunction;

	protected final BiFunction<UpdateEntityIn, Object[], UpdateEntityOut> updateFunction;
	protected final BiFunction<UpdateEntitiesIn, Object[], UpdateEntitiesOut> updateAllFunction;

	protected final BiFunction<DeleteEntityIn, Object[], DeleteEntityOut> deleteFunction;
	protected final BiFunction<DeleteEntitiesIn, Object[], DeleteEntitiesOut> deleteAllFunction;
	protected final BiFunction<DeleteIdIn, Object[], DeleteIdOut> deleteByIdFunction;
	protected final BiFunction<DeleteIdsIn, Object[], DeleteIdsOut> deleteAllByIdFunction;

	/**
	 * Default constructior
	 * 
	 * @param builder Object in charge to construct this one
	 */
	public CommandFacade(
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
	 * Method executed before {@link #save(Object, Object...) save} saveFunction,
	 * use it to change values.
	 * 
	 * @param saveEntityIn The object you want to save on the persistence mechanism
	 * @param directives   Objects used to configure the save operation
	 * 
	 * @return A {@code SaveEntityIn} object
	 */
	protected SaveEntityIn preSave(final SaveEntityIn saveEntityIn, final Object... directives) {
		LOGGER.debug("Pre save method, ");
		return saveEntityIn;
	}

	/**
	 * Method executed after {@link #save(Object, Object...) save} saveFunction,
	 * use it to change values.
	 * 
	 * @param saveEntityOut The object you saved and updated with the persistence
	 *                      mechanism
	 * @param directives    Objects used to configure the save operation
	 * 
	 * @return A {@code SaveEntityOut} object
	 */
	protected SaveEntityOut posSave(final SaveEntityOut saveEntityOut, final Object... directives) {
		return saveEntityOut;
	}

	/**
	 * Method used to handle {@link #save(Object, Object...) save} errors.
	 * 
	 * @param saveEntityIn The object you want to save on the persistence mechanism
	 * @param exception    Exception thrown by save operation
	 * @param directives   Objects used to configure the save operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorSave(
			final SaveEntityIn saveEntityIn,
			final Exception exception,
			final Object... directives) {
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SaveEntityOut save(final SaveEntityIn saveEntityIn, final Object... directives) {
		return executeOperation(
				saveEntityIn, SAVE_ENTITY, this::preSave,
				this::posSave, saveFunction::apply, this::errorSave, directives);
	}

	/**
	 * Method used to change a group of entities before save it.
	 * 
	 * @param saveEntiesIn The object to be changed
	 * @param directives   Objects used to configure the save operation
	 * 
	 * @return A new {@code SaveEntitiesIn} object
	 */
	protected SaveEntitiesIn preSaveAll(final SaveEntitiesIn saveEntiesIn, final Object... directives) {
		return saveEntiesIn;
	}

	/**
	 * Method used to change a group of entities after save it.
	 * 
	 * @param saveEntiesOut The group of objects to be changed
	 * @param directives    Objects used to configure the save operation
	 * 
	 * @return A new {@code SaveEntitiesOut} object
	 */
	protected SaveEntitiesOut posSaveAll(final SaveEntitiesOut saveEntiesOut, final Object... directives) {
		return saveEntiesOut;
	}

	/**
	 * Method used to handle {@link #saveAll(Object, Object...) save} errors.
	 * 
	 * @param saveEntitiesIn The objects you want to save on the persistence
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
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SaveEntitiesOut saveAll(final SaveEntitiesIn saveEntitiesIn, final Object... directives) {
		return executeOperation(
				saveEntitiesIn, SAVE_ENTITIES, this::preSaveAll,
				this::posSaveAll, saveAllFunction::apply, this::errorSaveAll, directives);
	}

	/**
	 * Method used to change an entity before update it.
	 * 
	 * @param updateEntityIn The object to be changed
	 * @param directives     Objects used to configure the update operation
	 * 
	 * @return A new {@code UpdateEntityIn} object
	 */
	protected UpdateEntityIn preUpdate(final UpdateEntityIn updateEntityIn, final Object... directives) {
		return updateEntityIn;
	}

	/**
	 * Method used to change an entity after update it.
	 * 
	 * @param updateEntityOut The object to be changed
	 * @param directives      Objects used to configure the update operation
	 * 
	 * @return A new {@code UpdateEntityOut} object
	 */
	protected UpdateEntityOut posUpdate(final UpdateEntityOut updateEntityOut, final Object... directives) {
		return updateEntityOut;
	}

	/**
	 * Method used to handle update errors.
	 * 
	 * @param updateEntityIn The object tried to update
	 * @param exception      Exception thrown by update operation
	 * @param directives     Objects used to configure the update operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorUpdate(
			final UpdateEntityIn updateEntityIn,
			final Exception exception,
			final Object... directives) {
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UpdateEntityOut update(final UpdateEntityIn updateEntityIn, final Object... directives) {
		return executeOperation(
				updateEntityIn, UPDATE_ENTITY, this::preUpdate,
				this::posUpdate, updateFunction::apply, this::errorUpdate, directives);
	}

	/**
	 * Method used to change a group of entities before update it.
	 * 
	 * @param updateEntitiesIn The object to be changed
	 * @param directives       Objects used to configure the update all operation
	 * 
	 * @return A new {@code UpdateEntitiesIn} object
	 */
	protected UpdateEntitiesIn preUpdateAll(final UpdateEntitiesIn updateEntitiesIn, final Object... directives) {
		return updateEntitiesIn;
	}

	/**
	 * Method used to change a group of entities after update it.
	 * 
	 * @param updateEntitiesOut The object to be changed
	 * @param directives        Objects used to configure the update all operation
	 * 
	 * @return A new {@code UpdateEntitiesOut} object
	 */
	protected UpdateEntitiesOut posUpdateAll(final UpdateEntitiesOut updateEntitiesOut, final Object... directives) {
		return updateEntitiesOut;
	}

	/**
	 * Method used to handle update all errors.
	 * 
	 * @param updateEntitiesIn The object tried to update
	 * @param exception        Exception thrown by save operation
	 * @param directives       Objects used to configure the save operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorUpdateAll(
			final UpdateEntitiesIn updateEntitiesIn,
			final Exception exception,
			final Object... directives) {
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UpdateEntitiesOut updateAll(final UpdateEntitiesIn updateEntitiesIn, final Object... directives) {
		return executeOperation(
				updateEntitiesIn, UPDATE_ENTITIES, this::preUpdateAll,
				this::posUpdateAll, updateAllFunction::apply, this::errorUpdateAll, directives);
	}

	/**
	 * Method used to change an entity before delete it.
	 * 
	 * @param deleteEntityIn The object to be changed
	 * @param directives     Objects used to configure the delete operation
	 * 
	 * @return A new {@code DeleteEntityIn} object
	 */
	protected DeleteEntityIn preDelete(final DeleteEntityIn deleteEntityIn, final Object... directives) {
		return deleteEntityIn;
	}

	/**
	 * Method used to change an entity after delete it.
	 * 
	 * @param deleteEntityOut The object to be changed
	 * @param directives      Objects used to configure the delete operation
	 * 
	 * @return A new {@code DeleteEntityOut} object
	 */
	protected DeleteEntityOut posDelete(final DeleteEntityOut deleteEntityOut, final Object... directives) {
		return deleteEntityOut;
	}

	/**
	 * Method used to handle delete errors.
	 * 
	 * @param deleteEntityIn The object tried to delete
	 * @param exception      Exception thrown by delete operation
	 * @param directives     Objects used to configure the delete operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorDelete(
			final DeleteEntityIn deleteEntityIn,
			final Exception exception,
			final Object... directives) {
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteEntityOut delete(final DeleteEntityIn deleteEntityIn, final Object... directives) {
		return executeOperation(
				deleteEntityIn, DELETE_ENTITY, this::preDelete,
				this::posDelete, deleteFunction::apply, this::errorDelete, directives);
	}

	/**
	 * Method used to change a group of entities before delete it.
	 * 
	 * @param deleteEntitiesIn The object to be changed
	 * @param directives       Objects used to configure the delete all operation
	 * 
	 * @return A new {@code DeleteEntitiesIn} object
	 */
	protected DeleteEntitiesIn preDeleteAll(final DeleteEntitiesIn deleteEntitiesIn, final Object... directives) {
		return deleteEntitiesIn;
	}

	/**
	 * Method used to change a group of entities after delete it.
	 * 
	 * @param deleteEntitiesOut The object to be changed
	 * @param directives        Objects used to configure the delete all operation
	 * 
	 * @return A new {@code DeleteEntitiesOut} object
	 */
	protected DeleteEntitiesOut posDeleteAll(final DeleteEntitiesOut deleteEntitiesOut, final Object... directives) {
		return deleteEntitiesOut;
	}

	/**
	 * Method used to handle delete all errors.
	 * 
	 * @param deleteEntitiesIn The object tried to delete all
	 * @param exception        Exception thrown by delete all operation
	 * @param directives       Objects used to configure the delete all operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorDeleteAll(
			final DeleteEntitiesIn deleteEntitiesIn,
			final Exception exception,
			final Object... directives) {
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteEntitiesOut deleteAll(final DeleteEntitiesIn deleteEntitiesIn, final Object... directives) {
		return executeOperation(
				deleteEntitiesIn, DELETE_ENTITIES, this::preDeleteAll,
				this::posDeleteAll, deleteAllFunction::apply, this::errorDeleteAll, directives);
	}

	// ----------------------------------------------------------------------------------------------------------

	/**
	 * Method used to change an id object before delete it.
	 * 
	 * @param deleteIdIn The object to be changed
	 * @param directives Objects used to configure the delete by id operation
	 * 
	 * @return A new {@code DeleteIdIn} object
	 */
	protected DeleteIdIn preDeleteBy(final DeleteIdIn deleteIdIn, final Object... directives) {
		return deleteIdIn;
	}

	/**
	 * Method used to change an id object after delete it.
	 * 
	 * @param deleteIdOut The object to be changed
	 * @param directives  Objects used to configure the delete by id operation
	 * 
	 * @return A new {@code DeleteIdOut} object
	 */
	protected DeleteIdOut posDeleteBy(final DeleteIdOut deleteIdOut, final Object... directives) {
		return deleteIdOut;
	}

	/**
	 * Method used to handle delete by id errors.
	 * 
	 * @param deleteIdIn The object tried to delete
	 * @param exception  Exception thrown by delete by id operation
	 * @param directives Objects used to configure the delete operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorDeleteBy(
			final DeleteIdIn deleteIdIn,
			final Exception exception,
			final Object... directives) {
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteIdOut deleteBy(final DeleteIdIn deleteIdIn, final Object... directives) {
		return executeOperation(
				deleteIdIn, DELETE_BY_ID, this::preDeleteBy, this::posDeleteBy,
				deleteByIdFunction::apply, this::errorDeleteBy, directives);
	}

	/**
	 * Method used to change a group of ids before save it.
	 * 
	 * @param deleteIdsIn The object to be changed
	 * @param directives  Objects used to configure the delete by id operation
	 * 
	 * @return A new {@code DeleteIdsIn} object
	 */
	protected DeleteIdsIn preDeleteEntitiesBy(final DeleteIdsIn deleteIdsIn, final Object... directives) {
		return deleteIdsIn;
	}

	/**
	 * Method used to change a group of ids after save it.
	 * 
	 * @param deleteIdsOut The object to be changed
	 * @param directives   Objects used to configure the delete by id operation
	 * 
	 * @return A new {@code DeleteIdsOut} object
	 */
	protected DeleteIdsOut posDeleteEntitiesBy(final DeleteIdsOut deleteIdsOut, final Object... directives) {
		return deleteIdsOut;
	}

	/**
	 * Method used to handle delete by ids errors.
	 * 
	 * @param deleteIdsIn The object tried to delete
	 * @param exception   Exception thrown by delete by ids operation
	 * @param directives  Objects used to configure the delete operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorDeleteAllBy(
			final DeleteIdsIn deleteIdsIn,
			final Exception exception,
			final Object... directives) {
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteIdsOut deleteAllBy(final DeleteIdsIn deleteIdsIn, final Object... directives) {
		return executeOperation(
				deleteIdsIn, DELETE_BY_IDS, this::preDeleteEntitiesBy, this::posDeleteEntitiesBy,
				deleteAllByIdFunction::apply, this::errorDeleteAllBy, directives);
	}
}