package org.reusablecomponent.core.application.command.entity;

import static java.lang.Boolean.FALSE;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import org.reusablecomponent.core.application.base.AbstractEntiyBaseFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.core.infra.exception.ElementConflictException;
import org.reusablecomponent.core.infra.exception.ElementWithIdNotFoundException;
import org.reusablecomponent.core.infra.messaging.event.Event;
import org.reusablecomponent.core.infra.messaging.event.OperationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 * @param <OneResult>
 * @param <MultipleResult>
 * @param <VoidResult>
 * @param <BooleanResult>
 */
public class EntityCommandFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultipleResult, VoidResult, BooleanResult> // Operations on Entity 
	extends AbstractEntiyBaseFacade<Entity, Id>  // Base facade
	implements InterfaceEntityCommandFacade<Entity, Id, OneResult, MultipleResult, VoidResult> {  // interface command facade

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityCommandFacade.class);

    protected final Function<Entity, OneResult> saveFunction;

    protected final Function<Entity, VoidResult> deleteFunction;
    
    protected final Function<Iterable<Entity>, VoidResult> deleteAllFunction;
    
    protected final Function<Id, VoidResult> deleteByIdFunction;
    
    protected final Function<Iterable<Id>, VoidResult> deleteAllByIdFunction;

    protected final Function<Id, BooleanResult> existsByIdFunction;
    
    protected final Predicate<BooleanResult> existsEntityFunction;

    protected final Function<Iterable<Entity>, MultipleResult> saveAllFunction;

    /**
     * @param saveFunction
     * @param saveAllFunction
     * @param deleteFunction
     * @param deleteAllFunction
     * @param deleteByIdFunction
     * @param deleteAllByIdFunction
     * @param existsByIdFunction
     * @param existsEntityFunction
     */
    public EntityCommandFacade(
		    @NotNull final Function<Entity, OneResult> saveFunction, // function to save one entity 
		    @NotNull final Function<Iterable<Entity>, MultipleResult> saveAllFunction, // save all Function
		    //
		    @NotNull final Function<Entity, VoidResult> deleteFunction, // function to delete one entity
		    @NotNull final Function<Iterable<Entity>, VoidResult> deleteAllFunction, // function to delete all entities
		    @NotNull final Function<Id, VoidResult> deleteByIdFunction, // function to delete one by id
		    @NotNull final Function<Iterable<Id>, VoidResult> deleteAllByIdFunction, // function to delete a entity's collection by id
		    //
		    @NotNull final Function<Id, BooleanResult> existsByIdFunction, // function check it exists by id, it's can return diferrent things
		    @NotNull final Predicate<BooleanResult> existsEntityFunction // Here we convert BooleanResult to Boolean
    ){
	super();
	this.saveFunction = saveFunction;
	this.saveAllFunction = saveAllFunction;
	//
	this.deleteFunction = deleteFunction;
	this.deleteAllFunction = deleteAllFunction;
	this.deleteByIdFunction = deleteByIdFunction;
	this.deleteAllByIdFunction = deleteAllByIdFunction;
	//
	this.existsByIdFunction = existsByIdFunction;
	this.existsEntityFunction = existsEntityFunction;
    }

    // ---------------------------------------------------------------------------

    /**
     * @param entity
     */
    protected void preSave(final Entity entity) {

    }

    /**
     * @param entity
     */
    protected void posSave(final Object entity) {

    }
    
    /**
     * @param entity
     */
    protected void publishSave(final Object entity) {
	
	final var operation = OperationEvent.SAVE_ITEM;
	
	final var event = new Event.Builder()
			.build();
	
	
	publisherSerice.publish(event);
    }
    
    /**
     * {@inheritDoc}
     */
    @Valid
    @NotNull
    @Override
    public OneResult save(@NotNull final Entity entity) {

	LOGGER.debug("");

	preSave(entity);

	final var savedEntity = saveFunction.apply(entity);

	if (entity.isPublishable()) {
	    publishSave(savedEntity);
	}
	
	posSave(savedEntity);

	LOGGER.debug("");
	
	return savedEntity;
    }

    /**
     * @param entities
     */
    protected void preSaveAll(final Iterable<Entity> entities) {
	
    }

    /**
     * @param entities
     */
    protected void posSaveAll(final MultipleResult entities) {
	
    }
    
    /**
     * @param entity
     */
    protected void publishSaveAll(final Iterable<Entity> entity) {
	
	final var operation = OperationEvent.SAVE_LIST;
	
	final var event = new Event.Builder()
			.build();
	
	
	publisherSerice.publish(event);
    }    
    
    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public MultipleResult saveAll(@NotNull final Iterable<@NotNull Entity> entities) {
	
	preSaveAll(entities);
	
	final var result = saveAllFunction.apply(entities);
	
	final var entitiestoPublish = StreamSupport.stream(entities.spliterator(), false)
			.filter(Entity::isPublishable)
			.toList();
	publishSaveAll(entitiestoPublish);
	
	posSaveAll(result);
	
	return result;
    }
    
    // ---------------------------------------------------------------------------
    
    /**
     * @param entity
     */
    protected void preUpdate(final Entity entity) {

    }

    /**
     * @param entity
     * @return
     */
    protected OneResult posUpdate(final OneResult entity) {
	return entity;
    }
    
    /**
     * @param entity
     */
    protected void publishUpdate(final Object entity) {
	
	final var operation = OperationEvent.UPDATE_ITEM;
	
	final var event = new Event.Builder()
			.build();
	
	
	publisherSerice.publish(event);
    }     
    
    /**
     * {@inheritDoc}
     */
    @Valid
    @Override
    public OneResult update(@NotNull @Valid final Entity entity) {
	preUpdate(entity);

	final var result = saveFunction.apply(entity);
	
	if (entity.isPublishable()) {
	    publishUpdate(entity);
	}

	return posUpdate(result);
    }
    
    /**
     * {@inheritDoc}
     */
    @Valid
    @NotNull
    @Override
    public OneResult update(@NotNull final Id id, @NotNull @Valid final Entity entity) {
	
	preUpdate(entity);

	final var existsEntity = checkEntityExists(existsEntityFunction, existsByIdFunction.apply(id));
	
	if (Objects.equals(FALSE, existsEntity)) {
	    throw new ElementWithIdNotFoundException(getEntityClazz(), getI18nService(), id);
	}

	final var result = saveFunction.apply(entity);
	
	if (entity.isPublishable()) {
	    publishUpdate(entity);
	}

	return posUpdate(result);
    }    
    
    
    /**
     * @param entities
     */
    protected void preUpdateAll(final Iterable<Entity> entities) {
	
    }

    /**
     * @param entities
     */
    protected void posUpdateAll(final Iterable<Entity> entities) {
	
    }    
    
    
    /**
     * @param entity
     */
    protected void publishUpdateAll(final Iterable<Entity> entities) {
	
	final var operation = OperationEvent.UPDATE_LIST;
	
	final var event = new Event.Builder()
			.build();
	
	
	publisherSerice.publish(event);
    }     
    
    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public MultipleResult updateAll(@NotNull final Iterable<@NotNull @Valid Entity> entities) {
	
	preUpdateAll(entities);
	
	final var result = saveAllFunction.apply(entities);
	
	final var entitiestoPublish = StreamSupport.stream(entities.spliterator(), false)
			.filter(Entity::isPublishable)
			.toList();
	
	posUpdateAll(entities);
	
	publishUpdateAll(entitiestoPublish);
	
	return result;
    }

    // ---------------------------------------------------------------------------
    /**
     * @param entity
     */
    protected void preDelete(final Entity entity) {
    }
    
    /**
     * @param entity
     */
    protected void posDelete(final Entity entity) {
    }
    
    /**
     * @param entity
     */
    protected void publishDelete(final Entity entity) {
	
	final var operation = OperationEvent.DELETE_ITEM;
	
	final var event = new Event.Builder()
			.build();
	
	
	publisherSerice.publish(event);
    }  
    
    /**
     * {@inheritDoc}
     */
    @Override
    public VoidResult delete(@NotNull final Entity entity) {

	final var existsEntity = checkEntityExists(existsEntityFunction, existsByIdFunction.apply(entity.getId()));
	
	if (Objects.equals(FALSE, existsEntity)) {
	    throw new ElementWithIdNotFoundException(getEntityClazz(), getI18nService(), entity.getId());
	}

	preDelete(entity);

	VoidResult result = null;
	
	try {

	    result = deleteFunction.apply(entity);

	} catch (final Exception ex) {

	    throw new ElementConflictException("{exception.entityDeleteDataIntegrityViolation}", ex, entity.getId().toString());
	}
	
	if (entity.isPublishable()) {
	    publishDelete(entity);
	}

	posDelete(entity);
	
	return result;
    }
    
    /**
     * @param entity
     */
    protected void preDeleteAll(final Iterable<Entity> entities) {
    }
    
    /**
     * @param entity
     */
    protected void posDeleteAll(final Iterable<Entity> entities) {
    }
    
    /**
     * @param entity
     */
    protected void publishDeleteAll(final Iterable<Entity> entities) {
	
	final var operation = OperationEvent.DELETE_LIST;
	
	final var event = new Event.Builder()
			.build();
	
	
	publisherSerice.publish(event);
    }  
    
    /**
     * {@inheritDoc}
     */
    @Override
    public VoidResult deleteAll(@NotNull final Iterable<@NotNull Entity> entities) {
	
	preDeleteAll(entities);
	
	final var result = deleteAllFunction.apply(entities);
	
	posDeleteAll(entities);
	
	final var entitiestoPublish = StreamSupport.stream(entities.spliterator(), false)
			.filter(Entity::isPublishable)
			.toList();
	publishDeleteAll(entitiestoPublish);
	
	return result;
    }
    
    /**
     * @param id
     */
    protected void preDeleteBy(final Id id) {
    }
    
    /**
     * @param id
     */
    protected void posDeleteBy(final Id id) {
    }

    /**
     * @param entity
     */
    protected void publishDeleteById(final Id id) {
	
	final var operation = OperationEvent.DELETE_ID;
	
	final var event = new Event.Builder()
			.build();
	
	
	publisherSerice.publish(event);
    } 
    
    /**
     * {@inheritDoc}
     */
    @Override
    public VoidResult deleteBy(@NotNull final Id id) {

	final var exists = checkEntityExists(existsEntityFunction, existsByIdFunction.apply(id));
	
	if (Objects.equals(Boolean.FALSE, exists)) {
	    throw new ElementWithIdNotFoundException(getEntityClazz(), getI18nService(), id);
	}
	
	preDeleteBy(id);

	try {

	    final var result = deleteByIdFunction.apply(id);

	    posDeleteBy(id);
	    
	    publishDeleteById(id);
	    
	    return result;
	    
	} catch (final Exception ex) {
	    throw new ElementConflictException("{exception.entityDeleteDataIntegrityViolation}", ex, id.toString());
	}
    }
    
    /**
     * @param id
     */
    protected void preDeleteBy(final Iterable<Id> ids) {
    }
    
    /**
     * @param id
     */
    protected void posDeleteBy(final Iterable<Id> ids) {
    }   

    /**
     * @param entity
     */
    protected void publishDeleteAllBy(final Iterable<Id> ids) {
	
	final var operation = OperationEvent.DELETE_IDS;
	
	final var event = new Event.Builder()
			.build();
	
	
	publisherSerice.publish(event);
    }  
    
    /**
     * {@inheritDoc}
     */
    @Override
    public VoidResult deleteAllBy(@NotNull final Iterable<Id> ids) {
	preDeleteBy(ids);
	
	final var result = deleteAllByIdFunction.apply(ids);
	
	posDeleteBy(ids);
	
	publishDeleteAllBy(ids);
	
	return result;
    }    

}
