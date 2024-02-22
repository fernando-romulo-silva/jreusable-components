package org.reusablecomponent.spring.application;

import java.util.Map;
import java.util.Optional;

import org.reusablecomponent.application.EntityFacade;
import org.reusablecomponent.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.application.query.entity.nonpaged.InterfaceEntityQueryFacade;
import org.reusablecomponent.domain.AbstractEntity;
import org.reusablecomponent.spring.domain.InterfaceSpringRepository;
import org.reusablecomponent.spring.infra.i18n.SpringI18nFunction;
import org.reusablecomponent.spring.infra.logging.Loggable;
import org.springframework.transaction.annotation.Transactional;

/**
 * @param <Entity>
 * @param <Id>
 */
@Loggable
@Transactional
public abstract class AbstractSpringEntityFacade<Entity extends AbstractEntity<Id>, Id> 
	extends EntityFacade<Entity, Id, Entity, Optional<Entity>, Iterable<Entity>, Long, Boolean, Void> {
    
    protected InterfaceSpringRepository<Entity, Id> interfaceSpringRepository;
    
    protected final SpringI18nFunction i18n;
    
    /**
     * @param entityCommandFacade
     * @param entityQueryFacade
     */
    protected AbstractSpringEntityFacade(
		    final InterfaceEntityCommandFacade<Entity, Id, Entity, Iterable<Entity>, Void> entityCommandFacade, 
		    final InterfaceEntityQueryFacade<Entity, Id, Optional<Entity>, Iterable<Entity>, Long, Boolean> entityQueryFacade, 
		    final SpringI18nFunction i18n) {
	
	super(entityCommandFacade, entityQueryFacade);
	this.i18n = i18n;
    }

    /**
     * @param repository
     * @param i18n
     */
    public AbstractSpringEntityFacade( //
		    final InterfaceSpringRepository<Entity, Id> repository, // 
		    final SpringI18nFunction i18n) { 
	
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
 		repository::findAll, // findAllFunction
 		repository::count // countAllFunction
 	);
 	
 	this.interfaceSpringRepository = repository;
 	this.i18n = i18n;
     }    
    
    // ---------------------------------------------------------------------------
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Entity> findBy(final Id id) {
        return super.findBy(id);
    }
    
    /**
     * {@inheritDoc}
     */    
    @Override
    @Transactional(readOnly = true)
    public Iterable<Entity> findAll(final Map<String, String[]> directives) {
        return super.findAll(directives);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Boolean existsBy(final Id id) {
	return entityQueryFacade.existsBy(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Long count() {
	return entityQueryFacade.count();
    }
    
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    @Transactional(readOnly = false)
//    public Entity save(final Entity entity) {
//	return super.save(entity);
//    }
//    
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    @Transactional(readOnly = false)
//    public Iterable<Entity> saveAll(final Iterable<Entity> entities) {
//	return super.saveAll(entities);
//    }
//    
//    // ---------------------------------------------------------------------------
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    @Transactional(readOnly = false)
//    public Entity update(final Entity entity) {
//	return super.update(entity);
//    }
//    
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    @Transactional(readOnly = false)
//    public Entity update(final Id id, final Entity entity) {
//	return super.update(id, entity);
//    }    
//    
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    @Transactional(readOnly = false)
//    public Iterable<Entity> updateAll(final Iterable<Entity> entities) {
//	return super.updateAll(entities);
//    }
//
//    // ---------------------------------------------------------------------------
//    
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    @Transactional(readOnly = false)
//    public Void delete(final Entity entity) {
//	return super.delete(entity);
//    }
//    
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    @Transactional(readOnly = false)
//    public Void deleteAll(final Iterable<Entity> entities) {
//	return super.deleteAll(entities);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    @Transactional(readOnly = false)
//    public Void deleteBy(Id id) {
//	return super.deleteBy(id);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    @Transactional(readOnly = false)
//    public Void deleteAllBy(final Iterable<Id> ids) {
//	return super.deleteAllBy(ids);
//    }
}
