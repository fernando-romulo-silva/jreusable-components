package org.reusablecomponent.core.application.full.entity.nonpaged;

import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.application.query.entity.nonpaged.InterfaceEntityQuerySpecificationFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

public interface InterfaceEntitySpecificationFacade 
      <Entity extends AbstractEntity<Id>, Id, OneResultCommand, OneResultQuery, MultipleResult, CountResult, ExistsResult, VoidResult, Specification> 

      extends InterfaceEntityCommandFacade<Entity, Id, OneResultCommand, MultipleResult, VoidResult>,
              InterfaceEntityQuerySpecificationFacade<Entity, Id, OneResultQuery, MultipleResult, CountResult, ExistsResult, Specification> {

}
