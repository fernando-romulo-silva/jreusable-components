package org.reusablecomponent.core.application.full.entity.nonpaged;

import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.application.query.entity.nonpaged.InterfaceEntityQueryFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

/**
 * @param <Entity>
 * @param <Id>
 * @param <OneResultCommand>
 * @param <OneResultQuery>
 * @param <MultipleResult>
 * @param <CountResult>
 * @param <ExistsResult>
 * @param <VoidResult>
 */
public interface InterfaceEntityFacade <Entity extends AbstractEntity<Id>, Id, OneResultCommand, OneResultQuery, MultipleResult, CountResult, ExistsResult, VoidResult>

	extends InterfaceEntityCommandFacade<Entity, Id, OneResultCommand, MultipleResult, VoidResult>,
	        InterfaceEntityQueryFacade<Entity, Id, OneResultQuery, MultipleResult, CountResult, ExistsResult> {

}
