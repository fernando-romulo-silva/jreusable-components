package org.reusablecomponent.core.application.command.entity;

import static org.reusablecomponent.core.infra.messaging.event.CommonEvent.DELETE_ID;
import static org.reusablecomponent.core.infra.messaging.event.CommonEvent.DELETE_IDS;
import static org.reusablecomponent.core.infra.messaging.event.CommonEvent.DELETE_ITEM;
import static org.reusablecomponent.core.infra.messaging.event.CommonEvent.DELETE_LIST;
import static org.reusablecomponent.core.infra.messaging.event.CommonEvent.SAVE_ITEM;
import static org.reusablecomponent.core.infra.messaging.event.CommonEvent.SAVE_LIST;
import static org.reusablecomponent.core.infra.messaging.event.CommonEvent.UPDATE_ITEM;
import static org.reusablecomponent.core.infra.messaging.event.CommonEvent.UPDATE_LIST;

import java.util.Objects;
import java.util.function.Function;

import org.reusablecomponent.core.application.base.AbstractEntiyBaseFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 * @param <SaveEntityIn>
 * @param <SaveEntityOut>
 * @param <SaveEntitiesIn>
 * @param <SaveEntitiesOut>
 * @param <UpdateEntityIn>
 * @param <UpdateEntityOut>
 * @param <UpdateEntitiesIn>
 * @param <UpdateEntitiesOut>
 * @param <DeleteEntityIn>
 * @param <DeleteEntityOut>
 * @param <DeleteEntitiesIn>
 * @param <DeleteEntitiesOut>
 * @param <DeleteIdIn>
 * @param <DeleteIdOut>
 * @param <DeleteIdsIn>
 * @param <DeleteIdsOut>
 */
