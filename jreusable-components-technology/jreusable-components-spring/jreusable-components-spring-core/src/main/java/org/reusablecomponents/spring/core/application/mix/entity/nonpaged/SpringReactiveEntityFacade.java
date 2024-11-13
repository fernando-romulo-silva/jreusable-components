package org.reusablecomponents.spring.core.application.mix.entity.nonpaged;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.spring.core.application.command.entity.InterfaceSpringReactiveEntityCommandFacade;
import org.reusablecomponents.spring.core.application.query.entity.nonpaged.InterfaceSpringReactiveEntityQueryFacade;

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
