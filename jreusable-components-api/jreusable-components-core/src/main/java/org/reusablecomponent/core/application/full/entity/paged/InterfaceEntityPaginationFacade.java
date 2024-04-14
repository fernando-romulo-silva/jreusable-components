package org.reusablecomponent.core.application.full.entity.paged;

import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.application.query.entity.paged.InterfaceEntityQueryPaginationFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

public interface InterfaceEntityPaginationFacade
	<Entity extends AbstractEntity<Id>, Id, OneResultCommand, OneResultQuery, VoidResult, MultipleResult, MultiplePagedResult, Pageable, Sort>

	extends InterfaceEntityCommandFacade<Entity, Id, OneResultCommand, MultipleResult, VoidResult>,
		InterfaceEntityQueryPaginationFacade<Entity, Id, OneResultQuery, MultiplePagedResult, Pageable, Sort> {

}
