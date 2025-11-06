package org.reusablecomponents.base.core.application.command.entity;

import static java.util.Objects.nonNull;

import org.reusablecomponents.base.core.application.base.BaseFacade;
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
import org.reusablecomponents.base.core.application.command.function.operation.save.ErrorSaveAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.save.ErrorSaveFunction;
import org.reusablecomponents.base.core.application.command.function.operation.save.PosSaveAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.save.PosSaveFunction;
import org.reusablecomponents.base.core.application.command.function.operation.save.PreSaveAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.save.PreSaveFunction;
import org.reusablecomponents.base.core.application.command.function.operation.update.ErrorUpdateAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.update.ErrorUpdateFunction;
import org.reusablecomponents.base.core.application.command.function.operation.update.PosUpdateAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.update.PosUpdateFunction;
import org.reusablecomponents.base.core.application.command.function.operation.update.PreUpdateAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.update.PreUpdateFunction;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
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
	 * Function executed in {@link CommandFacade#save(Object, Object...) save}
	 * method before the {@link CommandFacade#saveFunction saveFunction}, use it to
	 * configure, change, etc. the saveEntityIn object.
	 */
	protected final PreSaveFunction<SaveEntityIn> preSaveFunction;

	/**
	 * Function executed in {@link CommandFacade#save(Object, Object...) save}
	 * method after {@link CommandFacade#saveFunction saveFunction}, use it to
	 * configure, change, etc. the saveEntityOut object.
	 */
	protected final PosSaveFunction<SaveEntityOut> posSaveFunction;

	/**
	 * Function executed in {@link CommandFacade#save(Object, Object...) save}
	 * method to handle {@link CommandFacade#saveFunction saveFunction} errors.
	 */
	protected final ErrorSaveFunction<SaveEntityIn> errorSaveFunction;

	/**
	 * Function executed in {@link CommandFacade#saveAll(Object, Object...) saveAll}
	 * method before the {@link CommandFacade#saveAllFunction saveAllFunction}, use
	 * it to configure, change, * etc. the input.
	 */
	protected final PreSaveAllFunction<SaveEntitiesIn> preSaveAllFunction;

	/**
	 * Function executed in {@link CommandFacade#saveAll(Object, Object...) saveAll}
	 * method after {@link CommandFacade#saveAllFunction saveAllFunction}, use it to
	 * configure, change, etc. the output.
	 */
	protected final PosSaveAllFunction<SaveEntitiesOut> posSaveAllFunction;

	/**
	 * Function executed in {@link CommandFacade#saveAll(Object, Object...) saveAll}
	 * method to handle {@link CommandFacade#saveAllFunction saveAllFunction}
	 * errors.
	 */
	protected final ErrorSaveAllFunction<SaveEntitiesIn> errorSaveAllFunction;

	/**
	 * Function executed in {@link CommandFacade#update(Object, Object...) update}
	 * method before the {@link CommandFacade#updateFunction updateFunction}, use it
	 * to configure, change, etc. the input.
	 */
	protected final PreUpdateFunction<UpdateEntityIn> preUpdateFunction;

	/**
	 * Function executed in {@link CommandFacade#update(Object, Object...) update}
	 * method after {@link CommandFacade#updateFunction updateFunction}, use it to
	 * configure, change, etc. the output.
	 */
	protected final PosUpdateFunction<UpdateEntityOut> posUpdateFunction;

	/**
	 * Function executed in {@link CommandFacade#update(Object, Object...) update}
	 * method to handle {@link CommandFacade#updateFunction updateFunction} errors.
	 */
	protected final ErrorUpdateFunction<UpdateEntityIn> errorUpdateFunction;

	protected final PreUpdateAllFunction<UpdateEntitiesIn> preUpdateAllFunction;

	protected final PosUpdateAllFunction<UpdateEntitiesOut> posUpdateAllFunction;

	protected final ErrorUpdateAllFunction<UpdateEntitiesIn> errorUpdateAllFunction;

	protected final PreDeleteFunction<DeleteEntityIn> preDeleteFunction;

	protected final PosDeleteFunction<DeleteEntityOut> posDeleteFunction;

	protected final ErrorDeleteFunction<DeleteEntityIn> errorDeleteFunction;

	protected final PreDeleteAllFunction<DeleteEntitiesIn> preDeleteAllFunction;

	protected final PosDeleteAllFunction<DeleteEntitiesOut> posDeleteAllFunction;

	protected final ErrorDeleteAllFunction<DeleteEntitiesIn> errorDeleteAllFunction;

	protected final PreDeleteByIdFunction<DeleteIdIn> preDeleteByIdFunction;

	protected final PosDeleteByIdFunction<DeleteIdOut> posDeleteByIdFunction;

	protected final ErrorDeleteByIdFunction<DeleteIdIn> errorDeleteByIdFunction;

	protected final PreDeleteByIdsFunction<DeleteIdsIn> preDeleteByIdsFunction;

	protected final PosDeleteByIdsFunction<DeleteIdsOut> posDeleteByIdsFunction;

	protected final ErrorDeleteByIdsFunction<DeleteIdsIn> errorDeleteByIdsFunction;

	/**
	 * Default constructor
	 * 
	 * @param builder
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

		this.preUpdateAllFunction = nonNull(builder.preUpdateAllFunction) ? builder.preUpdateAllFunction
				: this::preUpdateAll;
		this.posUpdateAllFunction = nonNull(builder.posUpdateAllFunction) ? builder.posUpdateAllFunction
				: this::posUpdateAll;
		this.errorUpdateAllFunction = nonNull(builder.errorUpdateAllFunction) ? builder.errorUpdateAllFunction
				: this::errorUpdateAll;

		this.preDeleteFunction = nonNull(builder.preDeleteFunction) ? builder.preDeleteFunction
				: this::preDelete;
		this.posDeleteFunction = nonNull(builder.posDeleteFunction) ? builder.posDeleteFunction
				: this::posDelete;
		this.errorDeleteFunction = nonNull(builder.errorDeleteFunction) ? builder.errorDeleteFunction
				: this::errorDelete;

		this.preDeleteAllFunction = nonNull(builder.preDeleteAllFunction) ? builder.preDeleteAllFunction
				: this::preDeleteAll;
		this.posDeleteAllFunction = nonNull(builder.posDeleteAllFunction) ? builder.posDeleteAllFunction
				: this::posDeleteAll;
		this.errorDeleteAllFunction = nonNull(builder.errorDeleteAllFunction) ? builder.errorDeleteAllFunction
				: this::errorDeleteAll;

		this.preDeleteByIdFunction = nonNull(builder.preDeleteByIdFunction) ? builder.preDeleteByIdFunction
				: this::preDeleteBy;
		this.posDeleteByIdFunction = nonNull(builder.posDeleteByIdFunction) ? builder.posDeleteByIdFunction
				: this::posDeleteBy;
		this.errorDeleteByIdFunction = nonNull(builder.errorDeleteByIdFunction)
				? builder.errorDeleteByIdFunction
				: this::errorDeleteBy;

		this.preDeleteByIdsFunction = nonNull(builder.preDeleteByIdsFunction) ? builder.preDeleteByIdsFunction
				: this::preDeleteAllBy;
		this.posDeleteByIdsFunction = nonNull(builder.posDeleteByIdsFunction) ? builder.posDeleteByIdsFunction
				: this::posDeleteAllBy;
		this.errorDeleteByIdsFunction = nonNull(builder.errorDeleteByIdsFunction)
				? builder.errorDeleteByIdsFunction
				: this::errorDeleteAllBy;
	}

	@NotNull
	protected PreSaveFunction<SaveEntityIn> getPreSaveFunction() {
		LOGGER.debug("Returning pre save function {}", preSaveFunction.getName());
		return preSaveFunction;
	}

	@NotNull
	protected PosSaveFunction<SaveEntityOut> getPosSaveFunction() {
		LOGGER.debug("Returning pos save function {}", posSaveFunction.getName());
		return posSaveFunction;
	}

	@NotNull
	protected ErrorSaveFunction<SaveEntityIn> getErrorSaveFunction() {
		LOGGER.debug("Returning error save function {}", errorSaveFunction.getName());
		return errorSaveFunction;
	}

	@NotNull
	protected PreSaveAllFunction<SaveEntitiesIn> getPreSaveAllFunction() {
		LOGGER.debug("Returning pre save all function {}", preSaveAllFunction.getName());
		return preSaveAllFunction;
	}

	@NotNull
	protected PosSaveAllFunction<SaveEntitiesOut> getPosSaveAllFunction() {
		LOGGER.debug("Returning pos save all function {}", posSaveAllFunction.getName());
		return posSaveAllFunction;
	}

	@NotNull
	protected ErrorSaveAllFunction<SaveEntitiesIn> getErrorSaveAllFunction() {
		LOGGER.debug("Returning error save all function {}", errorSaveAllFunction.getName());
		return errorSaveAllFunction;
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
	protected UpdateEntitiesIn preUpdateAll(final UpdateEntitiesIn updateEntitiesIn, final Object... directives) {
		LOGGER.debug("Default preUpdateAll, updateEntityIn {}, directives {}", updateEntitiesIn, directives);
		return updateEntitiesIn;
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
	protected UpdateEntitiesOut posUpdateAll(
			final UpdateEntitiesOut updateEntitiesOut,
			final Object... directives) {
		LOGGER.debug("Default posUpdateAll, updateEntitiesOut {}, directives {} ",
				updateEntitiesOut, directives);
		return updateEntitiesOut;
	}

	/**
	 * Method executed in {@link CommandFacade#updateAll(Object, Object...)
	 * updateAll} method to handle {@link CommandFacade#updateAllFunction
	 * updateAllFunction} errors.
	 * 
	 * @param exception        Exception thrown by update all operation
	 * @param updateEntitiesIn The objects you tried to update all on the
	 *                         persistence mechanism
	 * @param directives       Objects used to configure the update all operation
	 * 
	 * @return The handled exception
	 */
	protected BaseException errorUpdateAll(
			final BaseException exception,
			final UpdateEntitiesIn updateEntitiesIn,
			final Object... directives) {
		LOGGER.debug("Default errorUpdateAll, updateEntitiesIn {}, exception {}, directives {} ",
				updateEntitiesIn, exception, directives);
		return exception;
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
	protected DeleteEntityIn preDelete(final DeleteEntityIn deleteEntityIn, final Object... directives) {
		LOGGER.debug("Default preDelete, deleteEntityIn {}, directives {} ", deleteEntityIn, directives);
		return deleteEntityIn;
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
	protected DeleteEntityOut posDelete(final DeleteEntityOut deleteEntityOut, final Object... directives) {
		LOGGER.debug("Default posDelete, deleteEntityOut {}, directives {} ", deleteEntityOut, directives);
		return deleteEntityOut;
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
	protected BaseException errorDelete(
			final BaseException exception,
			final DeleteEntityIn deleteEntityIn,
			final Object... directives) {
		LOGGER.debug("Default errorDelete, deleteEntityIn {}, exception {}, directives {}",
				deleteEntityIn, exception, directives);
		return exception;
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
	protected DeleteEntitiesIn preDeleteAll(final DeleteEntitiesIn deleteEntitiesIn, final Object... directives) {
		LOGGER.debug("Default preDeleteAll, deleteEntityIn {}, directives {}", deleteEntitiesIn, directives);
		return deleteEntitiesIn;
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
	protected DeleteEntitiesOut posDeleteAll(
			final DeleteEntitiesOut deleteEntitiesOut,
			final Object... directives) {
		LOGGER.debug("Executing default posDeleteAll, deleteEntitiesOut {}, directives {}",
				deleteEntitiesOut, directives);
		return deleteEntitiesOut;
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
	protected BaseException errorDeleteAll(
			final BaseException exception,
			final DeleteEntitiesIn deleteEntitiesIn,
			final Object... directives) {
		LOGGER.debug("Executing default errorDeleteAll, deleteEntitiesIn {}, exception {}, directives {} ",
				deleteEntitiesIn, exception, directives);
		return exception;
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
	protected DeleteIdIn preDeleteBy(final DeleteIdIn deleteIdIn, final Object... directives) {
		LOGGER.debug("Default preDeleteBy, deleteIdIn {}, directives {} ", deleteIdIn, directives);
		return deleteIdIn;
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
	protected DeleteIdOut posDeleteBy(final DeleteIdOut deleteIdOut, final Object... directives) {
		LOGGER.debug("Default posDeleteBy, deleteIdOut {}, directives {}", deleteIdOut, directives);
		return deleteIdOut;
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
	protected BaseException errorDeleteBy(
			final BaseException exception,
			final DeleteIdIn deleteIdIn,
			final Object... directives) {
		LOGGER.debug("Default errorDeleteBy, deleteIdIn {}, exception {}, directives {}",
				deleteIdIn, exception, directives);
		return exception;
	}

	/**
	 * Method executed in {@link CommandFacade#deleteAllBy(Object, Object...)
	 * deleteAllBy} method before the {@link CommandFacade#deleteByIdsFunction
	 * deleteAllByIdFunction}, use it to configure, change, etc. the input.
	 * 
	 * @param deleteIdsIn The object's ids you want to deleteBy on the persistence
	 *                    mechanism
	 * @param directives  Objects used to configure the deleteBy operation
	 * 
	 * @return A {@code DeleteIdsIn} object
	 */
	protected DeleteIdsIn preDeleteAllBy(final DeleteIdsIn deleteIdsIn, final Object... directives) {
		LOGGER.debug("Default preDeleteAllBy, deleteIdsIn {}, directives {}", deleteIdsIn, directives);
		return deleteIdsIn;
	}

	/**
	 * Method executed in {@link CommandFacade#deleteAllBy(Object, Object...)
	 * deleteAllBy} method after {@link CommandFacade#deleteByIdsFunction
	 * deleteAllByIdFunction}, use it to change values.
	 * 
	 * @param deleteIdsOut The object's ids you deleted on the persistence mechanism
	 * @param directives   Objects used to configure the delete operation
	 * 
	 * @return A {@code DeleteIdsOut} object
	 */
	protected DeleteIdsOut posDeleteAllBy(final DeleteIdsOut deleteIdsOut, final Object... directives) {
		LOGGER.debug("Default posDeleteAllBy, deleteIdsOut {}, directives {}", deleteIdsOut, directives);
		return deleteIdsOut;
	}

	/**
	 * Method executed in {@link CommandFacade#deleteAllBy(Object, Object...)
	 * deleteAllBy} method to handle {@link CommandFacade#deleteByIdsFunction
	 * deleteAllByIdFunction} errors.
	 * 
	 * @param deleteIdsIn The object's ids you tried to delete on the persistence
	 *                    mechanism
	 * @param exception   Exception thrown by delete by id operation
	 * @param directives  Objects used to configure the delete operation
	 * 
	 * @return The handled exception
	 */
	protected final BaseException errorDeleteAllBy(
			final BaseException exception,
			final DeleteIdsIn deleteIdsIn,
			final Object... directives) {
		LOGGER.debug("Executing default errorDeleteAllBy, deleteIdsIn {}, exception {}, directives {}",
				deleteIdsIn, exception, directives);
		return exception;
	}
}