public class EntityCommandFacade <  // generics 
		// default
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
                DeleteIdIn, DeleteIdOut, // delete a entity by id
                DeleteIdsIn, DeleteIdsOut // delete entities by id
	    > 
	// Base Facade
	extends AbstractEntiyBaseFacade<Entity, Id>  
	// Interface command facade
	implements InterfaceEntityCommandFacade	<
            Entity, Id, // basic
            //
            SaveEntityIn, SaveEntityOut, // save a entity
            SaveEntitiesIn, SaveEntitiesOut, // save entities
            
            UpdateEntityIn, UpdateEntityOut, // update a entity
            UpdateEntitiesIn, UpdateEntitiesOut, // update entities
            
            DeleteEntityIn, DeleteEntityOut, // delete a entity
            DeleteEntitiesIn, DeleteEntitiesOut, // delete entities
            
            DeleteIdIn, DeleteIdOut, // delete a entity by id
            DeleteIdsIn, DeleteIdsOut> { // delete entities by id
		

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityCommandFacade.class);

    protected final Function<SaveEntityIn, SaveEntityOut> saveFunction;
    protected final Function<SaveEntitiesIn, SaveEntitiesOut> saveAllFunction;
    
    protected final Function<UpdateEntityIn, UpdateEntityOut> updateFunction;
    protected final Function<UpdateEntitiesIn, UpdateEntitiesOut> updateAllFunction;    
    
    protected final Function<DeleteEntityIn, DeleteEntityOut> deleteFunction;
    protected final Function<DeleteEntitiesIn, DeleteEntitiesOut> deleteAllFunction;
    protected final Function<DeleteIdIn, DeleteIdOut> deleteByIdFunction;
    protected final Function<DeleteIdsIn, DeleteIdsOut> deleteAllByIdFunction;

    public EntityCommandFacade(final EntityCommandFacadeBuilder<Entity, Id, SaveEntityIn, SaveEntityOut, SaveEntitiesIn, SaveEntitiesOut, UpdateEntityIn, UpdateEntityOut, UpdateEntitiesIn, UpdateEntitiesOut, DeleteEntityIn, DeleteEntityOut, DeleteEntitiesIn, DeleteEntitiesOut, DeleteIdIn, DeleteIdOut, DeleteIdsIn, DeleteIdsOut> builder) {
	// super class parameters
	super(
		builder.publisherSerice, 
		builder.i18nService, 
		builder.securityService, 
		builder.exceptionTranslatorService
	);
	//
	this.saveFunction = builder.saveFunction;
	this.saveAllFunction = builder.saveAllFunction;
	//
	this.updateFunction = builder.updateFunction;
	this.updateAllFunction = builder.updateAllFunction;
	//
	this.deleteFunction = builder.deleteFunction;
	this.deleteAllFunction = builder.deleteAllFunction;
	//
	this.deleteByIdFunction = builder.deleteByIdFunction;
	this.deleteAllByIdFunction = builder.deleteAllByIdFunction;
    }
    
    // ----------------------------------------------------------------------------------------------------------
    
    /**
     * @param saveEntityIn
     * @return
     */
    protected String convertSaveEntityInToPublishData(final SaveEntityIn saveEntityIn) {
	return Objects.toString(saveEntityIn);
    }
    
    /**
     * @param saveEntityOut
     * @return
     */
    protected String convertSaveEntityOutToPublishData(final SaveEntityOut saveEntityOut) {
	return Objects.toString(saveEntityOut);
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
    @Valid
    @NotNull
    @Override
    public SaveEntityOut save(@NotNull final SaveEntityIn saveEntityIn) {

	final var session = securityService.getSession();
	
	LOGGER.debug("Saving entity '{}', session '{}'", saveEntityIn, session);

	final var finalSaveEntityIn = preSave(saveEntityIn);

	final SaveEntityOut result;
	
	try {
	    result = saveFunction.apply(finalSaveEntityIn);
	} catch (final Exception ex) {
	    throw exceptionTranslatorService.translate(ex, i18nService);
	}

	final var finalResult = posSave(result);
	
	final var dataIn = convertSaveEntityInToPublishData(finalSaveEntityIn);
	final var dataOut = convertSaveEntityOutToPublishData(finalResult);
	publish(dataIn, dataOut, SAVE_ITEM, finalSaveEntityIn, finalResult);

	LOGGER.debug("Saved entity '{}', session '{}'", finalResult, session);
	
	return finalResult;
    }

    
    // ----------------------------------------------------------------------------------------------------------
    
    protected String convertDataSaveEntitiesInToPublishData(final SaveEntitiesIn saveEntitiesIn) {
	return Objects.toString(saveEntitiesIn);
    }
    
    protected String convertSaveEntitiesOutToPublishData(final SaveEntitiesOut saveEntitiesOut) {
	return Objects.toString(saveEntitiesOut);
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
    @NotNull
    @Override
    public SaveEntitiesOut saveAll(@NotNull final SaveEntitiesIn saveEntitiesIn) {
	
	final var session = securityService.getSession();
	
	LOGGER.debug("Saving all entities '{}' with session '{}'", saveEntitiesIn, session);
	
	final var finalSaveEntitiesIn = preSaveAll(saveEntitiesIn);
	
	final SaveEntitiesOut result;
	
	try {
	    result = saveAllFunction.apply(finalSaveEntitiesIn);
	} catch (final Exception ex) {
	    throw exceptionTranslatorService.translate(ex, i18nService);
	}	
	
	final var finalResult = posSaveAll(result);
	
	final var dataIn = convertDataSaveEntitiesInToPublishData(finalSaveEntitiesIn);
	final var dataOut = convertSaveEntitiesOutToPublishData(finalResult);
	publish(dataIn, dataOut, SAVE_LIST, finalSaveEntitiesIn, finalResult);
	
	LOGGER.debug("Saved all entities '{}' with session '{}'", finalResult, session);	
	
	return finalResult;
    }
    
    // ----------------------------------------------------------------------------------------------------------
    
    protected String convertUpdateEntityInToPublishData(final UpdateEntityIn updateEntityIn) {
	return Objects.toString(updateEntityIn);
    }
    
    protected String convertUpdateEntityOutToPublishData(final UpdateEntityOut updateEntityOut) {
	return Objects.toString(updateEntityOut);
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
    @Valid
    @Override
    public UpdateEntityOut update(@NotNull @Valid final UpdateEntityIn updateEntityIn) {
	
	final var session = securityService.getSession();
	
	LOGGER.debug("Updating entity '{}' with session '{}'", updateEntityIn, session);
	
	final var finalUpdateEntityIn = preUpdate(updateEntityIn);

	final UpdateEntityOut result;
	
	try {
	    result = updateFunction.apply(finalUpdateEntityIn);
	} catch (final Exception ex) {
	    throw exceptionTranslatorService.translate(ex, i18nService);
	}	
	
	final var finalResult = posUpdate(result);
	
	final var dataIn = convertUpdateEntityInToPublishData(finalUpdateEntityIn);
	final var dataOut = convertUpdateEntityOutToPublishData(finalResult);
	publish(dataIn, dataOut, UPDATE_ITEM, finalUpdateEntityIn, finalResult);
	
	LOGGER.debug("Updated entity '{}' with session '{}'", finalResult, session);	
	
	return finalResult;
    }
    
    // ----------------------------------------------------------------------------------------------------------
    
    protected String convertUpdateEntitiesInToPublishData(final UpdateEntitiesIn updateEntitiesIn) {
	return Objects.toString(updateEntitiesIn);
    }
    
    protected String convertUpdateEntitiesOutToPublishData(final UpdateEntitiesOut updateEntitiesOut) {
	return Objects.toString(updateEntitiesOut);
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
    @NotNull
    @Override
    public UpdateEntitiesOut updateAll(@NotNull final UpdateEntitiesIn updateEntitiesIn) {
	
	final var session = securityService.getSession();
	
	LOGGER.debug("Updating entities '{}' with session '{}'", updateEntitiesIn, session);
	
	final var finalUpdateEntitiesIn = preUpdateAll(updateEntitiesIn);
	
	final UpdateEntitiesOut result;

	try {
	    result = updateAllFunction.apply(finalUpdateEntitiesIn);
	} catch (final Exception ex) {
	    throw exceptionTranslatorService.translate(ex, i18nService);
	}
	
	final var finalResult = posUpdateAll(result);
	
	final var dataIn = convertUpdateEntitiesInToPublishData(finalUpdateEntitiesIn);
	final var dataOut = convertUpdateEntitiesOutToPublishData(finalResult);
	publish(dataIn, dataOut, UPDATE_LIST, finalUpdateEntitiesIn, finalResult);
	
	LOGGER.debug("Updated all entities '{}' with session '{}'", finalResult, session);
	
	return result;
    }

    // ----------------------------------------------------------------------------------------------------------
    
    protected String convertDeleteEntityInToPublishData(final DeleteEntityIn deleteEntityIn) {
	return Objects.toString(deleteEntityIn);
    }
    
    protected String convertDeleteEntityOutToPublishData(final DeleteEntityOut deleteEntityOut) {
	return Objects.toString(deleteEntityOut);
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
    public DeleteEntityOut delete(@NotNull final DeleteEntityIn deleteEntityIn) {

	final var session = securityService.getSession();
	
	LOGGER.debug("Deleting entity '{}' with session '{}'", deleteEntityIn, session);	
	
	final var finalDeleteEntityIn = preDelete(deleteEntityIn);

	final DeleteEntityOut result;

	try {
	    result = deleteFunction.apply(finalDeleteEntityIn);
	} catch (final Exception ex) {
	    throw exceptionTranslatorService.translate(ex, i18nService);
	}	
	
	final var finalResult = posDelete(result);

	final var dataIn = convertDeleteEntityInToPublishData(finalDeleteEntityIn);
	final var dataOut = convertDeleteEntityOutToPublishData(finalResult);
	publish(dataIn, dataOut, DELETE_ITEM, finalDeleteEntityIn, finalResult);
	
	LOGGER.debug("Deleted entity '{}' with session '{}'", finalResult, session);
	
	return result;
    }
    
    
    // ---------------------------------------------------------------------------------------------------------- 
    
    protected String convertDeleteEntitiesInToPublishData(final DeleteEntitiesIn deleteEntitiesIn) {
	return Objects.toString(deleteEntitiesIn);
    }
    
    protected String convertDeleteEntitiesOutOutToPublishData(final DeleteEntitiesOut deleteEntitiesOut) {
	return Objects.toString(deleteEntitiesOut);
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
    public DeleteEntitiesOut deleteAll(@NotNull final DeleteEntitiesIn deleteEntitiesIn) {
	
	final var session = securityService.getSession();
	
	LOGGER.debug("Deleting entities '{}' with session '{}'", deleteEntitiesIn, session);
	
	final var finalDeleteEntitiesIn = preDeleteAll(deleteEntitiesIn);
	
	final DeleteEntitiesOut result; 
	
	try {
	    result = deleteAllFunction.apply(finalDeleteEntitiesIn);
	} catch (final Exception ex) {
	    throw exceptionTranslatorService.translate(ex, i18nService);
	}
	
	final var finalResult = posDeleteAll(result);
	
	final var dataIn = convertDeleteEntitiesInToPublishData(finalDeleteEntitiesIn);
	final var dataOut = convertDeleteEntitiesOutOutToPublishData(finalResult);
	publish(dataIn, dataOut, DELETE_LIST, finalDeleteEntitiesIn, finalResult);
	
	LOGGER.debug("Deleted entities '{}' with session '{}'", finalResult, session);
	
	return finalResult;
    }
    
    
    // ----------------------------------------------------------------------------------------------------------
    
    protected String convertDeleteIdInToPublishData(final DeleteIdIn deleteIdIn) {
	return Objects.toString(deleteIdIn);
    }
    
    protected String convertDeleteIdOutToPublishData(final DeleteIdOut deleteIdOut) {
	return Objects.toString(deleteIdOut);
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
    public DeleteIdOut deleteBy(@NotNull final DeleteIdIn deleteIdIn) {

	final var session = securityService.getSession();
	
	LOGGER.debug("Deleting by id '{}' with session '{}'", deleteIdIn, session);	
	
	final var finalDeleteIdIn = preDeleteById(deleteIdIn);

	final DeleteIdOut result;
	
	try {
	    result = deleteByIdFunction.apply(finalDeleteIdIn);
	} catch (final Exception ex) {
	    throw exceptionTranslatorService.translate(ex, i18nService);
	}
	
	final var finalResult = posDeleteById(result);

	final var dataIn = convertDeleteIdInToPublishData(finalDeleteIdIn);
	final var dataOut = convertDeleteIdOutToPublishData(finalResult);
	publish(dataIn, dataOut, DELETE_ID, finalDeleteIdIn, finalResult);
	
	LOGGER.debug("Deleted by id '{}' with session '{}'", finalResult, session);

	return finalResult;	    
    }
    
    
    // ----------------------------------------------------------------------------------------------------------
    
    protected String convertDeleteIdsInToPublishData(final DeleteIdsIn deleteIdsIn) {
	return Objects.toString(deleteIdsIn);
    }
    
    protected String convertDeleteIdsOutToPublishData(final DeleteIdsOut deleteIdsOut) {
	return Objects.toString(deleteIdsOut);
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
    public DeleteIdsOut deleteAllBy(@NotNull final DeleteIdsIn deleteIdsIn) {
	
	final var session = securityService.getSession();
	
	LOGGER.debug("Deleting by ids '{}' with session '{}'", deleteIdsIn, session);	
	
	final var finalDeleteIdsIn = preDeleteEntitiesBy(deleteIdsIn);
	
	final DeleteIdsOut result;
	
	try {
	    result = deleteAllByIdFunction.apply(finalDeleteIdsIn);
	} catch (final Exception ex) {
	    throw exceptionTranslatorService.translate(ex, i18nService);
	}
	
	final var finalResult = posDeleteEntitiesBy(result);
	
	final var dataIn = convertDeleteIdsInToPublishData(finalDeleteIdsIn);
	final var dataOut = convertDeleteIdsOutToPublishData(finalResult);
	publish(dataIn, dataOut, DELETE_IDS, finalDeleteIdsIn, finalResult);
	
	LOGGER.debug("Deleted by ids '{}' with session '{}'", finalResult, session);
	
	return finalResult;
    }    
}
