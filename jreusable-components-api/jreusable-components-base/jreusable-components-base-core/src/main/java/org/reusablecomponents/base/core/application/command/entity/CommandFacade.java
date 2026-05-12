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
import org.reusablecomponents.base.core.domain.InterfaceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * This class is responsible for implementing command operations defined in the
 * {@link InterfaceCommandFacade} interface using a functional approach. <br />
 * 
 * <p>
 * For each query operation, this class use a function:
 * <ul>
 * <li>{@link #saveFunction} function used to execute the
 * {@link #save(Object, Object...)} method.
 * <li>{@link #saveAllFunction} function used to execute the
 * {@link #saveAll(Object, Object...)} method.
 * <li>{@link #updateFunction} function used to execute the
 * {@link #update(Object, Object...)} method.
 * <li>{@link #updateAllFunction} function used to execute the
 * {@link #updateAll(Object, Object...)} method.
 * <li>{@link #deleteFunction} function used to execute the
 * {@link #delete(Object, Object...)} method.
 * <li>{@link #deleteAllFunction} function used to execute the
 * {@link #deleteAll(Object, Object...)} method.
 * <li>{@link #deleteByIdFunction} function used to execute the
 * {@link #deleteBy(Object, Object...)} method.
 * <li>{@link #deleteByIdsFunction} function used to execute the
 * {@link #deleteAllBy(Object, Object...)} method.
 * </ul>
 * 
 * Each query operation also have pre and pos functions, used to execute logic
 * before and after the main function, and an error function, used to execute
 * logic in case of error defined in
 * {@link AbstractCommandFacade}.
 * 
 * <p>
 * All functions used in this class are provided by the
 * {@link CommandFacadeBuilder} builder.
 * </p>
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 * 
 * @see AbstractCommandFacade
 * @see InterfaceCommandFacade
 */
public non-sealed class CommandFacade<Entity extends InterfaceEntity<Id>, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut>
		extends
		AbstractCommandFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut>
		implements
		InterfaceCommandFacade<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> {

	private static final String NON_NULL_GROUP_OF_ENTITIES_MSG = "Please pass a non-null group of %s entities";

	private static final String NON_NULL_ENTITY_MSG = "Please pass a non-null %s entity";

	private static final String NON_NULL_ID_MSG = "Please pass a non-null %s id";

	private static final String NON_NULL_GROUP_OF_IDS_MSG = "Please pass a non-null group of %s ids";

	private static final Logger LOGGER = LoggerFactory.getLogger(CommandFacade.class);

	/**
	 * Function that executes the save operation in the
	 * {@link #save(Object, Object...)} method
	 * 
	 * @see SaveFunction
	 */
	protected final SaveFunction<SaveEntityIn, SaveEntityOut> saveFunction;

	/**
	 * Function that executes the save all (bunch save) operation in the
	 * {@link #saveAll(Object, Object...)} method
	 * 
	 * @see SaveAllFunction
	 */
	protected final SaveAllFunction<SaveEntitiesIn, SaveEntitiesOut> saveAllFunction;

	/**
	 * Function that executes the update operation in the
	 * {@link #update(Object, Object...)} method
	 * 
	 * @see UpdateFunction
	 */
	protected final UpdateFunction<UpdateEntityIn, UpdateEntityOut> updateFunction;

	/**
	 * Function that executes the update all (bunch update) operation in the
	 * {@link #updateAll(Object, Object...)} method
	 * 
	 * @see UpdateAllFunction
	 */
	protected final UpdateAllFunction<UpdateEntitiesIn, UpdateEntitiesOut> updateAllFunction;

	/**
	 * Function that executes the delete operation in the
	 * {@link #delete(Object, Object...)} method
	 * 
	 * @see DeleteFunction
	 */
	protected final DeleteFunction<DeleteEntityIn, DeleteEntityOut> deleteFunction;

	/**
	 * Function that executes the delete all (bunch delete) operation in the
	 * {@link #deleteAll(Object, Object...)} method
	 * 
	 * @see DeleteAllFunction
	 */
	protected final DeleteAllFunction<DeleteEntitiesIn, DeleteEntitiesOut> deleteAllFunction;

	/**
	 * Function that executes the delete by id operation in the
	 * {@link #deleteBy(Object, Object...)} method
	 * 
	 * @see DeleteByIdFunction
	 */
	protected final DeleteByIdFunction<DeleteIdIn, DeleteIdOut> deleteByIdFunction;

	/**
	 * Function that executes the delete all by id (bunch delete by id) operation in
	 * the {@link #deleteAll(Object, Object...)} method
	 * 
	 * @see DeleteByIdsFunction
	 */
	protected final DeleteByIdsFunction<DeleteIdsIn, DeleteIdsOut> deleteByIdsFunction;

	/**
	 * Default constructor, used by the builder to construct this class.
	 * 
	 * @param builder Object in charge to construct this one, it cannot be null
	 * 
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

	/**
	 * Gets the save function {@link #saveFunction}, provided by the builder.
	 * 
	 * @return the save function
	 * 
	 * @see SaveFunction
	 */
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

	/**
	 * Gets the save all function {@link #saveAllFunction}, provided by the builder.
	 * 
	 * @return the save all function
	 * 
	 * @see SaveAllFunction
	 */
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

	/**
	 * Gets the update function {@link #updateFunction}, provided by the builder.
	 * 
	 * @return the update function
	 * 
	 * @see UpdateFunction
	 */
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

	/**
	 * Gets the update all function {@link #updateAllFunction}, provided by the
	 * builder.
	 * 
	 * @return the update all function
	 * 
	 * @see UpdateAllFunction
	 */
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

	/**
	 * Gets the delete function {@link #deleteFunction}, provided by the builder.
	 * 
	 * @return the delete function
	 * 
	 * @see DeleteFunction
	 */
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
		LOGGER.atDebug().log("Executing default deleteAll, deleteEntitiesIn {}, directives {}",
				deleteEntitiesIn, directives);

		checkNotNull(deleteEntitiesIn, NON_NULL_GROUP_OF_ENTITIES_MSG, getEntityClazz().getSimpleName());

		final var deleteEntitiesOut = execute(
				deleteEntitiesIn, getPreDeleteAllFunction(), getDeleteAllFunction(),
				getPosDeleteAllFunction(), getErrorDeleteAllFunction(), directives);

		LOGGER.atDebug().log("Default deleteAll executed, deleteEntitiesOut {}, directives {}",
				deleteEntitiesOut, directives);
		return deleteEntitiesOut;
	}

	/**
	 * Gets the delete all function {@link #deleteAllFunction}, provided by the
	 * builder.
	 * 
	 * @return the delete all function
	 * 
	 * @see DeleteAllFunction
	 */
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
		LOGGER.atDebug().log("Executing default deleteBy, deleteIdIn {}, directives {}", deleteIdIn, directives);
		checkNotNull(deleteIdIn, NON_NULL_ID_MSG, getEntityClazz().getSimpleName());

		final var deleteIdOut = execute(
				deleteIdIn, getPreDeleteByIdFunction(), getDeleteByIdFunction(),
				getPosDeleteByIdFunction(), getErrorDeleteByIdFunction(), directives);

		LOGGER.atDebug().log("Default deleteBy executed, deleteIdOut {}, directives {}", deleteIdOut, directives);
		return deleteIdOut;
	}

	/**
	 * Gets the delete by id function {@link #deleteByIdFunction}, provided by the
	 * builder.
	 * 
	 * @return the delete by id function
	 * 
	 * @see DeleteByIdFunction
	 */
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
		LOGGER.atDebug().log("Executing default deleteAllBy, deleteIdsIn {}, directives {}", deleteIdsIn, directives);

		checkNotNull(deleteIdsIn, NON_NULL_GROUP_OF_IDS_MSG, getEntityClazz().getSimpleName());

		final var deleteIdsOut = execute(
				deleteIdsIn, getPreDeleteByIdsFunction(), getDeleteByIdsFunction(),
				getPosDeleteByIdsFunction(), getErrorDeleteByIdsFunction(), directives);

		LOGGER.atDebug().log("Default deleteAllBy executed, deleteIdsOut {}, directives {}", deleteIdsOut, directives);
		return deleteIdsOut;
	}

	/**
	 * Gets the delete by ids function {@link #deleteByIdsFunction}, provided by the
	 * builder.
	 * 
	 * @return the delete by ids function
	 * 
	 * @see DeleteByIdsFunction
	 */
	@NotNull
	protected DeleteByIdsFunction<DeleteIdsIn, DeleteIdsOut> getDeleteByIdsFunction() {
		LOGGER.atDebug().log("Returning delete by ids function {}", deleteByIdsFunction.getName());
		return deleteByIdsFunction;
	}
}