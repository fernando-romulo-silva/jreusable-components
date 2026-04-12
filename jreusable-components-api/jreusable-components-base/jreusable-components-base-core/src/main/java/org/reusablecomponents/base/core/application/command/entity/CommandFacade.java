package org.reusablecomponents.base.core.application.command.entity;

import static com.google.common.base.Preconditions.checkNotNull;

import org.reusablecomponents.base.core.application.command.entity.function.delete.DeleteFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_all.DeleteAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_by_id.DeleteByIdFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_by_id_all.DeleteByIdsFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save.SaveFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save_all.SaveAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.update.UpdateFunction;
import org.reusablecomponents.base.core.application.command.entity.function.update_all.UpdateAllFunction;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * The default <code>InterfaceCommandFacade</code>'s implementation.
 * 
 * <p>
 * This class provides default implementations for the basic command operations:
 * <ul>
 * <li>save</li>
 * <li>save all</li>
 * <li>update</li>
 * <li>update all</li>
 * <li>delete</li>
 * <li>delete all</li>
 * <li>delete by id</li>
 * <li>delete by ids</li>
 * </ul>
 * <p>
 * 
 * @param <Entity>            The entity type
 * @param <Id>                The entity id type
 * @param <SaveEntityIn>      The input type for the save operation
 * @param <SaveEntityOut>     The output type for the save operation
 * 
 * @param <SaveEntitiesIn>    The input type for the save all operation (bulk
 *                            version)
 * @param <SaveEntitiesOut>   The output type for the save all operation (bulk
 *                            version)
 * 
 * @param <UpdateEntityIn>    The input type for the update operation
 * @param <UpdateEntityOut>   The output type for the update operation
 * 
 * @param <UpdateEntitiesIn>  The input type for the update all operation (bulk
 *                            version)
 * @param <UpdateEntitiesOut> The output type for the update all operation (bulk
 *                            version)
 * @param <DeleteEntityIn>    The input type for the delete operation
 * @param <DeleteEntityOut>   The output type for the delete operation
 * 
 * @param <DeleteEntitiesIn>  The input type for the delete all operation (bulk
 *                            version)
 * @param <DeleteEntitiesOut> The output type for the delete all operation (bulk
 *                            version)
 * 
 * @param <DeleteIdIn>        The input type for the delete by id operation
 * @param <DeleteIdOut>       The output type for the delete by id operation
 * 
 * @param <DeleteIdsIn>       The input type for the delete by ids operation
 *                            (bulk
 *                            version)
 * @param <DeleteIdsOut>      The output type for the delete by ids operation
 *                            (bulk
 *                            version)
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 * 
 * @see AbstractCommandFacade
 * @see InterfaceCommandFacade
 */
