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

abstract class AbstractCommandFacadeBuilder<Entity extends AbstractEntity<Id>, Id, // basic
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
		extends BaseFacadeBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCommandFacadeBuilder.class);

	public PreSaveFunction<SaveEntityIn> preSaveFunction;

	public PosSaveFunction<SaveEntityOut> posSaveFunction;

	public ErrorSaveFunction<SaveEntityIn> errorSaveFunction;

	public PreSaveAllFunction<SaveEntitiesIn> preSaveAllFunction;

	public PosSaveAllFunction<SaveEntitiesOut> posSaveAllFunction;

	public ErrorSaveAllFunction<SaveEntitiesIn> errorSaveAllFunction;

	public PreUpdateFunction<UpdateEntityIn> preUpdateFunction;

	public PosUpdateFunction<UpdateEntityOut> posUpdateFunction;

	public ErrorUpdateFunction<UpdateEntityIn> errorUpdateFunction;

	public PreUpdateAllFunction<UpdateEntitiesIn> preUpdateAllFunction;

	public PosUpdateAllFunction<UpdateEntitiesOut> posUpdateAllFunction;

	public ErrorUpdateAllFunction<UpdateEntitiesIn> errorUpdateAllFunction;

	public PreDeleteFunction<DeleteEntityIn> preDeleteFunction;

	public PosDeleteFunction<DeleteEntityOut> posDeleteFunction;

	public ErrorDeleteFunction<DeleteEntityIn> errorDeleteFunction;

	public PreDeleteAllFunction<DeleteEntitiesIn> preDeleteAllFunction;

	public PosDeleteAllFunction<DeleteEntitiesOut> posDeleteAllFunction;

	public ErrorDeleteAllFunction<DeleteEntitiesIn> errorDeleteAllFunction;

	public PreDeleteByIdFunction<DeleteIdIn> preDeleteByIdFunction;

	public PosDeleteByIdFunction<DeleteIdOut> posDeleteByIdFunction;

	public ErrorDeleteByIdFunction<DeleteIdIn> errorDeleteByIdFunction;

	public PreDeleteByIdsFunction<DeleteIdsIn> preDeleteByIdsFunction;

	public PosDeleteByIdsFunction<DeleteIdsOut> posDeleteByIdsFunction;

	public ErrorDeleteByIdsFunction<DeleteIdsIn> errorDeleteByIdsFunction;

	protected AbstractCommandFacadeBuilder(
			final Consumer<? extends AbstractCommandFacadeBuilder<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut>> builder) {
		LOGGER.debug("Constructing AbstractCommandFacadeBuilder {}", builder);
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

	private PreDeleteFunction<DeleteEntityIn> getPreDeleteFunction() {
		return nonNull(preDeleteFunction)
				? preDeleteFunction
				: (deleteEntityIn, directives) -> {
					LOGGER.debug("Default preDeleteFunction, deleteEntityIn {}, directives {}",
							deleteEntityIn, directives);
					return deleteEntityIn;
				};
	}

	private PosDeleteFunction<DeleteEntityOut> getPosDeleteFunction() {
		return nonNull(posDeleteFunction)
				? posDeleteFunction
				: (deleteEntityOut, directives) -> {
					LOGGER.debug("Default posDeleteFunction, deleteEntityOut {}, directives {}",
							deleteEntityOut, directives);
					return deleteEntityOut;
				};
	}

	private ErrorDeleteFunction<DeleteEntityIn> getErrorDeleteFunction() {
		return nonNull(errorDeleteFunction)
				? errorDeleteFunction
				: (exception, deleteEntityIn, directives) -> {
					LOGGER.debug("Default errorDeleteFunction, exception {}, deleteEntityIn {}, directives {}",
							deleteEntityIn, exception, directives);
					return exception;
				};
	}

	private PreSaveFunction<SaveEntityIn> getPreSaveFunction() {
		return nonNull(preSaveFunction)
				? preSaveFunction
				: (saveEntityIn, directives) -> {
					LOGGER.debug("Default preSaveFunction, saveEntityIn {}, directives {}", saveEntityIn, directives);
					return saveEntityIn;
				};
	}

	private PosSaveFunction<SaveEntityOut> getPosSaveFunction() {
		return nonNull(posSaveFunction)
				? posSaveFunction
				: (saveEntityOut, directives) -> {
					LOGGER.debug("Default posSaveFunction, saveEntityOut {}, directives {}",
							saveEntityOut, directives);
					return saveEntityOut;
				};
	}

	private ErrorSaveFunction<SaveEntityIn> getErrorSaveFunction() {
		return nonNull(errorSaveFunction)
				? errorSaveFunction
				: (exception, saveEntityIn, directives) -> {
					LOGGER.debug("Default errorSaveFunction, saveEntityIn {}, exception {}, directives {}",
							saveEntityIn, exception, directives);
					return exception;
				};
	}

	private PreSaveAllFunction<SaveEntitiesIn> getPreSaveAllFunction() {
		return nonNull(preSaveAllFunction)
				? preSaveAllFunction
				: (saveEntitiesIn, directives) -> {
					LOGGER.debug("Default preSaveAllFunction, saveEntiesIn {}, directives {}",
							saveEntitiesIn, directives);
					return saveEntitiesIn;
				};
	}

	private PosSaveAllFunction<SaveEntitiesOut> getPosSaveAllFunction() {
		return nonNull(posSaveAllFunction)
				? posSaveAllFunction
				: (saveEntitiesOut, directives) -> {
					LOGGER.debug("Default posSaveAllFunction, saveEntiesOut {}, directives {}",
							saveEntitiesOut, directives);
					return saveEntitiesOut;
				};
	}

	private ErrorSaveAllFunction<SaveEntitiesIn> getErrorSaveAllFunction() {
		return nonNull(errorSaveAllFunction)
				? errorSaveAllFunction
				: (exception, saveEntitiesIn, directives) -> {
					LOGGER.debug("Default errorSaveAllFunction, exception {}, saveEntitiesIn {}, directives {}",
							exception, saveEntitiesIn, directives);
					return exception;
				};
	}

	private PreUpdateFunction<UpdateEntityIn> getPreUpdateFunction() {
		return nonNull(preUpdateFunction)
				? preUpdateFunction
				: (updateEntityIn, directives) -> {
					LOGGER.debug("Default preUpdateFunction, updateEntityIn {}, directives {}",
							updateEntityIn, directives);
					return updateEntityIn;
				};
	}

	private PosUpdateFunction<UpdateEntityOut> getPosUpdateFunction() {
		return nonNull(posUpdateFunction)
				? posUpdateFunction
				: (updateEntityOut, directives) -> {
					LOGGER.debug("Default posUpdateFunction, updateEntityOut {}, directives {}",
							updateEntityOut, directives);
					return updateEntityOut;
				};
	}

	private ErrorUpdateFunction<UpdateEntityIn> getErrorUpdateFunction() {
		return nonNull(errorUpdateFunction)
				? errorUpdateFunction
				: (exception, updateEntityIn, directives) -> {
					LOGGER.debug("Default errorUpdate, exception {}, updateEntityIn {}, directives {}",
							exception, updateEntityIn, directives);
					return exception;
				};
	}

	private PreUpdateAllFunction<UpdateEntitiesIn> getPreUpdateAllFunction() {
		return nonNull(preUpdateAllFunction)
				? preUpdateAllFunction
				: (updateEntitiesIn, directives) -> {
					LOGGER.debug("Default preUpdateAllFunction, updateEntityIn {}, directives {}",
							updateEntitiesIn, directives);
					return updateEntitiesIn;
				};
	}

	private PosUpdateAllFunction<UpdateEntitiesOut> getPosUpdateAllFunction() {
		return nonNull(posUpdateAllFunction)
				? posUpdateAllFunction
				: (final UpdateEntitiesOut updateEntitiesOut,
						final Object... directives) -> {
					LOGGER.debug("Default posUpdateAllFunction, updateEntitiesOut {}, directives {}",
							updateEntitiesOut, directives);
					return updateEntitiesOut;
				};
	}

	private ErrorUpdateAllFunction<UpdateEntitiesIn> getErrorUpdateAllFunction() {
		return nonNull(errorUpdateAllFunction)
				? errorUpdateAllFunction
				: (exception, updateEntitiesIn, directives) -> {
					LOGGER.debug("Default errorUpdateAllFunction, updateEntitiesIn {}, exception {}, directives {}",
							updateEntitiesIn, exception, directives);
					return exception;
				};
	}

	private PreDeleteAllFunction<DeleteEntitiesIn> getPreDeleteAllFunction() {
		return nonNull(preDeleteAllFunction)
				? preDeleteAllFunction
				: (deleteEntitiesIn, directives) -> {
					LOGGER.debug("Default preDeleteAllFunction, deleteEntityIn {}, directives {}", deleteEntitiesIn,
							directives);
					return deleteEntitiesIn;
				};
	}

	private PosDeleteAllFunction<DeleteEntitiesOut> getPosDeleteAllFunction() {
		return nonNull(posDeleteAllFunction)
				? posDeleteAllFunction
				: (deleteEntitiesOut, directives) -> {
					LOGGER.debug("Executing default posDeleteAllFunction, deleteEntitiesOut {}, directives {}",
							deleteEntitiesOut, directives);
					return deleteEntitiesOut;
				};
	}

	private ErrorDeleteAllFunction<DeleteEntitiesIn> getErrorDeleteAllFunction() {
		return nonNull(errorDeleteAllFunction)
				? errorDeleteAllFunction
				: (exception, deleteEntitiesIn, directives) -> {
					LOGGER.debug(
							"Executing default errorDeleteAllFunction, exception {}, deleteEntitiesIn {}, directives {}",
							exception, deleteEntitiesIn, directives);
					return exception;
				};
	}

	private PreDeleteByIdFunction<DeleteIdIn> getPreDeleteByIdFunction() {
		return nonNull(preDeleteByIdFunction)
				? preDeleteByIdFunction
				: (deleteIdIn, directives) -> {
					LOGGER.debug("Default preDeleteByIdFunction, deleteIdIn {}, directives {}", deleteIdIn, directives);
					return deleteIdIn;
				};
	}

	private PosDeleteByIdFunction<DeleteIdOut> getPosDeleteByIdFunction() {
		return nonNull(posDeleteByIdFunction)
				? posDeleteByIdFunction
				: (deleteIdOut, directives) -> {
					LOGGER.debug("Default posDeleteByIdFunction, deleteIdOut {}, directives {}",
							deleteIdOut, directives);
					return deleteIdOut;
				};
	}

	private ErrorDeleteByIdFunction<DeleteIdIn> getErrorDeleteByIdFunction() {
		return nonNull(errorDeleteByIdFunction)
				? errorDeleteByIdFunction
				: (exception, deleteIdIn, directives) -> {
					LOGGER.debug("Default errorDeleteByIdFunction, exception {}, deleteIdIn {}, directives {}",
							exception, deleteIdIn, directives);
					return exception;
				};
	}

	private PreDeleteByIdsFunction<DeleteIdsIn> getPreDeleteByIdsFunction() {
		return nonNull(preDeleteByIdsFunction)
				? preDeleteByIdsFunction
				: (deleteIdsIn, directives) -> {
					LOGGER.debug("Default preDeleteByIdsFunction, deleteIdsIn {}, directives {}",
							deleteIdsIn, directives);
					return deleteIdsIn;
				};
	}

	private PosDeleteByIdsFunction<DeleteIdsOut> getPosDeleteByIdsFunction() {
		return nonNull(posDeleteByIdsFunction)
				? posDeleteByIdsFunction
				: (deleteIdsOut, directives) -> {
					LOGGER.debug("Default posDeleteByIdsFunction, deleteIdsOut {}, directives {}",
							deleteIdsOut, directives);
					return deleteIdsOut;
				};
	}

	private ErrorDeleteByIdsFunction<DeleteIdsIn> getErrorDeleteByIdsFunction() {
		return nonNull(errorDeleteByIdsFunction)
				? errorDeleteByIdsFunction
				: (exception, deleteIdsIn, directives) -> {
					LOGGER.debug(
							"Executing default errorDeleteByIdsFunction, deleteIdsIn {}, exception {}, directives {}",
							deleteIdsIn, exception, directives);
					return exception;
				};
	}
}
