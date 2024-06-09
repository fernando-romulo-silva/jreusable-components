package org.reusablecomponent.spring.core.application.query.entity.nonpaged;

import java.util.Optional;

import org.reusablecomponent.core.application.query.entity.nonpaged.InterfaceEntityQuerySpecificationFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

public interface InterfaceSpringEntityQuerySpecificationFacade<Entity extends AbstractEntity<Id>, Id, Specification>
	extends InterfaceEntityQuerySpecificationFacade<Entity, Id, 
		Optional<Entity>, 
		Iterable<Entity>, 
		Long, 
		Boolean, 
		Specification> {

}
