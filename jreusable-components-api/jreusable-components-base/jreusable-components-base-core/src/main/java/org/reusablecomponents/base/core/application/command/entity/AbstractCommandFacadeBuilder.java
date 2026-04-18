package org.reusablecomponents.base.core.application.command.entity;

import static java.util.Objects.nonNull;

import java.util.function.Consumer;

import org.reusablecomponents.base.core.application.base.BaseFacadeBuilder;
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

/**
 * The <code>AbstractCommandFacade</code> builder's abstract class.
 * 
 * This class is responsible for building the
 * <code>AbstractCommandFacadeBuilder</code> object.
 * 
 * For each function in this class, if it is not set, it will be set with a
 * default function that just logs the execution and returns the input
 * parameters, example: "Default function 'functionName', input parameters:
 * ['parameter1']".
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
 *                            (bulk version)
 * @param <DeleteIdsOut>      The output type for the delete by ids operation
 *                            (bulk version)
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
public abstract sealed class AbstractCommandFacadeBuilder<Entity extends AbstractEntity<Id>, Id, // basic
		// save
		SaveEntityIn, SaveEntityOut, // save a entity
		SaveEntitiesIn, SaveEntitiesOut, // save entities
		// update
		UpdateEntityIn, UpdateEntityOut, // update a entity
		UpdateEntitiesIn, UpdateEntitiesOut, // update entities
		// delete
		DeleteEntityIn, DeleteEntityOut, // delete a entity
		DeleteEntitiesIn, DeleteEntitiesOut, // delete entities
		// delete by id
		DeleteIdIn, DeleteIdOut, // delete entity by id
		DeleteIdsIn, DeleteIdsOut> // delete entities by ids
		extends BaseFacadeBuilder
		permits CommandFacadeBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCommandFacadeBuilder.class);

	/**
	 * Function that executes pre save operation
	 * 
	 * @see PreSaveFunction
	 */
	public PreSaveFunction<SaveEntityIn> preSaveFunction;

	/**
	 * Function that executes post save operation
	 * 
	 * @see PosSaveFunction
	 */
	public PosSaveFunction<SaveEntityOut> posSaveFunction;

	/**
	 * Function that executes error save operation
	 * 
	 * @see ErrorSaveFunction
	 */
	public ErrorSaveFunction<SaveEntityIn> errorSaveFunction;

	/**
	 * Function that executes pre save all operation
	 * 
	 * @see PreSaveAllFunction
	 */
	public PreSaveAllFunction<SaveEntitiesIn> preSaveAllFunction;

	/**
	 * Function that executes post save all operation
	 * 
	 * @see PosSaveAllFunction
	 */
	public PosSaveAllFunction<SaveEntitiesOut> posSaveAllFunction;

	/**
	 * Function that executes error save all operation
	 * 
	 * @see ErrorSaveAllFunction
	 */
	public ErrorSaveAllFunction<SaveEntitiesIn> errorSaveAllFunction;

	/**
	 * Function that executes pre update operation
	 * 
	 * @see PreUpdateFunction
	 */
	public PreUpdateFunction<UpdateEntityIn> preUpdateFunction;

	/**
	 * Function that executes post update operation
	 * 
	 * @see PosUpdateFunction
	 */
	public PosUpdateFunction<UpdateEntityOut> posUpdateFunction;

	/**
	 * Function that executes error update operation
	 * 
	 * @see ErrorUpdateFunction
	 */
	public ErrorUpdateFunction<UpdateEntityIn> errorUpdateFunction;

	/**
	 * Function that executes pre update all operation
	 * 
	 * @see PreUpdateAllFunction
	 */
	public PreUpdateAllFunction<UpdateEntitiesIn> preUpdateAllFunction;

	/**
	 * Function that executes post update all operation
	 * 
	 * @see PosUpdateAllFunction
	 */
	public PosUpdateAllFunction<UpdateEntitiesOut> posUpdateAllFunction;

	/**
	 * Function that executes error update all operation
	 * 
	 * @see ErrorUpdateAllFunction
	 */
	public ErrorUpdateAllFunction<UpdateEntitiesIn> errorUpdateAllFunction;

	/**
	 * Function that executes pre delete operation
	 * 
	 * @see PreDeleteFunction
	 */
	public PreDeleteFunction<DeleteEntityIn> preDeleteFunction;

	/**
	 * Function that executes post delete operation
	 * 
	 * @see PosDeleteFunction
	 */
	public PosDeleteFunction<DeleteEntityOut> posDeleteFunction;

	/**
	 * Function that executes error delete operation
	 * 
	 * @see ErrorDeleteFunction
	 */
	public ErrorDeleteFunction<DeleteEntityIn> errorDeleteFunction;

	/**
	 * Function that executes pre delete all operation
	 * 
	 * @see PreDeleteAllFunction
	 */
	public PreDeleteAllFunction<DeleteEntitiesIn> preDeleteAllFunction;

	/**
	 * Function that executes post delete all operation
	 * 
	 * @see PosDeleteAllFunction
	 */
	public PosDeleteAllFunction<DeleteEntitiesOut> posDeleteAllFunction;

	/**
	 * Function that executes error delete all operation
	 * 
	 * @see ErrorDeleteAllFunction
	 */
	public ErrorDeleteAllFunction<DeleteEntitiesIn> errorDeleteAllFunction;

	/**
	 * Function that executes pre delete by id operation
	 * 
	 * @see PreDeleteByIdFunction
	 */
	public PreDeleteByIdFunction<DeleteIdIn> preDeleteByIdFunction;

	/**
	 * Function that executes post delete by id operation
	 * 
	 * @see PosDeleteByIdFunction
	 */
	public PosDeleteByIdFunction<DeleteIdOut> posDeleteByIdFunction;

	/**
	 * Function that executes error delete by id operation
	 * 
	 * @see ErrorDeleteByIdFunction
	 */
	public ErrorDeleteByIdFunction<DeleteIdIn> errorDeleteByIdFunction;

	/**
	 * Function that executes pre delete by ids operation
	 * 
	 * @see PreDeleteByIdsFunction
	 */
	public PreDeleteByIdsFunction<DeleteIdsIn> preDeleteByIdsFunction;

	/**
	 * Function that executes post delete by ids operation
	 * 
	 * @see PosDeleteByIdsFunction
	 */
	public PosDeleteByIdsFunction<DeleteIdsOut> posDeleteByIdsFunction;

	/**
	 * Function that executes error delete by ids operation
	 * 
	 * @see ErrorDeleteByIdsFunction
	 */
	public ErrorDeleteByIdsFunction<DeleteIdsIn> errorDeleteByIdsFunction;

	protected AbstractCommandFacadeBuilder(
			final Consumer<? extends AbstractCommandFacadeBuilder<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut>> builder) {
		LOGGER.atDebug().log("Constructing AbstractCommandFacadeBuilder {}", builder);
		super(builder);

		this.preSaveFunction = getPreSaveFunction();
		this.posSaveFunction = getPosSaveFunction();
		this.errorSaveFunction = getErrorSaveFunction();

		this.preSaveAllFunction = getPreSaveAllFunction();
		this.posSaveAllFunction = getPosSaveAllFunction();
		this.errorSaveAllFunction = getErrorSaveAllFunction();

		this.preUpdateFunction = getPreUpdateFunction();
		this.posUpdateFunction = getPosUpdateFunction();
		this.errorUpdateFunction = getErrorUpdateFunction();

		this.preUpdateAllFunction = getPreUpdateAllFunction();
		this.posUpdateAllFunction = getPosUpdateAllFunction();
		this.errorUpdateAllFunction = getErrorUpdateAllFunction();

		this.preDeleteFunction = getPreDeleteFunction();
		this.posDeleteFunction = getPosDeleteFunction();
		this.errorDeleteFunction = getErrorDeleteFunction();

		this.preDeleteAllFunction = getPreDeleteAllFunction();
		this.posDeleteAllFunction = getPosDeleteAllFunction();
		this.errorDeleteAllFunction = getErrorDeleteAllFunction();

		this.preDeleteByIdFunction = getPreDeleteByIdFunction();
		this.posDeleteByIdFunction = getPosDeleteByIdFunction();
		this.errorDeleteByIdFunction = getErrorDeleteByIdFunction();

		this.preDeleteByIdsFunction = getPreDeleteByIdsFunction();
		this.posDeleteByIdsFunction = getPosDeleteByIdsFunction();
		this.errorDeleteByIdsFunction = getErrorDeleteByIdsFunction();
	}

	/**
	 * Gets the pre save function, if it is not set, it will be set with
	 * a default function that logs the execution.
	 * 
	 * @return the pre save function
	 * @see PreSaveFunction
	 */
	protected PreSaveFunction<SaveEntityIn> getPreSaveFunction() {
		return nonNull(preSaveFunction)
				? preSaveFunction
				: (saveEntityIn, directives) -> {
					LOGGER.atDebug().log("Default preSaveFunction, saveEntityIn {}, directives {}", saveEntityIn,
							directives);
					return saveEntityIn;
				};
	}

	/**
	 * Gets the pos save function, if it is not set, it will be set with
	 * a default function that logs the execution.
	 * 
	 * @return the pos save function
	 * @see PosSaveFunction
	 */
	protected PosSaveFunction<SaveEntityOut> getPosSaveFunction() {
		return nonNull(posSaveFunction)
				? posSaveFunction
				: (saveEntityOut, directives) -> {
					LOGGER.atDebug().log("Default posSaveFunction, saveEntityOut {}, directives {}",
							saveEntityOut, directives);
					return saveEntityOut;
				};
	}

	/**
	 * Gets the error save function, if it is not set, it will be set with
	 * a default function that logs the execution.
	 * 
	 * @return the error save function
	 * @see ErrorSaveFunction
	 */
	protected ErrorSaveFunction<SaveEntityIn> getErrorSaveFunction() {
		return nonNull(errorSaveFunction)
				? errorSaveFunction
				: (exception, saveEntityIn, directives) -> {
					LOGGER.atDebug().log("Default errorSaveFunction, saveEntityIn {}, exception {}, directives {}",
							saveEntityIn, exception, directives);
					return exception;
				};
	}

	/**
	 * Gets the pre save all function, if it is not set, it will be set with
	 * a default function that logs the execution.
	 * 
	 * @return the pre save all function
	 * @see PreSaveAllFunction
	 */
	protected PreSaveAllFunction<SaveEntitiesIn> getPreSaveAllFunction() {
		return nonNull(preSaveAllFunction)
				? preSaveAllFunction
				: (saveEntitiesIn, directives) -> {
					LOGGER.atDebug().log("Default preSaveAllFunction, saveEntiesIn {}, directives {}",
							saveEntitiesIn, directives);
					return saveEntitiesIn;
				};
	}

	/**
	 * Gets the pos save all function, if it is not set, it will be set with
	 * a default function that logs the execution.
	 * 
	 * @return the pos save all function
	 * @see PosSaveAllFunction
	 */
	protected PosSaveAllFunction<SaveEntitiesOut> getPosSaveAllFunction() {
		return nonNull(posSaveAllFunction)
				? posSaveAllFunction
				: (saveEntitiesOut, directives) -> {
					LOGGER.atDebug().log("Default posSaveAllFunction, saveEntiesOut {}, directives {}",
							saveEntitiesOut, directives);
					return saveEntitiesOut;
				};
	}

	/**
	 * Gets the error save all function, if it is not set, it will be set with
	 * a default function that logs the execution.
	 * 
	 * @return the error save all function
	 * @see ErrorSaveAllFunction
	 */
	protected ErrorSaveAllFunction<SaveEntitiesIn> getErrorSaveAllFunction() {
		return nonNull(errorSaveAllFunction)
				? errorSaveAllFunction
				: (exception, saveEntitiesIn, directives) -> {
					LOGGER.atDebug().log("Default errorSaveAllFunction, exception {}, saveEntitiesIn {}, directives {}",
							exception, saveEntitiesIn, directives);
					return exception;
				};
	}

	/**
	 * Gets the pos update function, if it is not set, it will be set with
	 * a default function that logs the execution.
	 * 
	 * @return the pos update function
	 * @see PosUpdateFunction
	 */
	protected PreUpdateFunction<UpdateEntityIn> getPreUpdateFunction() {
		return nonNull(preUpdateFunction)
				? preUpdateFunction
				: (updateEntityIn, directives) -> {
					LOGGER.atDebug().log("Default preUpdateFunction, updateEntityIn {}, directives {}",
							updateEntityIn, directives);
					return updateEntityIn;
				};
	}

	/**
	 * Gets the pos update function, if it is not set, it will be set with
	 * a default function that logs the execution.
	 * 
	 * @return the pos update function
	 * @see PosUpdateFunction
	 */
	protected PosUpdateFunction<UpdateEntityOut> getPosUpdateFunction() {
		return nonNull(posUpdateFunction)
				? posUpdateFunction
				: (updateEntityOut, directives) -> {
					LOGGER.atDebug().log("Default posUpdateFunction, updateEntityOut {}, directives {}",
							updateEntityOut, directives);
					return updateEntityOut;
				};
	}

	/**
	 * Gets the error update function, if it is not set, it will be set with
	 * a default function that logs the execution.
	 * 
	 * @return the error update function
	 * @see ErrorUpdateFunction
	 */
	protected ErrorUpdateFunction<UpdateEntityIn> getErrorUpdateFunction() {
		return nonNull(errorUpdateFunction)
				? errorUpdateFunction
				: (exception, updateEntityIn, directives) -> {
					LOGGER.atDebug().log("Default errorUpdateFunction, exception {}, updateEntityIn {}, directives {}",
							exception, updateEntityIn, directives);
					return exception;
				};
	}

	/**
	 * Gets the pre update all function, if it is not set, it will be set with
	 * a default function that logs the execution.
	 * 
	 * @return the pre update all function
	 * @see PreUpdateAllFunction
	 */
	protected PreUpdateAllFunction<UpdateEntitiesIn> getPreUpdateAllFunction() {
		return nonNull(preUpdateAllFunction)
				? preUpdateAllFunction
				: (updateEntitiesIn, directives) -> {
					LOGGER.atDebug().log("Default preUpdateAllFunction, updateEntitiesIn {}, directives {}",
							updateEntitiesIn, directives);
					return updateEntitiesIn;
				};
	}

	/**
	 * Gets the pos update all function, if it is not set, it will be set with
	 * a default function that logs the execution.
	 * 
	 * @return the pos update all function
	 * @see PosUpdateAllFunction
	 */
	protected PosUpdateAllFunction<UpdateEntitiesOut> getPosUpdateAllFunction() {
		return nonNull(posUpdateAllFunction)
				? posUpdateAllFunction
				: (final UpdateEntitiesOut updateEntitiesOut,
						final Object... directives) -> {
					LOGGER.atDebug().log("Default posUpdateAllFunction, updateEntitiesOut {}, directives {}",
							updateEntitiesOut, directives);
					return updateEntitiesOut;
				};
	}

	/**
	 * Gets the error update all function, if it is not set, it will be set with
	 * a default function that logs the execution.
	 * 
	 * @return the error update all function
	 * @see ErrorUpdateAllFunction
	 */
	protected ErrorUpdateAllFunction<UpdateEntitiesIn> getErrorUpdateAllFunction() {
		return nonNull(errorUpdateAllFunction)
				? errorUpdateAllFunction
				: (exception, updateEntitiesIn, directives) -> {
					LOGGER.atDebug().log(
							"Default errorUpdateAllFunction, updateEntitiesIn {}, exception {}, directives {}",
							updateEntitiesIn, exception, directives);
					return exception;
				};
	}

	/**
	 * Gets the pre delete function, if it is not set, it will be set with
	 * a default function that logs the execution.
	 * 
	 * @return the pre delete function
	 * @see PreDeleteFunction
	 */
	protected PreDeleteFunction<DeleteEntityIn> getPreDeleteFunction() {
		return nonNull(preDeleteFunction)
				? preDeleteFunction
				: (deleteEntityIn, directives) -> {
					LOGGER.atDebug().log("Default preDeleteFunction, deleteEntityIn {}, directives {}",
							deleteEntityIn, directives);
					return deleteEntityIn;
				};
	}

	/**
	 * Gets the pos delete function, if it is not set, it will be set with
	 * a default function that logs the execution.
	 * 
	 * @return the pos delete function
	 * @see PosDeleteFunction
	 */
	protected PosDeleteFunction<DeleteEntityOut> getPosDeleteFunction() {
		return nonNull(posDeleteFunction)
				? posDeleteFunction
				: (deleteEntityOut, directives) -> {
					LOGGER.atDebug().log("Default posDeleteFunction, deleteEntityOut {}, directives {}",
							deleteEntityOut, directives);
					return deleteEntityOut;
				};
	}

	/**
	 * Gets the error delete function, if it is not set, it will be set with
	 * a default function that logs the execution.
	 * 
	 * @return the error delete function
	 * @see ErrorDeleteFunction
	 */
	protected ErrorDeleteFunction<DeleteEntityIn> getErrorDeleteFunction() {
		return nonNull(errorDeleteFunction)
				? errorDeleteFunction
				: (exception, deleteEntityIn, directives) -> {
					LOGGER.atDebug().log("Default errorDeleteFunction, exception {}, deleteEntityIn {}, directives {}",
							exception, deleteEntityIn, directives);
					return exception;
				};
	}

	/**
	 * Gets the pre delete all function, if it is not set, it will be set with
	 * a default function that logs the execution.
	 * 
	 * @return the pre delete all function
	 * @see PreDeleteAllFunction
	 */
	protected PreDeleteAllFunction<DeleteEntitiesIn> getPreDeleteAllFunction() {
		return nonNull(preDeleteAllFunction)
				? preDeleteAllFunction
				: (deleteEntitiesIn, directives) -> {
					LOGGER.atDebug().log("Default preDeleteAllFunction, deleteEntitiesIn {}, directives {}",
							deleteEntitiesIn,
							directives);
					return deleteEntitiesIn;
				};
	}

	/**
	 * Gets the pos delete by id function, if it is not set, it will be set with
	 * a default function that logs the execution.
	 * 
	 * @return the pos delete by id function
	 * @see PosDeleteByIdFunction
	 */
	protected PosDeleteAllFunction<DeleteEntitiesOut> getPosDeleteAllFunction() {
		return nonNull(posDeleteAllFunction)
				? posDeleteAllFunction
				: (deleteEntitiesOut, directives) -> {
					LOGGER.atDebug().log("Executing default posDeleteAllFunction, deleteEntitiesOut {}, directives {}",
							deleteEntitiesOut, directives);
					return deleteEntitiesOut;
				};
	}

	/**
	 * Gets the error delete all function, if it is not set, it will be set with
	 * a default function that logs the execution.
	 * 
	 * @return the error delete all function
	 * @see ErrorDeleteAllFunction
	 */
	protected ErrorDeleteAllFunction<DeleteEntitiesIn> getErrorDeleteAllFunction() {
		return nonNull(errorDeleteAllFunction)
				? errorDeleteAllFunction
				: (exception, deleteEntitiesIn, directives) -> {
					LOGGER.atDebug().log(
							"Executing default errorDeleteAllFunction, exception {}, deleteEntitiesIn {}, directives {}",
							exception, deleteEntitiesIn, directives);
					return exception;
				};
	}

	/**
	 * Gets the pre delete by id function, if it is not set, it will be set with
	 * a default function that logs the execution.
	 * 
	 * @return the pre delete by id function
	 * @see PreDeleteByIdFunction
	 */
	protected PreDeleteByIdFunction<DeleteIdIn> getPreDeleteByIdFunction() {
		return nonNull(preDeleteByIdFunction)
				? preDeleteByIdFunction
				: (deleteIdIn, directives) -> {
					LOGGER.atDebug().log("Default preDeleteByIdFunction, deleteIdIn {}, directives {}", deleteIdIn,
							directives);
					return deleteIdIn;
				};
	}

	/**
	 * Gets the pos delete by id function, if it is not set, it will be set with
	 * a default function that logs the execution.
	 * 
	 * @return the pos delete by id function
	 * @see PosDeleteByIdFunction
	 */
	protected PosDeleteByIdFunction<DeleteIdOut> getPosDeleteByIdFunction() {
		return nonNull(posDeleteByIdFunction)
				? posDeleteByIdFunction
				: (deleteIdOut, directives) -> {
					LOGGER.atDebug().log("Default posDeleteByIdFunction, deleteIdOut {}, directives {}",
							deleteIdOut, directives);
					return deleteIdOut;
				};
	}

	/**
	 * Gets the error delete by id function, if it is not set, it will be set with
	 * a default function that logs the execution.
	 * 
	 * @return the error delete by id function
	 * @see ErrorDeleteByIdFunction
	 */
	protected ErrorDeleteByIdFunction<DeleteIdIn> getErrorDeleteByIdFunction() {
		return nonNull(errorDeleteByIdFunction)
				? errorDeleteByIdFunction
				: (exception, deleteIdIn, directives) -> {
					LOGGER.atDebug().log("Default errorDeleteByIdFunction, exception {}, deleteIdIn {}, directives {}",
							exception, deleteIdIn, directives);
					return exception;
				};
	}

	/**
	 * Gets the pre delete by ids function, if it is not set, it will be set with
	 * a default function that logs the execution.
	 * 
	 * @return the pre delete by ids function
	 * @see PreDeleteByIdsFunction
	 */
	protected PreDeleteByIdsFunction<DeleteIdsIn> getPreDeleteByIdsFunction() {
		return nonNull(preDeleteByIdsFunction)
				? preDeleteByIdsFunction
				: (deleteIdsIn, directives) -> {
					LOGGER.atDebug().log("Default preDeleteByIdsFunction, deleteIdsIn {}, directives {}",
							deleteIdsIn, directives);
					return deleteIdsIn;
				};
	}

	/**
	 * Gets the pos delete by ids function, if it is not set, it will be set with
	 * a default function that logs the execution.
	 * 
	 * @return the pos delete by ids function
	 * @see PosDeleteByIdsFunction
	 */
	protected PosDeleteByIdsFunction<DeleteIdsOut> getPosDeleteByIdsFunction() {
		return nonNull(posDeleteByIdsFunction)
				? posDeleteByIdsFunction
				: (deleteIdsOut, directives) -> {
					LOGGER.atDebug().log("Default posDeleteByIdsFunction, deleteIdsOut {}, directives {}",
							deleteIdsOut, directives);
					return deleteIdsOut;
				};
	}

	/**
	 * Gets the error delete by ids function, if it is not set, it will be set with
	 * a default function that logs the execution.
	 * 
	 * @return the error delete by ids function
	 * @see ErrorDeleteByIdsFunction
	 */
	protected ErrorDeleteByIdsFunction<DeleteIdsIn> getErrorDeleteByIdsFunction() {
		return nonNull(errorDeleteByIdsFunction)
				? errorDeleteByIdsFunction
				: (exception, deleteIdsIn, directives) -> {
					LOGGER.atDebug().log(
							"Executing default errorDeleteByIdsFunction, deleteIdsIn {}, exception {}, directives {}",
							deleteIdsIn, exception, directives);
					return exception;
				};
	}
}
