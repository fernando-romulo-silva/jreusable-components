package org.reusablecomponent.jakarta.application.query;

import java.util.Optional;
import java.util.stream.Stream;

import org.reusablecomponent.core.application.query.entity.nonpaged.EntityQueryFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.jakarta.domain.InterfaceJakartaRepository;

public class JakartaEntityQueryFacade<Entity extends AbstractEntity<Id>, Id> extends EntityQueryFacade<Entity, Id,
		Id, // by id arg
		// results
		Optional<Entity>, // One result
		Stream<Entity>, // multiple result
		Long, // count result
		Boolean> { // exists result

    protected final InterfaceJakartaRepository<Entity, Id> repository;
    
    protected JakartaEntityQueryFacade(final InterfaceJakartaRepository<Entity, Id> repository) {
	
	super( // functions
		(id) -> repository.findById(id).isPresent(), // existsByIdFunction
		(id, directives) -> repository.findById(id), // findByIdFunction
		(directives) -> repository.findAll(), // findAllFunction
		() -> repository.findAll().count() //  countAllFunction
	);
	
	this.repository = repository;
    }

}
