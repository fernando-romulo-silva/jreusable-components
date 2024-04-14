package org.reusablecomponent.jakarta.application;

import static jakarta.transaction.Transactional.TxType.REQUIRED;
import static jakarta.transaction.Transactional.TxType.SUPPORTS;

import java.util.Optional;

import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.application.full.entity.nonpaged.EntityFacade;
import org.reusablecomponent.core.application.query.entity.nonpaged.InterfaceEntityQueryFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.jakarta.domain.InterfaceJakartaRepository;

import jakarta.transaction.Transactional;

/**
 * @param <Entity>
 * @param <Id>
 */
@Transactional(value = SUPPORTS)
public abstract class AbstractJakartaEntityFacade <Entity extends AbstractEntity<Id>, Id> 
	extends EntityFacade<Entity, Id, Entity, Optional<Entity>, Iterable<Entity>, Long, Boolean, Void> {

    protected InterfaceJakartaRepository<Entity, Id> repository;
    
    /**
     * @param entityCommandFacade
     * @param entityQueryFacade
     */
    protected AbstractJakartaEntityFacade(
		    final InterfaceEntityCommandFacade<Entity, Id, Entity, Iterable<Entity>, Void> entityCommandFacade, 
		    final InterfaceEntityQueryFacade<Entity, Id, Optional<Entity>, Iterable<Entity>, Long, Boolean> entityQueryFacade) {
	
	super(entityCommandFacade, entityQueryFacade);
    }
    
    /**
     * @param repository
     */
    protected AbstractJakartaEntityFacade(final InterfaceJakartaRepository<Entity, Id> repository) {
 	super( // functions
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
 	 	repository::findById, // findByIdFunction
 	 	() -> repository.findAll().toList(), // findAllFunction
 	 	repository::count // countAllFunction
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
