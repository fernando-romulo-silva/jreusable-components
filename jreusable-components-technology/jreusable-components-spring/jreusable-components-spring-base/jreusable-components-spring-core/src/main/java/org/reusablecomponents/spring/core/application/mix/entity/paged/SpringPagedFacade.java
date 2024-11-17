package org.reusablecomponents.spring.core.application.mix.entity.paged;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.spring.core.application.command.entity.InterfaceSpringCommandFacade;
import org.reusablecomponents.spring.core.application.query.entity.paged.InterfaceSpringQueryPaginationFacade;
import org.reusablecomponents.spring.core.application.query.entity.paged.InterfaceSpringQueryPaginationSpecificationFacade;
import org.springframework.stereotype.Service;

@Service
public class SpringPagedFacade<Entity extends AbstractEntity<Id>, Id, Specification>
                implements InterfaceSpringPagedFacade<Entity, Id, Specification> {

        protected final InterfaceSpringCommandFacade<Entity, Id> entityCommandFacade;

        protected final InterfaceSpringQueryPaginationFacade<Entity, Id> entityQueryPaginationFacade;

        protected final InterfaceSpringQueryPaginationSpecificationFacade<Entity, Id, Specification> entityQueryPaginationSpecificationFacad;

        /**
         * @param entityCommandFacade
         * @param entityQueryFacade
         */
        protected SpringPagedFacade(
                        final InterfaceSpringCommandFacade<Entity, Id> entityCommandFacade,
                        final InterfaceSpringQueryPaginationFacade<Entity, Id> entityQueryPaginationFacade,
                        final InterfaceSpringQueryPaginationSpecificationFacade<Entity, Id, Specification> entityQueryPaginationSpecificationFacad) {

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
