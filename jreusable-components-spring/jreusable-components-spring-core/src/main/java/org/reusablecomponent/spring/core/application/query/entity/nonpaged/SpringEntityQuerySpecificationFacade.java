package org.reusablecomponent.spring.core.application.query.entity.nonpaged;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.reusablecomponent.core.application.query.entity.nonpaged.EntityQuerySpecificationFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.spring.core.domain.InterfaceSpringSpecificationRepository;

public class SpringEntityQuerySpecificationFacade<Entity extends AbstractEntity<Id>, Id, Specification>
        // base class
        extends EntityQuerySpecificationFacade<Entity, Id,
        		Optional<Entity>, // One result
        		Iterable<Entity>, // multiple result
        		Long, // count result
        		Boolean,
        		Specification>
	
	implements InterfaceSpringEntityQuerySpecificationFacade<Entity, Id, Specification> {


    public SpringEntityQuerySpecificationFacade(
		    BiFunction<Specification, Object[], Iterable<Entity>> findBySpecificationFunction,
		    BiFunction<Specification, Object[], Optional<Entity>> findOneByFunction, 
		    Function<Specification, Boolean> existsBySpecificationFunction,
		    Function<Specification, Long> countBySpecificationFunction) {
	
	super(findBySpecificationFunction, findOneByFunction, existsBySpecificationFunction, countBySpecificationFunction);
	// TODO Auto-generated constructor stub
    }

    protected InterfaceSpringSpecificationRepository<Entity, Id, Specification> repository;

}
