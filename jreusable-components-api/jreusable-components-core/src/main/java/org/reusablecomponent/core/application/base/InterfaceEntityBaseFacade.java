package org.reusablecomponent.core.application.base;

import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.application.query.entity.nonpaged.InterfaceEntityQueryFacade;
import org.reusablecomponent.core.application.query.entity.nonpaged.InterfaceEntityQuerySpecificationFacade;
import org.reusablecomponent.core.application.query.entity.paged.InterfaceEntityQueryPaginationFacade;
import org.reusablecomponent.core.application.query.entity.paged.InterfaceEntityQueryPaginationSpecificationFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

/**
 * @param <Entity>
 * @param <Id>
 */
public sealed interface InterfaceEntityBaseFacade<Entity extends AbstractEntity<Id>, Id> 
	permits InterfaceEntityCommandFacade, InterfaceEntityQueryFacade, InterfaceEntityQuerySpecificationFacade,
		InterfaceEntityQueryPaginationFacade, InterfaceEntityQueryPaginationSpecificationFacade,
		AbstractEntiyBaseFacade {

}