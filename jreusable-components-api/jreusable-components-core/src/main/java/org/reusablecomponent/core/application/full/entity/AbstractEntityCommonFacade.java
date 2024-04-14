package org.reusablecomponent.core.application.full.entity;

import java.util.function.Function;
import java.util.function.Predicate;

import org.reusablecomponent.core.application.command.entity.EntityCommandFacade;
import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 * @param <OneResult>
 * @param <MultipleResult>
 * @param <VoidResult>
 * @param <ExistsResult>
 */
public abstract class AbstractEntityCommonFacade <Entity extends AbstractEntity<Id>, Id, OneResult, MultipleResult, VoidResult, ExistsResult>
	implements InterfaceEntityCommandFacade<Entity, Id, OneResult, MultipleResult, VoidResult> {
    
    protected final InterfaceEntityCommandFacade<Entity, Id, OneResult, MultipleResult, VoidResult> entityCommandFacade;
    
    /**
     * @param entityCommandFacade
     */
    protected AbstractEntityCommonFacade(
		    @NotNull final InterfaceEntityCommandFacade<Entity, Id, OneResult, MultipleResult, VoidResult> entityCommandFacade) {
	super();
	this.entityCommandFacade = entityCommandFacade;
    }
    
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
    protected AbstractEntityCommonFacade( //
		    final Function<Entity, OneResult> saveFunction,
		    final Function<Iterable<Entity>, MultipleResult> saveAllFunction,
		    //
		    final Function<Entity, VoidResult> deleteFunction,
		    final Function<Iterable<Entity>, VoidResult> deleteAllFunction,
		    final Function<Id, VoidResult> deleteByIdFunction,
		    final Function<Iterable<Id>, VoidResult> deleteAllByIdFunction,
		    //
		    final Function<Id, ExistsResult> existsByIdFunction,
		    final Predicate<ExistsResult> existsEntityFunction) {
	
	this.entityCommandFacade = new EntityCommandFacade<>(
			saveFunction, 
			saveAllFunction, 
			deleteFunction, 
			deleteAllFunction, 
			deleteByIdFunction, 
			deleteAllByIdFunction, 
			existsByIdFunction, 
			existsEntityFunction
	);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OneResult save(final Entity entity) {
	return entityCommandFacade.save(entity);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MultipleResult saveAll(final Iterable<Entity> entities) {
	return entityCommandFacade.saveAll(entities);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public OneResult update(final Entity entity) {
	return entityCommandFacade.update(entity);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public OneResult update(final Id id, final Entity entity) {
	return entityCommandFacade.update(id, entity);
    }    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MultipleResult updateAll(final Iterable<Entity> entities) {
	return entityCommandFacade.updateAll(entities);
    }    

    /**
     * {@inheritDoc}
     */
    @Override
    public VoidResult delete(final Entity entity) {
	return entityCommandFacade.delete(entity);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public VoidResult deleteAll(final Iterable<Entity> entities) {
	return entityCommandFacade.deleteAll(entities);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public VoidResult deleteBy(final Id id) {
	return entityCommandFacade.deleteBy(id);
    }    

    /**
     * {@inheritDoc}
     */
    @Override
    public VoidResult deleteAllBy(final Iterable<Id> ids) {
	return entityCommandFacade.deleteAllBy(ids);
    }
}
