package org.reusablecomponent.jakarta.application.mix.entity.nonpaged;

import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.jakarta.application.command.entity.InterfaceJakartaCommandFacade;
import org.reusablecomponent.jakarta.application.query.entity.InterfaceJakartaEntityQueryFacade;
import org.reusablecomponent.jakarta.domain.InterfaceJakartaRepository;

/**
 * @param <Entity>
 * @param <Id>
 */
public class JakartaEntityFacade <Entity extends AbstractEntity<Id>, Id> 
	implements InterfaceJakartaEntityFacade<Entity, Id> {

    protected InterfaceJakartaRepository<Entity, Id> repository;
    
    protected final InterfaceJakartaCommandFacade<Entity, Id> entityCommandFacade;
    
    protected final InterfaceJakartaEntityQueryFacade<Entity, Id> entityQueryFacade;
    
    /**
     * @param entityCommandFacade
     * @param entityQueryFacade
     */
    protected JakartaEntityFacade(
		    final InterfaceJakartaCommandFacade<Entity, Id> entityCommandFacade, 
		    final InterfaceJakartaEntityQueryFacade<Entity, Id> entityQueryFacade) {
	
	this.entityCommandFacade = entityCommandFacade;
	this.entityQueryFacade = entityQueryFacade;
    }

    @Override
    public InterfaceJakartaCommandFacade<Entity, Id> getEntityCommandFacade() {
	return entityCommandFacade;
    }

    @Override
    public InterfaceJakartaEntityQueryFacade<Entity, Id> getEntityQueryFacade() {
	return entityQueryFacade;
    }
}
