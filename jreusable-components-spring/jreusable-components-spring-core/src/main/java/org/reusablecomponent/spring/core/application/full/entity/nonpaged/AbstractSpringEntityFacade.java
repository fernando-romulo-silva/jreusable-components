package org.reusablecomponent.spring.core.application.full.entity.nonpaged;

import java.util.Optional;

import org.reusablecomponent.core.application.full.entity.nonpaged.EntityFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.spring.core.application.command.InterfaceSpringCommandFacade;
import org.reusablecomponent.spring.core.application.query.nonpaged.InterfaceSpringEntityQueryFacade;
import org.reusablecomponent.spring.core.domain.InterfaceSpringRepository;
import org.reusablecomponent.spring.core.infra.i18n.SpringI18nService;
import org.reusablecomponent.spring.core.infra.logging.Loggable;

/**
 * @param <Entity>
 * @param <Id>
 */
@Loggable
public abstract class AbstractSpringEntityFacade<Entity extends AbstractEntity<Id>, Id> extends EntityFacade<Entity, Id, // basic
	// ------------ command
	// save
	Entity, Entity, // save a entity
	Iterable<Entity>, Iterable<Entity>, // save entities
	// update
	Entity, Entity, // update a entity
	Iterable<Entity>, Iterable<Entity>, // update entities
	// delete entity
	Entity, Void, // delete a entity
	Iterable<Entity>, Void, // delete entities
	// delete by id
	Id, Void, // delete a entity by id
	Iterable<Id>, Void,
	// ------------ query	
	//
	Id, // by id arg
	// results
	Optional<Entity>, // One result
	Iterable<Entity>, // multiple result
	Long, // count result
	Boolean> // exist result
	implements InterfaceSpringEntityFacade<Entity, Id>{
    
    protected InterfaceSpringRepository<Entity, Id> interfaceSpringRepository;
    
    /**
     * @param entityCommandFacade
     * @param entityQueryFacade
     */
    protected AbstractSpringEntityFacade(
		    final InterfaceSpringCommandFacade<Entity, Id> entityCommandFacade, 
		    final InterfaceSpringEntityQueryFacade<Entity, Id> entityQueryFacade, 
		    final SpringI18nService i18n) {
	
	super(entityCommandFacade, entityQueryFacade);
    }

    /**
     * @param repository
     * @param i18n
     */
//    public AbstractSpringEntityFacade( //
//		    final InterfaceSpringRepository<Entity, Id> repository, // 
//		    final SpringI18nService i18n) { 
//	
// 	super( // functions
// 		repository::save, // saveFunction
// 		repository::saveAll, // saveAllFunction
// 		//
// 		entity -> { repository.delete(entity); return null;}, // deleteFunction
// 		entities -> { repository.deleteAll(entities); return null; }, // deleteAllFunction
// 		id -> { repository.deleteById(id); return null; }, // deleteByIdFunciton
// 		ids -> { repository.deleteAllById(ids); return null; }, // deleteAllByIdFunction
// 		//
// 		repository::existsById, // existsByIdFunction
// 		booleanResult -> booleanResult, // existsEntityFunction
// 		//
// 		repository::findById, // findByIdFunction
// 		repository::findAll, // findAllFunction
// 		repository::count // countAllFunction
// 	);
// 	
// 	this.interfaceSpringRepository = repository;
//     }    
}
