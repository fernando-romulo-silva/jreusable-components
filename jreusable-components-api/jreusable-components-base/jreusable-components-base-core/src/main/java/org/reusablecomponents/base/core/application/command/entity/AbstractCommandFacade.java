package org.reusablecomponents.base.core.application.command.entity;

import org.reusablecomponents.base.core.application.base.BaseFacade;
import org.reusablecomponents.base.core.application.command.entity.function.delete.ErrorDeleteFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete.PosDeleteFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete.PreDeleteFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_all.ErrorDeleteAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_all.PosDeleteAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_all.PreDeleteAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_by_id.ErrorDeleteByIdFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_by_id.PosDeleteByIdFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_by_id.PreDeleteByIdFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_by_id_all.ErrorDeleteByIdsFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_by_id_all.PosDeleteByIdsFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_by_id_all.PreDeleteByIdsFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save.ErrorSaveFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save.PosSaveFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save.PreSaveFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save_all.ErrorSaveAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save_all.PosSaveAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save_all.PreSaveAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.update.ErrorUpdateFunction;
import org.reusablecomponents.base.core.application.command.entity.function.update.PosUpdateFunction;
import org.reusablecomponents.base.core.application.command.entity.function.update.PreUpdateFunction;
import org.reusablecomponents.base.core.application.command.entity.function.update_all.ErrorUpdateAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.update_all.PosUpdateAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.update_all.PreUpdateAllFunction;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * Abstract class for command facades, providing common functionality for
 * handling pre, post, and error functions for various command operations.
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
 * @since 1.0
 * 
 * @see BaseFacade
 */
