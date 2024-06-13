package org.reusablecomponents.core.application.base;

import org.reusablecomponents.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponents.core.application.query.entity.nonpaged.InterfaceEntityQueryFacade;
import org.reusablecomponents.core.application.query.entity.nonpaged.InterfaceEntityQuerySpecificationFacade;
import org.reusablecomponents.core.application.query.entity.paged.InterfaceEntityQueryPaginationFacade;
import org.reusablecomponents.core.application.query.entity.paged.InterfaceEntityQueryPaginationSpecificationFacade;
import org.reusablecomponents.core.domain.AbstractEntity;

/**
 * @param <Entity>
 * @param <Id>
 */
public sealed interface InterfaceEntityBaseFacade<Entity extends AbstractEntity<Id>, Id> 
	permits InterfaceEntityCommandFacade, InterfaceEntityQueryFacade, InterfaceEntityQuerySpecificationFacade,
		InterfaceEntityQueryPaginationFacade, InterfaceEntityQueryPaginationSpecificationFacade,
		EntiyBaseFacade {

}