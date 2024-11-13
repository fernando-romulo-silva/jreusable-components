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
public class JakartaEntityFacade<Entity extends AbstractEntity<Id>, Id>
        implements InterfaceJakartaEntityFacade<Entity, Id> {

    protected InterfaceJakartaRepository<Entity, Id> repository;

    protected final InterfaceJakartaCommandFacade<Entity, Id> entityCommandFacade;

    protected final InterfaceJakartaEntityQueryFacade<Entity, Id> entityQueryFacade;

    protected final InterfaceJakartaEntityQuerySpecificationFacade<Entity, Id> entityQuerySpecificationFacade;

    /**
     * @param entityCommandFacade
     * @param entityQueryFacade
     */
    protected JakartaEntityFacade(
            final InterfaceJakartaCommandFacade<Entity, Id> entityCommandFacade,
            final InterfaceJakartaEntityQueryFacade<Entity, Id> entityQueryFacade,
            final InterfaceJakartaEntityQuerySpecificationFacade<Entity, Id> entityQuerySpecificationFacade) {

        this.entityCommandFacade = entityCommandFacade;
        this.entityQueryFacade = entityQueryFacade;
        this.entityQuerySpecificationFacade = entityQuerySpecificationFacade;
    }

    @Override
    public InterfaceJakartaCommandFacade<Entity, Id> getEntityCommandFacade() {
        return entityCommandFacade;
    }

    @Override
    public InterfaceJakartaEntityQueryFacade<Entity, Id> getEntityQueryFacade() {
        return entityQueryFacade;
    }

    @Override
    public InterfaceJakartaEntityQuerySpecificationFacade<Entity, Id> getEntityQuerySpecificationFacade() {
        return entityQuerySpecificationFacade;
    }
}
