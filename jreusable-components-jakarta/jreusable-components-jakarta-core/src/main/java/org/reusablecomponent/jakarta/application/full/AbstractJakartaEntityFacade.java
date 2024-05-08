package org.reusablecomponent.jakarta.application.full;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

import java.util.List;
import java.util.Optional;

import org.reusablecomponent.core.application.full.entity.nonpaged.EntityFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.jakarta.application.command.InterfaceJakartaCommandFacade;
import org.reusablecomponent.jakarta.application.query.InterfaceJakartaEntityQueryFacade;
import org.reusablecomponent.jakarta.domain.InterfaceJakartaRepository;

/**
 * @param <Entity>
 * @param <Id>
 */
public abstract class AbstractJakartaEntityFacade <Entity extends AbstractEntity<Id>, Id> 
	extends EntityFacade<Entity, Id, Entity, Optional<Entity>, List<Entity>, Long, Boolean, Void> 

	implements InterfaceJakartaEntityFacade<Entity, Id> {

    protected InterfaceJakartaRepository<Entity, Id> repository;
    
    /**
     * @param entityCommandFacade
     * @param entityQueryFacade
     */
    protected AbstractJakartaEntityFacade(
		    final InterfaceJakartaCommandFacade<Entity, Id> entityCommandFacade, 
		    final InterfaceJakartaEntityQueryFacade<Entity, Id> entityQueryFacade) {
	
	super(entityCommandFacade, entityQueryFacade);
    }
    
    /**
     * @param repository
     */
    protected AbstractJakartaEntityFacade(final InterfaceJakartaRepository<Entity, Id> repository) {
 	super( // functions
 	 	repository::save, // saveFunction
// 	 	repository::saveAll, // saveAllFunction
		(Iterable<Entity> entities) -> repository.saveAll(stream(entities.spliterator(), false).collect(toList())), // saveAllFunction
 	 	//
 	 	entity -> { repository.delete(entity); return null;}, // deleteFunction
 	 	entities -> { repository.deleteAll(stream(entities.spliterator(), false).collect(toList())); return null; }, // deleteAllFunction
 	 	id -> { repository.deleteById(id); return null; }, // deleteByIdFunciton
// 	 	ids -> { repository.deleteAllById(ids); return null; }, // deleteAllByIdFunction
 	 	null,
 	 	//
// 	 	repository::existsById, // existsByIdFunction
 	 	null,
// 	 	booleanResult -> booleanResult, // existsEntityFunction
 	 	null,
 	 	
 	 	//
// 	 	repository::findById, // findByIdFunction
 	 	null,
// 	 	() -> repository.findAll().toList(), // findAllFunction
 	 	null,
// 	 	repository::count // countAllFunction
 	 	null
 	 );	
	
	this.repository = repository;
//	this.i18n = i18n;
    }
}
