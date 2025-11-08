package org.reusablecomponents.base.core.application.command.entity;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.nonNull;

import java.util.List;
import java.util.function.Consumer;

import org.reusablecomponents.base.core.application.command.function.operation.delete.DeleteAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.DeleteByIdFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.DeleteByIdsFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.DeleteFunction;
import org.reusablecomponents.base.core.application.command.function.operation.save.SaveAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.save.SaveFunction;
import org.reusablecomponents.base.core.application.command.function.operation.update.UpdateAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.update.UpdateFunction;
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

		this.saveFunction = getPreSaveFunction(saveFunction);

		checkNotNull(saveFunction, "Please pass a non-null 'saveFunction'");
		checkNotNull(saveAllFunction, "Please pass a non-null 'saveAllFunction'");
		checkNotNull(updateFunction, "Please pass a non-null 'updateFunction'");
		checkNotNull(updateAllFunction, "Please pass a non-null 'updateAllFunction'");
		checkNotNull(deleteFunction, "Please pass a non-null 'deleteFunction'");
		checkNotNull(deleteAllFunction, "Please pass a non-null 'deleteAllFunction'");
		checkNotNull(deleteByIdFunction, "Please pass a non-null 'deleteByIdFunction'");
		checkNotNull(deleteByIdsFunction, "Please pass a non-null 'deleteByIdsFunction'");

		checkNotNull(saveFunction, "Please pass a non-null 'preSaveFunction'");

		LOGGER.debug("CommandFacadeBuilder constructed commands, functions {}",
				List.of(saveFunction, saveAllFunction,
						updateFunction, updateAllFunction,
						deleteFunction, deleteAllFunction,
						deleteByIdFunction, deleteByIdsFunction));
	}

	private SaveFunction<SaveEntityIn, SaveEntityOut> getPreSaveFunction(
			final SaveFunction<SaveEntityIn, SaveEntityOut> saveFunction) {
		return nonNull(saveFunction)
				? saveFunction
				: (saveEntityIn, directives) -> {
					throw new UnsupportedOperationException("Unimplemented function 'saveFunction'");
				};
	}
}
