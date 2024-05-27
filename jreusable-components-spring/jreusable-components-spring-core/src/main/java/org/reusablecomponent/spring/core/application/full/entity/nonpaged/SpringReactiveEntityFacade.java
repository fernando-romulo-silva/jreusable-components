package org.reusablecomponent.spring.core.application.full.entity.nonpaged;

import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.spring.core.application.command.InterfaceSpringReactiveEntityCommandFacade;
import org.reusablecomponent.spring.core.application.query.nonpaged.InterfaceSpringReactiveEntityQueryFacade;

/**
 * @param <Entity>
 * @param <Id>
 */
public class SpringReactiveEntityFacade<Entity extends AbstractEntity<Id>, Id>	
	implements InterfaceSpringReactiveEntityFacade<Entity, Id> { 	
	
    protected final InterfaceSpringReactiveEntityCommandFacade<Entity, Id> entityCommandFacade;
    
    protected final InterfaceSpringReactiveEntityQueryFacade<Entity, Id> entityQueryFacade;
    
    /**
     * @param entityCommandFacade
     * @param entityQueryFacade
     */
    protected SpringReactiveEntityFacade(
		    final InterfaceSpringReactiveEntityCommandFacade<Entity, Id> entityCommandFacade, 
		    final InterfaceSpringReactiveEntityQueryFacade<Entity, Id> entityQueryFacade) {
	
	this.entityCommandFacade = entityCommandFacade;
	this.entityQueryFacade = entityQueryFacade;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public InterfaceSpringReactiveEntityCommandFacade<Entity, Id> getEntityCommandFacade() {
	return entityCommandFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterfaceSpringReactiveEntityQueryFacade<Entity, Id> getEntityQueryFacade() {
	return entityQueryFacade;
    }
}
