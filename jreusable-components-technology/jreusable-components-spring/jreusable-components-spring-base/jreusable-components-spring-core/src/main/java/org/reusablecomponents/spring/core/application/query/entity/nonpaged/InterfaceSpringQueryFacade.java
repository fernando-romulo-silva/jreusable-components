package org.reusablecomponents.spring.core.application.query.entity.nonpaged;

import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

import java.util.Optional;

import org.reusablecomponents.base.core.application.query.entity.simple.InterfaceQueryFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
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
  @Transactional(readOnly = true, propagation = SUPPORTS)
  Optional<Entity> findById(final Id id, final Object... directives);

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional(readOnly = true, propagation = SUPPORTS)
  Iterable<Entity> findAll(final Object... directives);

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional(readOnly = true, propagation = SUPPORTS)
  Boolean existsById(final Id id, final Object... directives);

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional(readOnly = true, propagation = SUPPORTS)
  Long countAll(final Object... directives);
}
