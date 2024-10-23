package org.reusablecomponents.base.core.application.command.entity;

import static java.util.Optional.ofNullable;
import static org.reusablecomponents.base.core.infra.util.Functions.createNullPointerException;
import static org.reusablecomponents.base.messaging.operation.CommandOperation.DELETE_BY_ID;
import static org.reusablecomponents.base.messaging.operation.CommandOperation.DELETE_BY_IDS;
import static org.reusablecomponents.base.messaging.operation.CommandOperation.DELETE_ENTITIES;
import static org.reusablecomponents.base.messaging.operation.CommandOperation.DELETE_ENTITY;
import static org.reusablecomponents.base.messaging.operation.CommandOperation.SAVE_ENTITIES;
import static org.reusablecomponents.base.messaging.operation.CommandOperation.SAVE_ENTITY;
import static org.reusablecomponents.base.messaging.operation.CommandOperation.UPDATE_ENTITIES;
import static org.reusablecomponents.base.messaging.operation.CommandOperation.UPDATE_ENTITY;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.reusablecomponents.base.core.application.base.EntiyBaseFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default <code>InterfaceEntityCommandFacade</code>'s implementation.
 */
public non-sealed class EntityCommandFacade< // generics
		// default
		Entity extends AbstractEntity<Id>, Id, // basic
		// save
		SaveEntityIn, SaveEntityOut, //
		SaveEntitiesIn, SaveEntitiesOut, //
		// update
		UpdateEntityIn, UpdateEntityOut, //
		UpdateEntitiesIn, UpdateEntitiesOut, //
		// delete
		DeleteEntityIn, DeleteEntityOut, //
		DeleteEntitiesIn, DeleteEntitiesOut, //
		// delete by id
		DeleteIdIn, DeleteIdOut, //
		DeleteIdsIn, DeleteIdsOut> //

		// Base Facade
		extends EntiyBaseFacade<Entity, Id>
		// Interface command facade
		implements InterfaceEntityCommandFacade<Entity, Id, // basic

				SaveEntityIn, SaveEntityOut, //
				SaveEntitiesIn, SaveEntitiesOut, //

				UpdateEntityIn, UpdateEntityOut, //
				UpdateEntitiesIn, UpdateEntitiesOut, //

				DeleteEntityIn, DeleteEntityOut, //
				DeleteEntitiesIn, DeleteEntitiesOut, //

				DeleteIdIn, DeleteIdOut, //
				DeleteIdsIn, DeleteIdsOut> {

	private static final Logger LOGGER = LoggerFactory.getLogger(EntityCommandFacade.class);

	// BiFunction<SaveEntityIn, Object[], SaveEntityOut>
	protected final BiFunction<SaveEntityIn, Object[], SaveEntityOut> saveFunction;
	protected final Function<SaveEntitiesIn, SaveEntitiesOut> saveAllFunction;

	protected final Function<UpdateEntityIn, UpdateEntityOut> updateFunction;
	protected final Function<UpdateEntitiesIn, UpdateEntitiesOut> updateAllFunction;

	protected final Function<DeleteEntityIn, DeleteEntityOut> deleteFunction;
	protected final Function<DeleteEntitiesIn, DeleteEntitiesOut> deleteAllFunction;
	protected final Function<DeleteIdIn, DeleteIdOut> deleteByIdFunction;
	protected final Function<DeleteIdsIn, DeleteIdsOut> deleteAllByIdFunction;

	/**
	 * Default constructior
	 * 
	 * @param builder Object in charge to construct this one
	 */
	public EntityCommandFacade(
			final EntityCommandFacadeBuilder<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> builder) {

		super(builder);

		this.saveFunction = builder.saveFunction;
		this.saveAllFunction = builder.saveAllFunction;

		this.updateFunction = builder.updateFunction;
		this.updateAllFunction = builder.updateAllFunction;

		this.deleteFunction = builder.deleteFunction;
		this.deleteAllFunction = builder.deleteAllFunction;

		this.deleteByIdFunction = builder.deleteByIdFunction;
		this.deleteAllByIdFunction = builder.deleteAllByIdFunction;
	}

	// ----------------------------------------------------------------------------------------------------------

	/**
	 * Method used to change an entity before save it.
	 * 
	 * @param saveEntityIn The object to be changed
	 * 
	 * @param directives   Objects used to configure the save
	 *                     operation
	 * 
	 * @return A new {@code SaveEntityIn} object
	 */
	protected SaveEntityIn preSave(final SaveEntityIn saveEntityIn, final Object... directives) {
		return saveEntityIn;
	}

	/**
	 * Method used to change an entity after save it.
	 * 
	 * @param saveEntityOut The object to be changed
	 * 
	 * @param directives    Objects used to configure the save operation
	 * 
	 * @return A new {@code SaveEntityOut} object
	 */
	protected SaveEntityOut posSave(final SaveEntityOut saveEntityOut, final Object... directives) {
		return saveEntityOut;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SaveEntityOut save(final SaveEntityIn saveEntityIn, final Object... directives) {

		final var session = securityService.getSession();

		LOGGER.debug("Saving entity '{}', session '{}', and directives '{}'", saveEntityIn, session, directives);

		final var preSaveEntityIn = preSave(saveEntityIn, directives);

		final var finalSaveEntityIn = ofNullable(preSaveEntityIn)
				.orElseThrow(createNullPointerException(i18nService, "preSaveEntityIn"));

		LOGGER.debug("Saving finalSaveEntityIn '{}'", finalSaveEntityIn);

		final SaveEntityOut saveEntityOut;

		try {
			saveEntityOut = saveFunction.apply(finalSaveEntityIn, directives);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(ex, i18nService, SAVE_ENTITY, getEntityClazz(), finalSaveEntityIn);
		}

		LOGGER.debug("Saved saveEntityOut '{}'", saveEntityOut);

		final var posSaveEntityOut = posSave(saveEntityOut, directives);

		final var finalSaveEntityOut = ofNullable(posSaveEntityOut)
				.orElseThrow(createNullPointerException(i18nService, "posSaveEntityOut"));

		LOGGER.debug("Saved entity '{}', session '{}'", finalSaveEntityOut, session);

		return finalSaveEntityOut;
	}

	// ----------------------------------------------------------------------------------------------------------

	/**
	 * Method used to change a group of entities before save it.
	 * 
	 * @param saveEntiesIn The object to be changed
	 * 
	 * @param directives   Objects used to configure the save operation
	 * 
	 * @return A new {@code SaveEntitiesIn} object
	 */
	protected SaveEntitiesIn preSaveAll(final SaveEntitiesIn saveEntiesIn, final Object... directives) {
		return saveEntiesIn;
	}

	/**
	 * Method used to change a group of entities after save it.
	 * 
	 * @param saveEntiesOut The group of objects to be changed
	 * 
	 * @param directives    Objects used to configure the save operation
	 * 
	 * @return A new {@code SaveEntitiesOut} object
	 */
	protected SaveEntitiesOut posSaveAll(final SaveEntitiesOut saveEntiesOut, final Object... directives) {
		return saveEntiesOut;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SaveEntitiesOut saveAll(final SaveEntitiesIn saveEntitiesIn, final Object... directives) {

		final var session = securityService.getSession();

		LOGGER.debug("Saving all entities '{}' with session '{}'", saveEntitiesIn, session);

		final var preSaveEntitiesIn = preSaveAll(saveEntitiesIn, directives);

		final var finalSaveEntitiesIn = ofNullable(preSaveEntitiesIn)
				.orElseThrow(createNullPointerException(i18nService, "saveEntitiesIn"));

		LOGGER.debug("Saving finalSaveEntitiesIn '{}'", finalSaveEntitiesIn);

		final SaveEntitiesOut saveEntitiesOut;

		try {
			saveEntitiesOut = saveAllFunction.apply(finalSaveEntitiesIn);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					SAVE_ENTITIES,
					getEntityClazz(),
					finalSaveEntitiesIn);
		}

		LOGGER.debug("Saved saveEntitiesOut '{}'", saveEntitiesOut);

		final var posSaveEntitiesOut = posSaveAll(saveEntitiesOut, directives);

		final var finalSaveEntitiesOut = ofNullable(posSaveEntitiesOut)
				.orElseThrow(createNullPointerException(i18nService, "posSaveEntitiesOut"));

		LOGGER.debug("Saved all entities '{}' with session '{}'", finalSaveEntitiesOut, session);

		return finalSaveEntitiesOut;
	}

	// ----------------------------------------------------------------------------------------------------------

	/**
	 * Method used to change an entity before update it.
	 * 
	 * @param updateEntityIn The object to be changed
	 * 
	 * @param directives     Objects used to configure the update operation
	 * 
	 * @return A new {@code UpdateEntityIn} object
	 */
	protected UpdateEntityIn preUpdate(final UpdateEntityIn updateEntityIn, final Object... directives) {
		return updateEntityIn;
	}

	/**
	 * Method used to change an entity after update it.
	 * 
	 * @param updateEntityOut The object to be changed
	 * 
	 * @param directives      Objects used to configure the update operation
	 * 
	 * @return A new {@code UpdateEntityOut} object
	 */
	protected UpdateEntityOut posUpdate(final UpdateEntityOut updateEntityOut, final Object... directives) {
		return updateEntityOut;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UpdateEntityOut update(final UpdateEntityIn updateEntityIn, final Object... directives) {

		final var session = securityService.getSession();

		LOGGER.debug("Updating entity '{}' with session '{}'", updateEntityIn, session);

		final var preUpdateEntityIn = preUpdate(updateEntityIn, directives);

		final var finalUpdateEntityIn = ofNullable(preUpdateEntityIn)
				.orElseThrow(createNullPointerException(i18nService, "preUpdateEntityIn"));

		LOGGER.debug("Updating finalUpdateEntityIn '{}'", finalUpdateEntityIn);

		final UpdateEntityOut updateEntityOut;

		try {
			updateEntityOut = updateFunction.apply(finalUpdateEntityIn);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					UPDATE_ENTITY,
					getEntityClazz(),
					finalUpdateEntityIn);
		}

		LOGGER.debug("Updated result '{}'", updateEntityOut);

		final var posUpdateEntityOut = posUpdate(updateEntityOut, directives);

		final var finalUpdateEntityOut = ofNullable(posUpdateEntityOut)
				.orElseThrow(createNullPointerException(i18nService, "posUpdateEntityOut"));

		LOGGER.debug("Updated entity '{}' with session '{}'", finalUpdateEntityOut, session);

		return finalUpdateEntityOut;
	}

	// ----------------------------------------------------------------------------------------------------------

	/**
	 * Method used to change a group of entities before update it.
	 * 
	 * @param updateEntitiesIn The object to be changed
	 * 
	 * @param directives       Objects used to configure the update all operation
	 * 
	 * @return A new {@code UpdateEntitiesIn} object
	 */
	protected UpdateEntitiesIn preUpdateAll(final UpdateEntitiesIn updateEntitiesIn, final Object... directives) {
		return updateEntitiesIn;
	}

	/**
	 * Method used to change a group of entities after update it.
	 * 
	 * @param updateEntitiesOut The object to be changed
	 * 
	 * @param directives        Objects used to configure the update all operation
	 * 
	 * @return A new {@code UpdateEntitiesOut} object
	 */
	protected UpdateEntitiesOut posUpdateAll(final UpdateEntitiesOut updateEntitiesOut, final Object... directives) {
		return updateEntitiesOut;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UpdateEntitiesOut updateAll(final UpdateEntitiesIn updateEntitiesIn, final Object... directives) {

		final var session = securityService.getSession();

		LOGGER.debug("Updating entities '{}' with session '{}'", updateEntitiesIn, session);

		final var preUpdateEntitiesIn = preUpdateAll(updateEntitiesIn);

		final var finalUpdateEntitiesIn = ofNullable(preUpdateEntitiesIn)
				.orElseThrow(createNullPointerException(i18nService, "preUpdateEntitiesIn"));

		LOGGER.debug("Updating finalUpdateEntitiesIn '{}'", finalUpdateEntitiesIn);

		final UpdateEntitiesOut updateEntitiesOut;

		try {
			updateEntitiesOut = updateAllFunction.apply(finalUpdateEntitiesIn);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					UPDATE_ENTITIES,
					getEntityClazz(),
					finalUpdateEntitiesIn);
		}

		LOGGER.debug("Updated updateEntitiesOut '{}'", updateEntitiesOut);

		final var posUpdateEntitiesOut = posUpdateAll(updateEntitiesOut);

		final var finalUpdateEntitiesOut = ofNullable(posUpdateEntitiesOut)
				.orElseThrow(createNullPointerException(i18nService, "posUpdateAllEntitiesOut"));

		LOGGER.debug("Updated all entities '{}' with session '{}'", finalUpdateEntitiesOut, session);

		return finalUpdateEntitiesOut;
	}

	// ----------------------------------------------------------------------------------------------------------

	/**
	 * Method used to change an entity before delete it.
	 * 
	 * @param deleteEntityIn The object to be changed
	 * 
	 * @param directives     Objects used to configure the delete operation
	 * 
	 * @return A new {@code DeleteEntityIn} object
	 */
	protected DeleteEntityIn preDelete(final DeleteEntityIn deleteEntityIn, final Object... directives) {
		return deleteEntityIn;
	}

	/**
	 * Method used to change an entity after delete it.
	 * 
	 * @param deleteEntityOut The object to be changed
	 * 
	 * @param directives      Objects used to configure the delete operation
	 * 
	 * @return A new {@code DeleteEntityOut} object
	 */
	protected DeleteEntityOut posDelete(final DeleteEntityOut deleteEntityOut, final Object... directives) {
		return deleteEntityOut;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteEntityOut delete(final DeleteEntityIn deleteEntityIn, final Object... directives) {

		final var session = securityService.getSession();

		LOGGER.debug("Deleting entity '{}' with session '{}'", deleteEntityIn, session);

		final var preDeleteEntityIn = preDelete(deleteEntityIn, directives);

		final var finalDeleteEntityIn = ofNullable(preDeleteEntityIn)
				.orElseThrow(createNullPointerException(i18nService, "preDeleteEntityIn"));

		LOGGER.debug("Deleting finalDeleteEntityIn '{}'", finalDeleteEntityIn);

		final DeleteEntityOut deleteEntityOut;

		try {
			deleteEntityOut = deleteFunction.apply(finalDeleteEntityIn);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					DELETE_ENTITY,
					getEntityClazz(),
					finalDeleteEntityIn);
		}

		LOGGER.debug("Delete result '{}'", deleteEntityOut);

		final var posDeleteEntityOut = posDelete(deleteEntityOut, directives);

		final var finalDeleteEntityOut = ofNullable(posDeleteEntityOut)
				.orElseThrow(createNullPointerException(i18nService, "posDeleteEntityOut"));

		LOGGER.debug("Deleted entity '{}' with session '{}'", finalDeleteEntityOut, session);

		return finalDeleteEntityOut;
	}

	// ----------------------------------------------------------------------------------------------------------

	/**
	 * Method used to change a group of entities before delete it.
	 * 
	 * @param deleteEntitiesIn The object to be changed
	 * 
	 * @param directives       Objects used to configure the delete all operation
	 * 
	 * @return A new {@code DeleteEntitiesIn} object
	 */
	protected DeleteEntitiesIn preDeleteAll(final DeleteEntitiesIn deleteEntitiesIn, final Object... directives) {
		return deleteEntitiesIn;
	}

	/**
	 * Method used to change a group of entities after delete it.
	 * 
	 * @param deleteEntitiesOut The object to be changed
	 * 
	 * @param directives        Objects used to configure the delete all operation
	 * 
	 * @return A new {@code DeleteEntitiesOut} object
	 */
	protected DeleteEntitiesOut posDeleteAll(final DeleteEntitiesOut deleteEntitiesOut, final Object... directives) {
		return deleteEntitiesOut;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteEntitiesOut deleteAll(final DeleteEntitiesIn deleteEntitiesIn, final Object... directives) {

		final var session = securityService.getSession();

		LOGGER.debug("Deleting entities '{}' with session '{}'", deleteEntitiesIn, session);

		final var preDeleteEntityIn = preDeleteAll(deleteEntitiesIn, directives);

		final var finalDeleteEntitiesIn = ofNullable(preDeleteEntityIn)
				.orElseThrow(createNullPointerException(i18nService, "preDeleteEntitiesIn"));

		LOGGER.debug("Deleting finalDeleteEntitiesIn '{}'", finalDeleteEntitiesIn);

		final DeleteEntitiesOut deleteEntitiesOut;

		try {
			deleteEntitiesOut = deleteAllFunction.apply(finalDeleteEntitiesIn);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					DELETE_ENTITIES,
					getEntityClazz(),
					finalDeleteEntitiesIn);
		}

		LOGGER.debug("Delete all deleteEntitiesOut '{}'", deleteEntitiesOut);

		final var posDeleteEntitiesOut = posDeleteAll(deleteEntitiesOut, directives);

		final var finalDeleteEntitiesOut = ofNullable(posDeleteEntitiesOut)
				.orElseThrow(createNullPointerException(i18nService, "posDeleteEntitiesOut"));

		LOGGER.debug("Deleted entities '{}' with session '{}'", finalDeleteEntitiesOut, session);

		return finalDeleteEntitiesOut;
	}

	// ----------------------------------------------------------------------------------------------------------

	/**
	 * Method used to change an id object before delete it.
	 * 
	 * @param deleteIdIn The object to be changed
	 * 
	 * @param directives Objects used to configure the delete by id operation
	 * 
	 * @return A new {@code DeleteIdIn} object
	 */
	protected DeleteIdIn preDeleteBy(final DeleteIdIn deleteIdIn, final Object... directives) {
		return deleteIdIn;
	}

	/**
	 * Method used to change an id object after delete it.
	 * 
	 * @param deleteIdOut The object to be changed
	 * 
	 * @param directives  Objects used to configure the delete by id operation
	 * 
	 * @return A new {@code DeleteIdOut} object
	 */
	protected DeleteIdOut posDeleteBy(final DeleteIdOut deleteIdOut, final Object... directives) {
		return deleteIdOut;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteIdOut deleteBy(final DeleteIdIn deleteIdIn, final Object... directives) {

		final var session = securityService.getSession();

		LOGGER.debug("Deleting by id '{}' with session '{}'", deleteIdIn, session);

		final var preDeleteIdIn = preDeleteBy(deleteIdIn, directives);

		final var finalDeleteIdIn = ofNullable(preDeleteIdIn)
				.orElseThrow(createNullPointerException(i18nService, "preDeleteIdIn"));

		LOGGER.debug("Deleting by id finalDeleteIdIn '{}'", finalDeleteIdIn);

		final DeleteIdOut deleteIdOut;

		try {
			deleteIdOut = deleteByIdFunction.apply(finalDeleteIdIn);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					DELETE_BY_ID,
					getEntityClazz(),
					finalDeleteIdIn);
		}

		LOGGER.debug("Delete by id result '{}' with session '{}'", deleteIdOut, session);

		final var posDeleteDeleteIdOut = posDeleteBy(deleteIdOut, directives);

		final var finalDeleteIdOut = ofNullable(posDeleteDeleteIdOut)
				.orElseThrow(createNullPointerException(i18nService, "posDeleteDeleteIdOut"));

		LOGGER.debug("Deleted by id '{}' with session '{}'", finalDeleteIdOut, session);

		return finalDeleteIdOut;
	}

	// ----------------------------------------------------------------------------------------------------------
	/**
	 * Method used to change a group of ids before save it.
	 * 
	 * @param deleteIdsIn The object to be changed
	 * 
	 * @param directives  Objects used to configure the delete by id operation
	 * 
	 * @return A new {@code DeleteIdsIn} object
	 */
	protected DeleteIdsIn preDeleteEntitiesBy(final DeleteIdsIn deleteIdsIn, final Object... directives) {
		return deleteIdsIn;
	}

	/**
	 * Method used to change a group of ids after save it.
	 * 
	 * @param deleteIdsOut The object to be changed
	 * 
	 * @param directives   Objects used to configure the delete by id operation
	 * 
	 * @return A new {@code DeleteIdsOut} object
	 */
	protected DeleteIdsOut posDeleteEntitiesBy(final DeleteIdsOut deleteIdsOut, final Object... directives) {
		return deleteIdsOut;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteIdsOut deleteAllBy(final DeleteIdsIn deleteIdsIn, final Object... directives) {

		final var session = securityService.getSession();

		LOGGER.debug("Deleting by ids '{}' with session '{}'", deleteIdsIn, session);

		final var preDeleteIdsIn = preDeleteEntitiesBy(deleteIdsIn, directives);

		final var finalDeleteIdsIn = ofNullable(preDeleteIdsIn)
				.orElseThrow(createNullPointerException(i18nService, "preDeleteIdsIn"));

		LOGGER.debug("Deleting All by finalDeleteIdsIn '{}'", finalDeleteIdsIn);

		final DeleteIdsOut deleteIdsOut;

		try {
			deleteIdsOut = deleteAllByIdFunction.apply(finalDeleteIdsIn);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					DELETE_BY_IDS,
					getEntityClazz(),
					finalDeleteIdsIn);
		}

		LOGGER.debug("Delete all by id result '{}'", deleteIdsOut);

		final var posDeleteEntitiesOut = posDeleteEntitiesBy(deleteIdsOut, directives);

		final var finalDeleteIdsOut = ofNullable(posDeleteEntitiesOut)
				.orElseThrow(createNullPointerException(i18nService, "posDeleteEntitiesOut"));

		LOGGER.debug("Deleted by ids '{}' with session '{}'", finalDeleteIdsOut, session);

		return finalDeleteIdsOut;
	}
}