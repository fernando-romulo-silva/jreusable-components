package org.reusablecomponents.jakarta.application.mix.entity.nonpaged;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.jakarta.application.command.entity.InterfaceJakartaCommandFacade;
import org.reusablecomponents.jakarta.application.query.entity.InterfaceJakartaQueryFacade;
import org.reusablecomponents.jakarta.application.query.entity.InterfaceJakartaQuerySpecificationFacade;
import org.reusablecomponents.jakarta.domain.InterfaceJakartaRepository;

/**
 * @param <Entity>
 * @param <Id>
 */
public class JakartaNonPagedFacade<Entity extends AbstractEntity<Id>, Id, Specification>
        implements InterfaceJakartaNonPagedFacade<Entity, Id, Specification> {

    protected InterfaceJakartaRepository<Entity, Id> repository;

    protected final InterfaceJakartaCommandFacade<Entity, Id> entityCommandFacade;

    protected final InterfaceJakartaQueryFacade<Entity, Id> entityQueryFacade;

    protected final InterfaceJakartaQuerySpecificationFacade<Entity, Id, Specification> entityQuerySpecificationFacade;

    /**
     * @param entityCommandFacade
     * @param entityQueryFacade
     * @param entityQuerySpecificationFacade
     */
    protected JakartaNonPagedFacade(
            final InterfaceJakartaCommandFacade<Entity, Id> entityCommandFacade,
            final InterfaceJakartaQueryFacade<Entity, Id> entityQueryFacade,
            final InterfaceJakartaQuerySpecificationFacade<Entity, Id, Specification> entityQuerySpecificationFacade) {

        this.entityCommandFacade = entityCommandFacade;
        this.entityQueryFacade = entityQueryFacade;
        this.entityQuerySpecificationFacade = entityQuerySpecificationFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterfaceJakartaCommandFacade<Entity, Id> getEntityCommandFacade() {
        return entityCommandFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterfaceJakartaQueryFacade<Entity, Id> getEntityQueryFacade() {
        return entityQueryFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterfaceJakartaQuerySpecificationFacade<Entity, Id, Specification> getEntityQuerySpecificationFacade() {
        return entityQuerySpecificationFacade;
    }
}