public non-sealed class CommandFacade<Entity extends AbstractEntity<Id>, Id, // basic
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
	 * 
	 * @see SaveFunction
	 */
	protected final SaveFunction<SaveEntityIn, SaveEntityOut> saveFunction;

	/**
	 * Function that executes the save all (bunch save) operation in the
	 * {@link #saveAll(Object, Object...) saveAll} method
	 * 
	 * @see SaveAllFunction
	 */
	protected final SaveAllFunction<SaveEntitiesIn, SaveEntitiesOut> saveAllFunction;

	/**
	 * Function that executes the update operation in the
	 * {@link #update(Object, Object...) update} method
	 * 
	 * @see UpdateFunction
	 */
	protected final UpdateFunction<UpdateEntityIn, UpdateEntityOut> updateFunction;

	/**
	 * Function that executes the update all (bunch update) operation in the
	 * {@link #updateAll(Object, Object...) updateAll} method
	 * 
	 * @see UpdateAllFunction
	 */
	protected final UpdateAllFunction<UpdateEntitiesIn, UpdateEntitiesOut> updateAllFunction;

	/**
	 * Function that executes the delete operation in the
	 * {@link #delete(Object, Object...) delete} method
	 * 
	 * @see DeleteFunction
	 */
	protected final DeleteFunction<DeleteEntityIn, DeleteEntityOut> deleteFunction;

	/**
	 * Function that executes the delete all (bunch delete) operation in the
	 * {@link #deleteAll(Object, Object...) deleteAll} method
	 * 
	 * @see DeleteAllFunction
	 */
	protected final DeleteAllFunction<DeleteEntitiesIn, DeleteEntitiesOut> deleteAllFunction;

	/**
	 * Function that executes the delete by id operation in the
	 * {@link #deleteBy(Object, Object...) deleteBy} method
	 * 
	 * @see DeleteByIdFunction
	 */
	protected final DeleteByIdFunction<DeleteIdIn, DeleteIdOut> deleteByIdFunction;

	/**
	 * Function that executes the delete all by id (bunch delete by id) operation in
	 * the {@link #deleteAllBy(Object, Object...) deleteAllBy} method
	 * 
	 * @see DeleteByIdsFunction
	 */
	protected final DeleteByIdsFunction<DeleteIdsIn, DeleteIdsOut> deleteByIdsFunction;

	/**
	 * Default constructor, used by the builder to construct this class.
	 * 
	 * @param builder Object in charge to construct this one, it cannot be null
	 * @throws NullPointerException if the builder is null
	 * 
	 * @see CommandFacadeBuilder
	 */
	protected CommandFacade(
			final CommandFacadeBuilder<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> builder) {
		LOGGER.atDebug().log("Creating Command Facade with builder {}", builder);
		super(builder);

		this.saveFunction = builder.saveFunction;
		this.saveAllFunction = builder.saveAllFunction;
		this.updateFunction = builder.updateFunction;
		this.updateAllFunction = builder.updateAllFunction;

		this.deleteFunction = builder.deleteFunction;
		this.deleteAllFunction = builder.deleteAllFunction;
		this.deleteByIdFunction = builder.deleteByIdFunction;
		this.deleteByIdsFunction = builder.deleteByIdsFunction;

		LOGGER.atDebug().log("Command Facade created {}", this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SaveEntityOut save(final SaveEntityIn saveEntityIn, final Object... directives) {
		LOGGER.atDebug().log("Executing default save, saveEntityIn {}, directives {}", saveEntityIn, directives);

		checkNotNull(saveEntityIn, NON_NULL_ENTITY_MSG, getEntityClazz().getSimpleName());

		final var saveEntityOut = execute(
				saveEntityIn, getPreSaveFunction(), getSaveFunction(),
				getPosSaveFunction(), getErrorSaveFunction(), directives);

		LOGGER.atDebug().log("Default save executed, saveEntityOut {}, directives {}", saveEntityOut, directives);
		return saveEntityOut;
	}

	@NotNull
	protected SaveFunction<SaveEntityIn, SaveEntityOut> getSaveFunction() {
		LOGGER.atDebug().log("Returning save function {}", saveFunction.getName());
		return saveFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SaveEntitiesOut saveAll(final SaveEntitiesIn saveEntitiesIn, final Object... directives) {
		LOGGER.atDebug().log("Executing default saveAll, saveEntitiesIn {}, directives {}", saveEntitiesIn, directives);

		checkNotNull(saveEntitiesIn, NON_NULL_GROUP_OF_ENTITIES_MSG, getEntityClazz().getSimpleName());

		final var saveEntitiesOut = execute(
				saveEntitiesIn, getPreSaveAllFunction(), getSaveAllFunction(),
				getPosSaveAllFunction(), getErrorSaveAllFunction(), directives);

		LOGGER.atDebug().log("Default saveAll executed, saveEntitiesOut {}, directives {}", saveEntitiesOut,
				directives);
		return saveEntitiesOut;
	}

	@NotNull
	protected SaveAllFunction<SaveEntitiesIn, SaveEntitiesOut> getSaveAllFunction() {
		LOGGER.atDebug().log("Returning save all function {}", saveAllFunction.getName());
		return saveAllFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UpdateEntityOut update(final UpdateEntityIn updateEntityIn, final Object... directives) {
		LOGGER.atDebug().log("Executing default update, updateEntityIn {}, directives {} ", updateEntityIn, directives);

		checkNotNull(updateEntityIn, NON_NULL_ENTITY_MSG, getEntityClazz().getSimpleName());

		final var updateEntityOut = execute(
				updateEntityIn, getPreUpdateFunction(), getUpdateFunction(),
				getPosUpdateFunction(), getErrorUpdateFunction(), directives);

		LOGGER.atDebug().log("Default update executed, updateEntityOut {}, directives {} ", updateEntityOut,
				directives);
		return updateEntityOut;
	}

	@NotNull
	protected UpdateFunction<UpdateEntityIn, UpdateEntityOut> getUpdateFunction() {
		LOGGER.atDebug().log("Returning update function {}", updateFunction.getName());
		return updateFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UpdateEntitiesOut updateAll(final UpdateEntitiesIn updateEntitiesIn, final Object... directives) {
		LOGGER.atDebug().log("Executing default updateAll, updateEntityIn {}, directives {} ", updateEntitiesIn,
				directives);

		checkNotNull(updateEntitiesIn, NON_NULL_GROUP_OF_ENTITIES_MSG, getEntityClazz().getSimpleName());

		final var updateEntitiesOut = execute(
				updateEntitiesIn, getPreUpdateAllFunction(), getUpdateAllFunction(),
				getPosUpdateAllFunction(), getErrorUpdateAllFunction(), directives);

		LOGGER.atDebug().log("Default updateAll executed, updateEntitiesOut {}, directives {} ", updateEntitiesOut,
				directives);
		return updateEntitiesOut;
	}

	@NotNull
	protected UpdateAllFunction<UpdateEntitiesIn, UpdateEntitiesOut> getUpdateAllFunction() {
		LOGGER.atDebug().log("Returning update all function {}", updateAllFunction.getName());
		return updateAllFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteEntityOut delete(final DeleteEntityIn deleteEntityIn, final Object... directives) {
		LOGGER.atDebug().log("Executing default delete, deleteEntityIn {}, directives {}", deleteEntityIn, directives);

		checkNotNull(deleteEntityIn, NON_NULL_ENTITY_MSG, getEntityClazz().getSimpleName());

		final var deleteEntityOut = execute(
				deleteEntityIn, getPreDeleteFunction(), getDeleteFunction(),
				getPosDeleteFunction(), getErrorDeleteFunction(), directives);

		LOGGER.atDebug().log("Default delete executed, deleteEntityOut {}, directives {}", deleteEntityOut, directives);
		return deleteEntityOut;
	}

	@NotNull
	protected DeleteFunction<DeleteEntityIn, DeleteEntityOut> getDeleteFunction() {
		LOGGER.atDebug().log("Returning delete function {}", deleteFunction.getName());
		return deleteFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteEntitiesOut deleteAll(final DeleteEntitiesIn deleteEntitiesIn, final Object... directives) {
		LOGGER.atDebug().log("Executing default deleteAll, deleteEntitiesIn {}, directives {} ", deleteEntitiesIn,
				directives);

		checkNotNull(deleteEntitiesIn, NON_NULL_GROUP_OF_ENTITIES_MSG, getEntityClazz().getSimpleName());

		final var deleteEntitiesOut = execute(
				deleteEntitiesIn, getPreDeleteAllFunction(), getDeleteAllFunction(),
				getPosDeleteAllFunction(), getErrorDeleteAllFunction(), directives);

		LOGGER.atDebug().log("Default deleteAll executed, deleteEntitiesOut {}, directives {} ", deleteEntitiesOut,
				directives);
		return deleteEntitiesOut;
	}

	@NotNull
	protected DeleteAllFunction<DeleteEntitiesIn, DeleteEntitiesOut> getDeleteAllFunction() {
		LOGGER.atDebug().log("Returning delete all function {}", deleteAllFunction.getName());
		return deleteAllFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteIdOut deleteBy(final DeleteIdIn deleteIdIn, final Object... directives) {
		LOGGER.atDebug().log("Executing default deleteBy, deleteIdIn {}, directives {} ", deleteIdIn, directives);

		checkNotNull(deleteIdIn, NON_NULL_ID_MSG, getEntityClazz().getSimpleName());

		final var deleteIdOut = execute(
				deleteIdIn, getPreDeleteByIdFunction(), getDeleteByIdFunction(),
				getPosDeleteByIdFunction(), getErrorDeleteByIdFunction(), directives);

		LOGGER.atDebug().log("Default deleteBy executed, deleteIdOut {}, directives {} ", deleteIdOut, directives);
		return deleteIdOut;
	}

	@NotNull
	protected DeleteByIdFunction<DeleteIdIn, DeleteIdOut> getDeleteByIdFunction() {
		LOGGER.atDebug().log("Returning delete by id function {}", deleteByIdFunction.getName());
		return deleteByIdFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteIdsOut deleteAllBy(final DeleteIdsIn deleteIdsIn, final Object... directives) {
		LOGGER.atDebug().log("Executing default deleteAllBy, deleteIdsIn {}, directives {} ", deleteIdsIn, directives);

		checkNotNull(deleteIdsIn, NON_NULL_GROUP_OF_IDS_MSG, getEntityClazz().getSimpleName());

		final var deleteIdsOut = execute(
				deleteIdsIn, getPreDeleteByIdsFunction(), getDeleteByIdsFunction(),
				getPosDeleteByIdsFunction(), getErrorDeleteByIdsFunction(), directives);

		LOGGER.atDebug().log("Default deleteAllBy executed, deleteIdsOut {}, directives {} ", deleteIdsOut, directives);
		return deleteIdsOut;
	}

	@NotNull
	protected DeleteByIdsFunction<DeleteIdsIn, DeleteIdsOut> getDeleteByIdsFunction() {
		LOGGER.atDebug().log("Returning delete by ids function {}", deleteByIdsFunction.getName());
		return deleteByIdsFunction;
	}
}