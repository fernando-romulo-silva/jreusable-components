package org.reusablecomponents.base.core.application.command.entity;

import static java.util.Objects.nonNull;

import java.util.function.Consumer;

import org.reusablecomponents.base.core.application.base.BaseFacadeBuilder;
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

public abstract class AbstractCommandFacadeBuilder<Entity extends AbstractEntity<Id>, Id, // basic
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
			final Consumer<? extends AbstractCommandFacadeBuilder<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut>> function) {
		LOGGER.debug("Constructing AbstractCommandFacadeBuilder {}", function);
		super(function);

		this.preSaveFunction = getPreSaveFunction(preSaveFunction);
		this.posSaveFunction = getPosSaveFunction(posSaveFunction);
		this.errorSaveFunction = getErrorSaveFunction(errorSaveFunction);

		this.preSaveAllFunction = getPreSaveAllFunction(preSaveAllFunction);
		this.posSaveAllFunction = getPosSaveAllFunction(posSaveAllFunction);
		this.errorSaveAllFunction = getErrorSaveAllFunction(errorSaveAllFunction);

		this.preUpdateFunction = getPreUpdateFunction(preUpdateFunction);
		this.posUpdateFunction = getPosUpdateFunction(posUpdateFunction);
		this.errorUpdateFunction = getErrorUpdateFunction(errorUpdateFunction);

		this.preUpdateAllFunction = getPreUpdateAllFunction(preUpdateAllFunction);
		this.posUpdateAllFunction = getPosUpdateAllFunction(posUpdateAllFunction);
		this.errorUpdateAllFunction = getErrorUpdateAllFunction(errorUpdateAllFunction);

		this.preDeleteFunction = getPreDeleteFunction(preDeleteFunction);
		this.posDeleteFunction = getPosDeleteFunction(posDeleteFunction);
		this.errorDeleteFunction = getErrorDeleteFunction(errorDeleteFunction);

		this.preDeleteAllFunction = getDeleteAllFunction(preDeleteAllFunction);
		this.posDeleteAllFunction = getPosDeleteAllFunction(posDeleteAllFunction);
		this.errorDeleteAllFunction = getErrorDeleteAllFunction(errorDeleteAllFunction);

		this.preDeleteByIdFunction = getPreDeleteByIdFunction(preDeleteByIdFunction);
		this.posDeleteByIdFunction = getPosDeleteByIdFunction(posDeleteByIdFunction);
		this.errorDeleteByIdFunction = getErrorDeleteByIdFunction(errorDeleteByIdFunction);

		this.preDeleteByIdsFunction = getPreDeleteByIdsFunction(preDeleteByIdsFunction);
		this.posDeleteByIdsFunction = getPosDeleteByIdsFunction(posDeleteByIdsFunction);
		this.errorDeleteByIdsFunction = getErrorDeleteByIdsFunction(errorDeleteByIdsFunction);
	}

	private PreDeleteFunction<DeleteEntityIn> getPreDeleteFunction(
			PreDeleteFunction<DeleteEntityIn> newPreDeleteFunction) {
		return nonNull(newPreDeleteFunction)
				? newPreDeleteFunction
				: (deleteEntityIn, directives) -> {
					LOGGER.debug("Default preDelete, deleteEntityIn {}, directives {}", deleteEntityIn, directives);
					return deleteEntityIn;
				};
	}

	private PosDeleteFunction<DeleteEntityOut> getPosDeleteFunction(
			final PosDeleteFunction<DeleteEntityOut> newPosDeleteFunction) {
		return nonNull(newPosDeleteFunction)
				? newPosDeleteFunction
				: (deleteEntityOut, directives) -> {
					LOGGER.debug("Default posDelete, deleteEntityOut {}, directives {}", deleteEntityOut, directives);
					return deleteEntityOut;
				};
	}

	private ErrorDeleteFunction<DeleteEntityIn> getErrorDeleteFunction(
			final ErrorDeleteFunction<DeleteEntityIn> newErrorDeleteFunction) {
		return nonNull(newErrorDeleteFunction)
				? newErrorDeleteFunction
				: (exception, deleteEntityIn, directives) -> {
					LOGGER.debug("Default errorDelete, deleteEntityIn {}, exception {}, directives {}",
							deleteEntityIn, exception, directives);
					return exception;
				};
	}

	private PreSaveFunction<SaveEntityIn> getPreSaveFunction(
			final PreSaveFunction<SaveEntityIn> newPreSaveFunction) {
		return nonNull(newPreSaveFunction)
				? newPreSaveFunction
				: (saveEntityIn, directives) -> {
					LOGGER.debug("Default preSave, saveEntityIn {}, directives {}", saveEntityIn, directives);
					return saveEntityIn;
				};
	}

	private PosSaveFunction<SaveEntityOut> getPosSaveFunction(
			final PosSaveFunction<SaveEntityOut> newPosSaveFunction) {
		return nonNull(newPosSaveFunction)
				? newPosSaveFunction
				: (saveEntityOut, directives) -> {
					LOGGER.debug("Default posSave, saveEntityOut {}, directives {}", saveEntityOut,
							directives);
					return saveEntityOut;
				};
	}

	private ErrorSaveFunction<SaveEntityIn> getErrorSaveFunction(
			final ErrorSaveFunction<SaveEntityIn> newErrorSaveFunction) {
		return nonNull(newErrorSaveFunction)
				? newErrorSaveFunction
				: (exception, saveEntityIn, directives) -> {
					LOGGER.debug("Default errorSave, saveEntityIn {}, exception {}, directives {}",
							saveEntityIn, exception, directives);
					return exception;
				};
	}

	private PreSaveAllFunction<SaveEntitiesIn> getPreSaveAllFunction(
			final PreSaveAllFunction<SaveEntitiesIn> newPreSaveAllFunction) {
		return nonNull(newPreSaveAllFunction)
				? newPreSaveAllFunction
				: (saveEntitiesIn, directives) -> {
					LOGGER.debug("Default preSaveAll, saveEntiesIn {}, directives {}",
							saveEntitiesIn, directives);
					return saveEntitiesIn;
				};
	}

	private PosSaveAllFunction<SaveEntitiesOut> getPosSaveAllFunction(
			final PosSaveAllFunction<SaveEntitiesOut> newPosSaveAllFunction) {
		return nonNull(newPosSaveAllFunction)
				? newPosSaveAllFunction
				: (saveEntitiesOut, directives) -> {
					LOGGER.debug("Default posSaveAll, saveEntiesOut {}, directives {}",
							saveEntitiesOut, directives);
					return saveEntitiesOut;
				};
	}

	private ErrorSaveAllFunction<SaveEntitiesIn> getErrorSaveAllFunction(
			final ErrorSaveAllFunction<SaveEntitiesIn> newErrorSaveAllFunction) {
		return nonNull(newErrorSaveAllFunction)
				? newErrorSaveAllFunction
				: (exception, saveEntitiesIn, directives) -> {
					LOGGER.debug("Default errorSaveAll, saveEntitiesIn {}, exception {}, directives {}",
							saveEntitiesIn, exception, directives);
					return exception;
				};
	}

	private PreUpdateFunction<UpdateEntityIn> getPreUpdateFunction(
			final PreUpdateFunction<UpdateEntityIn> newPreUpdateFunction) {
		return nonNull(newPreUpdateFunction)
				? newPreUpdateFunction
				: (updateEntityIn, directives) -> {
					LOGGER.debug("Default preUpdate, updateEntityIn {}, directives {}",
							updateEntityIn, directives);
					return updateEntityIn;
				};
	}

	private PosUpdateFunction<UpdateEntityOut> getPosUpdateFunction(
			final PosUpdateFunction<UpdateEntityOut> newPosUpdateFunction) {
		return nonNull(newPosUpdateFunction)
				? newPosUpdateFunction
				: (updateEntityOut, directives) -> {
					LOGGER.debug("Executing default preUpdate, updateEntityOut {}, directives {}",
							updateEntityOut,
							directives);
					return updateEntityOut;
				};
	}

	private ErrorUpdateFunction<UpdateEntityIn> getErrorUpdateFunction(
			final ErrorUpdateFunction<UpdateEntityIn> newErrorUpdateFunction) {
		return nonNull(newErrorUpdateFunction)
				? newErrorUpdateFunction
				: (exception, updateEntityIn, directives) -> {
					LOGGER.debug("Executing default errorUpdate, updateEntityIn {}, exception {}, directives {}",
							updateEntityIn, exception, directives);
					return exception;
				};
	}

	private PreUpdateAllFunction<UpdateEntitiesIn> getPreUpdateAllFunction(
			final PreUpdateAllFunction<UpdateEntitiesIn> newPreUpdateAllFunction) {
		return nonNull(newPreUpdateAllFunction)
				? newPreUpdateAllFunction
				: (updateEntitiesIn, directives) -> {
					LOGGER.debug("Default preUpdateAll, updateEntityIn {}, directives {}",
							updateEntitiesIn, directives);
					return updateEntitiesIn;
				};
	}

	private PosUpdateAllFunction<UpdateEntitiesOut> getPosUpdateAllFunction(
			final PosUpdateAllFunction<UpdateEntitiesOut> newPosUpdateAllFunction) {
		return nonNull(newPosUpdateAllFunction)
				? newPosUpdateAllFunction
				: (final UpdateEntitiesOut updateEntitiesOut,
						final Object... directives) -> {
					LOGGER.debug("Default posUpdateAll, updateEntitiesOut {}, directives {}",
							updateEntitiesOut, directives);
					return updateEntitiesOut;
				};
	}

	private ErrorUpdateAllFunction<UpdateEntitiesIn> getErrorUpdateAllFunction(
			final ErrorUpdateAllFunction<UpdateEntitiesIn> newErrorUpdateAllFunction) {
		return nonNull(newErrorUpdateAllFunction)
				? newErrorUpdateAllFunction
				: (exception, updateEntitiesIn, directives) -> {
					LOGGER.debug("Default errorUpdateAll, updateEntitiesIn {}, exception {}, directives {}",
							updateEntitiesIn, exception, directives);
					return exception;
				};
	}

	private PreDeleteAllFunction<DeleteEntitiesIn> getDeleteAllFunction(
			final PreDeleteAllFunction<DeleteEntitiesIn> newPreDeleteAllFunction) {
		return nonNull(newPreDeleteAllFunction)
				? newPreDeleteAllFunction
				: (deleteEntitiesIn, directives) -> {
					LOGGER.debug("Default preDeleteAll, deleteEntityIn {}, directives {}", deleteEntitiesIn,
							directives);
					return deleteEntitiesIn;
				};
	}

	private PosDeleteAllFunction<DeleteEntitiesOut> getPosDeleteAllFunction(
			final PosDeleteAllFunction<DeleteEntitiesOut> posDeleteAllFunction) {
		return nonNull(posDeleteAllFunction)
				? posDeleteAllFunction
				: (deleteEntitiesOut, directives) -> {
					LOGGER.debug("Executing default posDeleteAll, deleteEntitiesOut {}, directives {}",
							deleteEntitiesOut, directives);
					return deleteEntitiesOut;
				};
	}

	private ErrorDeleteAllFunction<DeleteEntitiesIn> getErrorDeleteAllFunction(
			final ErrorDeleteAllFunction<DeleteEntitiesIn> errorDeleteAllFunction) {
		return nonNull(errorDeleteAllFunction)
				? errorDeleteAllFunction
				: (exception, deleteEntitiesIn, directives) -> {
					LOGGER.debug("Executing default errorDeleteAll, deleteEntitiesIn {}, exception {}, directives {}",
							deleteEntitiesIn, exception, directives);
					return exception;
				};
	}

	private PreDeleteByIdFunction<DeleteIdIn> getPreDeleteByIdFunction(
			final PreDeleteByIdFunction<DeleteIdIn> newPreDeleteByIdFunction) {
		return nonNull(newPreDeleteByIdFunction)
				? newPreDeleteByIdFunction
				: (deleteIdIn, directives) -> {
					LOGGER.debug("Default preDeleteBy, deleteIdIn {}, directives {}", deleteIdIn, directives);
					return deleteIdIn;
				};
	}

	private PosDeleteByIdFunction<DeleteIdOut> getPosDeleteByIdFunction(
			final PosDeleteByIdFunction<DeleteIdOut> newPosDeleteByIdFunction) {
		return nonNull(newPosDeleteByIdFunction)
				? newPosDeleteByIdFunction
				: (deleteIdOut, directives) -> {
					LOGGER.debug("Default posDeleteBy, deleteIdOut {}, directives {}", deleteIdOut, directives);
					return deleteIdOut;
				};
	}

	private ErrorDeleteByIdFunction<DeleteIdIn> getErrorDeleteByIdFunction(
			final ErrorDeleteByIdFunction<DeleteIdIn> errorDeleteByIdFunction) {
		return nonNull(errorDeleteByIdFunction)
				? errorDeleteByIdFunction
				: (exception, deleteIdIn, directives) -> {
					LOGGER.debug("Default errorDeleteBy, deleteIdIn {}, exception {}, directives {}",
							deleteIdIn, exception, directives);
					return exception;
				};
	}

	private PreDeleteByIdsFunction<DeleteIdsIn> getPreDeleteByIdsFunction(
			final PreDeleteByIdsFunction<DeleteIdsIn> newPreDeleteByIdsFunction) {
		return nonNull(newPreDeleteByIdsFunction)
				? newPreDeleteByIdsFunction
				: (deleteIdsIn, directives) -> {
					LOGGER.debug("Default preDeleteAllBy, deleteIdsIn {}, directives {}", deleteIdsIn, directives);
					return deleteIdsIn;
				};
	}

	private PosDeleteByIdsFunction<DeleteIdsOut> getPosDeleteByIdsFunction(
			final PosDeleteByIdsFunction<DeleteIdsOut> newPosDeleteByIdsFunction) {
		return nonNull(newPosDeleteByIdsFunction)
				? newPosDeleteByIdsFunction
				: (deleteIdsOut, directives) -> {
					LOGGER.debug("Default posDeleteAllBy, deleteIdsOut {}, directives {}", deleteIdsOut, directives);
					return deleteIdsOut;
				};
	}

	private ErrorDeleteByIdsFunction<DeleteIdsIn> getErrorDeleteByIdsFunction(
			final ErrorDeleteByIdsFunction<DeleteIdsIn> errorDeleteByIdsFunction) {
		return nonNull(errorDeleteByIdsFunction)
				? errorDeleteByIdsFunction
				: (exception, deleteIdsIn, directives) -> {
					LOGGER.debug("Executing default errorDeleteAllBy, deleteIdsIn {}, exception {}, directives {}",
							deleteIdsIn, exception, directives);
					return exception;
				};
	}
}
