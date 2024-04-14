package org.reusablecomponent.spring.core.application;

import java.util.Optional;

import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.application.full.entity.paged.EntityPaginationSpecificationFacade;
import org.reusablecomponent.core.application.query.entity.paged.InterfaceEntityQueryPaginationSpecificationFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.spring.core.domain.InterfaceSpringPaginationRepository;
import org.reusablecomponent.spring.core.infra.i18n.SpringI18nService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

/**
 * @param <Entity>
 * @param <Id>
 * @param <Specification>
 */
@Transactional(readOnly = true)
public abstract class AbstractSpringEntityPaginationSpecificationFacade <Entity extends AbstractEntity<Id>, Id, Specification> 
	extends EntityPaginationSpecificationFacade<Entity, Id, Entity, Optional<Entity>, Iterable<Entity>, Page<Entity>, Long, Boolean, Void, Pageable, Sort, Specification> {

    protected InterfaceSpringPaginationRepository<Entity, Id> repository;
    
    protected SpringI18nService i18n;
    
    /**
     * @param entityCommandFacade
     * @param entityQueryPaginationFacade
     */
    protected AbstractSpringEntityPaginationSpecificationFacade(
		    final InterfaceEntityCommandFacade<Entity, Id, Entity, Iterable<Entity>, Void> entityCommandFacade, 
		    final InterfaceEntityQueryPaginationSpecificationFacade<Entity, Id, Optional<Entity>, Page<Entity>, Pageable, Sort, Specification> entityQueryPaginationFacade) {
	super(entityCommandFacade, entityQueryPaginationFacade);
    }
    
    /**
     * @param repository
     * @param i18n
     */
    protected AbstractSpringEntityPaginationSpecificationFacade(final InterfaceSpringPaginationRepository<Entity, Id> repository, final SpringI18nService i18n) {
	super(
		repository::save, // saveFunction
		repository::saveAll, // saveAllFunction
		//
 		entity -> { repository.delete(entity); return null;}, // deleteFunction
 		entities -> { repository.deleteAll(entities); return null; }, // deleteAllFunction
 		id -> { repository.deleteById(id); return null; }, // deleteByIdFunciton
 		ids -> { repository.deleteAllById(ids); return null; }, // deleteAllByIdFunction
 		//
 		repository::existsById, // existsByIdFunction
 		booleanResult -> booleanResult, // existsEntityFunction
		//
		(specification, pageable) -> Page.empty(), // findBySpecificationFunction
		(specification, sort) -> Optional.empty() // findOneByFunctionWithOrder
		
	);
	 // final BiFunction<Specification, Pageable, Page> findBySpecificationFunction, 
	 // final BiFunction<Specification, Sort, Optional<Entity>> findOneByFunctionWithOrder
	
	this.repository = repository;
	this.i18n = i18n;
    }
    
    // ---------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Entity save(final Entity entity) {
	return super.save(entity);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Iterable<Entity> saveAll(final Iterable<Entity> entities) {
	return super.saveAll(entities);
    }
    
    // ---------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Entity update(final Entity entity) {
	return super.update(entity);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Entity update(final Id id, final Entity entity) {
	return super.update(id, entity);
    }    
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Iterable<Entity> updateAll(final Iterable<Entity> entities) {
	return super.updateAll(entities);
    }

    // ---------------------------------------------------------------------------
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Void delete(final Entity entity) {
	return super.delete(entity);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Void deleteAll(final Iterable<Entity> entities) {
	return super.deleteAll(entities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Void deleteBy(Id id) {
        return super.deleteBy(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Void deleteAllBy(final Iterable<Id> ids) {
	return super.deleteAllBy(ids);
    }
}
