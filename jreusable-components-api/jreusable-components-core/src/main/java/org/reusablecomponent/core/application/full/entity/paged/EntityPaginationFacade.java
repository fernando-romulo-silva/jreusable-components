package org.reusablecomponent.core.application.full.entity.paged;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.application.full.entity.AbstractEntityCommonFacade;
import org.reusablecomponent.core.application.query.entity.paged.EntityQueryPaginationFacade;
import org.reusablecomponent.core.application.query.entity.paged.InterfaceEntityQueryPaginationFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

/**
 * @param <Entity>
 * @param <Id>
 * @param <Page>
 * @param <Pageable>
 * @param <Specification>
 * @param <Sort>
 */
public class EntityPaginationFacade
        <Entity extends AbstractEntity<Id>, Id, OneResultCommand, OneResultQuery, ExistsResult, VoidResult, MultipleResult, MultiplePagedResult, Pageable, Sort>

	extends AbstractEntityCommonFacade<Entity, Id, OneResultCommand, MultipleResult, VoidResult, ExistsResult>
	implements InterfaceEntityPaginationFacade <Entity, Id, OneResultCommand, OneResultQuery, VoidResult, MultipleResult, MultiplePagedResult, Pageable, Sort> {
    
    protected final InterfaceEntityQueryPaginationFacade<Entity, Id, OneResultQuery, MultiplePagedResult, Pageable, Sort> entityQueryPaginationFacade;
    
    /**
     * @param entityCommandFacade
     * @param entityQueryPaginationFacade
     */
    protected EntityPaginationFacade(
		    final InterfaceEntityCommandFacade<Entity, Id, OneResultCommand, MultipleResult, VoidResult> entityCommandFacade, 
		    final InterfaceEntityQueryPaginationFacade<Entity, Id, OneResultQuery, MultiplePagedResult, Pageable, Sort> entityQueryPaginationFacade) {

	super(entityCommandFacade);
	this.entityQueryPaginationFacade = entityQueryPaginationFacade;
    }
    
    protected EntityPaginationFacade( 
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
		    final Function<Pageable, MultiplePagedResult> findAllFunction,
		    final Function<Sort, OneResultQuery> findFirstFunction) {
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
	
	this.entityQueryPaginationFacade = new EntityQueryPaginationFacade<>(findAllFunction, findFirstFunction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MultiplePagedResult findAll(final Pageable pageable, final Map<String, String[]> directives) {
	return entityQueryPaginationFacade.findAll(pageable, directives);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OneResultQuery findFirst(final Sort sort) {
	return entityQueryPaginationFacade.findFirst(sort);
    }
}
