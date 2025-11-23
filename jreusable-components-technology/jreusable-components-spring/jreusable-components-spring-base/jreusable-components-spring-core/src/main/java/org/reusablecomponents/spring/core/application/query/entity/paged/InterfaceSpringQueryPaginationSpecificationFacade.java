package org.reusablecomponents.spring.core.application.query.entity.paged;

import java.util.Optional;

import org.reusablecomponents.base.core.application.query.entity.paginationspecification.InterfaceQuerySpecificationPaginationFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @param <Entity>
 * @param <Id>
 * @param <Specification>
 */
public interface InterfaceSpringQueryPaginationSpecificationFacade<Entity extends AbstractEntity<Id>, Id, Specification>
                extends InterfaceQuerySpecificationPaginationFacade<Entity, Id, // basic
                                // results
                                Optional<Entity>, // one result type
                                Page<Entity>, // multiple result type
                                // Pagination
                                Specification, // query spec
                                Pageable, // pageable type
                                Sort> { // sort type

        /**
         * {@inheritDoc}
         */
        @Override
        @Cacheable
        Page<Entity> findByPaginationPaged(final Specification specification, final Pageable pageable,
                        final Object... directives);

        /**
         * {@inheritDoc}
         */
        @Override
        @Cacheable
        Optional<Entity> findOneByPaginationSorted(final Specification specification, final Sort sort,
                        final Object... directives);
}
