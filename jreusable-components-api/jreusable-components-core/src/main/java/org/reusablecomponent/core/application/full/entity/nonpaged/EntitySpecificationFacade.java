package org.reusablecomponent.core.application.full.entity.nonpaged;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.application.full.entity.AbstractEntityCommonFacade;
import org.reusablecomponent.core.application.query.entity.nonpaged.EntityQuerySpecificationFacade;
import org.reusablecomponent.core.application.query.entity.nonpaged.InterfaceEntityQuerySpecificationFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 * @param <Specification>
 */
public class EntitySpecificationFacade 
        <Entity extends AbstractEntity<Id>, Id, OneResultCommand, OneResultQuery, MultipleResult, CountResult, ExistsResult, VoidResult, Specification>

	extends AbstractEntityCommonFacade<Entity, Id, OneResultCommand, MultipleResult, VoidResult, ExistsResult>
	implements InterfaceEntitySpecificationFacade <Entity, Id, OneResultCommand, OneResultQuery, MultipleResult, CountResult, ExistsResult, VoidResult, Specification>  {
    
    protected final InterfaceEntityQuerySpecificationFacade<Entity, Id, OneResultQuery, MultipleResult, CountResult, ExistsResult, Specification> entityQueryFacade;

    /**
     * @param entityCommandFacade
     * @param entityQueryFacade
     */
    protected EntitySpecificationFacade(
		    @NotNull final InterfaceEntityCommandFacade<Entity, Id, OneResultCommand, MultipleResult, VoidResult> entityCommandFacade, 
		    @NotNull final InterfaceEntityQuerySpecificationFacade<Entity, Id, OneResultQuery, MultipleResult, CountResult, ExistsResult, Specification> entityQueryFacade) {
	super(entityCommandFacade);
	this.entityQueryFacade = entityQueryFacade;
    }
    
    /**
     * @param saveFunction
     * @param saveAllFunction
     * @param deleteFunction
     * @param deleteAllFunction
     * @param deleteByIdFunction
     * @param deleteAllByIdFunction
     * @param existsByIdFunction
     * @param findByIdFunction
     * @param findBySpecificationFunction
     * @param findOneByFunction
     * @param existsBySpecificationFunction
     * @param countBySpecificationFunction
     */
    protected EntitySpecificationFacade(
		    final Function<Entity, OneResultCommand> saveFunction,
		    final Function<Iterable<Entity>, MultipleResult> saveAllFunction,
		    //
		    final Function<Entity, VoidResult> deleteFunction,
		    final Function<Iterable<Entity>, VoidResult> deleteAllFunction,
		    final Function<Id, VoidResult> deleteByIdFunction,
		    final Function<Iterable<Id>, VoidResult> deleteAllByIdFunction,
		    //
		    final Function<Id, ExistsResult> existsByIdFunction,
		    final Predicate<ExistsResult> existsEntityFunction,
		    //
		    final Function<Specification, MultipleResult> findBySpecificationFunction,
		    final Function<Specification, OneResultQuery> findOneByFunction,
		    final Function<Specification, ExistsResult> existsBySpecificationFunction,
		    final Function<Specification, CountResult> countBySpecificationFunction) {
	
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
	
	this.entityQueryFacade = new EntityQuerySpecificationFacade<>(
		findBySpecificationFunction, 
		findOneByFunction, 
		existsBySpecificationFunction, 
		countBySpecificationFunction
	);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ExistsResult existsBy(final Specification specification) {
	return entityQueryFacade.existsBy(specification);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public OneResultQuery findBy(final Specification specification) {
	return entityQueryFacade.findBy(specification);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MultipleResult findBy(final Specification specification, final Map<String, String[]> directives) {
	return entityQueryFacade.findBy(specification, directives);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CountResult count(final Specification specification) {
	return entityQueryFacade.count(specification);
    }
}
