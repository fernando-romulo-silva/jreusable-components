package org.reusablecomponents.spring.core.application.mix.entity.nonpaged;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.spring.core.application.command.entity.InterfaceSpringCommandFacade;
import org.reusablecomponents.spring.core.application.query.entity.nonpaged.InterfaceSpringQueryFacade;
import org.reusablecomponents.spring.core.application.query.entity.nonpaged.InterfaceSpringQuerySpecificationFacade;

/**
 * @param <Entity>
 * @param <Id>
 */
public class SpringEntityNonPagedFacade<Entity extends AbstractEntity<Id>, Id, Specification>
        implements InterfaceSpringEntityNonPagedFacade<Entity, Id, Specification> {

    protected final InterfaceSpringCommandFacade<Entity, Id> entityCommandFacade;

    protected final InterfaceSpringQueryFacade<Entity, Id> entityQueryFacade;

    protected final InterfaceSpringQuerySpecificationFacade<Entity, Id, Specification> entityQuerySpecificationFacade;

    /**
     * @param entityCommandFacade
     * @param entityQueryFacade
     */
    protected SpringEntityNonPagedFacade(
            final InterfaceSpringCommandFacade<Entity, Id> entityCommandFacade,
            final InterfaceSpringQueryFacade<Entity, Id> entityQueryFacade,
            final InterfaceSpringQuerySpecificationFacade<Entity, Id, Specification> entityQuerySpecificationFacade) {

        this.entityCommandFacade = entityCommandFacade;
        this.entityQueryFacade = entityQueryFacade;
        this.entityQuerySpecificationFacade = entityQuerySpecificationFacade;
    }

    // /**
    // * @param entityCommandFacade
    // * @param entityQueryFacade
    // */
    // @SuppressWarnings("unchecked")
    // protected SpringEntityNonPagedFacade() {

    // https://stackoverflow.com/questions/30374267/get-spring-bean-via-context-using-generic

    // this.entityCommandFacade =
    // ContextAwareUtil.getBeanFrom(SpringEntityCommandFacade.class);

    // this.entityQueryFacade =
    // ContextAwareUtil.getBeanFrom(InterfaceSpringEntityQueryFacade.class);

    // this.entityQuerySpecificationFacade = ContextAwareUtil
    // .getBeanFrom(InterfaceSpringEntityQuerySpecificationFacade.class);
    // }

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
}
