package org.reusablecomponent.application;

import static jakarta.transaction.Transactional.TxType.REQUIRED;
import static jakarta.transaction.Transactional.TxType.SUPPORTS;

import org.reusablecomponent.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.application.query.entity.nonpaged.InterfaceEntityQueryFacade;
import org.reusablecomponent.domain.AbstractEntity;
import org.reusablecomponent.domain.InterfaceQuarkusRepository;

import jakarta.transaction.Transactional;

@Transactional(value = SUPPORTS)
public class AbstractQuarkusEntityFacade <Entity extends AbstractEntity<Id>, Id> 
	extends EntityFacade<Entity, Id, Entity, Entity, Iterable<Entity>, Long, Boolean, Void> {


    protected InterfaceQuarkusRepository<Entity, Id> repository;
    
    /**
     * @param entityCommandFacade
     * @param entityQueryFacade
     */
    protected AbstractQuarkusEntityFacade(
		    final InterfaceEntityCommandFacade<Entity, Id, Entity, Iterable<Entity>, Void> entityCommandFacade, 
		    final InterfaceEntityQueryFacade<Entity, Id, Entity, Iterable<Entity>, Long, Boolean> entityQueryFacade) {
	
	super(entityCommandFacade, entityQueryFacade);
    }
    
    protected AbstractQuarkusEntityFacade(final InterfaceQuarkusRepository<Entity, Id> repository) {
 	super( // functions
 	 	entity -> { repository.persist(entity); return entity;}, // saveFunction
 	 	entities -> { repository.persist(entities); return entities;},// saveAllFunction
 	 	//
 	 	entity -> { repository.delete(entity); return null;}, // deleteFunction
 	 	entities -> { repository.deleteAll(); return null; }, // deleteAllFunction
 	 	id -> { repository.deleteById(id); return null; }, // deleteByIdFunciton
 	 	null,// ids -> { repository.deleteAllById(ids); return null; }, // deleteAllByIdFunction
 	 	//
 	 	null,// repository::existsById, // existsByIdFunction
 	 	null,// booleanResult -> booleanResult, // existsEntityFunction
 	 	//
 	 	repository::findById, // findByIdFunction
 	        repository::listAll, // findAllFunction
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
