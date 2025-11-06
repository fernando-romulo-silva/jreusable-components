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
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
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
    }

    private PreSaveFunction<SaveEntityIn> getPreSaveFunction(final PreSaveFunction<SaveEntityIn> preSaveFunction) {
        return nonNull(preSaveFunction)
                ? preSaveFunction
                : (saveEntityIn, directives) -> {
                    LOGGER.debug("Default preSave, saveEntityIn {}, directives {}",
                            saveEntityIn, directives);
                    return saveEntityIn;
                };
    }

    private PosSaveFunction<SaveEntityOut> getPosSaveFunction(final PosSaveFunction<SaveEntityOut> posSaveFunction) {
        return nonNull(posSaveFunction)
                ? posSaveFunction
                : (saveEntityOut, directives) -> {
                    LOGGER.debug("Default posSave, saveEntityOut {}, directives {}", saveEntityOut, directives);
                    return saveEntityOut;
                };
    }

    private ErrorSaveFunction<SaveEntityIn> getErrorSaveFunction(
            final ErrorSaveFunction<SaveEntityIn> errorSaveFunction) {
        return nonNull(errorSaveFunction)
                ? errorSaveFunction
                : (exception, saveEntityIn, directives) -> {
                    LOGGER.debug("Default errorSave, saveEntityIn {}, exception {}, directives {}",
                            saveEntityIn, exception, directives);
                    return exception;
                };
    }

    private PreSaveAllFunction<SaveEntitiesIn> getPreSaveAllFunction(
            final PreSaveAllFunction<SaveEntitiesIn> preSaveAllFunction) {
        return nonNull(preSaveAllFunction)
                ? preSaveAllFunction
                : (saveEntitiesIn, directives) -> {
                    LOGGER.debug("Default preSaveAll, saveEntiesIn {}, directives {}", saveEntitiesIn, directives);
                    return saveEntitiesIn;
                };
    }

    private PosSaveAllFunction<SaveEntitiesOut> getPosSaveAllFunction(
            final PosSaveAllFunction<SaveEntitiesOut> posSaveAllFunction) {
        return nonNull(posSaveAllFunction)
                ? posSaveAllFunction
                : (saveEntitiesOut, directives) -> {
                    LOGGER.debug("Default posSaveAll, saveEntiesOut {}, directives {}", saveEntitiesOut, directives);
                    return saveEntitiesOut;
                };
    }

    private ErrorSaveAllFunction<SaveEntitiesIn> getErrorSaveAllFunction(
            final ErrorSaveAllFunction<SaveEntitiesIn> errorSaveAllFunction) {
        return nonNull(errorSaveAllFunction)
                ? errorSaveAllFunction
                : (exception, saveEntitiesIn, directives) -> {
                    LOGGER.debug("Default errorSaveAll, saveEntitiesIn {}, exception {}, directives {}",
                            saveEntitiesIn, exception, directives);
                    return exception;
                };
    }

    private PreUpdateFunction<UpdateEntityIn> getPreUpdateFunction(
            final PreUpdateFunction<UpdateEntityIn> preUpdateFunction) {
        return nonNull(preUpdateFunction)
                ? preUpdateFunction
                : (updateEntityIn, directives) -> {
                    LOGGER.debug("Default preUpdate, updateEntityIn {}, directives {}", updateEntityIn, directives);
                    return updateEntityIn;
                };
    }

    private PosUpdateFunction<UpdateEntityOut> getPosUpdateFunction(
            final PosUpdateFunction<UpdateEntityOut> posUpdateFunction) {
        return nonNull(posUpdateFunction)
                ? posUpdateFunction
                : (updateEntityOut, directives) -> {
                    LOGGER.debug("Executing default preUpdate, updateEntityOut {}, directives {} ", updateEntityOut,
                            directives);
                    return updateEntityOut;
                };
    }

    private ErrorUpdateFunction<UpdateEntityIn> getErrorUpdateFunction(
            final ErrorUpdateFunction<UpdateEntityIn> errorUpdateFunction) {
        return nonNull(errorUpdateFunction)
                ? errorUpdateFunction
                : (exception, updateEntityIn, directives) -> {
                    LOGGER.debug("Executing default errorUpdate, updateEntityIn {}, exception {}, directives {} ",
                            updateEntityIn, exception, directives);
                    return exception;
                };
    }
}
