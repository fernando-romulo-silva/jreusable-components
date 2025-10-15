package org.reusablecomponents.base.core.application.command.entity;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.DELETE_BY_ID;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.DELETE_BY_IDS;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.DELETE_ENTITIES;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.DELETE_ENTITY;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.SAVE_ENTITIES;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.SAVE_ENTITY;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.UPDATE_ENTITIES;
import static org.reusablecomponents.base.core.infra.util.operation.CommandOperation.UPDATE_ENTITY;

import java.util.function.BiFunction;

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
		extends AbstractCommandFacade<Entity, Id, // basic
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
		// Interface command facade
		implements InterfaceCommandFacade<Entity, Id, // basic
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
				DeleteIdsIn, DeleteIdsOut> { // ids

	private static final String NON_NULL_GROUP_OF_ENTITIES_MSG = "Please pass a non-null group of %s entities";

	private static final String NON_NULL_ENTITY_MSG = "Please pass a non-null %s entity";

	private static final String NON_NULL_ID_MSG = "Please pass a non-null %s id";

	private static final String NON_NULL_GROUP_OF_IDS_MSG = "Please pass a non-null group of %s ids";

	private static final Logger LOGGER = LoggerFactory.getLogger(CommandFacade.class);

	/**
	 * Function that executes the save operation in the
	 * {@link #save(Object, Object...) save} method
	 */
	protected final BiFunction<SaveEntityIn, Object[], SaveEntityOut> saveFunction;

	/**
	 * Function that executes the save all (bunch save) operation in the
	 * {@link #saveAll(Object, Object...) saveAll} method
	 */
	protected final BiFunction<SaveEntitiesIn, Object[], SaveEntitiesOut> saveAllFunction;

	/**
	 * Function that executes the update operation in the
	 * {@link #update(Object, Object...) update} method
	 */
	protected final BiFunction<UpdateEntityIn, Object[], UpdateEntityOut> updateFunction;

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
	 * {@inheritDoc}
	 */
	@Override
	public SaveEntityOut save(final SaveEntityIn saveEntityIn, final Object... directives) {
		LOGGER.debug("Executing default save, saveEntityIn {}, directives {} ", saveEntityIn, directives);

		checkNotNull(saveEntityIn, NON_NULL_ENTITY_MSG, getEntityClazz().getSimpleName());

		final var saveEntityOut = execute(
				saveEntityIn, SAVE_ENTITY, this::preSave,
				this::posSave, saveFunction::apply, this::errorSave, directives);

		LOGGER.debug("Default save executed, saveEntityOut {}, directives {} ", saveEntityOut, directives);
		return saveEntityOut;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SaveEntitiesOut saveAll(final SaveEntitiesIn saveEntitiesIn, final Object... directives) {
		LOGGER.debug("Executing default saveAll, saveEntitiesIn {}, directives {} ", saveEntitiesIn, directives);

		checkNotNull(saveEntitiesIn, NON_NULL_GROUP_OF_ENTITIES_MSG, getEntityClazz().getSimpleName());

		final var saveEntitiesOut = execute(
				saveEntitiesIn, SAVE_ENTITIES, this::preSaveAll,
				this::posSaveAll, saveAllFunction::apply, this::errorSaveAll, directives);

		LOGGER.debug("Default saveAll executed, saveEntitiesOut {}, directives {} ", saveEntitiesOut, directives);
		return saveEntitiesOut;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UpdateEntityOut update(final UpdateEntityIn updateEntityIn, final Object... directives) {
		LOGGER.debug("Executing default update, updateEntityIn {}, directives {} ", updateEntityIn, directives);

		checkNotNull(updateEntityIn, NON_NULL_ENTITY_MSG, getEntityClazz().getSimpleName());

		final var updateEntityOut = execute(
				updateEntityIn, UPDATE_ENTITY, this::preUpdate,
				this::posUpdate, updateFunction::apply, this::errorUpdate, directives);

		LOGGER.debug("Default update executed, updateEntityOut {}, directives {} ", updateEntityOut, directives);
		return updateEntityOut;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UpdateEntitiesOut updateAll(final UpdateEntitiesIn updateEntitiesIn, final Object... directives) {
		LOGGER.debug("Executing default updateAll, updateEntityIn {}, directives {} ", updateEntitiesIn, directives);

		checkNotNull(updateEntitiesIn, NON_NULL_GROUP_OF_ENTITIES_MSG, getEntityClazz().getSimpleName());

		final var updateEntitiesOut = execute(
				updateEntitiesIn, UPDATE_ENTITIES, this::preUpdateAll,
				this::posUpdateAll, updateAllFunction::apply, this::errorUpdateAll, directives);

		LOGGER.debug("Default updateAll executed, updateEntitiesOut {}, directives {} ", updateEntitiesOut, directives);
		return updateEntitiesOut;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteEntityOut delete(final DeleteEntityIn deleteEntityIn, final Object... directives) {
		LOGGER.debug("Executing default delete, deleteEntityIn {}, directives {}", deleteEntityIn, directives);

		checkNotNull(deleteEntityIn, NON_NULL_ENTITY_MSG, getEntityClazz().getSimpleName());

		final var deleteEntityOut = execute(
				deleteEntityIn, DELETE_ENTITY,
				this::preDelete, this::posDelete,
				deleteFunction::apply, this::errorDelete,
				directives);

		LOGGER.debug("Default delete executed, deleteEntityOut {}, directives {}", deleteEntityOut, directives);
		return deleteEntityOut;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteEntitiesOut deleteAll(final DeleteEntitiesIn deleteEntitiesIn, final Object... directives) {
		LOGGER.debug("Executing default deleteAll, deleteEntitiesIn {}, directives {} ", deleteEntitiesIn, directives);

		checkNotNull(deleteEntitiesIn, NON_NULL_GROUP_OF_ENTITIES_MSG, getEntityClazz().getSimpleName());

		final var deleteEntitiesOut = execute(
				deleteEntitiesIn, DELETE_ENTITIES, this::preDeleteAll,
				this::posDeleteAll, deleteAllFunction::apply, this::errorDeleteAll, directives);

		LOGGER.debug("Default deleteAll executed, deleteEntitiesOut {}, directives {} ", deleteEntitiesOut, directives);
		return deleteEntitiesOut;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteIdOut deleteBy(final DeleteIdIn deleteIdIn, final Object... directives) {
		LOGGER.debug("Executing default deleteBy, deleteIdIn {}, directives {} ", deleteIdIn, directives);

		checkNotNull(deleteIdIn, NON_NULL_ID_MSG, getEntityClazz().getSimpleName());

		final var deleteIdOut = execute(
				deleteIdIn, DELETE_BY_ID, this::preDeleteBy, this::posDeleteBy,
				deleteByIdFunction::apply, this::errorDeleteBy, directives);

		LOGGER.debug("Default deleteBy executed, deleteIdOut {}, directives {} ", deleteIdOut, directives);
		return deleteIdOut;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteIdsOut deleteAllBy(final DeleteIdsIn deleteIdsIn, final Object... directives) {
		LOGGER.debug("Executing default deleteAllBy, deleteIdsIn {}, directives {} ", deleteIdsIn, directives);

		checkNotNull(deleteIdsIn, NON_NULL_GROUP_OF_IDS_MSG, getEntityClazz().getSimpleName());

		final var deleteIdsOut = execute(
				deleteIdsIn, DELETE_BY_IDS, this::preDeleteAllBy, this::posDeleteAllBy,
				deleteAllByIdFunction::apply, this::errorDeleteAllBy, directives);

		LOGGER.debug("Default deleteAllBy executed, deleteIdsOut {}, directives {} ", deleteIdsOut, directives);
		return deleteIdsOut;
	}
}