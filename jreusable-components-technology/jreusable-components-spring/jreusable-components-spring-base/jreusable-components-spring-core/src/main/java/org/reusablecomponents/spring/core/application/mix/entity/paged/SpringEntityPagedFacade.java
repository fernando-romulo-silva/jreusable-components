package org.reusablecomponents.spring.core.application.mix.entity.paged;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.spring.core.application.command.entity.InterfaceSpringCommandFacade;
import org.reusablecomponents.spring.core.application.query.entity.paged.InterfaceSpringEntityQueryPaginationFacade;
import org.reusablecomponents.spring.core.application.query.entity.paged.InterfaceSpringEntityQueryPaginationSpecificationFacade;
import org.springframework.stereotype.Service;

@Service
public class SpringEntityPagedFacade<Entity extends AbstractEntity<Id>, Id, Specification>
                implements InterfaceSpringEntityPagedFacade<Entity, Id, Specification> {

        protected final InterfaceSpringCommandFacade<Entity, Id> entityCommandFacade;

        protected final InterfaceSpringEntityQueryPaginationFacade<Entity, Id> entityQueryPaginationFacade;

        protected final InterfaceSpringEntityQueryPaginationSpecificationFacade<Entity, Id, Specification> entityQueryPaginationSpecificationFacad;

        /**
         * @param entityCommandFacade
         * @param entityQueryFacade
         */
        protected SpringEntityPagedFacade(
                        final InterfaceSpringCommandFacade<Entity, Id> entityCommandFacade,
                        final InterfaceSpringEntityQueryPaginationFacade<Entity, Id> entityQueryPaginationFacade,
                        final InterfaceSpringEntityQueryPaginationSpecificationFacade<Entity, Id, Specification> entityQueryPaginationSpecificationFacad) {

                this.entityCommandFacade = entityCommandFacade;
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
