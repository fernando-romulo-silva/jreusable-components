package org.reusablecomponents.base.core.application.command.entity;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.reusablecomponents.base.core.application.base.EntiyBaseFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;

/**
 * 
 */
public final class EntityCommandFacadeBuilder< // Generics spec
		Entity extends AbstractEntity<Id>, Id, // basic
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
		extends EntiyBaseFacadeBuilder {

	public BiFunction<SaveEntityIn, Object[], SaveEntityOut> saveFunction;
	public Function<SaveEntitiesIn, SaveEntitiesOut> saveAllFunction;

	public Function<UpdateEntityIn, UpdateEntityOut> updateFunction;
	public Function<UpdateEntitiesIn, UpdateEntitiesOut> updateAllFunction;

	public Function<DeleteEntityIn, DeleteEntityOut> deleteFunction;
	public Function<DeleteEntitiesIn, DeleteEntitiesOut> deleteAllFunction;

	public Function<DeleteIdIn, DeleteIdOut> deleteByIdFunction;
	public Function<DeleteIdsIn, DeleteIdsOut> deleteAllByIdFunction;

	public EntityCommandFacadeBuilder(
			final Consumer<EntityCommandFacadeBuilder<Entity, Id, //
					//
					SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut,
					//
					UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut,
					//
					DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut>> function) {

		super(function);

		// load the functions
		function.accept(this);

		checkNotNull(saveFunction, "Please pass a non-null 'saveFunction'");
		checkNotNull(saveAllFunction, "Please pass a non-null 'saveAllFunction'");

		checkNotNull(updateFunction, "Please pass a non-null 'updateFunction'");
		checkNotNull(updateAllFunction, "Please pass a non-null 'updateAllFunction'");

		checkNotNull(deleteFunction, "Please pass a non-null 'deleteFunction'");
		checkNotNull(deleteAllFunction, "Please pass a non-null 'deleteAllFunction'");
		checkNotNull(deleteByIdFunction, "Please pass a non-null 'deleteByIdFunction'");
		checkNotNull(deleteAllByIdFunction, "Please pass a non-null 'deleteAllByIdFunction'");
	}
}
