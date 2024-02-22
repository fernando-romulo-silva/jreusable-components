package org.reusablecomponent.application.query.entity.paged;

import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Nullable;

import org.reusablecomponent.application.base.AbstractEntiyBaseFacade;
import org.reusablecomponent.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 * @param <MultiplePagedResult>
 * @param <Pageable>
 * @param <Specification>
 * @param <Sort>
 */
public class EntityQueryPaginationSpecificationFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification>
	extends AbstractEntiyBaseFacade<Entity, Id>
	implements InterfaceEntityQueryPaginationSpecificationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityQueryPaginationSpecificationFacade.class);
    
    protected final BiFunction<Specification, Pageable, MultiplePagedResult> findBySpecificationFunction;

    protected final BiFunction<Specification, Sort, OneResult> findOneByFunctionWithOrder;
    
    /**
     * @param findBySpecificationFunction
     * @param findOneByFunctionWithOrder
     */
    public EntityQueryPaginationSpecificationFacade(
		    @NotNull final BiFunction<Specification, Pageable, MultiplePagedResult> findBySpecificationFunction, 
		    @NotNull final BiFunction<Specification, Sort, OneResult> findOneByFunctionWithOrder) {
	super();
	this.findBySpecificationFunction = findBySpecificationFunction;
	this.findOneByFunctionWithOrder = findOneByFunctionWithOrder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MultiplePagedResult findBy(@Nullable final Pageable pageable, @Nullable final Specification specification, @Nullable final Map<String, String[]> directives) {
	
	LOGGER.debug("");
	
	final var result = findBySpecificationFunction.apply(specification, pageable);
	
    	return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public OneResult findBy(@Nullable final Specification specification, @Nullable final Sort sort) {

	final var result = findOneByFunctionWithOrder.apply(specification, sort);
			//.orElseThrow(() -> new ElementWithIdNotFoundException(getEntityClazz(), specification));
	
	return result;
    }
}
