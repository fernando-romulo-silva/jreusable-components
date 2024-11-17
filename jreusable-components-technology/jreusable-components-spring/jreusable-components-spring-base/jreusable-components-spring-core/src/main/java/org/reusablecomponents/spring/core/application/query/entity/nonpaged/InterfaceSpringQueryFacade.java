package org.reusablecomponents.spring.core.application.query.entity.nonpaged;

import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

import java.util.Optional;

import org.reusablecomponents.base.core.application.query.entity.nonpaged.InterfaceQueryFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

/**
 * @param <Entity>
 * @param <Id>
 */
public interface InterfaceSpringQueryFacade<Entity extends AbstractEntity<Id>, Id>
    extends InterfaceQueryFacade<Entity, Id, Id, // by id arg
        Optional<Entity>, // One result
        Iterable<Entity>, // multiple result
        Long, // count result
        Boolean> { // exists result
  /**
   * {@inheritDoc}
   */
  @Override
  @Cacheable
  @Transactional(readOnly = true, propagation = SUPPORTS)
  Optional<Entity> findById(final Id id, final Object... directives);

  /**
   * {@inheritDoc}
   */
  @Override
  @Cacheable
  @Transactional(readOnly = true, propagation = SUPPORTS)
  Iterable<Entity> findAll(final Object... directives);

  /**
   * {@inheritDoc}
   */
  @Override
  @Cacheable
  @Transactional(readOnly = true, propagation = SUPPORTS)
  Boolean existsById(final Id id);

  /**
   * {@inheritDoc}
   */
  @Override
  @Cacheable
  @Transactional(readOnly = true, propagation = SUPPORTS)
  Long countAll();
}
