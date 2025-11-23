package org.reusablecomponents.spring.core.application.query.entity.paged;

import java.util.Optional;

import org.reusablecomponents.base.core.application.query.entity.pagination.InterfaceQueryPaginationFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @param <Entity>
 * @param <Id>
 */
public interface InterfaceSpringQueryPaginationFacade<Entity extends AbstractEntity<Id>, Id>
    extends InterfaceQueryPaginationFacade<Entity, Id, // basic
        // results
        Optional<Entity>, // one result type
        Page<Entity>, // multiple result type
        // Pagination
        Pageable, // pageable type
        Sort> { // sort type

  /**
   * {@inheritDoc}
   */
  @Override
  @Cacheable
  Page<Entity> findAllPaged(final Pageable pageable, final Object... directives);

  /**
   * {@inheritDoc}
   */
  @Override
  @Cacheable
  Optional<Entity> findOneSorted(final Sort sort, final Object... directives);
}
