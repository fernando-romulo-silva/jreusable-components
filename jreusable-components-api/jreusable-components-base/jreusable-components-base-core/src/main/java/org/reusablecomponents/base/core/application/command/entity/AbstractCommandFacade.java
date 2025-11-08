package org.reusablecomponents.base.core.application.command.entity;

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

	/**
	 * Function executed in {@link CommandFacade#updateAll(Object, Object...)
	 * updateAll} method before the {@link CommandFacade#updateAllFunction
	 * updateAllFunction}, use it to change values.
	 */
	protected final PreUpdateAllFunction<UpdateEntitiesIn> preUpdateAllFunction;

	/**
	 * Function executed in {@link CommandFacade#updateAll(Object, Object...)
	 * updateAll} method after {@link CommandFacade#updateAllFunction
	 * updateAllFunction}, use it to configure, change, etc. the output.
	 */
	protected final PosUpdateAllFunction<UpdateEntitiesOut> posUpdateAllFunction;

	/**
	 * Function executed in {@link CommandFacade#updateAll(Object, Object...)
	 * updateAll} method to handle {@link CommandFacade#updateAllFunction
	 * updateAllFunction} errors.
	 */
	protected final ErrorUpdateAllFunction<UpdateEntitiesIn> errorUpdateAllFunction;

	/**
	 * Function executed in {@link CommandFacade#delete(Object, Object...) delete}
	 * method before the {@link CommandFacade#deleteFunction deleteFunction}, use it
	 * to configure, change, etc. the input.
	 */
	protected final PreDeleteFunction<DeleteEntityIn> preDeleteFunction;

	/**
	 * Function executed in {@link CommandFacade#delete(Object, Object...) delete}
	 * method after {@link CommandFacade#deleteFunction deleteFunction}, use it to
	 * configure, change, etc. the output.
	 */
	protected final PosDeleteFunction<DeleteEntityOut> posDeleteFunction;

	/**
	 * Function executed in {@link CommandFacade#delete(Object, Object...) delete}
	 * method to handle {@link CommandFacade#deleteFunction deleteFunction} errors.
	 */
	protected final ErrorDeleteFunction<DeleteEntityIn> errorDeleteFunction;

	/**
	 * Function executed in {@link CommandFacade#deleteAll(Object, Object...)
	 * deleteAll} method before the {@link CommandFacade#deleteAllFunction
	 * deleteAllFunction}, use it to change values.
	 */
	protected final PreDeleteAllFunction<DeleteEntitiesIn> preDeleteAllFunction;

	/**
	 * Function executed in {@link CommandFacade#deleteAll(Object, Object...)
	 * deleteAll} method after {@link CommandFacade#deleteAllFunction
	 * deleteAllFunction}, use it to configure, change, etc. the output.
	 */
	protected final PosDeleteAllFunction<DeleteEntitiesOut> posDeleteAllFunction;

	/**
	 * Function executed in {@link CommandFacade#deleteAll(Object, Object...)
	 * deleteAll} method to handle {@link CommandFacade#deleteAllFunction
	 * deleteAllFunction} errors.
	 */
	protected final ErrorDeleteAllFunction<DeleteEntitiesIn> errorDeleteAllFunction;

	/**
	 * Function executed in {@link CommandFacade#deleteBy(Object, Object...)
	 * deleteBy} method before the {@link CommandFacade#deleteByIdFunction
	 * deleteByIdFunction}, use it to change values.
	 */
	protected final PreDeleteByIdFunction<DeleteIdIn> preDeleteByIdFunction;

	/**
	 * Function executed in {@link CommandFacade#deleteBy(Object, Object...)
	 * deleteBy} method after {@link CommandFacade#deleteByIdFunction
	 * deleteByIdFunction}, use it to configure, change, etc. the input.
	 */
	protected final PosDeleteByIdFunction<DeleteIdOut> posDeleteByIdFunction;

	/**
	 * Function executed in {@link CommandFacade#deleteBy(Object, Object...)
	 * deleteBy} method to handle {@link CommandFacade#deleteByIdFunction
	 * deleteByIdFunction} errors.
	 */
	protected final ErrorDeleteByIdFunction<DeleteIdIn> errorDeleteByIdFunction;

	/**
	 * Function executed in {@link CommandFacade#deleteAllBy(Object, Object...)
	 * deleteAllBy} method before the {@link CommandFacade#deleteByIdsFunction
	 * deleteAllByIdFunction}, use it to configure, change, etc. the input.
	 */
	protected final PreDeleteByIdsFunction<DeleteIdsIn> preDeleteByIdsFunction;

	/**
	 * Function executed in {@link CommandFacade#deleteAllBy(Object, Object...)
	 * deleteAllBy} method after {@link CommandFacade#deleteByIdsFunction
	 * deleteAllByIdFunction}, use it to change values.
	 */
	protected final PosDeleteByIdsFunction<DeleteIdsOut> posDeleteByIdsFunction;

	/**
	 * Function executed in {@link CommandFacade#deleteAllBy(Object, Object...)
	 * deleteAllBy} method to handle {@link CommandFacade#deleteByIdsFunction
	 * deleteAllByIdFunction} errors.
	 */
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
		LOGGER.debug("Returning preSaveFunction function {}", preSaveFunction.getName());
		return preSaveFunction;
	}

	@NotNull
	protected PosSaveFunction<SaveEntityOut> getPosSaveFunction() {
		LOGGER.debug("Returning posSaveFunction function {}", posSaveFunction.getName());
		return posSaveFunction;
	}

	@NotNull
	protected ErrorSaveFunction<SaveEntityIn> getErrorSaveFunction() {
		LOGGER.debug("Returning errorSaveFunction function {}", errorSaveFunction.getName());
		return errorSaveFunction;
	}

	@NotNull
	protected PreSaveAllFunction<SaveEntitiesIn> getPreSaveAllFunction() {
		LOGGER.debug("Returning preSaveAllFunction function {}", preSaveAllFunction.getName());
		return preSaveAllFunction;
	}

	@NotNull
	protected PosSaveAllFunction<SaveEntitiesOut> getPosSaveAllFunction() {
		LOGGER.debug("Returning posSaveAllFunction function {}", posSaveAllFunction.getName());
		return posSaveAllFunction;
	}

	@NotNull
	protected ErrorSaveAllFunction<SaveEntitiesIn> getErrorSaveAllFunction() {
		LOGGER.debug("Returning eerrorSaveAllFunction function {}", errorSaveAllFunction.getName());
		return errorSaveAllFunction;
	}

	@NotNull
	protected PreUpdateFunction<UpdateEntityIn> getPreUpdateFunction() {
		LOGGER.debug("Returning error preUpdateFunction function {}", preUpdateFunction.getName());
		return preUpdateFunction;
	}

	@NotNull
	protected PosUpdateFunction<UpdateEntityOut> getPosUpdateFunction() {
		LOGGER.debug("Returning error posUpdateFunction function {}", posUpdateFunction.getName());
		return posUpdateFunction;
	}

	@NotNull
	protected ErrorUpdateFunction<UpdateEntityIn> getErrorUpdateFunction() {
		LOGGER.debug("Returning error errorUpdateFunction function {}", errorUpdateFunction.getName());
		return errorUpdateFunction;
	}

	@NotNull
	protected PreUpdateAllFunction<UpdateEntitiesIn> getPreUpdateAllFunction() {
		LOGGER.debug("Returning error preUpdateAllFunction function {}", preUpdateAllFunction.getName());
		return preUpdateAllFunction;
	}

	@NotNull
	protected PosUpdateAllFunction<UpdateEntitiesOut> getPosUpdateAllFunction() {
		LOGGER.debug("Returning error posUpdateAllFunction function {}", posUpdateAllFunction.getName());
		return posUpdateAllFunction;
	}

	@NotNull
	protected ErrorUpdateAllFunction<UpdateEntitiesIn> getErrorUpdateAllFunction() {
		LOGGER.debug("Returning error errorUpdateAllFunction function {}", errorUpdateAllFunction.getName());
		return errorUpdateAllFunction;
	}

	@NotNull
	protected PreDeleteFunction<DeleteEntityIn> getPreDeleteFunction() {
		LOGGER.debug("Returning error preDeleteFunction function {}", preDeleteFunction.getName());
		return preDeleteFunction;
	}

	@NotNull
	protected PosDeleteFunction<DeleteEntityOut> getPosDeleteFunction() {
		LOGGER.debug("Returning error posDeleteFunction function {}", posDeleteFunction.getName());
		return posDeleteFunction;
	}

	@NotNull
	protected ErrorDeleteFunction<DeleteEntityIn> getErrorDeleteFunction() {
		LOGGER.debug("Returning error errorDeleteFunction function {}", errorDeleteFunction.getName());
		return errorDeleteFunction;
	}

	@NotNull
	protected PreDeleteAllFunction<DeleteEntitiesIn> getPreDeleteAllFunction() {
		LOGGER.debug("Returning error preDeleteAllFunction function {}", preDeleteAllFunction.getName());
		return preDeleteAllFunction;
	}

	@NotNull
	protected PosDeleteAllFunction<DeleteEntitiesOut> getPosDeleteAllFunction() {
		LOGGER.debug("Returning error posDeleteAllFunction function {}", posDeleteAllFunction.getName());
		return posDeleteAllFunction;
	}

	@NotNull
	protected ErrorDeleteAllFunction<DeleteEntitiesIn> getErrorDeleteAllFunction() {
		LOGGER.debug("Returning error errorDeleteAllFunction function {}", errorDeleteAllFunction.getName());
		return errorDeleteAllFunction;
	}

	@NotNull
	protected PreDeleteByIdFunction<DeleteIdIn> getPreDeleteByIdFunction() {
		LOGGER.debug("Returning error preDeleteByIdFunction function {}", preDeleteByIdFunction.getName());
		return preDeleteByIdFunction;
	}

	@NotNull
	protected PosDeleteByIdFunction<DeleteIdOut> getPosDeleteByIdFunction() {
		LOGGER.debug("Returning error posDeleteByIdFunction function {}", posDeleteByIdFunction.getName());
		return posDeleteByIdFunction;
	}

	@NotNull
	protected ErrorDeleteByIdFunction<DeleteIdIn> getErrorDeleteByIdFunction() {
		LOGGER.debug("Returning error errorDeleteByIdFunction function {}", errorDeleteByIdFunction.getName());
		return errorDeleteByIdFunction;
	}

	@NotNull
	protected PreDeleteByIdsFunction<DeleteIdsIn> getPreDeleteByIdsFunction() {
		LOGGER.debug("Returning error preDeleteByIdsFunction function {}", preDeleteByIdsFunction.getName());
		return preDeleteByIdsFunction;
	}

	@NotNull
	protected PosDeleteByIdsFunction<DeleteIdsOut> getPosDeleteByIdsFunction() {
		LOGGER.debug("Returning error posDeleteByIdsFunction function {}", posDeleteByIdsFunction.getName());
		return posDeleteByIdsFunction;
	}

	@NotNull
	protected ErrorDeleteByIdsFunction<DeleteIdsIn> getErrorDeleteByIdsFunction() {
		LOGGER.debug("Returning error errorDeleteByIdsFunction function {}", errorDeleteByIdsFunction.getName());
		return errorDeleteByIdsFunction;
	}
}
