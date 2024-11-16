package org.reusablecomponents.spring.core.application.mix.entity;

import javax.swing.text.html.parser.Entity;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.spring.core.application.command.entity.InterfaceSpringCommandFacade;
import org.reusablecomponents.spring.core.application.query.entity.nonpaged.InterfaceSpringEntityQueryFacade;
import org.reusablecomponents.spring.core.application.query.entity.nonpaged.InterfaceSpringEntityQuerySpecificationFacade;
import org.reusablecomponents.spring.core.application.query.entity.paged.InterfaceSpringEntityQueryPaginationFacade;
import org.reusablecomponents.spring.core.application.query.entity.paged.InterfaceSpringEntityQueryPaginationSpecificationFacade;

public class SpringEntityFacade<Entity extends AbstractEntity<Id>, Id, Specification>
        implements InterfaceSpringEntityFacade<Entity, Id, Specification> {

    protected InterfaceSpringCommandFacade<Entity, Id> entityCommandFacade;

    protected InterfaceSpringEntityQueryFacade<Entity, Id> entityQueryFacade;

    protected InterfaceSpringEntityQuerySpecificationFacade<Entity, Id, Specification> entityQuerySpecificationFacade;

    protected InterfaceSpringEntityQueryPaginationFacade<Entity, Id> entityQueryPaginationFacade;

    protected InterfaceSpringEntityQueryPaginationSpecificationFacade<Entity, Id, Specification> entityQueryPaginationSpecificationFacad;

    public SpringEntityFacade(
            final InterfaceSpringCommandFacade<Entity, Id> entityCommandFacade,
            final InterfaceSpringEntityQueryFacade<Entity, Id> entityQueryFacade,
            final InterfaceSpringEntityQuerySpecificationFacade<Entity, Id, Specification> entityQuerySpecificationFacade,
            final InterfaceSpringEntityQueryPaginationFacade<Entity, Id> entityQueryPaginationFacade,
            final InterfaceSpringEntityQueryPaginationSpecificationFacade<Entity, Id, Specification> entityQueryPaginationSpecificationFacad) {

        this.entityCommandFacade = entityCommandFacade;
        this.entityQueryFacade = entityQueryFacade;
        this.entityQuerySpecificationFacade = entityQuerySpecificationFacade;
        this.entityQueryPaginationFacade = entityQueryPaginationFacade;
        this.entityQueryPaginationSpecificationFacad = entityQueryPaginationSpecificationFacad;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public InterfaceSpringEntityQueryPaginationFacade<Entity, Id> getEntityQueryPaginationFacade() {
        return entityQueryPaginationFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterfaceSpringEntityQueryPaginationSpecificationFacade<Entity, Id, Specification> getEntityQueryPaginationSpecificationFacade() {
        return entityQueryPaginationSpecificationFacad;
    }
}
