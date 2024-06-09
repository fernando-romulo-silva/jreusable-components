package org.reusablecomponent.spring.core.application.mix.entity.paged;

import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.spring.core.application.command.entity.InterfaceSpringCommandFacade;
import org.reusablecomponent.spring.core.application.query.entity.paged.InterfaceSpringEntityQueryPaginationSpecificationFacade;

public interface InterfaceSpringEntityPaginationSpecificationFacade <Entity extends AbstractEntity<Id>, Id, Specification>
        
	extends InterfaceSpringCommandFacade<Entity, Id>,
		InterfaceSpringEntityQueryPaginationSpecificationFacade<Entity, Id, Specification> {

}
