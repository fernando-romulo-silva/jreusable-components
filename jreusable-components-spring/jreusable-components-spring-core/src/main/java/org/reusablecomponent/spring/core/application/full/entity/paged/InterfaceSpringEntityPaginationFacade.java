package org.reusablecomponent.spring.core.application.full.entity.paged;

import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.spring.core.application.command.InterfaceSpringCommandFacade;
import org.reusablecomponent.spring.core.application.query.paged.InterfaceSpringEntityQueryPaginationFacade;

public interface InterfaceSpringEntityPaginationFacade<Entity extends AbstractEntity<Id>, Id> 
	extends InterfaceSpringCommandFacade<Entity, Id>,
		InterfaceSpringEntityQueryPaginationFacade<Entity, Id> {
    
}
