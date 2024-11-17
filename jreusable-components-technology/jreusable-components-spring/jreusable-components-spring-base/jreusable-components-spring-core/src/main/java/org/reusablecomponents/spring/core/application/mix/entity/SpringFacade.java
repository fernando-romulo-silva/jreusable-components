package org.reusablecomponents.spring.core.application.mix.entity;

import javax.swing.text.html.parser.Entity;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.spring.core.application.command.entity.InterfaceSpringCommandFacade;
import org.reusablecomponents.spring.core.application.query.entity.nonpaged.InterfaceSpringQueryFacade;
import org.reusablecomponents.spring.core.application.query.entity.nonpaged.InterfaceSpringQuerySpecificationFacade;
import org.reusablecomponents.spring.core.application.query.entity.paged.InterfaceSpringQueryPaginationFacade;
import org.reusablecomponents.spring.core.application.query.entity.paged.InterfaceSpringQueryPaginationSpecificationFacade;

public class SpringFacade<Entity extends AbstractEntity<Id>, Id, Specification>
        implements InterfaceSpringFacade<Entity, Id, Specification> {

    protected InterfaceSpringCommandFacade<Entity, Id> entityCommandFacade;

    protected InterfaceSpringQueryFacade<Entity, Id> entityQueryFacade;

    protected InterfaceSpringQuerySpecificationFacade<Entity, Id, Specification> entityQuerySpecificationFacade;

    protected InterfaceSpringQueryPaginationFacade<Entity, Id> entityQueryPaginationFacade;

    protected InterfaceSpringQueryPaginationSpecificationFacade<Entity, Id, Specification> entityQueryPaginationSpecificationFacad;

    public SpringFacade(
            final InterfaceSpringCommandFacade<Entity, Id> entityCommandFacade,
            final InterfaceSpringQueryFacade<Entity, Id> entityQueryFacade,
            final InterfaceSpringQuerySpecificationFacade<Entity, Id, Specification> entityQuerySpecificationFacade,
            final InterfaceSpringQueryPaginationFacade<Entity, Id> entityQueryPaginationFacade,
            final InterfaceSpringQueryPaginationSpecificationFacade<Entity, Id, Specification> entityQueryPaginationSpecificationFacad) {

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
    public InterfaceSpringQueryFacade<Entity, Id> getEntityQueryFacade() {
        return entityQueryFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterfaceSpringQuerySpecificationFacade<Entity, Id, Specification> getEntityQuerySpecificationFacade() {
        return entityQuerySpecificationFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterfaceSpringQueryPaginationFacade<Entity, Id> getEntityQueryPaginationFacade() {
        return entityQueryPaginationFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterfaceSpringQueryPaginationSpecificationFacade<Entity, Id, Specification> getEntityQueryPaginationSpecificationFacade() {
        return entityQueryPaginationSpecificationFacad;
    }
}
