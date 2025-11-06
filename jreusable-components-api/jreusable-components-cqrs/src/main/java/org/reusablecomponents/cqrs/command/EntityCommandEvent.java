package org.reusablecomponents.cqrs.command;

import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;
import static org.reusablecomponents.base.core.infra.util.function.operation.CommandOperation.DELETE_BY_ID;
import static org.reusablecomponents.base.core.infra.util.function.operation.CommandOperation.DELETE_BY_IDS;
import static org.reusablecomponents.base.core.infra.util.function.operation.CommandOperation.DELETE_ENTITIES;
import static org.reusablecomponents.base.core.infra.util.function.operation.CommandOperation.DELETE_ENTITY;
import static org.reusablecomponents.base.core.infra.util.function.operation.CommandOperation.SAVE_ENTITIES;
import static org.reusablecomponents.base.core.infra.util.function.operation.CommandOperation.SAVE_ENTITY;
import static org.reusablecomponents.base.core.infra.util.function.operation.CommandOperation.UPDATE_ENTITIES;
import static org.reusablecomponents.base.core.infra.util.function.operation.CommandOperation.UPDATE_ENTITY;

import java.util.Objects;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.cqrs.base.EntiyBaseEvent;
import org.reusablecomponents.cqrs.base.EntiyBaseEventBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public class EntityCommandEvent< // generics
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
		DeleteIdsIn, DeleteIdsOut> extends EntiyBaseEvent {

	private static final Logger LOGGER = LoggerFactory.getLogger(EntityCommandEvent.class);

	/**
	 * 
	 * @param builder
	 */
	protected EntityCommandEvent(final EntiyBaseEventBuilder builder) {
		super(builder);
	}

	/**
	 * Converts a {@code Exception} object to String to show in logs, the default
	 * is the <code>ExceptionUtils.getStackTrace</code>
	 * 
	 * @param exception The exception to transform
	 * 
	 * @return A string object
	 */
	@NotNull
	protected String convertExceptionToPublishDataOut(final Exception exception) {
		return ExceptionUtils.getStackTrace(getRootCause(exception));
	}

	// --------------------------------------------------------

	/**
	 * Converts a {@code SaveEntityIn} object to String to show in logs, the default
	 * is the <code>java.util.Objects.toString</code>
	 * 
	 * @param saveEntityIn The entity to transform
	 * 
	 * @return A string object
	 */
	@NotNull
	protected String convertSaveEntityInToPublishDataIn(final SaveEntityIn saveEntityIn) {
		return Objects.toString(saveEntityIn);
	}

	/**
	 * Converts a {@code SaveEntityOut} object to String to show in logs, the
	 * default is the <code>java.util.Objects.toString</code>
	 * 
	 * @param saveEntityOut The entity to transform
	 * 
	 * @return A string object
	 */
	@NotNull
	protected String convertSaveEntityOutToPublishDataOut(final SaveEntityOut saveEntityOut) {
		return Objects.toString(saveEntityOut);
	}

	/**
	 * Publish successful save event to message service
	 * 
	 * @param saveEntityIn  The object you want to save on the persistence
	 *                      mechanism
	 * @param saveEntityOut The save command result
	 * @param directives    Objects used to configure the save operation
	 */
	protected void publishSaveEvent(
			final SaveEntityIn saveEntityIn,
			final SaveEntityOut saveEntityOut,
			final Object... directives) {
		publishCommandEvent(
				saveEntityIn, saveEntityOut, SAVE_ENTITY,
				this::convertSaveEntityInToPublishDataIn,
				this::convertSaveEntityOutToPublishDataOut, directives);
	}

	/**
	 * Publish unsuccessful save event to message service
	 * 
	 * @param saveEntityIn The object you want to save on the persistence
	 *                     mechanism
	 * @param exception    The save command exception
	 * @param directives   Objects used to configure the save operation
	 */
	protected void publishSaveEvent(
			final SaveEntityIn saveEntityIn,
			final Exception exception,
			final Object... directives) {
		publishCommandEvent(
				saveEntityIn, exception, SAVE_ENTITY,
				this::convertSaveEntityInToPublishDataIn,
				this::convertExceptionToPublishDataOut, directives);
	}

	// --------------------------------------------------------

	/**
	 * Converts a {@code SaveEntitiesIn} object to String to show in logs, the
	 * default is the <code>java.util.Objects.toString</code>
	 * 
	 * @param saveEntitiesIn The entity to transform
	 * 
	 * @return A string object
	 */
	protected String convertSaveEntitiesInToPublishDataIn(final SaveEntitiesIn saveEntitiesIn) {
		return Objects.toString(saveEntitiesIn);
	}

	/**
	 * Converts a {@code SaveEntitiesOut} object to String to show in logs, the
	 * default is the <code>java.util.Objects.toString</code>
	 * 
	 * @param SaveEntitiesOut The entity to transform
	 * 
	 * @return A string object
	 */
	protected String convertSaveEntitiesOutToPublishDataOut(final SaveEntitiesOut saveEntitiesOut) {
		return Objects.toString(saveEntitiesOut);
	}

	/**
	 * Publish successful save all event to message service
	 * 
	 * @param saveEntitiesIn  The objects you want to save on the persistence
	 *                        mechanism
	 * @param saveEntitiesOut The save command result
	 * @param directives      Objects used to configure the save operation
	 */
	protected void publishSaveAllEvent(
			final SaveEntitiesIn saveEntitiesIn,
			final SaveEntitiesOut saveEntitiesOut,
			final Object... directives) {

		publishCommandEvent(
				saveEntitiesIn, saveEntitiesOut, SAVE_ENTITIES,
				this::convertSaveEntitiesInToPublishDataIn,
				this::convertSaveEntitiesOutToPublishDataOut, directives);
	}

	/**
	 * Publish unsuccessful save all event to message service
	 * 
	 * @param saveEntitiesIn The objects you want to save on the persistence
	 *                       mechanism
	 * @param exception      The save all command exception
	 * @param directives     Objects used to configure the save operation
	 */
	protected void publishSaveAllEvent(
			final SaveEntitiesIn saveEntitiesIn,
			final Exception exception,
			final Object... directives) {

		publishCommandEvent(
				saveEntitiesIn, exception, SAVE_ENTITIES,
				this::convertSaveEntitiesInToPublishDataIn,
				this::convertExceptionToPublishDataOut, directives);
	}

	// --------------------------------------------------------

	/**
	 * Converts a {@code UpdateEntityIn} object to String to show in logs, the
	 * default is the
	 * <code>java.util.Objects.toString</code>
	 * 
	 * @param updateEntityIn The entity to transform
	 * 
	 * @return A String object
	 */
	protected String convertUpdateEntityInToPublishDataIn(final UpdateEntityIn updateEntityIn) {
		return Objects.toString(updateEntityIn);
	}

	/**
	 * Create a supplier function (deferred execution) that converts a
	 * {@code UpdateEntityOut} object to String to show in logs, the default is the
	 * <code>java.util.Objects.toString</code>
	 * 
	 * @param updateEntityOut The entity to transform
	 * 
	 * @return A Supplier object
	 */
	protected String convertUpdateEntityOutToPublishDataOut(final UpdateEntityOut updateEntityOut) {
		return Objects.toString(updateEntityOut);
	}

	/**
	 * Publish successful update event to message service
	 * 
	 * @param updateEntityIn  The objects you want to update on the persistence
	 *                        mechanism
	 * @param updateEntityOut The update command result
	 * @param directives      Objects used to configure the update operation
	 */
	protected void publishUpdateEvent(
			final UpdateEntityIn updateEntityIn,
			final UpdateEntityOut updateEntityOut,
			final Object... directives) {

		publishCommandEvent(
				updateEntityIn, updateEntityOut, UPDATE_ENTITY,
				this::convertUpdateEntityInToPublishDataIn,
				this::convertUpdateEntityOutToPublishDataOut, directives);
	}

	/**
	 * Publish unsuccessful update event to message service
	 * 
	 * @param updateEntityIn The objects you want to update on the persistence
	 *                       mechanism
	 * @param exception      The update command exception
	 * @param directives     Objects used to configure the save operation
	 */
	protected void publishUpdateEvent(
			final UpdateEntityIn updateEntityIn,
			final Exception exception,
			final Object... directives) {

		publishCommandEvent(
				updateEntityIn, exception, UPDATE_ENTITY,
				this::convertUpdateEntityInToPublishDataIn,
				this::convertExceptionToPublishDataOut, directives);
	}

	// --------------------------------------------------------

	/**
	 * Converts a {@code UpdateEntitiesIn} object to String to show in logs, the
	 * default is the <code>java.util.Objects.toString</code>
	 * 
	 * @param updateEntitiesIn The group of entities to transform
	 * 
	 * @return A string object
	 */
	protected String convertUpdateEntitiesInToPublishDataIn(final UpdateEntitiesIn updateEntitiesIn) {
		return Objects.toString(updateEntitiesIn);
	}

	/**
	 * Converts a {@code UpdateEntitiesOut} object to String to show in logs, the
	 * default is the <code>java.util.Objects.toString</code>
	 * 
	 * @param updateEntitiesOut The group of entities to transform
	 * 
	 * @return A string object
	 */
	protected String convertUpdateEntitiesOutToPublishDataOut(final UpdateEntitiesOut updateEntitiesOut) {
		return Objects.toString(updateEntitiesOut);
	}

	/**
	 * Publish successful update all event to message service
	 * 
	 * @param updateEntitiesIn  The objects you want to update on the
	 *                          persistence mechanism
	 * @param updateEntitiesOut The update command result
	 * @param directives        Objects used to configure the save operation
	 */
	protected void publishUpdateAllEvent(
			final UpdateEntitiesIn updateEntitiesIn,
			final UpdateEntitiesOut updateEntitiesOut,
			final Object... directives) {

		publishCommandEvent(
				updateEntitiesIn, updateEntitiesOut, UPDATE_ENTITIES,
				this::convertUpdateEntitiesInToPublishDataIn,
				this::convertUpdateEntitiesOutToPublishDataOut, directives);
	}

	/**
	 * Publish unsuccessful update all event to message service
	 * 
	 * @param updateEntitiesIn The objects you want to update on the
	 *                         persistence mechanism
	 * @param exception        The update command exception
	 * @param directives       Objects used to configure the save operation
	 */
	protected void publishUpdateAllEvent(
			final UpdateEntitiesIn updateEntitiesIn,
			final Exception exception,
			final Object... directives) {

		publishCommandEvent(
				updateEntitiesIn, exception, UPDATE_ENTITIES,
				this::convertUpdateEntitiesInToPublishDataIn,
				this::convertExceptionToPublishDataOut, directives);
	}

	// --------------------------------------------------------

	/**
	 * Converts a {@code DeleteEntityIn} object to String to show in logs, the
	 * default is the <code>java.util.Objects.toString</code>
	 * 
	 * @param deleteEntityIn The entity to transform
	 * 
	 * @return A string object
	 */
	protected String convertDeleteEntityInToPublishDataIn(final DeleteEntityIn deleteEntityIn) {
		return Objects.toString(deleteEntityIn);
	}

	/**
	 * Converts a {@code DeleteEntityOut} object to String to show in logs, the
	 * default is the <code>java.util.Objects.toString</code>
	 * 
	 * @param deleteEntityOut The entity to transform
	 * 
	 * @return A Supplier object
	 */
	protected String convertDeleteEntityOutToPublishDataOut(final DeleteEntityOut deleteEntityOut) {
		return Objects.toString(deleteEntityOut);
	}

	/**
	 * Publish successful delete event to message service
	 * 
	 * @param deleteEntityIn  The objects you want to update on the persistence
	 *                        mechanism
	 * @param deleteEntityOut The update command result
	 * @param directives      Objects used to configure the update operation
	 */
	protected void publishDeleteEvent(
			final DeleteEntityIn deleteEntityIn,
			final DeleteEntityOut deleteEntityOut,
			final Object... directives) {

		publishCommandEvent(
				deleteEntityIn, deleteEntityOut, DELETE_ENTITY,
				this::convertDeleteEntityInToPublishDataIn,
				this::convertDeleteEntityOutToPublishDataOut, directives);
	}

	/**
	 * Publish unsuccessful delete event to message service
	 * 
	 * @param updateEntityIn The objects you want to update on the persistence
	 *                       mechanism
	 * @param exception      The update command exception
	 * @param directives     Objects used to configure the save operation
	 */
	protected void publishDeleteEvent(
			final DeleteEntityIn deleteEntityIn,
			final Exception exception,
			final Object... directives) {

		publishCommandEvent(
				deleteEntityIn, exception, DELETE_ENTITY,
				this::convertDeleteEntityInToPublishDataIn,
				this::convertExceptionToPublishDataOut, directives);
	}

	// --------------------------------------------------------

	/**
	 * Converts a {@code DeleteEntitiesIn} object to String to show in logs, the
	 * default is the <code>java.util.Objects.toString</code>
	 * 
	 * @param deleteEntitiesIn The entity to transform
	 * 
	 * @return A string object
	 */
	protected String convertDeleteEntitiesInToPublishDataIn(final DeleteEntitiesIn deleteEntitiesIn) {
		return Objects.toString(deleteEntitiesIn);
	}

	/**
	 * Converts a {@code DeleteEntitiesOut} object to String to show in logs, the
	 * default is the <code>java.util.Objects.toString</code>
	 * 
	 * @param deleteEntitiesOut The entity to transform
	 * 
	 * @return A string object
	 */
	protected String convertDeleteEntitiesOutToPublishDataOut(final DeleteEntitiesOut deleteEntitiesOut) {
		return Objects.toString(deleteEntitiesOut);
	}

	/**
	 * Publish successful delete all event to message service
	 * 
	 * @param deleteEntitiesIn  The objects you want to save on the persistence
	 *                          mechanism
	 * @param deleteEntitiesOut The save command result
	 * @param directives        Objects used to configure the save operation
	 */
	protected void publishDeleteAllEvent(
			final DeleteEntitiesIn deleteEntitiesIn,
			final DeleteEntitiesOut deleteEntitiesOut,
			final Object... directives) {

		publishCommandEvent(
				deleteEntitiesIn, deleteEntitiesOut, DELETE_ENTITIES,
				this::convertDeleteEntitiesInToPublishDataIn,
				this::convertDeleteEntitiesOutToPublishDataOut, directives);
	}

	/**
	 * Publish unsuccessful delete all event to message service
	 * 
	 * @param deleteEntitiesIn The objects you want to save on the persistence
	 *                         mechanism
	 * @param exception        The delete all command exception
	 * @param directives       Objects used to configure the save operation
	 */
	protected void publishDeleteAllEvent(
			final DeleteEntitiesIn deleteEntitiesIn,
			final Exception exception,
			final Object... directives) {

		publishCommandEvent(
				deleteEntitiesIn, exception, SAVE_ENTITIES,
				this::convertDeleteEntitiesInToPublishDataIn,
				this::convertExceptionToPublishDataOut, directives);
	}

	// --------------------------------------------------------

	/**
	 * Converts a {@code DeleteIdIn} object to String to show in logs, the default
	 * is the <code>java.util.Objects.toString</code>
	 * 
	 * @param deleteIdIn The entity to transform
	 * 
	 * @return A string object
	 */
	@NotNull
	protected String convertDeleteIdInToPublishDataIn(final DeleteIdIn deleteIdIn) {
		return Objects.toString(deleteIdIn);
	}

	/**
	 * Converts a {@code DeleteIdOut} object to String to show in logs, the
	 * default is the <code>java.util.Objects.toString</code>
	 * 
	 * @param deleteIdOut The entity to transform
	 * 
	 * @return A string object
	 */
	@NotNull
	protected String convertDeleteIdOutToPublishDataOut(final DeleteIdOut deleteIdOut) {
		return Objects.toString(deleteIdOut);
	}

	/**
	 * Publish successful delete by id event to message service
	 * 
	 * @param deleteIdIn  The object id you want to delete by id on the persistence
	 *                    mechanism
	 * @param deleteIdOut The delete by id command result
	 * @param directives  Objects used to configure the delete by id operation
	 */
	protected void publishDeleteByIdEvent(
			final DeleteIdIn deleteIdIn,
			final DeleteIdOut deleteIdOut,
			final Object... directives) {

		publishCommandEvent(
				deleteIdIn, deleteIdOut, DELETE_BY_ID,
				this::convertDeleteIdInToPublishDataIn,
				this::convertDeleteIdOutToPublishDataOut, directives);
	}

	/**
	 * Publish unsuccessful delete by id event to message service
	 * 
	 * @param deleteIdIn The object id you want to delete on the persistence
	 *                   mechanism
	 * @param exception  The save command exception
	 * @param directives Objects used to configure the save operation
	 */
	protected void publishDeleteByIdEvent(
			final DeleteIdIn deleteIdIn,
			final Exception exception,
			final Object... directives) {

		publishCommandEvent(
				deleteIdIn, exception, DELETE_BY_ID,
				this::convertDeleteIdInToPublishDataIn,
				this::convertExceptionToPublishDataOut, directives);
	}

	// --------------------------------------------------------

	/**
	 * Converts a {@code DeleteIdsIn} object to String to show in logs, the default
	 * is the <code>java.util.Objects.toString</code>
	 * 
	 * @param deleteIdsIn The entity to transform
	 * 
	 * @return A string object
	 */
	@NotNull
	protected String convertDeleteIdsInToPublishDataIn(final DeleteIdsIn deleteIdsIn) {
		return Objects.toString(deleteIdsIn);
	}

	/**
	 * Converts a {@code DeleteIdOut} object to String to show in logs, the
	 * default is the <code>java.util.Objects.toString</code>
	 * 
	 * @param deleteIdsOut The entity to transform
	 * 
	 * @return A string object
	 */
	@NotNull
	protected String convertDeleteIdsOutToPublishDataOut(final DeleteIdsOut deleteIdsOut) {
		return Objects.toString(deleteIdsOut);
	}

	/**
	 * Publish successful delete by ids event to message service
	 * 
	 * @param deleteIdsIn  The object id you want to delete by id on the persistence
	 *                     mechanism
	 * @param deleteIdsOut The delete by id command result
	 * @param directives   Objects used to configure the delete by ids operation
	 */
	protected void publishDeleteByIdsEvent(
			final DeleteIdsIn deleteIdsIn,
			final DeleteIdsOut deleteIdsOut,
			final Object... directives) {

		publishCommandEvent(
				deleteIdsIn, deleteIdsOut, DELETE_BY_IDS,
				this::convertDeleteIdsInToPublishDataIn,
				this::convertDeleteIdsOutToPublishDataOut, directives);
	}

	/**
	 * Publish unsuccessful delete by ids event to message service
	 * 
	 * @param deleteIdsIn The object id you want to delete on the persistence
	 *                    mechanism
	 * @param exception   The save command exception
	 * @param directives  Objects used to configure the delete by ids operation
	 */
	protected void publishDeleteByIdsEvent(
			final DeleteIdsIn deleteIdsIn,
			final Exception exception,
			final Object... directives) {

		publishCommandEvent(
				deleteIdsIn, exception, DELETE_BY_IDS,
				this::convertDeleteIdsInToPublishDataIn,
				this::convertExceptionToPublishDataOut, directives);
	}

}
