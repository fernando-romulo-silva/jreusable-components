package org.reusablecomponent.jakarta.application;

import static jakarta.transaction.Transactional.TxType.REQUIRED;
import static jakarta.transaction.Transactional.TxType.SUPPORTS;

import java.util.Optional;

import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.application.full.entity.paged.EntityPaginationSpecificationFacade;
import org.reusablecomponent.core.application.query.entity.paged.InterfaceEntityQueryPaginationSpecificationFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.jakarta.domain.InterfaceJakartaPaginationRepository;

import jakarta.data.repository.Page;
import jakarta.data.repository.Pageable;
import jakarta.data.repository.Sort;
import jakarta.transaction.Transactional;

/**
 * @param <Entity>
 * @param <Id>
 * @param <Specification>
 */
@Transactional(value = SUPPORTS)
public abstract class AbstractJakartaEntityPaginationSpecificationFacade <Entity extends AbstractEntity<Id>, Id, Specification> 
	extends EntityPaginationSpecificationFacade<Entity, Id, Entity, Optional<Entity>, Iterable<Entity>, Page<Entity>, Long, Boolean, Void, Pageable, Sort, Specification> {

    protected InterfaceJakartaPaginationRepository<Entity, Id> repository;
    
    /**
     * @param entityCommandFacade
     * @param entityQueryPaginationFacade
     */
    protected AbstractJakartaEntityPaginationSpecificationFacade(
		    final InterfaceEntityCommandFacade<Entity, Id, Entity, Iterable<Entity>, Void> entityCommandFacade, 
		    final InterfaceEntityQueryPaginationSpecificationFacade<Entity, Id, Optional<Entity>, Page<Entity>, Pageable, Sort, Specification> entityQueryPaginationFacade) {
	super(entityCommandFacade, entityQueryPaginationFacade);
    }
    
    protected AbstractJakartaEntityPaginationSpecificationFacade(final InterfaceJakartaPaginationRepository<Entity, Id> repository) {
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
		(specification, pageable) -> null, // findBySpecificationFunction
		(specification, sort) -> Optional.empty() // findOneByFunctionWithOrder
	);
	
	this.repository = repository;
//	this.i18n = i18n;
    }
    
    // ---------------------------------------------------------------------------    
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = REQUIRED)
    public Entity save(final Entity entity) {
	return super.save(entity);
    }
   
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = REQUIRED)
    public Iterable<Entity> saveAll(final Iterable<Entity> entities) {
	return super.saveAll(entities);
    }
   
    // ---------------------------------------------------------------------------
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = REQUIRED)
    public Entity update(final Entity entity) {
	return super.update(entity);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = REQUIRED)
    public Entity update(final Id id, final Entity entity) {
	return super.update(id, entity);
    }    

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = REQUIRED)
    public Iterable<Entity> updateAll(final Iterable<Entity> entities) {
	return super.updateAll(entities);
    }

    // ---------------------------------------------------------------------------
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = REQUIRED)
    public Void delete(final Entity entity) {
	return super.delete(entity);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = REQUIRED)
    public Void deleteAll(final Iterable<Entity> entities) {
	return super.deleteAll(entities);
    }

    /**
     * {@inheritDoc}
     */    
    @Override
    @Transactional(value = REQUIRED)    
    public Void deleteBy(final Id id) {
        return super.deleteBy(id);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = REQUIRED)
    public Void deleteAllBy(final Iterable<Id> ids) {
	return super.deleteAllBy(ids);
    }
}
