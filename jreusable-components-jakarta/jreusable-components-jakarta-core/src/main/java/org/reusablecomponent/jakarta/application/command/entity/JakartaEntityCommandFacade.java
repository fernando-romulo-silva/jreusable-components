package org.reusablecomponent.jakarta.application.command.entity;

import java.util.List;

import org.reusablecomponent.core.application.command.entity.EntityCommandFacade;
import org.reusablecomponent.core.application.command.entity.EntityCommandFacadeBuilder;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.jakarta.domain.InterfaceJakartaRepository;

public class JakartaEntityCommandFacade<Entity extends AbstractEntity<Id>, Id> // basic types
		// basic class
		extends EntityCommandFacade<Entity, Id, // basic types
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
				List<Id>, Void>
		// jakarta interface
		implements InterfaceJakartaCommandFacade<Entity, Id> {
    
    protected final InterfaceJakartaRepository<Entity, Id> repository;

    protected JakartaEntityCommandFacade(final InterfaceJakartaRepository<Entity, Id> repository) {
	super(new EntityCommandFacadeBuilder<>($ -> {
	    // save
	    $.saveFunction = repository::save; 
	    $.saveAllFunction = repository::saveAll;

	    // update
	    $.updateFunction = repository::update;
	    $.updateAllFunction = repository::updateAll;
	    
	    // delete
	    $.deleteFunction = entity -> {
		repository.delete(entity);
		return null;
	    };
	    $.deleteAllFunction = entities -> {
		repository.deleteAll(entities);
		return null;
	    };
	    
	    // delete by id
	    $.deleteByIdFunction = (id) -> {
		repository.deleteById(id);
		return null;
	    };
	    $.deleteAllByIdFunction = (ids) -> {
		ids.forEach((id) -> repository.deleteById(id));
		return null;
	    };
	}));
	
	this.repository = repository;
    }
}
