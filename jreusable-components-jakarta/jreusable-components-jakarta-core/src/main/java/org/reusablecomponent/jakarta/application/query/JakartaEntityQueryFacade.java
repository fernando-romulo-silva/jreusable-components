package org.reusablecomponent.jakarta.application.query;

import java.util.Optional;
import java.util.stream.Stream;

import org.reusablecomponent.core.application.query.entity.nonpaged.EntityQueryFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.jakarta.domain.InterfaceJakartaRepository;

public class JakartaEntityQueryFacade<Entity extends AbstractEntity<Id>, Id> 
	extends EntityQueryFacade<Entity, Id, // basic
		Id, // by id arg
		Optional<Entity>, // One result
		Stream<Entity>, // multiple result
		Long, // count result
		Boolean> // boolean result
	implements InterfaceJakartaEntityQueryFacade<Entity, Id> { // exists result

    protected final InterfaceJakartaRepository<Entity, Id> repository;
    
    protected JakartaEntityQueryFacade(final InterfaceJakartaRepository<Entity, Id> repository) {
	
	super( // functions
		(id) -> repository.findById(id).isPresent(), // existsByIdFunction
		(id, directives) -> repository.findById(id), // findByIdFunction
		(directives) -> repository.findAll(), // findAllFunction
		() -> repository.findAll().count(), //  countAllFunction
		() -> repository.findAll().count() > 0 // existsAllFunction
	);
	
	this.repository = repository;
    }

}
