package org.reusablecomponents.base.core.application.command.entity;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.function.Consumer;

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

/**
 * The <code>CommandFacade</code> builder's class.
 * 
 * @see CommandFacade
 */
public final class CommandFacadeBuilder<Entity extends AbstractEntity<Id>, Id, // basic
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
		extends AbstractCommandFacadeBuilder<Entity, Id, SaveEntityIn, SaveEntityOut, // save a entity
				SaveEntitiesIn, SaveEntitiesOut, // save entities
				// update
				UpdateEntityIn, UpdateEntityOut, // update a entity
				UpdateEntitiesIn, UpdateEntitiesOut, // update entities
				// delete
				DeleteEntityIn, DeleteEntityOut, // delete a entity
				DeleteEntitiesIn, DeleteEntitiesOut, // delete entities
				// delete by id
				DeleteIdIn, DeleteIdOut, // delete entity by id
				DeleteIdsIn, DeleteIdsOut> {// delete entities by ids

	private static final Logger LOGGER = LoggerFactory.getLogger(CommandFacadeBuilder.class);

	public SaveFunction<SaveEntityIn, SaveEntityOut> saveFunction;
	public SaveAllFunction<SaveEntitiesIn, SaveEntitiesOut> saveAllFunction;

	public UpdateFunction<UpdateEntityIn, UpdateEntityOut> updateFunction;
	public UpdateAllFunction<UpdateEntitiesIn, UpdateEntitiesOut> updateAllFunction;

	public DeleteFunction<DeleteEntityIn, DeleteEntityOut> deleteFunction;
	public DeleteAllFunction<DeleteEntitiesIn, DeleteEntitiesOut> deleteAllFunction;

	public DeleteByIdFunction<DeleteIdIn, DeleteIdOut> deleteByIdFunction;
	public DeleteByIdsFunction<DeleteIdsIn, DeleteIdsOut> deleteByIdsFunction;

	public CommandFacadeBuilder(
			final Consumer<CommandFacadeBuilder<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut>> function) {
		LOGGER.debug("Constructing CommandFacadeBuilder function {} ", function);
		super(function);

		this.saveFunction = getPreSaveFunction();
		this.saveAllFunction = getSaveAllFunction();
		this.updateFunction = getUpdateFunction();
		this.updateAllFunction = getUpdateAllFunction();
		this.deleteFunction = getDeleteFunction();
		this.deleteAllFunction = getDeleteAllFunction();
		this.deleteByIdFunction = getDeleteByIdFunction();
		this.deleteByIdsFunction = getDeleteByIdsFunction();

		LOGGER.debug("CommandFacadeBuilder constructed commands, functions {}",
				List.of(saveFunction, saveAllFunction,
						updateFunction, updateAllFunction,
						deleteFunction, deleteAllFunction,
						deleteByIdFunction, deleteByIdsFunction));
	}

	private SaveFunction<SaveEntityIn, SaveEntityOut> getPreSaveFunction() {
		return nonNull(saveFunction)
				? saveFunction
				: (saveEntityIn, directives) -> {
					throw new UnsupportedOperationException("Unimplemented function 'saveFunction'");
				};
	}

	private SaveAllFunction<SaveEntitiesIn, SaveEntitiesOut> getSaveAllFunction() {
		return nonNull(saveAllFunction)
				? saveAllFunction
				: (saveEntitiesIn, directives) -> {
					throw new UnsupportedOperationException("Unimplemented function 'saveAllFunction'");
				};
	}

	private UpdateFunction<UpdateEntityIn, UpdateEntityOut> getUpdateFunction() {
		return nonNull(updateFunction)
				? updateFunction
				: (updateEntityIn, directives) -> {
					throw new UnsupportedOperationException("Unimplemented function 'updateFunction'");
				};
	}

	private UpdateAllFunction<UpdateEntitiesIn, UpdateEntitiesOut> getUpdateAllFunction() {
		return nonNull(updateAllFunction)
				? updateAllFunction
				: (updateEntitiesIn, directives) -> {
					throw new UnsupportedOperationException("Unimplemented function 'updateAllFunction'");
				};
	}

	private DeleteFunction<DeleteEntityIn, DeleteEntityOut> getDeleteFunction() {
		return nonNull(deleteFunction)
				? deleteFunction
				: (deleteEntityIn, directives) -> {
					throw new UnsupportedOperationException("Unimplemented function 'deleteFunction'");
				};
	}

	private DeleteAllFunction<DeleteEntitiesIn, DeleteEntitiesOut> getDeleteAllFunction() {
		return nonNull(deleteAllFunction)
				? deleteAllFunction
				: (deleteEntitiesIn, directives) -> {
					throw new UnsupportedOperationException("Unimplemented function 'deleteAllFunction'");
				};
	}

	private DeleteByIdFunction<DeleteIdIn, DeleteIdOut> getDeleteByIdFunction() {
		return nonNull(deleteByIdFunction)
				? deleteByIdFunction
				: (deleteIdIn, directives) -> {
					throw new UnsupportedOperationException("Unimplemented function 'deleteByIdFunction'");
				};
	}

	private DeleteByIdsFunction<DeleteIdsIn, DeleteIdsOut> getDeleteByIdsFunction() {
		return nonNull(deleteByIdsFunction)
				? deleteByIdsFunction
				: (deleteIdIn, directives) -> {
					throw new UnsupportedOperationException("Unimplemented function 'deleteByIdsFunction'");
				};
	}
}
