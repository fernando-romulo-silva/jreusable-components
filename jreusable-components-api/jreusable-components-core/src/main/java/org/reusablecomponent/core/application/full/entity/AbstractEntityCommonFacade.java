package org.reusablecomponent.core.application.full.entity;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

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
public abstract class AbstractEntityCommonFacade <Entity extends AbstractEntity<Id>, Id, OneResult, MultipleResult, VoidResult, ExistsResult, WrapEntity, WrapEntities, WrapIds>
	implements InterfaceEntityCommandFacade<Entity, Id, OneResult, MultipleResult, VoidResult, WrapEntity, WrapEntities, WrapIds> {
    
    protected final InterfaceEntityCommandFacade<Entity, Id, OneResult, MultipleResult, VoidResult, WrapEntity, WrapEntities, WrapIds> entityCommandFacade;
    
    /**
     * @param entityCommandFacade
     */
    protected AbstractEntityCommonFacade(
		    @NotNull final InterfaceEntityCommandFacade<Entity, Id, OneResult, MultipleResult, VoidResult, WrapEntity, WrapEntities, WrapIds> entityCommandFacade) {
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
		    final Function<WrapEntity, OneResult> saveFunction,
		    final Function<WrapEntities, MultipleResult> saveAllFunction,
		    //
		    final Function<WrapEntity, VoidResult> deleteFunction,
		    final Function<WrapEntities, VoidResult> deleteAllFunction,
		    final Function<Id, VoidResult> deleteByIdFunction,
		    final Function<Iterable<Id>, VoidResult> deleteAllByIdFunction,
		    //
		    final Function<Id, ExistsResult> existsByIdFunction,
		    final Predicate<ExistsResult> existsEntityFunction,
		    final Function<WrapEntity, Entity> unWrapEntity) {
	
	    @NotNull final Function<WrapEntity, OneResult> saveFunction, // function to save one entity 
	    @NotNull final Function<WrapEntities, MultipleResult> saveAllFunction, // save all Function
	    //
	    @NotNull final Function<WrapEntity, VoidResult> deleteFunction, // function to delete one entity
	    @NotNull final Function<WrapEntities, VoidResult> deleteAllFunction, // function to delete all entities
	    @NotNull final Function<Id, VoidResult> deleteByIdFunction, // function to delete one by id
	    @NotNull final Function<WrapIds, VoidResult> deleteAllByIdFunction, // function to delete a entity's collection by id
	    //
	    @NotNull final Function<Id, OneResult> findByIdFunction,
	    @NotNull final Function<Id, BooleanResult> existsByIdFunction, // function check it exists by id, it's can return diferrent things
	    @NotNull final Predicate<BooleanResult> existsEntityFunction, // Here we convert BooleanResult to Boolean
	    ){ 		

	
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
    public MultipleResult saveAll(final MultipleResult entities) {
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
    public MultipleResult updateAll(final MultipleResult entities) {
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
    public VoidResult deleteAll(final MultipleResult entities) {
	return entityCommandFacade.deleteAll(entities);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public VoidResult deleteBy(final Id id) {
	return entityCommandFacade.deleteBy(id);
    }    


    @Override
    public VoidResult deleteAllBy(final Iterable<Id> ids) {
	return entityCommandFacade.deleteAllBy(ids);
    }
}
