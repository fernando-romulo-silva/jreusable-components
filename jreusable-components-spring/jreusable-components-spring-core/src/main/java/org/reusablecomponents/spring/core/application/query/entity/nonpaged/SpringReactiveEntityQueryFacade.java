package org.reusablecomponents.spring.core.application.query.entity.nonpaged;

import org.reactivestreams.Publisher;
import org.reusablecomponents.core.application.query.entity.nonpaged.EntityQueryFacade;
import org.reusablecomponents.core.domain.AbstractEntity;
import org.reusablecomponents.spring.core.domain.InterfaceSpringReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class SpringReactiveEntityQueryFacade<Entity extends AbstractEntity<Id>, Id>
		// base class
		extends EntityQueryFacade<Entity, Id,
				Publisher<Id>, // by id arg
				Mono<Entity>, // One result
				Flux<Entity>, // multiple result
				Mono<Long>, // count result
				Mono<Boolean>> // exists result
		implements InterfaceSpringReactiveEntityQueryFacade<Entity, Id> { 
    
    protected final InterfaceSpringReactiveRepository<Entity, Id> repository;
    
    public SpringReactiveEntityQueryFacade(final InterfaceSpringReactiveRepository<Entity, Id> repository) {
	super( // functions
		repository::existsById, // Function<Id, Boolean> existsByIdFunction, 
		(id, directives) -> repository.findById(id), // BiFunction<Id, Object[], Optional<Entity>> findByIdFunction
		(directives) -> repository.findAll(), // Function<Object[], Iterable<Entity>> findAllFunction 
		repository::count, // Supplier<Long> countAllFunction
		() -> repository.count().map(m -> m.longValue() > 0) // Supplier<ExistsResult> existsAllFunction
	);
	
	this.repository = repository;
    }
}
