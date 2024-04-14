package org.reusablecomponent.core.application.query.entity.nonpaged;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import org.reusablecomponent.core.application.base.AbstractEntiyBaseFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.core.infra.exception.ElementWithIdNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 */
public class EntityQueryFacade <Entity extends AbstractEntity<Id>, Id, OneResult, MultipleResult, CountResult, BooleanResult> 
	extends AbstractEntiyBaseFacade<Entity, Id> 
	implements InterfaceEntityQueryFacade<Entity, Id, OneResult, MultipleResult, CountResult, BooleanResult> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityQueryFacade.class);
    
    protected final Function<Id, BooleanResult> existsByIdFunction;
    
    protected final Function<Id, OneResult> findByIdFunction;
    
    protected final Supplier<MultipleResult> findAllFunction;
    
    protected final Supplier<CountResult> countAllFunction;
    
    /**
     * @param existsByIdFunction
     * @param findByIdFunction
     * @param findAllFunction
     * @param countAllFunction
     */
    public EntityQueryFacade(
		    @NotNull final Function<Id, BooleanResult> existsByIdFunction,
		    @NotNull final Function<Id, OneResult> findByIdFunction,
		    @NotNull final Supplier<MultipleResult> findAllFunction,
		    @NotNull final Supplier<CountResult> countAllFunction) {
	super();
	this.existsByIdFunction = existsByIdFunction;
	this.findByIdFunction = findByIdFunction;
	this.findAllFunction = findAllFunction;
	this.countAllFunction = countAllFunction;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MultipleResult findAll(@Nullable final Map<String, String[]> directives) {
	
	LOGGER.debug("");
	
//	final var formatDirectives = Optional.ofNullable(directives)
//        	.map(params -> params.get("format"))
//        	.stream()
//        	.flatMap(Arrays::stream)
//        	.collect(Collectors.toList());
//        	.anyMatch("full"::equalsIgnoreCase);	
	
	final var result = findAllFunction.get();
	
	LOGGER.debug("");
	
	return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public OneResult findBy(@NotNull final Id id) {
	
	final var result = findByIdFunction.apply(id);
	
	if (result instanceof Optional resultOptional && resultOptional.isEmpty()) {
	    throw new ElementWithIdNotFoundException(getEntityClazz(), id);
	}
	
	return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanResult existsBy(@NotNull final Id id) {
	
	final var result = existsByIdFunction.apply(id);
	
	return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountResult count() {
	return countAllFunction.get();
    }
}
