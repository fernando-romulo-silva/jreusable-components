package org.reusablecomponent.application.query.entity.paged;

import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.reusablecomponent.application.base.AbstractEntiyBaseFacade;
import org.reusablecomponent.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 * @param <OneResult>
 * @param <MultiplePagedResult>
 * @param <Pageable>
 * @param <Sort>
 */
public class EntityQueryPaginationFacade<Entity extends AbstractEntity<Id>, Id,  OneResult,  MultiplePagedResult, Pageable, Sort>
	extends AbstractEntiyBaseFacade<Entity, Id>
	implements InterfaceEntityQueryPaginationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityQueryPaginationFacade.class);
    
    protected final Function<Pageable, MultiplePagedResult> findAllFunction;
    
    protected final  Function<Sort, OneResult> findFirstFunction;
    
    /**
     * @param findAllFunction
     * @param findFirstFunction
     */
    public EntityQueryPaginationFacade(
		    @NotNull final Function<Pageable, MultiplePagedResult> findAllFunction, 
		    @NotNull final Function<Sort, OneResult> findFirstFunction) {
	super();
	this.findAllFunction = findAllFunction;
	this.findFirstFunction = findFirstFunction;
    }

    @Override
    public MultiplePagedResult findAll(@Nullable final Pageable pageable, @Nullable final Map<String, String[]> directives) {
	
	final MultiplePagedResult result = findAllFunction.apply(pageable);
	
	return result;
    }

    @Override
    public OneResult findFirst(@Nullable final Sort sort) {
	
	final var result = findFirstFunction.apply(sort);
	
	return result;
    }


//    @Override
//    public Entity findOneFirst(@Nullable final Sort sort) {
//	
//	final var result = findFirstFunction.apply(sort)
//			.orElseThrow(() -> new ElementWithIdNotFoundException(getEntityClazz(), sort));
//	
//	return result;
//    }
}
