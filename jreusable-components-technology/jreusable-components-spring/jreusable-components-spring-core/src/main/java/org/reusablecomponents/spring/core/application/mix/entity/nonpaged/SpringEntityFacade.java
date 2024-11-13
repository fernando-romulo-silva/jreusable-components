package org.reusablecomponents.spring.core.application.mix.entity.nonpaged;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.spring.core.application.command.entity.InterfaceSpringCommandFacade;
import org.reusablecomponents.spring.core.application.query.entity.nonpaged.InterfaceSpringEntityQueryFacade;

/**
 * @param <Entity>
 * @param <Id>
 */
public class SpringEntityFacade<Entity extends AbstractEntity<Id>, Id>
        implements InterfaceSpringEntityFacade<Entity, Id> {

    protected InterfaceSpringCommandFacade<Entity, Id> entityCommandFacade;

    protected InterfaceSpringEntityQueryFacade<Entity, Id> entityQueryFacade;

    /**
     * @param entityCommandFacade
     * @param entityQueryFacade
     */
    protected SpringEntityFacade(
            final InterfaceSpringCommandFacade<Entity, Id> entityCommandFacade,
            final InterfaceSpringEntityQueryFacade<Entity, Id> entityQueryFacade) {

        this.entityCommandFacade = entityCommandFacade;
        this.entityQueryFacade = entityQueryFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterfaceSpringCommandFacade<Entity, Id> getEntityCommandFacade() {
        return entityCommandFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterfaceSpringEntityQueryFacade<Entity, Id> getEntityQueryFacade() {
        return entityQueryFacade;
    }
}
