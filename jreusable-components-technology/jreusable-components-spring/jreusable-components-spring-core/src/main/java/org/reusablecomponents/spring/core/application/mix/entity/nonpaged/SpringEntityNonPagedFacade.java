package org.reusablecomponents.spring.core.application.mix.entity.nonpaged;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.spring.core.application.command.entity.InterfaceSpringCommandFacade;
import org.reusablecomponents.spring.core.application.query.entity.nonpaged.InterfaceSpringEntityQueryFacade;
import org.reusablecomponents.spring.core.application.query.entity.nonpaged.InterfaceSpringEntityQuerySpecificationFacade;

/**
 * @param <Entity>
 * @param <Id>
 */
public class SpringEntityNonPagedFacade<Entity extends AbstractEntity<Id>, Id, Specification>
        implements InterfaceSpringEntityNonPagedFacade<Entity, Id, Specification> {

    protected final InterfaceSpringCommandFacade<Entity, Id> entityCommandFacade;

    protected final InterfaceSpringEntityQueryFacade<Entity, Id> entityQueryFacade;

    protected final InterfaceSpringEntityQuerySpecificationFacade<Entity, Id, Specification> entityQuerySpecificationFacade;

    /**
     * @param entityCommandFacade
     * @param entityQueryFacade
     */
    protected SpringEntityNonPagedFacade(
            final InterfaceSpringCommandFacade<Entity, Id> entityCommandFacade,
            final InterfaceSpringEntityQueryFacade<Entity, Id> entityQueryFacade,
            final InterfaceSpringEntityQuerySpecificationFacade<Entity, Id, Specification> entityQuerySpecificationFacade) {

        this.entityCommandFacade = entityCommandFacade;
        this.entityQueryFacade = entityQueryFacade;
        this.entityQuerySpecificationFacade = entityQuerySpecificationFacade;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public InterfaceSpringEntityQuerySpecificationFacade<Entity, Id, Specification> getEntityQuerySpecificationFacade() {
        return entityQuerySpecificationFacade;
    }
}
