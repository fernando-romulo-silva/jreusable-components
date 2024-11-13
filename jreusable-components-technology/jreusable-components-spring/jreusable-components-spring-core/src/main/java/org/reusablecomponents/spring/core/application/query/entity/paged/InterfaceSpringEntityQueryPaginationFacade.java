package org.reusablecomponents.spring.core.application.query.entity.paged;

import java.util.Optional;

import org.reusablecomponents.base.core.application.query.entity.paged.InterfaceEntityQueryPaginationFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface InterfaceSpringEntityQueryPaginationFacade<Entity extends AbstractEntity<Id>, Id>
    extends InterfaceEntityQueryPaginationFacade<Entity, Id, // basic
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
  Page<Entity> findAll(final Pageable pageable, final Object... directives);

  /**
   * {@inheritDoc}
   */
  @Override
  @Cacheable
  Optional<Entity> findOne(final Sort sort, final Object... directives);
}
