package org.reusablecomponent.core.application.full.entity.paged;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.application.full.entity.AbstractEntityCommonFacade;
import org.reusablecomponent.core.application.query.entity.paged.EntityQueryPaginationSpecificationFacade;
import org.reusablecomponent.core.application.query.entity.paged.InterfaceEntityQueryPaginationSpecificationFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;


/**
 * @param <Entity>
 * @param <Id>
 * @param <OneResultCommand>
 * @param <OneResultQuery>
 * @param <MultipleResult>
 * @param <MultiplePagedResult>
 * @param <CountResult>
 * @param <ExistsResult>
 * @param <VoidResult>
 * @param <Pageable>
 * @param <Sort>
 * @param <Specification>
 */
public class EntityPaginationSpecificationFacade
	<Entity extends AbstractEntity<Id>, Id, OneResultCommand, OneResultQuery,  MultipleResult, MultiplePagedResult, CountResult, ExistsResult, VoidResult, Pageable, Sort, Specification> 
	
	extends AbstractEntityCommonFacade<Entity, Id, OneResultCommand, MultipleResult, VoidResult, ExistsResult>
	implements InterfaceEntityCommandFacade<Entity, Id, OneResultCommand, MultipleResult, VoidResult>,
		   InterfaceEntityQueryPaginationSpecificationFacade<Entity, Id, OneResultQuery, MultiplePagedResult, Pageable, Sort, Specification > {
    
    protected final InterfaceEntityQueryPaginationSpecificationFacade<Entity, Id, OneResultQuery, MultiplePagedResult, Pageable, Sort, Specification> entityQueryPaginationSpecificationFacade; 

    /**
     * @param entityCommandFacade
     * @param entityQueryPaginationSpecificationFacade
     */
    protected EntityPaginationSpecificationFacade(
		    @NotNull final InterfaceEntityCommandFacade<Entity, Id, OneResultCommand, MultipleResult, VoidResult> entityCommandFacade, 
		    @NotNull final InterfaceEntityQueryPaginationSpecificationFacade<Entity, Id, OneResultQuery, MultiplePagedResult, Pageable, Sort, Specification> entityQueryPaginationSpecificationFacade) {
	super(entityCommandFacade);
	this.entityQueryPaginationSpecificationFacade = entityQueryPaginationSpecificationFacade;
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
     * @param findBySpecificationFunction
     * @param findOneByFunctionWithOrder
     */
    protected EntityPaginationSpecificationFacade(
		    final Function<Entity, OneResultCommand> saveFunction,
		    final UnaryOperator<MultipleResult> saveAllFunction,
		    //
		    final Function<Entity, VoidResult> deleteFunction,
		    final Function<MultipleResult, VoidResult> deleteAllFunction,
		    final Function<Id, VoidResult> deleteByIdFunction,
		    final Function<Iterable<Id>, VoidResult> deleteAllByIdFunction,
		    //
		    final Function<Id, ExistsResult> existsByIdFunction,
		    final Predicate<ExistsResult> existsEntityFunction,
		    //
		    final BiFunction<Specification, Pageable, MultiplePagedResult> findBySpecificationFunction, 
		    final BiFunction<Specification, Sort, OneResultQuery> findOneByFunctionWithOrder) {
	
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
	
	this.entityQueryPaginationSpecificationFacade = new EntityQueryPaginationSpecificationFacade<>(findBySpecificationFunction, findOneByFunctionWithOrder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MultiplePagedResult findBy(@Nullable final Pageable pageable, @Nullable final Specification specification, @Nullable final Map<String, String[]> directives) {
	return entityQueryPaginationSpecificationFacade.findBy(pageable, specification, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OneResultQuery findBy(@Nullable final Specification specification, @Nullable final Sort sort) {
	return entityQueryPaginationSpecificationFacade.findBy(specification, sort);
    }
}
