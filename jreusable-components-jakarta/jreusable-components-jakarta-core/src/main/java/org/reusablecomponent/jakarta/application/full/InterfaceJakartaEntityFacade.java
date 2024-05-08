package org.reusablecomponent.jakarta.application.full;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;

import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.jakarta.application.command.InterfaceJakartaCommandFacade;
import org.reusablecomponent.jakarta.application.query.InterfaceJakartaEntityQueryFacade;

import jakarta.transaction.Transactional;

/**
 * @param <Entity>
 * @param <Id>
 */
@Transactional(value = SUPPORTS)
public interface InterfaceJakartaEntityFacade<Entity extends AbstractEntity<Id>, Id>
	//
	extends //InterfaceEntityFacade<Entity, Id, Entity, Optional<Entity>, Iterable<Entity>, Long, Boolean, Void>, 
	 	InterfaceJakartaCommandFacade<Entity, Id>,
		InterfaceJakartaEntityQueryFacade <Entity, Id>{
    
}
