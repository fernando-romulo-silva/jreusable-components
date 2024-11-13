package org.reusablecomponents.jakarta.application.mix.entity.nonpaged;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.jakarta.application.command.entity.InterfaceJakartaCommandFacade;
import org.reusablecomponents.jakarta.application.query.entity.InterfaceJakartaEntityQueryFacade;
import org.reusablecomponents.jakarta.application.query.entity.InterfaceJakartaEntityQuerySpecificationFacade;
import org.reusablecomponents.jakarta.domain.InterfaceJakartaRepository;

/**
 * @param <Entity>
 * @param <Id>
 */
public class JakartaEntityNonPagedFacade<Entity extends AbstractEntity<Id>, Id, Specification>
        implements InterfaceJakartaEntityNonPagedFacade<Entity, Id, Specification> {

    protected InterfaceJakartaRepository<Entity, Id> repository;

    protected final InterfaceJakartaCommandFacade<Entity, Id> entityCommandFacade;

    protected final InterfaceJakartaEntityQueryFacade<Entity, Id> entityQueryFacade;

    protected final InterfaceJakartaEntityQuerySpecificationFacade<Entity, Id, Specification> entityQuerySpecificationFacade;

    /**
     * @param entityCommandFacade
     * @param entityQueryFacade
     * @param entityQuerySpecificationFacade
     */
    protected JakartaEntityNonPagedFacade(
            final InterfaceJakartaCommandFacade<Entity, Id> entityCommandFacade,
            final InterfaceJakartaEntityQueryFacade<Entity, Id> entityQueryFacade,
            final InterfaceJakartaEntityQuerySpecificationFacade<Entity, Id, Specification> entityQuerySpecificationFacade) {

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
    public InterfaceJakartaEntityQueryFacade<Entity, Id> getEntityQueryFacade() {
        return entityQueryFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterfaceJakartaEntityQuerySpecificationFacade<Entity, Id, Specification> getEntityQuerySpecificationFacade() {
        return entityQuerySpecificationFacade;
    }
}
