package org.reusablecomponents.core.application.query.dto;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import org.reusablecomponents.core.application.base.EntiyBaseFacade;
import org.reusablecomponents.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;


/**
 * @param <DTO>
 * @param <Entity>
 * @param <Id>
 * @param <OneResult>
 * @param <MultipleResult>
 */
public class DtoQueryFacade <DTO, Entity extends AbstractEntity<Id>, Id, OneResult, MultipleResult> //
	//extends EntiyBaseFacade<Entity, Id>  // base
	implements InterfaceDtoQueryFacade<Id, OneResult, MultipleResult> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DtoQueryFacade.class);
    
    protected final Function<Id, OneResult> findByIdFunction;
    
    protected final Supplier<MultipleResult> findAllFunction;
    
    //-----------------------------------------
    
    public DtoQueryFacade(
		    @NotNull final Function<Id, OneResult> findByIdFunction, 
		    @NotNull final Supplier<MultipleResult> findAllFunction) {
	super();
	this.findByIdFunction = findByIdFunction;
	this.findAllFunction = findAllFunction;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MultipleResult findAll(final Map<String, String[]> directives) {
	
	final var result = findAllFunction.get();
	
	return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OneResult findById(@NotNull final Id id) {
	
	final var result = findByIdFunction.apply(id);
			//.orElseThrow(() -> new ElementWithIdNotFoundException(getEntityClazz(), id));
	
	return result;
    }

}
