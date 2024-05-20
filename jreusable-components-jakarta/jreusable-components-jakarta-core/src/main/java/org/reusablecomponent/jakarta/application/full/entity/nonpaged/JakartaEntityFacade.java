package org.reusablecomponent.jakarta.application.full.entity.nonpaged;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.reusablecomponent.core.application.full.entity.nonpaged.EntityFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.jakarta.application.command.InterfaceJakartaCommandFacade;
import org.reusablecomponent.jakarta.application.query.InterfaceJakartaEntityQueryFacade;
import org.reusablecomponent.jakarta.domain.InterfaceJakartaRepository;

/**
 * @param <Entity>
 * @param <Id>
 */
public class JakartaEntityFacade <Entity extends AbstractEntity<Id>, Id> 
	// basic class
	extends EntityFacade<Entity, Id, 
	// ------------ command
	// save
	Entity, Entity, // save a entity
	List<Entity>, List<Entity>, // save entities
	// update
	Entity, Entity, // update a entity
	List<Entity>, List<Entity>, // update entities
	// delete entity
	Entity, Void, // delete a entity
	List<Entity>, Void, // delete entities
	// delete by id
	Id, Void, // delete a entity by id
	List<Id>, Void,  // delete entities by id
	// ------------ query
	Id, // by id arg
	// results
	Optional<Entity>, // One result
	Stream<Entity>, // multiple result
	Long, // count result
	Boolean> // exists result
	// jakarta interface
	implements InterfaceJakartaEntityFacade<Entity, Id> {

    protected InterfaceJakartaRepository<Entity, Id> repository;
    
    /**
     * @param entityCommandFacade
     * @param entityQueryFacade
     */
    protected JakartaEntityFacade(
		    final InterfaceJakartaCommandFacade<Entity, Id> entityCommandFacade, 
		    final InterfaceJakartaEntityQueryFacade<Entity, Id> entityQueryFacade) {
	super(entityCommandFacade, entityQueryFacade);
    }
}
