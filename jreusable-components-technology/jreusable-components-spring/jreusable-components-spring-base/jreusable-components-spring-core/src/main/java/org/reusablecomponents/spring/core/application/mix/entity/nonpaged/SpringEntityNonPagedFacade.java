package org.reusablecomponents.spring.core.application.mix.entity.nonpaged;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.spring.core.application.command.entity.InterfaceSpringCommandFacade;
import org.reusablecomponents.spring.core.application.command.entity.SpringEntityCommandFacade;
import org.reusablecomponents.spring.core.application.query.entity.nonpaged.InterfaceSpringEntityQueryFacade;
import org.reusablecomponents.spring.core.application.query.entity.nonpaged.InterfaceSpringEntityQuerySpecificationFacade;
import org.reusablecomponents.spring.core.infra.util.ContextAwareUtil;
import org.springframework.stereotype.Service;

/**
 * @param <Entity>
 * @param <Id>
 */
@Service
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
     * @param entityCommandFacade
     * @param entityQueryFacade
     */
    @SuppressWarnings("unchecked")
    protected SpringEntityNonPagedFacade() {

        // https: //
        // stackoverflow.com/questions/30374267/get-spring-bean-via-context-using-generic

        this.entityCommandFacade = ContextAwareUtil.getBeanFrom(SpringEntityCommandFacade.class);

        this.entityQueryFacade = ContextAwareUtil.getBeanFrom(InterfaceSpringEntityQueryFacade.class);

        this.entityQuerySpecificationFacade = ContextAwareUtil
                .getBeanFrom(InterfaceSpringEntityQuerySpecificationFacade.class);
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
