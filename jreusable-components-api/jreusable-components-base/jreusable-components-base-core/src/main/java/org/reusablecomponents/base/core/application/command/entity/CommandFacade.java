package org.reusablecomponents.base.core.application.command.entity;

import static com.google.common.base.Preconditions.checkNotNull;

import org.reusablecomponents.base.core.application.command.function.operation.delete.DeleteAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.DeleteByIdFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.DeleteByIdsFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.DeleteFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.ErrorDeleteAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.ErrorDeleteByIdFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.ErrorDeleteByIdsFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.ErrorDeleteFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.PosDeleteAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.PosDeleteByIdFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.PosDeleteByIdsFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.PosDeleteFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.PreDeleteAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.PreDeleteByIdFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.PreDeleteByIdsFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.PreDeleteFunction;
import org.reusablecomponents.base.core.application.command.function.operation.save.SaveAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.save.SaveFunction;
import org.reusablecomponents.base.core.application.command.function.operation.update.ErrorUpdateAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.update.ErrorUpdateFunction;
import org.reusablecomponents.base.core.application.command.function.operation.update.PosUpdateAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.update.PosUpdateFunction;
import org.reusablecomponents.base.core.application.command.function.operation.update.PreUpdateAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.update.PreUpdateFunction;
import org.reusablecomponents.base.core.application.command.function.operation.update.UpdateAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.update.UpdateFunction;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

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
	protected final SaveFunction<SaveEntityIn, SaveEntityOut> saveFunction;

	/**
	 * Function that executes the save all (bunch save) operation in the
	 * {@link #saveAll(Object, Object...) saveAll} method
	 */
	protected final SaveAllFunction<SaveEntitiesIn, SaveEntitiesOut> saveAllFunction;

	/**
	 * Function that executes the update operation in the
	 * {@link #update(Object, Object...) update} method
	 */
	protected final UpdateFunction<UpdateEntityIn, UpdateEntityOut> updateFunction;

	/**
	 * Function that executes the update all (bunch update) operation in the
	 * {@link #updateAll(Object, Object...) updateAll} method
	 */
	protected final UpdateAllFunction<UpdateEntitiesIn, UpdateEntitiesOut> updateAllFunction;

	/**
	 * Function that executes the delete operation in the
	 * {@link #delete(Object, Object...) delete} method
	 */
	protected final DeleteFunction<DeleteEntityIn, DeleteEntityOut> deleteFunction;

	/**
	 * Function that executes the delete all (bunch delete) operation in the
	 * {@link #deleteAll(Object, Object...) deleteAll} method
	 */
	protected final DeleteAllFunction<DeleteEntitiesIn, DeleteEntitiesOut> deleteAllFunction;

	/**
	 * Function that executes the delete by id operation in the
	 * {@link #deleteBy(Object, Object...) deleteBy} method
	 */
	protected final DeleteByIdFunction<DeleteIdIn, DeleteIdOut> deleteByIdFunction;

	/**
	 * Function that executes the delete all by id (bunch delete by id) operation in
	 * the {@link #deleteAllBy(Object, Object...) deleteAllBy} method
	 */
	protected final DeleteByIdsFunction<DeleteIdsIn, DeleteIdsOut> deleteByIdsFunction;

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
		this.deleteByIdsFunction = builder.deleteByIdsFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SaveEntityOut save(final SaveEntityIn saveEntityIn, final Object... directives) {
		LOGGER.debug("Executing default save, saveEntityIn {}, directives {}", saveEntityIn, directives);

		checkNotNull(saveEntityIn, NON_NULL_ENTITY_MSG, getEntityClazz().getSimpleName());

		final var saveEntityOut = execute(
				saveEntityIn, getPreSaveFunction(), getSaveFunction(),
				getPosSaveFunction(), getErrorSaveFunction(), directives);

		LOGGER.debug("Default save executed, saveEntityOut {}, directives {}", saveEntityOut, directives);
		return saveEntityOut;
	}

	@NotNull
	protected SaveFunction<SaveEntityIn, SaveEntityOut> getSaveFunction() {
		LOGGER.debug("Returning save function {}", saveFunction.getName());
		return saveFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SaveEntitiesOut saveAll(final SaveEntitiesIn saveEntitiesIn, final Object... directives) {
		LOGGER.debug("Executing default saveAll, saveEntitiesIn {}, directives {}", saveEntitiesIn, directives);

		checkNotNull(saveEntitiesIn, NON_NULL_GROUP_OF_ENTITIES_MSG, getEntityClazz().getSimpleName());

		final var saveEntitiesOut = execute(
				saveEntitiesIn, getPreSaveAllFunction(), getSaveAllFunction(),
				getPosSaveAllFunction(), getErrorSaveAllFunction(), directives);

		LOGGER.debug("Default saveAll executed, saveEntitiesOut {}, directives {}", saveEntitiesOut, directives);
		return saveEntitiesOut;
	}

	@NotNull
	protected SaveAllFunction<SaveEntitiesIn, SaveEntitiesOut> getSaveAllFunction() {
		LOGGER.debug("Returning save all function {}", saveAllFunction.getName());
		return saveAllFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UpdateEntityOut update(final UpdateEntityIn updateEntityIn, final Object... directives) {
		LOGGER.debug("Executing default update, updateEntityIn {}, directives {} ", updateEntityIn, directives);

		checkNotNull(updateEntityIn, NON_NULL_ENTITY_MSG, getEntityClazz().getSimpleName());

		final var updateEntityOut = execute(
				updateEntityIn, getPreUpdateFunction(), getUpdateFunction(),
				getPosUpdateFunction(), getErrorUpdateFunction(), directives);

		LOGGER.debug("Default update executed, updateEntityOut {}, directives {} ", updateEntityOut, directives);
		return updateEntityOut;
	}

	@NotNull
	protected PreUpdateFunction<UpdateEntityIn> getPreUpdateFunction() {
		LOGGER.debug("Returning pre update function {}", preUpdateFunction.getName());
		return preUpdateFunction;
	}

	@NotNull
	protected UpdateFunction<UpdateEntityIn, UpdateEntityOut> getUpdateFunction() {
		LOGGER.debug("Returning update function {}", updateFunction.getName());
		return updateFunction;
	}

	@NotNull
	protected PosUpdateFunction<UpdateEntityOut> getPosUpdateFunction() {
		LOGGER.debug("Returning pos update function {}", posUpdateFunction.getName());
		return posUpdateFunction;
	}

	@NotNull
	protected ErrorUpdateFunction<UpdateEntityIn> getErrorUpdateFunction() {
		LOGGER.debug("Returning error update function {}", errorUpdateFunction.getName());
		return errorUpdateFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UpdateEntitiesOut updateAll(final UpdateEntitiesIn updateEntitiesIn, final Object... directives) {
		LOGGER.debug("Executing default updateAll, updateEntityIn {}, directives {} ", updateEntitiesIn, directives);

		checkNotNull(updateEntitiesIn, NON_NULL_GROUP_OF_ENTITIES_MSG, getEntityClazz().getSimpleName());

		final var updateEntitiesOut = execute(
				updateEntitiesIn, getPreUpdateAllFunction(), getUpdateAllFunction(),
				getPosUpdateAllFunction(), getErrorUpdateAllFunction(), directives);

		LOGGER.debug("Default updateAll executed, updateEntitiesOut {}, directives {} ", updateEntitiesOut, directives);
		return updateEntitiesOut;
	}

	@NotNull
	protected PreUpdateAllFunction<UpdateEntitiesIn> getPreUpdateAllFunction() {
		LOGGER.debug("Returning pre update all function {}", preUpdateAllFunction.getName());
		return preUpdateAllFunction;
	}

	@NotNull
	protected UpdateAllFunction<UpdateEntitiesIn, UpdateEntitiesOut> getUpdateAllFunction() {
		LOGGER.debug("Returning update all function {}", updateAllFunction.getName());
		return updateAllFunction;
	}

	@NotNull
	protected PosUpdateAllFunction<UpdateEntitiesOut> getPosUpdateAllFunction() {
		LOGGER.debug("Returning pos update all function {}", posUpdateAllFunction.getName());
		return posUpdateAllFunction;
	}

	@NotNull
	protected ErrorUpdateAllFunction<UpdateEntitiesIn> getErrorUpdateAllFunction() {
		LOGGER.debug("Returning error update all function {}", errorUpdateAllFunction.getName());
		return errorUpdateAllFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteEntityOut delete(final DeleteEntityIn deleteEntityIn, final Object... directives) {
		LOGGER.debug("Executing default delete, deleteEntityIn {}, directives {}", deleteEntityIn, directives);

		checkNotNull(deleteEntityIn, NON_NULL_ENTITY_MSG, getEntityClazz().getSimpleName());

		final var deleteEntityOut = execute(
				deleteEntityIn, getPreDeleteFunction(), getDeleteFunction(),
				getPosDeleteFunction(), getErrorDeleteFunction(), directives);

		LOGGER.debug("Default delete executed, deleteEntityOut {}, directives {}", deleteEntityOut, directives);
		return deleteEntityOut;
	}

	@NotNull
	protected PreDeleteFunction<DeleteEntityIn> getPreDeleteFunction() {
		LOGGER.debug("Returning pre delete function {}", preDeleteFunction.getName());
		return preDeleteFunction;
	}

	@NotNull
	protected DeleteFunction<DeleteEntityIn, DeleteEntityOut> getDeleteFunction() {
		LOGGER.debug("Returning delete function {}", deleteFunction.getName());
		return deleteFunction;
	}

	@NotNull
	protected PosDeleteFunction<DeleteEntityOut> getPosDeleteFunction() {
		LOGGER.debug("Returning pos delete function {}", posDeleteFunction.getName());
		return posDeleteFunction;
	}

	@NotNull
	protected ErrorDeleteFunction<DeleteEntityIn> getErrorDeleteFunction() {
		LOGGER.debug("Returning error delete function {}", errorDeleteFunction.getName());
		return errorDeleteFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteEntitiesOut deleteAll(final DeleteEntitiesIn deleteEntitiesIn, final Object... directives) {
		LOGGER.debug("Executing default deleteAll, deleteEntitiesIn {}, directives {} ", deleteEntitiesIn, directives);

		checkNotNull(deleteEntitiesIn, NON_NULL_GROUP_OF_ENTITIES_MSG, getEntityClazz().getSimpleName());

		final var deleteEntitiesOut = execute(
				deleteEntitiesIn, getPreDeleteAllFunction(), getDeleteAllFunction(),
				getPosDeleteAllFunction(), getErrorDeleteAllFunction(), directives);

		LOGGER.debug("Default deleteAll executed, deleteEntitiesOut {}, directives {} ", deleteEntitiesOut, directives);
		return deleteEntitiesOut;
	}

	@NotNull
	protected PreDeleteAllFunction<DeleteEntitiesIn> getPreDeleteAllFunction() {
		LOGGER.debug("Returning pre delete all function {}", preDeleteAllFunction.getName());
		return preDeleteAllFunction;
	}

	@NotNull
	protected DeleteAllFunction<DeleteEntitiesIn, DeleteEntitiesOut> getDeleteAllFunction() {
		LOGGER.debug("Returning delete all function {}", deleteAllFunction.getName());
		return deleteAllFunction;
	}

	@NotNull
	protected PosDeleteAllFunction<DeleteEntitiesOut> getPosDeleteAllFunction() {
		LOGGER.debug("Returning pos delete all function {}", posDeleteAllFunction.getName());
		return posDeleteAllFunction;
	}

	@NotNull
	protected ErrorDeleteAllFunction<DeleteEntitiesIn> getErrorDeleteAllFunction() {
		LOGGER.debug("Returning error delete all function {}", errorDeleteAllFunction.getName());
		return errorDeleteAllFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteIdOut deleteBy(final DeleteIdIn deleteIdIn, final Object... directives) {
		LOGGER.debug("Executing default deleteBy, deleteIdIn {}, directives {} ", deleteIdIn, directives);

		checkNotNull(deleteIdIn, NON_NULL_ID_MSG, getEntityClazz().getSimpleName());

		final var deleteIdOut = execute(
				deleteIdIn, getPreDeleteByIdFunction(), getDeleteByIdFunction(),
				getPosDeleteByIdFunction(), getErrorDeleteByIdFunction(), directives);

		LOGGER.debug("Default deleteBy executed, deleteIdOut {}, directives {} ", deleteIdOut, directives);
		return deleteIdOut;
	}

	@NotNull
	protected PreDeleteByIdFunction<DeleteIdIn> getPreDeleteByIdFunction() {
		LOGGER.debug("Returning pre delete by id function {}", preDeleteByIdFunction.getName());
		return preDeleteByIdFunction;
	}

	@NotNull
	protected DeleteByIdFunction<DeleteIdIn, DeleteIdOut> getDeleteByIdFunction() {
		LOGGER.debug("Returning delete by id function {}", deleteByIdFunction.getName());
		return deleteByIdFunction;
	}

	@NotNull
	protected PosDeleteByIdFunction<DeleteIdOut> getPosDeleteByIdFunction() {
		LOGGER.debug("Returning pos delete by id function {}", posDeleteByIdFunction.getName());
		return posDeleteByIdFunction;
	}

	@NotNull
	protected ErrorDeleteByIdFunction<DeleteIdIn> getErrorDeleteByIdFunction() {
		LOGGER.debug("Returning error delete by id function {}", errorDeleteByIdFunction.getName());
		return errorDeleteByIdFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteIdsOut deleteAllBy(final DeleteIdsIn deleteIdsIn, final Object... directives) {
		LOGGER.debug("Executing default deleteAllBy, deleteIdsIn {}, directives {} ", deleteIdsIn, directives);

		checkNotNull(deleteIdsIn, NON_NULL_GROUP_OF_IDS_MSG, getEntityClazz().getSimpleName());

		final var deleteIdsOut = execute(
				deleteIdsIn, getPreDeleteByIdsFunction(), getDeleteByIdsFunction(),
				getPosDeleteByIdsFunction(), getErrorDeleteByIdsFunction(), directives);

		LOGGER.debug("Default deleteAllBy executed, deleteIdsOut {}, directives {} ", deleteIdsOut, directives);
		return deleteIdsOut;
	}

	@NotNull
	protected PreDeleteByIdsFunction<DeleteIdsIn> getPreDeleteByIdsFunction() {
		LOGGER.debug("Returning pre delete by ids function {}", preDeleteByIdsFunction.getName());
		return preDeleteByIdsFunction;
	}

	@NotNull
	protected DeleteByIdsFunction<DeleteIdsIn, DeleteIdsOut> getDeleteByIdsFunction() {
		LOGGER.debug("Returning delete by ids function {}", deleteByIdsFunction.getName());
		return deleteByIdsFunction;
	}

	@NotNull
	protected PosDeleteByIdsFunction<DeleteIdsOut> getPosDeleteByIdsFunction() {
		LOGGER.debug("Returning pos delete by ids function {}", posDeleteByIdsFunction.getName());
		return posDeleteByIdsFunction;
	}

	@NotNull
	protected ErrorDeleteByIdsFunction<DeleteIdsIn> getErrorDeleteByIdsFunction() {
		LOGGER.debug("Returning error delete by ids function {}", errorDeleteByIdsFunction.getName());
		return errorDeleteByIdsFunction;
	}
}