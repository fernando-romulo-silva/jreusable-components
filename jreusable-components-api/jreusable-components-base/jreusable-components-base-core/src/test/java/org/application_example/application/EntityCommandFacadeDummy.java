package org.application_example.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.application_example.infra.DummyExceptionTranslatorService;
import org.application_example.infra.DummyI18nService;
import org.application_example.infra.DummySecurityService;
import org.reusablecomponents.base.core.application.command.entity.EntityCommandFacade;
import org.reusablecomponents.base.core.application.command.entity.EntityCommandFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.messaging.logger.LoggerPublisherSerice;

public class EntityCommandFacadeDummy<Entity extends AbstractEntity<Id>, Id> 
	//
	extends EntityCommandFacade< // Basic Command Facade
		// basic
		Entity, Id, // 
		// Save
		Entity, Entity, // save entity
	        List<Entity>, List<Entity>, // save entities
	        // update
	        Entity, Entity, // update entity
	        List<Entity>, List<Entity>, // update entities
	        // delete
	        Entity, Boolean, // delete entity
	        List<Entity>, List<Boolean>, // delete entities
	        //
	        Id, Boolean, // delete entity by id
	        List<Id>, List<Boolean> > { // delete entities by id
    
    private final List<AbstractEntity<Id>> repository;

    @SuppressWarnings("unchecked")
    public EntityCommandFacadeDummy(final List<Entity> repository) {
	super(new EntityCommandFacadeBuilder<>($ -> {
	    
	    // save -------------------------------- 
	    $.saveFunction = new SaveFunction<Entity>(repository);
	    
	    $.saveAllFunction = entities -> {
		repository.addAll(entities);
		return entities;
	    };
	    
	    // update --------------------------------
	    $.updateFunction = (entity) -> {
		
		final var index = repository.indexOf(entity);
		
		if (index < 0) {
		    throw new IllegalArgumentException("Entity not found: " + entity);
		}
		
		repository.set(index, entity);
		
		return entity;
	    };
	    
	    $.updateAllFunction = entities -> {
		repository.addAll(repository);
		return entities;
	    };
	    
	    // delete --------------------------------
	    $.deleteFunction = (entity) -> {
		
		if (!repository.remove(entity)) {
		    throw new IllegalArgumentException("Entity not found: " + entity);
		}
		
		return Boolean.TRUE;
	    };
	    
	    $.deleteAllFunction = (entities) -> {
		
		if (!repository.removeAll(entities)) {
		    throw new IllegalArgumentException("Invalid entity: " + entities);
		}
		
		final var result = new ArrayList<Boolean>(entities.size());
		Collections.fill(result, Boolean.TRUE);
		
		return result;
	    };
	    
	    // delete id --------------------------------
	    $.deleteByIdFunction = (id) -> {
		
		final var entityFinded = repository.stream()
			.filter(entity -> Objects.equals(entity.getId(), id))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Id not found: " + id));
		
		repository.remove(entityFinded);
		
		return Boolean.TRUE;
	    };
	    
	    $.deleteAllByIdFunction = (ids) -> {
		
		final var entities = repository.stream()
			.filter(entity -> ids.contains(entity.getId()))
			.toList();
		
		if (entities.size() != ids.size()) {
		    
		}
		
		if (!repository.removeAll(entities)) {
		    throw new IllegalArgumentException("Invalid entity: " + entities);
		}
		
		final var result = new ArrayList<Boolean>(entities.size());
		Collections.fill(result, Boolean.TRUE);
		
		return result;
	    };
	    
	    // others --------------------------------
	    $.securityService = new DummySecurityService();
	    $.publisherService = new LoggerPublisherSerice();
	    $.exceptionTranslatorService = new DummyExceptionTranslatorService();
	    $.i18nService = new DummyI18nService();
	}));
	
	this.repository = (List<AbstractEntity<Id>>) repository;
    }
    
    static class SaveFunction<Entity> implements Function<Entity, Entity> {

	final private List<Entity> data;

	SaveFunction(final List<Entity> data) {
	    super();
	    this.data = data;
	}

	@Override
	public Entity apply(final Entity entity) {
	    if (data.contains(entity)) {
		throw new IllegalArgumentException("Entity already stored: " + entity);
	    }

	    if (!data.add(entity)) {
		throw new IllegalArgumentException("Invalid entity: " + entity);
	    }

	    return entity;
	}
    }
    
    public List<AbstractEntity<Id>> getData() {
        return repository;
    }
}
