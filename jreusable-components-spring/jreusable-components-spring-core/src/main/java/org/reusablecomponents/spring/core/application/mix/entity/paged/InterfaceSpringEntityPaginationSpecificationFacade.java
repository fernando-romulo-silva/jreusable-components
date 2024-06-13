package org.reusablecomponents.spring.core.application.mix.entity.paged;

import org.reusablecomponents.core.domain.AbstractEntity;
import org.reusablecomponents.spring.core.application.command.entity.InterfaceSpringCommandFacade;
import org.reusablecomponents.spring.core.application.query.entity.paged.InterfaceSpringEntityQueryPaginationSpecificationFacade;

public interface InterfaceSpringEntityPaginationSpecificationFacade <Entity extends AbstractEntity<Id>, Id, Specification>
        
	extends InterfaceSpringCommandFacade<Entity, Id>,
		InterfaceSpringEntityQueryPaginationSpecificationFacade<Entity, Id, Specification> {

}
