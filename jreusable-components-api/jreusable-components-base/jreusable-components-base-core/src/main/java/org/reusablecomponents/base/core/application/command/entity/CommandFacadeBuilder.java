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
 * This class is responsible for building the <code>CommandFacade</code> object.
 * 
 * For each function in this class, if it is not set, it will be set with a
 * default function that throws an UnsupportedOperationException with a message
 * that the function is not implemented, example: "Unimplemented function
 * 'functionName'".
 * 
 * @see CommandFacade
 */
public non-sealed class CommandFacadeBuilder<Entity extends AbstractEntity<Id>, Id, // basic
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

	/**
	 * Function that executes save operation
	 * 
	 * @see SaveFunction
	 */
	public SaveFunction<SaveEntityIn, SaveEntityOut> saveFunction;

	/**
	 * Function that executes save all operation
	 * 
	 * @see SaveAllFunction
	 */
	public SaveAllFunction<SaveEntitiesIn, SaveEntitiesOut> saveAllFunction;

	/**
	 * Function that executes update operation
	 * 
	 * @see UpdateFunction
	 */
	public UpdateFunction<UpdateEntityIn, UpdateEntityOut> updateFunction;

	/**
	 * Function that executes update all operation
	 * 
	 * @see UpdateAllFunction
	 */
	public UpdateAllFunction<UpdateEntitiesIn, UpdateEntitiesOut> updateAllFunction;

	/**
	 * Function that executes delete operation
	 * 
	 * @see DeleteFunction
	 */
	public DeleteFunction<DeleteEntityIn, DeleteEntityOut> deleteFunction;

	/**
	 * Function that executes delete all operation
	 * 
	 * @see DeleteAllFunction
	 */
	public DeleteAllFunction<DeleteEntitiesIn, DeleteEntitiesOut> deleteAllFunction;

	/**
	 * Function that executes delete by id operation
	 * 
	 * @see DeleteByIdFunction
	 */
	public DeleteByIdFunction<DeleteIdIn, DeleteIdOut> deleteByIdFunction;

	/**
	 * Function that executes delete all by ids operation
	 * 
	 * @see DeleteByIdsFunction
	 */
	public DeleteByIdsFunction<DeleteIdsIn, DeleteIdsOut> deleteByIdsFunction;

	/**
	 * Default constructor.
	 * 
	 * @param function Consumer function, can't be null, used to set the builder
	 *                 attributes with a lambda expression, example:
	 * 
	 *                 <pre>
	 *                 new CommandFacadeBuilder&lt;Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut&gt;(
	 *                 		builder -&gt; {
	 *                 			builder.saveFunction = (saveEntityIn, directives) -&gt; {
	 *                 				// implementation of the save operation in the persistence layer.
	 *                 			};
	 *                 			builder.saveAllFunction = (saveEntitiesIn, directives) -&gt; {
	 *                 				// implementation of the save all operation in the persistence layer.
	 *                 			};
	 * 
	 *                 			// set other functions...
	 *                 		});
	 *                 </pre>
	 */
	public CommandFacadeBuilder(
			final Consumer<CommandFacadeBuilder<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut>> function) {
		LOGGER.atDebug().log("Constructing CommandFacadeBuilder function {} ", function);
		super(function);

		this.saveFunction = getSaveFunction();
		this.saveAllFunction = getSaveAllFunction();
		this.updateFunction = getUpdateFunction();
		this.updateAllFunction = getUpdateAllFunction();
		this.deleteFunction = getDeleteFunction();
		this.deleteAllFunction = getDeleteAllFunction();
		this.deleteByIdFunction = getDeleteByIdFunction();
		this.deleteByIdsFunction = getDeleteByIdsFunction();

		LOGGER.atDebug().log("CommandFacadeBuilder constructed commands, functions {}",
				List.of(saveFunction, saveAllFunction,
						updateFunction, updateAllFunction,
						deleteFunction, deleteAllFunction,
						deleteByIdFunction, deleteByIdsFunction));
	}

	/**
	 * Gets the save function, if it is not set, it will be set with a
	 * function that throws an UnsupportedOperationException when executed.
	 * 
	 * @return the save function
	 * 
	 * @see SaveFunction
	 * @see UnsupportedOperationException
	 */
	protected SaveFunction<SaveEntityIn, SaveEntityOut> getSaveFunction() {
		return nonNull(saveFunction)
				? saveFunction
				: (saveEntityIn, directives) -> {
					throw new UnsupportedOperationException("Unimplemented function 'saveFunction'");
				};
	}

	/**
	 * Gets the save all function, if it is not set, it will be set with a
	 * function that throws an UnsupportedOperationException when executed.
	 * 
	 * @return the save all function
	 * 
	 * @see SaveAllFunction
	 * @see UnsupportedOperationException
	 */
	protected SaveAllFunction<SaveEntitiesIn, SaveEntitiesOut> getSaveAllFunction() {
		return nonNull(saveAllFunction)
				? saveAllFunction
				: (saveEntitiesIn, directives) -> {
					throw new UnsupportedOperationException("Unimplemented function 'saveAllFunction'");
				};
	}

	/**
	 * Gets the update function, if it is not set, it will be set with a
	 * function that throws an UnsupportedOperationException when executed.
	 * 
	 * @return the update function
	 * 
	 * @see UpdateFunction
	 * @see UnsupportedOperationException
	 */
	protected UpdateFunction<UpdateEntityIn, UpdateEntityOut> getUpdateFunction() {
		return nonNull(updateFunction)
				? updateFunction
				: (updateEntityIn, directives) -> {
					throw new UnsupportedOperationException("Unimplemented function 'updateFunction'");
				};
	}

	/**
	 * Gets the update all function, if it is not set, it will be set with a
	 * function that throws an UnsupportedOperationException when executed.
	 * 
	 * @return the update all function
	 * 
	 * @see UpdateAllFunction
	 * @see UnsupportedOperationException
	 */
	protected UpdateAllFunction<UpdateEntitiesIn, UpdateEntitiesOut> getUpdateAllFunction() {
		return nonNull(updateAllFunction)
				? updateAllFunction
				: (updateEntitiesIn, directives) -> {
					throw new UnsupportedOperationException("Unimplemented function 'updateAllFunction'");
				};
	}

	/**
	 * Gets the delete function, if it is not set, it will be set with a
	 * function that throws an UnsupportedOperationException when executed.
	 * 
	 * @return the delete function
	 * 
	 * @see DeleteFunction
	 * @see UnsupportedOperationException
	 */
	protected DeleteFunction<DeleteEntityIn, DeleteEntityOut> getDeleteFunction() {
		return nonNull(deleteFunction)
				? deleteFunction
				: (deleteEntityIn, directives) -> {
					throw new UnsupportedOperationException("Unimplemented function 'deleteFunction'");
				};
	}

	/**
	 * Gets the delete all function, if it is not set, it will be set with a
	 * function that throws an UnsupportedOperationException when executed.
	 * 
	 * @return the delete all function
	 * 
	 * @see DeleteAllFunction
	 * @see UnsupportedOperationException
	 */
	protected DeleteAllFunction<DeleteEntitiesIn, DeleteEntitiesOut> getDeleteAllFunction() {
		return nonNull(deleteAllFunction)
				? deleteAllFunction
				: (deleteEntitiesIn, directives) -> {
					throw new UnsupportedOperationException("Unimplemented function 'deleteAllFunction'");
				};
	}

	/**
	 * Gets the delete by id function, if it is not set, it will be set with a
	 * function that throws an UnsupportedOperationException when executed.
	 * 
	 * @return the delete by id function
	 * 
	 * @see DeleteByIdFunction
	 * @see UnsupportedOperationException
	 */
	protected DeleteByIdFunction<DeleteIdIn, DeleteIdOut> getDeleteByIdFunction() {
		return nonNull(deleteByIdFunction)
				? deleteByIdFunction
				: (deleteIdIn, directives) -> {
					throw new UnsupportedOperationException("Unimplemented function 'deleteByIdFunction'");
				};
	}

	/**
	 * Gets the delete by ids function, if it is not set, it will be set with a
	 * function that throws an UnsupportedOperationException when executed.
	 * 
	 * @return the delete by ids function
	 * 
	 * @see DeleteByIdsFunction
	 * @see UnsupportedOperationException
	 */
	protected DeleteByIdsFunction<DeleteIdsIn, DeleteIdsOut> getDeleteByIdsFunction() {
		return nonNull(deleteByIdsFunction)
				? deleteByIdsFunction
				: (deleteIdsIn, directives) -> {
					throw new UnsupportedOperationException("Unimplemented function 'deleteByIdsFunction'");
				};
	}
}
