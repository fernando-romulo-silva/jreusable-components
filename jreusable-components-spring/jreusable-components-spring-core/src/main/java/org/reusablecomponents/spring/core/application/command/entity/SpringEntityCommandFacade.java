package org.reusablecomponents.spring.core.application.command.entity;

import org.reusablecomponents.base.core.application.command.entity.EntityCommandFacade;
import org.reusablecomponents.base.core.application.command.entity.EntityCommandFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.spring.core.domain.InterfaceSpringRepository;
import org.reusablecomponents.spring.core.infra.i18n.SpringI18nService;

public class SpringEntityCommandFacade<Entity extends AbstractEntity<Id>, Id> // basic
		// basic class
		extends EntityCommandFacade<Entity, Id, // basic
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
				Iterable<Id>, Void>
		// spring interface
		implements InterfaceSpringCommandFacade<Entity, Id> {

    protected final InterfaceSpringRepository<Entity, Id> repository;
    
    public SpringEntityCommandFacade(
		    final InterfaceSpringRepository<Entity, Id> repository, 
		    final SpringI18nService i18Service) {
	// invoke super wih builder
	super(new EntityCommandFacadeBuilder<>($ -> {
	    // save
	    $.saveFunction = repository::save;
	    $.saveAllFunction = repository::saveAll;
	    
	    // update
	    $.updateFunction = repository::save;
	    $.updateAllFunction = repository::saveAll;
	    
	    // delete
	    $.deleteFunction = entity -> { repository.delete(entity); return null;};
	    $.deleteAllFunction = entities -> { repository.deleteAll(entities); return null; };
	    
	    // delete by id
	    $.deleteByIdFunction = id -> { repository.deleteById(id); return null; };
	    $.deleteAllByIdFunction = ids -> { repository.deleteAllById(ids); return null; };
	    
	    // services
	    $.i18nService = i18Service;
	    // $.publisherSerice =
	    // $.exceptionTranslatorService =
	    // $.securityService =
	}));
	
	this.repository = repository;
    }
}
