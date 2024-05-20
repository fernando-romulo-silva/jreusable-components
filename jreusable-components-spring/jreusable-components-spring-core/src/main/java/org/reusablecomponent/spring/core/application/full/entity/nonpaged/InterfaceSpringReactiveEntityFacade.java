package org.reusablecomponent.spring.core.application.full.entity.nonpaged;

import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.spring.core.application.command.InterfaceSpringReactiveEntityCommandFacade;
import org.reusablecomponent.spring.core.application.query.nonpaged.InterfaceSpringReactiveEntityQueryFacade;

public interface InterfaceSpringReactiveEntityFacade <Entity extends AbstractEntity<Id>, Id>
	extends InterfaceSpringReactiveEntityCommandFacade<Entity, Id>,
		InterfaceSpringReactiveEntityQueryFacade<Entity, Id> {

}