public abstract sealed class AbstractCommandFacade<Entity extends AbstractEntity<Id>, Id, // basic
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
	 * Function executed in {@link CommandFacade#save(Object, Object...) save}
	 * method before the {@link CommandFacade#saveFunction saveFunction}, use it to
	 * configure, change, etc. the saveEntityIn object.
	 * 
	 * @see PreSaveFunction
	 */
	protected final PreSaveFunction<SaveEntityIn> preSaveFunction;

	/**
	 * Function executed in {@link CommandFacade#save(Object, Object...) save}
	 * method after {@link CommandFacade#saveFunction saveFunction}, use it to
	 * configure, change, etc. the saveEntityOut object.
	 * 
	 * @see PosSaveFunction
	 */
	protected final PosSaveFunction<SaveEntityOut> posSaveFunction;

	/**
	 * Function executed in {@link CommandFacade#save(Object, Object...) save}
	 * method to handle {@link CommandFacade#saveFunction saveFunction} errors.
	 * 
	 * @see ErrorSaveFunction
	 */
	protected final ErrorSaveFunction<SaveEntityIn> errorSaveFunction;

	/**
	 * Function executed in {@link CommandFacade#saveAll(Object, Object...) saveAll}
	 * method before the {@link CommandFacade#saveAllFunction saveAllFunction}, use
	 * it to configure, change, * etc. the input.
	 * 
	 * @see PreSaveAllFunction
	 */
	protected final PreSaveAllFunction<SaveEntitiesIn> preSaveAllFunction;

	/**
	 * Function executed in {@link CommandFacade#saveAll(Object, Object...) saveAll}
	 * method after {@link CommandFacade#saveAllFunction saveAllFunction}, use it to
	 * configure, change, etc. the output.
	 * 
	 * @see PosSaveAllFunction
	 */
	protected final PosSaveAllFunction<SaveEntitiesOut> posSaveAllFunction;

	/**
	 * Function executed in {@link CommandFacade#saveAll(Object, Object...) saveAll}
	 * method to handle {@link CommandFacade#saveAllFunction saveAllFunction}
	 * errors.
	 * 
	 * @see ErrorSaveAllFunction
	 */
	protected final ErrorSaveAllFunction<SaveEntitiesIn> errorSaveAllFunction;

	/**
	 * Function executed in {@link CommandFacade#update(Object, Object...) update}
	 * method before the {@link CommandFacade#updateFunction updateFunction}, use it
	 * to configure, change, etc. the input.
	 * 
	 * @see PreUpdateFunction
	 */
	protected final PreUpdateFunction<UpdateEntityIn> preUpdateFunction;

	/**
	 * Function executed in {@link CommandFacade#update(Object, Object...) update}
	 * method after {@link CommandFacade#updateFunction updateFunction}, use it to
	 * configure, change, etc. the output.
	 * 
	 * @see PosUpdateFunction
	 */
	protected final PosUpdateFunction<UpdateEntityOut> posUpdateFunction;

	/**
	 * Function executed in {@link CommandFacade#update(Object, Object...) update}
	 * method to handle {@link CommandFacade#updateFunction updateFunction} errors.
	 * 
	 * @see ErrorUpdateFunction
	 */
	protected final ErrorUpdateFunction<UpdateEntityIn> errorUpdateFunction;

	/**
	 * Function executed in {@link CommandFacade#updateAll(Object, Object...)
	 * updateAll} method before the {@link CommandFacade#updateAllFunction
	 * updateAllFunction}, use it to change values.
	 * 
	 * @see PreUpdateAllFunction
	 */
	protected final PreUpdateAllFunction<UpdateEntitiesIn> preUpdateAllFunction;

	/**
	 * Function executed in {@link CommandFacade#updateAll(Object, Object...)
	 * updateAll} method after {@link CommandFacade#updateAllFunction
	 * updateAllFunction}, use it to configure, change, etc. the output.
	 * 
	 * @see PosUpdateAllFunction
	 */
	protected final PosUpdateAllFunction<UpdateEntitiesOut> posUpdateAllFunction;

	/**
	 * Function executed in {@link CommandFacade#updateAll(Object, Object...)
	 * updateAll} method to handle {@link CommandFacade#updateAllFunction
	 * updateAllFunction} errors.
	 * 
	 * @see ErrorUpdateAllFunction
	 */
	protected final ErrorUpdateAllFunction<UpdateEntitiesIn> errorUpdateAllFunction;

	/**
	 * Function executed in {@link CommandFacade#delete(Object, Object...) delete}
	 * method before the {@link CommandFacade#deleteFunction deleteFunction}, use it
	 * to configure, change, etc. the input.
	 * 
	 * @see PreDeleteFunction
	 */
	protected final PreDeleteFunction<DeleteEntityIn> preDeleteFunction;

	/**
	 * Function executed in {@link CommandFacade#delete(Object, Object...) delete}
	 * method after {@link CommandFacade#deleteFunction deleteFunction}, use it to
	 * configure, change, etc. the output.
	 * 
	 * @see PosDeleteFunction
	 */
	protected final PosDeleteFunction<DeleteEntityOut> posDeleteFunction;

	/**
	 * Function executed in {@link CommandFacade#delete(Object, Object...) delete}
	 * method to handle {@link CommandFacade#deleteFunction deleteFunction} errors.
	 * 
	 * @see ErrorDeleteFunction
	 */
	protected final ErrorDeleteFunction<DeleteEntityIn> errorDeleteFunction;

	/**
	 * Function executed in {@link CommandFacade#deleteAll(Object, Object...)
	 * deleteAll} method before the {@link CommandFacade#deleteAllFunction
	 * deleteAllFunction}, use it to change values.
	 * 
	 * @see PreDeleteAllFunction
	 * 
	 */
	protected final PreDeleteAllFunction<DeleteEntitiesIn> preDeleteAllFunction;

	/**
	 * Function executed in {@link CommandFacade#deleteAll(Object, Object...)
	 * deleteAll} method after {@link CommandFacade#deleteAllFunction
	 * deleteAllFunction}, use it to configure, change, etc. the output.
	 * 
	 * @see PosDeleteAllFunction
	 */
	protected final PosDeleteAllFunction<DeleteEntitiesOut> posDeleteAllFunction;

	/**
	 * Function executed in {@link CommandFacade#deleteAll(Object, Object...)
	 * deleteAll} method to handle {@link CommandFacade#deleteAllFunction
	 * deleteAllFunction} errors.
	 * 
	 * @see ErrorDeleteAllFunction
	 */
	protected final ErrorDeleteAllFunction<DeleteEntitiesIn> errorDeleteAllFunction;

	/**
	 * Function executed in {@link CommandFacade#deleteBy(Object, Object...)
	 * deleteBy} method before the {@link CommandFacade#deleteByIdFunction
	 * deleteByIdFunction}, use it to change values.
	 * 
	 * @see PreDeleteByIdFunction
	 */
	protected final PreDeleteByIdFunction<DeleteIdIn> preDeleteByIdFunction;

	/**
	 * Function executed in {@link CommandFacade#deleteBy(Object, Object...)
	 * deleteBy} method after {@link CommandFacade#deleteByIdFunction
	 * deleteByIdFunction}, use it to configure, change, etc. the input.
	 * 
	 * @see PosDeleteByIdFunction
	 */
	protected final PosDeleteByIdFunction<DeleteIdOut> posDeleteByIdFunction;

	/**
	 * Function executed in {@link CommandFacade#deleteBy(Object, Object...)
	 * deleteBy} method to handle {@link CommandFacade#deleteByIdFunction
	 * deleteByIdFunction} errors.
	 * 
	 * @see ErrorDeleteByIdFunction
	 */
	protected final ErrorDeleteByIdFunction<DeleteIdIn> errorDeleteByIdFunction;

	/**
	 * Function executed in {@link CommandFacade#deleteAllBy(Object, Object...)
	 * deleteAllBy} method before the {@link CommandFacade#deleteByIdsFunction
	 * deleteAllByIdFunction}, use it to configure, change, etc. the input.
	 * 
	 * @see PreDeleteByIdsFunction
	 */
	protected final PreDeleteByIdsFunction<DeleteIdsIn> preDeleteByIdsFunction;

	/**
	 * Function executed in {@link CommandFacade#deleteAllBy(Object, Object...)
	 * deleteAllBy} method after {@link CommandFacade#deleteByIdsFunction
	 * deleteAllByIdFunction}, use it to change values.
	 * 
	 * @see PosDeleteByIdsFunction
	 */
	protected final PosDeleteByIdsFunction<DeleteIdsOut> posDeleteByIdsFunction;

	/**
	 * Function executed in {@link CommandFacade#deleteAllBy(Object, Object...)
	 * deleteAllBy} method to handle {@link CommandFacade#deleteByIdsFunction
	 * deleteAllByIdFunction} errors.
	 * 
	 * @see ErrorDeleteByIdsFunction
	 */
	protected final ErrorDeleteByIdsFunction<DeleteIdsIn> errorDeleteByIdsFunction;

	/**
	 * Default constructor, used by the builder to construct this class.
	 * 
	 * @param builder Object constructor, can't be null
	 * @throws NullPointerException if the builder is null
	 * 
	 * @see AbstractCommandFacadeBuilder
	 */
	protected AbstractCommandFacade(
			@NotNull final AbstractCommandFacadeBuilder<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> builder) {
		super(builder);
		this.preSaveFunction = builder.preSaveFunction;
		this.posSaveFunction = builder.posSaveFunction;
		this.errorSaveFunction = builder.errorSaveFunction;

		this.preSaveAllFunction = builder.preSaveAllFunction;
		this.posSaveAllFunction = builder.posSaveAllFunction;
		this.errorSaveAllFunction = builder.errorSaveAllFunction;

		this.preUpdateFunction = builder.preUpdateFunction;
		this.posUpdateFunction = builder.posUpdateFunction;
		this.errorUpdateFunction = builder.errorUpdateFunction;

		this.preUpdateAllFunction = builder.preUpdateAllFunction;
		this.posUpdateAllFunction = builder.posUpdateAllFunction;
		this.errorUpdateAllFunction = builder.errorUpdateAllFunction;

		this.preDeleteFunction = builder.preDeleteFunction;
		this.posDeleteFunction = builder.posDeleteFunction;
		this.errorDeleteFunction = builder.errorDeleteFunction;

		this.preDeleteAllFunction = builder.preDeleteAllFunction;
		this.posDeleteAllFunction = builder.posDeleteAllFunction;
		this.errorDeleteAllFunction = builder.errorDeleteAllFunction;

		this.preDeleteByIdFunction = builder.preDeleteByIdFunction;
		this.posDeleteByIdFunction = builder.posDeleteByIdFunction;
		this.errorDeleteByIdFunction = builder.errorDeleteByIdFunction;

		this.preDeleteByIdsFunction = builder.preDeleteByIdsFunction;
		this.posDeleteByIdsFunction = builder.posDeleteByIdsFunction;
		this.errorDeleteByIdsFunction = builder.errorDeleteByIdsFunction;
	}

	@NotNull
	protected PreSaveFunction<SaveEntityIn> getPreSaveFunction() {
		LOGGER.atDebug().log("Returning preSaveFunction function {}", preSaveFunction.getName());
		return preSaveFunction;
	}

	@NotNull
	protected PosSaveFunction<SaveEntityOut> getPosSaveFunction() {
		LOGGER.atDebug().log("Returning posSaveFunction function {}", posSaveFunction.getName());
		return posSaveFunction;
	}

	@NotNull
	protected ErrorSaveFunction<SaveEntityIn> getErrorSaveFunction() {
		LOGGER.atDebug().log("Returning errorSaveFunction function {}", errorSaveFunction.getName());
		return errorSaveFunction;
	}

	@NotNull
	protected PreSaveAllFunction<SaveEntitiesIn> getPreSaveAllFunction() {
		LOGGER.atDebug().log("Returning preSaveAllFunction function {}", preSaveAllFunction.getName());
		return preSaveAllFunction;
	}

	@NotNull
	protected PosSaveAllFunction<SaveEntitiesOut> getPosSaveAllFunction() {
		LOGGER.atDebug().log("Returning posSaveAllFunction function {}", posSaveAllFunction.getName());
		return posSaveAllFunction;
	}

	@NotNull
	protected ErrorSaveAllFunction<SaveEntitiesIn> getErrorSaveAllFunction() {
		LOGGER.atDebug().log("Returning errorSaveAllFunction function {}", errorSaveAllFunction.getName());
		return errorSaveAllFunction;
	}

	@NotNull
	protected PreUpdateFunction<UpdateEntityIn> getPreUpdateFunction() {
		LOGGER.atDebug().log("Returning preUpdateFunction function {}", preUpdateFunction.getName());
		return preUpdateFunction;
	}

	@NotNull
	protected PosUpdateFunction<UpdateEntityOut> getPosUpdateFunction() {
		LOGGER.atDebug().log("Returning posUpdateFunction function {}", posUpdateFunction.getName());
		return posUpdateFunction;
	}

	@NotNull
	protected ErrorUpdateFunction<UpdateEntityIn> getErrorUpdateFunction() {
		LOGGER.atDebug().log("Returning errorUpdateFunction function {}", errorUpdateFunction.getName());
		return errorUpdateFunction;
	}

	@NotNull
	protected PreUpdateAllFunction<UpdateEntitiesIn> getPreUpdateAllFunction() {
		LOGGER.atDebug().log("Returning preUpdateAllFunction function {}", preUpdateAllFunction.getName());
		return preUpdateAllFunction;
	}

	@NotNull
	protected PosUpdateAllFunction<UpdateEntitiesOut> getPosUpdateAllFunction() {
		LOGGER.atDebug().log("Returning posUpdateAllFunction function {}", posUpdateAllFunction.getName());
		return posUpdateAllFunction;
	}

	@NotNull
	protected ErrorUpdateAllFunction<UpdateEntitiesIn> getErrorUpdateAllFunction() {
		LOGGER.atDebug().log("Returning errorUpdateAllFunction function {}", errorUpdateAllFunction.getName());
		return errorUpdateAllFunction;
	}

	@NotNull
	protected PreDeleteFunction<DeleteEntityIn> getPreDeleteFunction() {
		LOGGER.atDebug().log("Returning preDeleteFunction function {}", preDeleteFunction.getName());
		return preDeleteFunction;
	}

	@NotNull
	protected PosDeleteFunction<DeleteEntityOut> getPosDeleteFunction() {
		LOGGER.atDebug().log("Returning posDeleteFunction function {}", posDeleteFunction.getName());
		return posDeleteFunction;
	}

	@NotNull
	protected ErrorDeleteFunction<DeleteEntityIn> getErrorDeleteFunction() {
		LOGGER.atDebug().log("Returning errorDeleteFunction function {}", errorDeleteFunction.getName());
		return errorDeleteFunction;
	}

	@NotNull
	protected PreDeleteAllFunction<DeleteEntitiesIn> getPreDeleteAllFunction() {
		LOGGER.atDebug().log("Returning preDeleteAllFunction function {}", preDeleteAllFunction.getName());
		return preDeleteAllFunction;
	}

	@NotNull
	protected PosDeleteAllFunction<DeleteEntitiesOut> getPosDeleteAllFunction() {
		LOGGER.atDebug().log("Returning posDeleteAllFunction function {}", posDeleteAllFunction.getName());
		return posDeleteAllFunction;
	}

	@NotNull
	protected ErrorDeleteAllFunction<DeleteEntitiesIn> getErrorDeleteAllFunction() {
		LOGGER.atDebug().log("Returning errorDeleteAllFunction function {}", errorDeleteAllFunction.getName());
		return errorDeleteAllFunction;
	}

	@NotNull
	protected PreDeleteByIdFunction<DeleteIdIn> getPreDeleteByIdFunction() {
		LOGGER.atDebug().log("Returning preDeleteByIdFunction function {}", preDeleteByIdFunction.getName());
		return preDeleteByIdFunction;
	}

	@NotNull
	protected PosDeleteByIdFunction<DeleteIdOut> getPosDeleteByIdFunction() {
		LOGGER.atDebug().log("Returning posDeleteByIdFunction function {}", posDeleteByIdFunction.getName());
		return posDeleteByIdFunction;
	}

	@NotNull
	protected ErrorDeleteByIdFunction<DeleteIdIn> getErrorDeleteByIdFunction() {
		LOGGER.atDebug().log("Returning errorDeleteByIdFunction function {}", errorDeleteByIdFunction.getName());
		return errorDeleteByIdFunction;
	}

	@NotNull
	protected PreDeleteByIdsFunction<DeleteIdsIn> getPreDeleteByIdsFunction() {
		LOGGER.atDebug().log("Returning preDeleteByIdsFunction function {}", preDeleteByIdsFunction.getName());
		return preDeleteByIdsFunction;
	}

	@NotNull
	protected PosDeleteByIdsFunction<DeleteIdsOut> getPosDeleteByIdsFunction() {
		LOGGER.atDebug().log("Returning posDeleteByIdsFunction function {}", posDeleteByIdsFunction.getName());
		return posDeleteByIdsFunction;
	}

	@NotNull
	protected ErrorDeleteByIdsFunction<DeleteIdsIn> getErrorDeleteByIdsFunction() {
		LOGGER.atDebug().log("Returning errorDeleteByIdsFunction function {}", errorDeleteByIdsFunction.getName());
		return errorDeleteByIdsFunction;
	}
}