package org.reusablecomponent.application.query.entity.nonpaged;

import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.reusablecomponent.application.base.AbstractEntiyBaseFacade;
import org.reusablecomponent.domain.AbstractEntity;

import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 * @param <Specification>
 */
public class EntityQuerySpecificationFacade <Entity extends AbstractEntity<Id>, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification> 
	extends AbstractEntiyBaseFacade<Entity, Id> 
	implements InterfaceEntityQuerySpecificationFacade<Entity, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification> {
    
    protected final Function<Specification, MultipleResult> findBySpecificationFunction;
    
    protected final Function<Specification, OneResult> findOneByFunction;
    
    protected final Function<Specification, ExistsResult> existsBySpecificationFunction;
    
    protected final Function<Specification, CountResult> countBySpecificationFunction;
    
    /**
     * @param saveFunction
     * @param deleteFunction
     * @param existsByIdFunction
     * @param findByIdFunction
     * @param findAllFunction
     */
    public EntityQuerySpecificationFacade(
		    @NotNull final Function<Specification, MultipleResult> findBySpecificationFunction,
		    @NotNull final Function<Specification, OneResult> findOneByFunction,
		    @NotNull final Function<Specification, ExistsResult> existsBySpecificationFunction,
		    @NotNull final Function<Specification, CountResult> countBySpecificationFunction) {
	
	this.findBySpecificationFunction = findBySpecificationFunction;
	this.findOneByFunction = findOneByFunction;
	this.existsBySpecificationFunction = existsBySpecificationFunction;
	this.countBySpecificationFunction = countBySpecificationFunction;
    }

    
    /**
     * {@inheritDoc}
     */
    @Override
    public MultipleResult findBy(@NotNull final Specification specification, @Nullable final Map<String, String[]> directives) {
	
	final var result = findBySpecificationFunction.apply(specification);
	
	return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public OneResult findBy(@NotNull final Specification specification) {
	
	final var result = findOneByFunction.apply(specification);
			// .orElseThrow(() -> new ElementWithIdNotFoundException(getEntityClazz(), specification));
	
    	return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ExistsResult existsBy(@NotNull final Specification specification) {
	
	final var result = existsBySpecificationFunction.apply(specification);
	
	return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountResult count(@NotNull final Specification specification) {
	
	final var result = countBySpecificationFunction.apply(specification);
	
	return result;
    }
}
