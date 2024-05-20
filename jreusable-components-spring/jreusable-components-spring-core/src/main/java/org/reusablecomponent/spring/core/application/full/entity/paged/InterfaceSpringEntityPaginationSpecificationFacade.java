package org.reusablecomponent.spring.core.application.full.entity.paged;

import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.spring.core.application.command.InterfaceSpringCommandFacade;
import org.reusablecomponent.spring.core.application.query.paged.InterfaceSpringEntityQueryPaginationSpecificationFacade;

public interface InterfaceSpringEntityPaginationSpecificationFacade <Entity extends AbstractEntity<Id>, Id, Specification> 
	extends InterfaceSpringCommandFacade<Entity, Id>,
		InterfaceSpringEntityQueryPaginationSpecificationFacade<Entity, Id, Specification> {

}
