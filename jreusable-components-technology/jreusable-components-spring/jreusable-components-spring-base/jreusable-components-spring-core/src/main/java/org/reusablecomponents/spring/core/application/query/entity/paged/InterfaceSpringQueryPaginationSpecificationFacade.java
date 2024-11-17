package org.reusablecomponents.spring.core.application.query.entity.paged;

import java.util.Optional;

import org.reusablecomponents.base.core.application.query.entity.paged.InterfaceQueryPaginationSpecificationFacade;
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
    extends InterfaceQueryPaginationSpecificationFacade<Entity, Id, // basic
        // results
        Optional<Entity>, // one result type
        Page<Entity>, // multiple result type
        // Pagination
        Pageable, // pageable type
        Sort, // sort type
        Specification> { // query spec

  /**
   * {@inheritDoc}
   */
  @Override
  @Cacheable
  Page<Entity> findBy(final Pageable pageable, final Specification specification, final Object... directives);

  /**
   * {@inheritDoc}
   */
  @Override
  @Cacheable
  Optional<Entity> findOneBy(final Sort sort, final Specification specification, final Object... directives);
}
