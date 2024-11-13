package org.reusablecomponents.spring.core.application.mix.entity.paged;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.spring.core.application.command.entity.InterfaceSpringCommandFacade;
import org.reusablecomponents.spring.core.application.query.entity.paged.InterfaceSpringEntityQueryPaginationSpecificationFacade;
import org.reusablecomponents.spring.core.domain.InterfaceSpringPaginationRepository;
import org.reusablecomponents.spring.core.infra.i18n.SpringI18nService;

/**
 * @param <Entity>
 * @param <Id>
 * @param <Specification>
 */
public abstract class AbstractSpringEntityPaginationSpecificationFacade<Entity extends AbstractEntity<Id>, Id, Specification>
                implements InterfaceSpringEntityPaginationSpecificationFacade<Entity, Id, Specification> {

        protected InterfaceSpringPaginationRepository<Entity, Id> repository;

        protected SpringI18nService i18n;

        /**
         * @param entityCommandFacade
         * @param entityQueryPaginationFacade
         */
        protected AbstractSpringEntityPaginationSpecificationFacade(
                        final InterfaceSpringCommandFacade<Entity, Id> entityCommandFacade,
                        final InterfaceSpringEntityQueryPaginationSpecificationFacade<Entity, Id, Specification> entityQueryPaginationFacade) {
        }
}
