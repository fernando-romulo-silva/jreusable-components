package org.reusablecomponent.spring.core.application.full.entity.nonpaged;

import org.reactivestreams.Publisher;
import org.reusablecomponent.core.application.full.entity.nonpaged.EntityFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.spring.core.application.command.InterfaceSpringReactiveEntityCommandFacade;
import org.reusablecomponent.spring.core.application.query.nonpaged.InterfaceSpringReactiveEntityQueryFacade;
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
public abstract class AbstractSpringReactiveEntityFacade<Entity extends AbstractEntity<Id>, Id>	extends EntityFacade<Entity, Id, 
	// ------------ command
	// save
	Entity, Mono<Entity>, // save a entity
	Publisher<Entity>, Flux<Entity>, // save entities
	// update
	Entity, Mono<Entity>, // update a entity
	Publisher<Entity>, Flux<Entity>, // update entities
	// delete entity
	Entity, Mono<Void>, // delete a entity
	Publisher<Entity>, Mono<Void>, // delete entities
	// delete by id
	Id, Mono<Void>, // delete a entity by id
	Publisher<Id>, Mono<Void>,
	// ------------ query	
	// args
	Publisher<Id>, // by id arg
	// results
	Mono<Entity>, // One result
	Flux<Entity>, // multiple result
	Mono<Long>, // count result
	Mono<Boolean>> // exists result
	//
	implements InterfaceSpringReactiveEntityFacade<Entity, Id> { 	
	
    
    protected InterfaceSpringReactiveRepository<Entity, Id> repository;
    
    protected final SpringI18nService i18n;
    
    /**
     * @param entityCommandFacade
     * @param entityQueryFacade
     */
    protected AbstractSpringReactiveEntityFacade(
		    final InterfaceSpringReactiveEntityCommandFacade<Entity, Id> entityCommandFacade, 
		    final InterfaceSpringReactiveEntityQueryFacade<Entity, Id> entityQueryFacade, 
		    final SpringI18nService i18n) {
	super(entityCommandFacade, entityQueryFacade);
	this.i18n = i18n;
    }

    /**
     * @param repository
     * @param i18n
     */
//    protected AbstractSpringReactiveEntityFacade(final InterfaceSpringReactiveRepository<Entity, Id> repository, final SpringI18nService i18n) {
// 	super( // functions
// 		repository::save, // saveFunction
// 		repository::saveAll, // saveAllFunction
// 		//
// 		entity -> { repository.delete(entity); return null; }, // deleteFunction
// 		entities -> { repository.deleteAll(entities); return null; }, // deleteAllFunction
// 		id -> { repository.deleteById(id); return null; }, // deleteByIdFunciton
// 		ids -> { repository.deleteAllById(ids); return null; }, // deleteAllByIdFunction
// 		//
// 		repository::existsById, // existsByIdFunction
// 		Mono::block, // existsEntityFunction
// 		//
// 		repository::findById, // findByIdFunction
// 		repository::findAll, // findAllFunction
// 		repository::count // countAllFunction
// 	);
// 	
// 	this.repository = repository;
// 	this.i18n = i18n;
//     }    
    
}
