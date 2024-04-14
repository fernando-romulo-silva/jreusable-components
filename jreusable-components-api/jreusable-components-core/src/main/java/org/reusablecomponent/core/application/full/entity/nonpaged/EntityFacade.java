package org.reusablecomponent.core.application.full.entity.nonpaged;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.application.full.entity.AbstractEntityCommonFacade;
import org.reusablecomponent.core.application.query.entity.nonpaged.EntityQueryFacade;
import org.reusablecomponent.core.application.query.entity.nonpaged.InterfaceEntityQueryFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

import jakarta.validation.constraints.NotNull;


/**
 * @param <Entity>
 * @param <Id>
 * @param <Specification>
 */
public class EntityFacade 
	<Entity extends AbstractEntity<Id>, Id, OneResultCommand, OneResultQuery, MultipleResult, CountResult, ExistsResult, VoidResult>
        //
	extends AbstractEntityCommonFacade<Entity, Id, OneResultCommand, MultipleResult, VoidResult, ExistsResult>
	implements InterfaceEntityFacade <Entity, Id, OneResultCommand, OneResultQuery, MultipleResult, CountResult, ExistsResult, VoidResult> {

    protected final InterfaceEntityQueryFacade<Entity, Id, OneResultQuery, MultipleResult, CountResult, ExistsResult> entityQueryFacade;
    
    /**
     * @param entityCommandFacade
     * @param entityQueryFacade
     */
    protected EntityFacade(
		    @NotNull final InterfaceEntityCommandFacade<Entity, Id, OneResultCommand, MultipleResult, VoidResult> entityCommandFacade, 
		    @NotNull final InterfaceEntityQueryFacade<Entity, Id, OneResultQuery, MultipleResult, CountResult, ExistsResult> entityQueryFacade) {
	
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
     * @param findAllFunction
     */
    protected EntityFacade( //
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
		    final Function<Id, OneResultQuery> findByIdFunction,
		    final Supplier<MultipleResult> findAllFunction,
		    final Supplier<CountResult> countAllFunction) {
	
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
	
	this.entityQueryFacade = new EntityQueryFacade<>(
			existsByIdFunction, 
			findByIdFunction, 
			findAllFunction, 
			countAllFunction
	);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MultipleResult findAll(final Map<String, String[]> directives) {
	return entityQueryFacade.findAll(directives);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public OneResultQuery findBy(final Id id) {
	return entityQueryFacade.findBy(id);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ExistsResult existsBy(final Id id) {
	return entityQueryFacade.existsBy(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountResult count() {
	return entityQueryFacade.count();
    }
}
