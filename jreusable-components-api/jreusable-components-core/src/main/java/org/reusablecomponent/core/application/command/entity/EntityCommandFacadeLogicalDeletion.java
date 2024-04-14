package org.reusablecomponent.core.application.command.entity;

import static java.lang.Boolean.FALSE;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.reusablecomponent.core.domain.AbstractEntityLogicalDeletion;
import org.reusablecomponent.core.infra.exception.ElementConflictException;
import org.reusablecomponent.core.infra.exception.ElementWithIdNotFoundException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 */
public class EntityCommandFacadeLogicalDeletion <Entity extends AbstractEntityLogicalDeletion<Id>, Id, OneResult, MultipleResult, VoidResult, BooleanResult>  
	extends EntityCommandFacade<Entity, Id, OneResult, MultipleResult, VoidResult, BooleanResult> { // Operations on Entity  
    
    protected final Function<Id, OneResult> findByIdFunction;

    protected EntityCommandFacadeLogicalDeletion(
		    @NotNull final Function<Entity, OneResult> saveFunction, // function to save one entity 
		    @NotNull final Function<Iterable<Entity>, MultipleResult> saveAllFunction, // save all Function
		    //
		    @NotNull final Function<Entity, VoidResult> deleteFunction, // function to delete one entity
		    @NotNull final Function<Iterable<Entity>, VoidResult> deleteAllFunction, // function to delete all entities
		    @NotNull final Function<Id, VoidResult> deleteByIdFunction, // function to delete one by id
		    @NotNull final Function<Iterable<Id>, VoidResult> deleteAllByIdFunction, // function to delete a entity's collection by id
		    //
		    @NotNull final Function<Id, OneResult> findByIdFunction,
		    @NotNull final Function<Id, BooleanResult> existsByIdFunction, // function check it exists by id, it's can return diferrent things
		    @NotNull final Predicate<BooleanResult> existsEntityFunction){ // Here we convert BooleanResult to Boolean
		   	
	super(
		saveFunction, 
		saveAllFunction, 
		deleteFunction, 
		deleteAllFunction, 
		deleteByIdFunction, 
		deleteAllByIdFunction, 
		existsByIdFunction,
		existsEntityFunction
	);
	
	this.findByIdFunction = findByIdFunction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VoidResult delete(@NotNull @Valid final Entity entity) {
	
	if (Objects.equals(FALSE, existsByIdFunction.apply(entity.getId()))) {
	    throw new ElementWithIdNotFoundException(getEntityClazz(), getI18nService(), entity.getId());
	}	

	preDelete(entity);
	
	if (entity.isLogicalDeletion()) {
	    
	    entity.delete();
	    
	    saveFunction.apply(entity);
	    
	} else {
	    
	    try {
		
		deleteFunction.apply(entity);
		
	    } catch (final Exception ex) {
		
		throw new ElementConflictException("{exception.entityDeleteDataIntegrityViolation}", ex, entity.getId().toString());
	    }
	}

	posDelete(entity);
	
	return null;
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public VoidResult deleteBy(@NotNull @Valid final Id id) {
	
	final var findByIdFunctionResult = findByIdFunction.apply(id);
	
	//		.orElseThrow(() -> new ElementWithIdNotFoundException(getEntityClazz(), getI18nFunction(), id));
	
	if (findByIdFunctionResult instanceof AbstractEntityLogicalDeletion entity) {

	    preDeleteBy(id);

	    if (entity.isLogicalDeletion()) {

		entity.delete();

		saveFunction.apply((Entity) entity);

	    } else {
		try {

		    deleteFunction.apply((Entity) entity);

		} catch (final Exception ex) {

		    throw new ElementConflictException("{exception.entityDeleteDataIntegrityViolation}", ex, entity.getId().toString());
		}

	    }

	    posDeleteBy(id);
	}
	
	return null;
    }
}
