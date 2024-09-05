package org.reusablecomponents.base.core.application.command.entity;

import static org.reusablecomponents.base.messaging.operation.CommandOperation.DELETE_BY_ID;
import static org.reusablecomponents.base.messaging.operation.CommandOperation.DELETE_BY_IDS;
import static org.reusablecomponents.base.messaging.operation.CommandOperation.DELETE_ENTITIES;
import static org.reusablecomponents.base.messaging.operation.CommandOperation.DELETE_ENTITY;
import static org.reusablecomponents.base.messaging.operation.CommandOperation.SAVE_ENTITIES;
import static org.reusablecomponents.base.messaging.operation.CommandOperation.SAVE_ENTITY;
import static org.reusablecomponents.base.messaging.operation.CommandOperation.UPDATE_ENTITIES;
import static org.reusablecomponents.base.messaging.operation.CommandOperation.UPDATE_ENTITY;

import static java.util.Optional.ofNullable;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

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
		DeleteIdsIn, DeleteIdsOut //
>
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

	protected final Function<SaveEntityIn, SaveEntityOut> saveFunction;
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

		super(
				builder.publisherService,
				builder.i18nService,
				builder.securityService,
				builder.exceptionAdapterService);

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
	 * @param saveEntityIn
	 * @return
	 */
	protected Supplier<String> convertSaveEntityInToPublishDataIn(final SaveEntityIn saveEntityIn) {
		return () -> Objects.toString(saveEntityIn);
	}

	/**
	 * @param saveEntityOut
	 * @return
	 */
	protected Supplier<String> convertSaveEntityOutToPublishDataOut(final SaveEntityOut saveEntityOut) {
		return () -> Objects.toString(saveEntityOut);
	}

	/**
	 * @param saveEntityIn
	 * @return
	 */
	protected SaveEntityIn preSave(final SaveEntityIn saveEntityIn) {
		return saveEntityIn;
	}

	/**
	 * @param saveEntityOut
	 * @return
	 */
	protected SaveEntityOut posSave(final SaveEntityOut saveEntityOut) {
		return saveEntityOut;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SaveEntityOut save(final SaveEntityIn saveEntityIn) {

		final var session = securityService.getSession();

		LOGGER.debug("Saving entity '{}', session '{}'", saveEntityIn, session);

		final var preSaveEntityIn = preSave(saveEntityIn);

		final var finalSaveEntityIn = ofNullable(preSaveEntityIn)
				.orElseThrow(createNullPointerException("preSaveEntityIn"));

		LOGGER.debug("Saving finalSaveEntityIn '{}'", finalSaveEntityIn);

		final SaveEntityOut result;

		try {
			result = saveFunction.apply(finalSaveEntityIn);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(ex, i18nService, SAVE_ENTITY, getEntityClazz(), saveEntityIn);
		}

		LOGGER.debug("Saved result '{}'", result);

		final var posSaveEntityOut = posSave(result);

		final var finalResult = ofNullable(posSaveEntityOut)
				.orElseThrow(createNullPointerException("posSaveEntityOut"));

		final var dataIn = convertSaveEntityInToPublishDataIn(finalSaveEntityIn);
		final var dataOut = convertSaveEntityOutToPublishDataOut(finalResult);
		publishEvent(dataIn, dataOut, SAVE_ENTITY, finalSaveEntityIn, finalResult);

		LOGGER.debug("Saved entity '{}', session '{}'", finalResult, session);

		return finalResult;
	}

	// ----------------------------------------------------------------------------------------------------------

	protected Supplier<String> convertDataSaveEntitiesInToPublishDataIn(final SaveEntitiesIn saveEntitiesIn) {
		return () -> Objects.toString(saveEntitiesIn);
	}

	protected Supplier<String> convertSaveEntitiesOutToPublishDataOut(final SaveEntitiesOut saveEntitiesOut) {
		return () -> Objects.toString(saveEntitiesOut);
	}

	protected SaveEntitiesIn preSaveAll(final SaveEntitiesIn saveEntiesIn) {
		return saveEntiesIn;
	}

	protected SaveEntitiesOut posSaveAll(final SaveEntitiesOut saveEntiesOut) {
		return saveEntiesOut;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SaveEntitiesOut saveAll(final SaveEntitiesIn saveEntitiesIn) {

		final var session = securityService.getSession();

		LOGGER.debug("Saving all entities '{}' with session '{}'", saveEntitiesIn, session);

		final var preSaveEntitiesIn = preSaveAll(saveEntitiesIn);

		final var finalSaveEntitiesIn = ofNullable(preSaveEntitiesIn)
				.orElseThrow(createNullPointerException("saveEntitiesIn"));

		LOGGER.debug("Saving finalSaveEntitiesIn '{}'", finalSaveEntitiesIn);

		final SaveEntitiesOut result;

		try {
			result = saveAllFunction.apply(finalSaveEntitiesIn);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(ex, i18nService, SAVE_ENTITIES);
		}

		LOGGER.debug("Saved result '{}'", result);

		final var posSaveEntitiesOut = posSaveAll(result);

		final var finalResult = ofNullable(posSaveEntitiesOut)
				.orElseThrow(createNullPointerException("posSaveEntitiesOut"));

		final var dataIn = convertDataSaveEntitiesInToPublishDataIn(finalSaveEntitiesIn);
		final var dataOut = convertSaveEntitiesOutToPublishDataOut(finalResult);
		publishEvent(dataIn, dataOut, SAVE_ENTITIES, finalSaveEntitiesIn, finalResult);

		LOGGER.debug("Saved all entities '{}' with session '{}'", finalResult, session);

		return finalResult;
	}

	// ----------------------------------------------------------------------------------------------------------

	protected Supplier<String> convertUpdateEntityInToPublishDataIn(final UpdateEntityIn updateEntityIn) {
		return () -> Objects.toString(updateEntityIn);
	}

	protected Supplier<String> convertUpdateEntityOutToPublishDataOut(final UpdateEntityOut updateEntityOut) {
		return () -> Objects.toString(updateEntityOut);
	}

	protected UpdateEntityIn preUpdate(final UpdateEntityIn updateEntityIn) {
		return updateEntityIn;
	}

	protected UpdateEntityOut posUpdate(final UpdateEntityOut updateEntityOut) {
		return updateEntityOut;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UpdateEntityOut update(final UpdateEntityIn updateEntityIn) {

		final var session = securityService.getSession();

		LOGGER.debug("Updating entity '{}' with session '{}'", updateEntityIn, session);

		final var preUpdateEntityIn = preUpdate(updateEntityIn);

		final var finalUpdateEntityIn = ofNullable(preUpdateEntityIn)
				.orElseThrow(createNullPointerException("preUpdateEntityIn"));

		LOGGER.debug("Updating finalSaveEntityIn '{}'", finalUpdateEntityIn);

		final UpdateEntityOut result;

		try {
			result = updateFunction.apply(finalUpdateEntityIn);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(ex, i18nService);
		}

		LOGGER.debug("Updated result '{}'", result);

		final var posUpdateEntityOut = posUpdate(result);

		final var finalResult = ofNullable(posUpdateEntityOut)
				.orElseThrow(createNullPointerException("posUpdateEntityOut"));

		final var dataIn = convertUpdateEntityInToPublishDataIn(finalUpdateEntityIn);
		final var dataOut = convertUpdateEntityOutToPublishDataOut(finalResult);
		publishEvent(dataIn, dataOut, UPDATE_ENTITY, finalUpdateEntityIn, finalResult);

		LOGGER.debug("Updated entity '{}' with session '{}'", finalResult, session);

		return finalResult;
	}

	// ----------------------------------------------------------------------------------------------------------

	protected Supplier<String> convertUpdateEntitiesInToPublishDataIn(final UpdateEntitiesIn updateEntitiesIn) {
		return () -> Objects.toString(updateEntitiesIn);
	}

	protected Supplier<String> convertUpdateEntitiesOutToPublishDataOut(final UpdateEntitiesOut updateEntitiesOut) {
		return () -> Objects.toString(updateEntitiesOut);
	}

	protected UpdateEntitiesIn preUpdateAll(final UpdateEntitiesIn updateEntitiesIn) {
		return updateEntitiesIn;
	}

	protected UpdateEntitiesOut posUpdateAll(final UpdateEntitiesOut updateEntitiesOut) {
		return updateEntitiesOut;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UpdateEntitiesOut updateAll(final UpdateEntitiesIn updateEntitiesIn) {

		final var session = securityService.getSession();

		LOGGER.debug("Updating entities '{}' with session '{}'", updateEntitiesIn, session);

		final var preUpdateEntitiesIn = preUpdateAll(updateEntitiesIn);

		final var finalUpdateEntitiesIn = ofNullable(preUpdateEntitiesIn)
				.orElseThrow(createNullPointerException("preUpdateEntitiesIn"));

		LOGGER.debug("Updating finalUpdateEntitiesIn '{}'", finalUpdateEntitiesIn);

		final UpdateEntitiesOut result;

		try {
			result = updateAllFunction.apply(finalUpdateEntitiesIn);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(ex, i18nService);
		}

		LOGGER.debug("Updated result '{}'", result);

		final var posUpdateEntitiesOut = posUpdateAll(result);

		final var finalResult = ofNullable(posUpdateEntitiesOut)
				.orElseThrow(createNullPointerException("posUpdateAllEntitiesOut"));

		final var dataIn = convertUpdateEntitiesInToPublishDataIn(finalUpdateEntitiesIn);
		final var dataOut = convertUpdateEntitiesOutToPublishDataOut(finalResult);
		publishEvent(dataIn, dataOut, UPDATE_ENTITIES, finalUpdateEntitiesIn, finalResult);

		LOGGER.debug("Updated all entities '{}' with session '{}'", finalResult, session);

		return result;
	}

	// ----------------------------------------------------------------------------------------------------------

	protected Supplier<String> convertDeleteEntityInToPublishDataIn(final DeleteEntityIn deleteEntityIn) {
		return () -> Objects.toString(deleteEntityIn);
	}

	protected Supplier<String> convertDeleteEntityOutToPublishDataOut(final DeleteEntityOut deleteEntityOut) {
		return () -> Objects.toString(deleteEntityOut);
	}

	protected DeleteEntityIn preDelete(final DeleteEntityIn deleteEntityIn) {
		return deleteEntityIn;
	}

	protected DeleteEntityOut posDelete(final DeleteEntityOut deleteEntityOut) {
		return deleteEntityOut;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteEntityOut delete(final DeleteEntityIn deleteEntityIn) {

		final var session = securityService.getSession();

		LOGGER.debug("Deleting entity '{}' with session '{}'", deleteEntityIn, session);

		final var preDeleteEntityIn = preDelete(deleteEntityIn);

		final var finalDeleteEntityIn = ofNullable(preDeleteEntityIn)
				.orElseThrow(createNullPointerException("preUpdateEntitiesIn"));

		LOGGER.debug("Deleting finalDeleteEntityIn '{}'", finalDeleteEntityIn);

		final DeleteEntityOut result;

		try {
			result = deleteFunction.apply(finalDeleteEntityIn);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(ex, i18nService);
		}

		LOGGER.debug("Delete result '{}'", result);

		final var posDeleteEntityOut = posDelete(result);

		final var finalResult = ofNullable(posDeleteEntityOut)
				.orElseThrow(createNullPointerException("posDeleteEntityOut"));

		final var dataIn = convertDeleteEntityInToPublishDataIn(finalDeleteEntityIn);
		final var dataOut = convertDeleteEntityOutToPublishDataOut(finalResult);
		publishEvent(dataIn, dataOut, DELETE_ENTITY, finalDeleteEntityIn, finalResult);

		LOGGER.debug("Deleted entity '{}' with session '{}'", finalResult, session);

		return result;
	}

	// ----------------------------------------------------------------------------------------------------------

	protected Supplier<String> convertDeleteEntitiesInToPublishDataIn(final DeleteEntitiesIn deleteEntitiesIn) {
		return () -> Objects.toString(deleteEntitiesIn);
	}

	protected Supplier<String> convertDeleteEntitiesOutOutToPublishDataOut(final DeleteEntitiesOut deleteEntitiesOut) {
		return () -> Objects.toString(deleteEntitiesOut);
	}

	protected DeleteEntitiesIn preDeleteAll(final DeleteEntitiesIn deleteEntitiesIn) {
		return deleteEntitiesIn;
	}

	protected DeleteEntitiesOut posDeleteAll(final DeleteEntitiesOut deleteEntitiesOut) {
		return deleteEntitiesOut;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteEntitiesOut deleteAll(final DeleteEntitiesIn deleteEntitiesIn) {

		final var session = securityService.getSession();

		LOGGER.debug("Deleting entities '{}' with session '{}'", deleteEntitiesIn, session);

		final var preDeleteEntityIn = preDeleteAll(deleteEntitiesIn);

		final var finalDeleteEntitiesIn = ofNullable(preDeleteEntityIn)
				.orElseThrow(createNullPointerException("preDeleteEntityIn"));

		final DeleteEntitiesOut result;

		try {
			result = deleteAllFunction.apply(finalDeleteEntitiesIn);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(ex, i18nService);
		}

		LOGGER.debug("Delete all result '{}'", result);

		final var posDeleteEntitiesOut = posDeleteAll(result);

		final var finalResult = ofNullable(posDeleteEntitiesOut)
				.orElseThrow(createNullPointerException("posDeleteEntitiesOut"));

		final var dataIn = convertDeleteEntitiesInToPublishDataIn(finalDeleteEntitiesIn);
		final var dataOut = convertDeleteEntitiesOutOutToPublishDataOut(finalResult);
		publishEvent(dataIn, dataOut, DELETE_ENTITIES, finalDeleteEntitiesIn, finalResult);

		LOGGER.debug("Deleted entities '{}' with session '{}'", finalResult, session);

		return finalResult;
	}

	// ----------------------------------------------------------------------------------------------------------

	protected Supplier<String> convertDeleteIdInToPublishDataIn(final DeleteIdIn deleteIdIn) {
		return () -> Objects.toString(deleteIdIn);
	}

	protected Supplier<String> convertDeleteIdOutToPublishDataOut(final DeleteIdOut deleteIdOut) {
		return () -> Objects.toString(deleteIdOut);
	}

	protected DeleteIdIn preDeleteById(final DeleteIdIn deleteIdIn) {
		return deleteIdIn;
	}

	protected DeleteIdOut posDeleteById(final DeleteIdOut deleteIdOut) {
		return deleteIdOut;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteIdOut deleteBy(final DeleteIdIn deleteIdIn) {

		final var session = securityService.getSession();

		LOGGER.debug("Deleting by id '{}' with session '{}'", deleteIdIn, session);

		final var preDeleteIdIn = preDeleteById(deleteIdIn);

		final var finalDeleteIdIn = ofNullable(preDeleteIdIn)
				.orElseThrow(createNullPointerException("preDeleteIdIn"));

		final DeleteIdOut result;

		try {
			result = deleteByIdFunction.apply(finalDeleteIdIn);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(ex, i18nService);
		}

		LOGGER.debug("Delete by id result '{}' with session '{}'", result, session);

		final var posDeleteDeleteIdOut = posDeleteById(result);

		final var finalResult = ofNullable(posDeleteDeleteIdOut)
				.orElseThrow(createNullPointerException("posDeleteDeleteIdOut"));

		final var dataIn = convertDeleteIdInToPublishDataIn(finalDeleteIdIn);
		final var dataOut = convertDeleteIdOutToPublishDataOut(finalResult);
		publishEvent(dataIn, dataOut, DELETE_BY_ID, finalDeleteIdIn, finalResult);

		LOGGER.debug("Deleted by id '{}' with session '{}'", finalResult, session);

		return finalResult;
	}

	// ----------------------------------------------------------------------------------------------------------

	protected Supplier<String> convertDeleteIdsInToPublishDataIn(final DeleteIdsIn deleteIdsIn) {
		return () -> Objects.toString(deleteIdsIn);
	}

	protected Supplier<String> convertDeleteIdsOutToPublishDataOut(final DeleteIdsOut deleteIdsOut) {
		return () -> Objects.toString(deleteIdsOut);
	}

	protected DeleteIdsIn preDeleteEntitiesBy(final DeleteIdsIn deleteIdsIn) {
		return deleteIdsIn;
	}

	protected DeleteIdsOut posDeleteEntitiesBy(final DeleteIdsOut deleteIdsOut) {
		return deleteIdsOut;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeleteIdsOut deleteAllBy(final DeleteIdsIn deleteIdsIn) {

		final var session = securityService.getSession();

		LOGGER.debug("Deleting by ids '{}' with session '{}'", deleteIdsIn, session);

		final var preDeleteIdsIn = preDeleteEntitiesBy(deleteIdsIn);

		final var finalDeleteIdsIn = ofNullable(preDeleteIdsIn)
				.orElseThrow(createNullPointerException("preDeleteIdsIn"));

		final DeleteIdsOut result;

		try {
			result = deleteAllByIdFunction.apply(finalDeleteIdsIn);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(ex, i18nService);
		}

		LOGGER.debug("Delete all by id result '{}'", result);

		final var posDeleteEntitiesOut = posDeleteEntitiesBy(result);

		final var finalResult = ofNullable(posDeleteEntitiesOut)
				.orElseThrow(createNullPointerException("posDeleteEntitiesOut"));

		final var dataIn = convertDeleteIdsInToPublishDataIn(finalDeleteIdsIn);
		final var dataOut = convertDeleteIdsOutToPublishDataOut(finalResult);
		publishEvent(dataIn, dataOut, DELETE_BY_IDS, finalDeleteIdsIn, finalResult);

		LOGGER.debug("Deleted by ids '{}' with session '{}'", finalResult, session);

		return finalResult;
	}
}