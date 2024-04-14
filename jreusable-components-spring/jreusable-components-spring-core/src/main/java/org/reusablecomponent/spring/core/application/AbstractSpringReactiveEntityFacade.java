package org.reusablecomponent.spring.core.application;

import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.application.full.entity.nonpaged.EntityFacade;
import org.reusablecomponent.core.application.query.entity.nonpaged.InterfaceEntityQueryFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.spring.core.domain.InterfaceSpringReactiveRepository;
import org.reusablecomponent.spring.core.infra.i18n.SpringI18nService;
import org.reusablecomponent.spring.core.infra.logging.Loggable;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @param <Entity>
 * @param <Id>
 */
@Loggable
@Transactional(readOnly = true)
public abstract class AbstractSpringReactiveEntityFacade<Entity extends AbstractEntity<Id>, Id> 
	extends EntityFacade<Entity, Id, Mono<Entity>, Mono<Entity>, Flux<Entity>, Mono<Long>, Mono<Boolean>, Void> {
    
    protected InterfaceSpringReactiveRepository<Entity, Id> repository;
    
    protected final SpringI18nService i18n;
    
    /**
     * @param entityCommandFacade
     * @param entityQueryFacade
     */
    protected AbstractSpringReactiveEntityFacade(
		    final InterfaceEntityCommandFacade<Entity, Id,  Mono<Entity>, Flux<Entity>, Void> entityCommandFacade, 
		    final InterfaceEntityQueryFacade<Entity, Id, Mono<Entity>, Flux<Entity>, Mono<Long>, Mono<Boolean>> entityQueryFacade, 
		    final SpringI18nService i18n) {
	super(entityCommandFacade, entityQueryFacade);
	this.i18n = i18n;
    }

    /**
     * @param repository
     * @param i18n
     */
    protected AbstractSpringReactiveEntityFacade(final InterfaceSpringReactiveRepository<Entity, Id> repository, final SpringI18nService i18n) {
 	super( // functions
 		repository::save, // saveFunction
 		repository::saveAll, // saveAllFunction
 		//
 		entity -> { repository.delete(entity); return null; }, // deleteFunction
 		entities -> { repository.deleteAll(entities); return null; }, // deleteAllFunction
 		id -> { repository.deleteById(id); return null; }, // deleteByIdFunciton
 		ids -> { repository.deleteAllById(ids); return null; }, // deleteAllByIdFunction
 		//
 		repository::existsById, // existsByIdFunction
 		Mono::block, // existsEntityFunction
 		//
 		repository::findById, // findByIdFunction
 		repository::findAll, // findAllFunction
 		repository::count // countAllFunction
 	);
 	
 	this.repository = repository;
 	this.i18n = i18n;
     }    
    
    // ---------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public Mono<Entity> save(final Entity entity) {
	return super.save(entity);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public Flux<Entity> saveAll(final Iterable<Entity> entities) {
	return super.saveAll(entities);
    }
    
    // ---------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public Mono<Entity> update(final Entity entity) {
	return super.update(entity);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public Mono<Entity> update(final Id id, final Entity entity) {
	return super.update(id, entity);
    }    
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public Flux<Entity> updateAll(final Iterable<Entity> entities) {
	return super.updateAll(entities);
    }

    // ---------------------------------------------------------------------------
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public Void delete(final Entity entity) {
	return super.delete(entity);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public Void deleteAll(final Iterable<Entity> entities) {
	return super.deleteAll(entities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public Void deleteBy(final Id id) {
	return super.deleteBy(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public Void deleteAllBy(final Iterable<Id> ids) {
	return super.deleteAllBy(ids);
    }
}
