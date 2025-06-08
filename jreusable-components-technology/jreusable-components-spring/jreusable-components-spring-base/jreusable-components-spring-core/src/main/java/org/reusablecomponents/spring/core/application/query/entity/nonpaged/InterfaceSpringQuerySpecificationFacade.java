package org.reusablecomponents.spring.core.application.query.entity.nonpaged;

import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

import java.util.Optional;

import org.reusablecomponents.base.core.application.query.entity.specification.InterfaceQuerySpecificationFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 */
public interface InterfaceSpringQuerySpecificationFacade<Entity extends AbstractEntity<Id>, Id, Specification>
		extends
		InterfaceQuerySpecificationFacade<Entity, Id, Optional<Entity>, Iterable<Entity>, Long, Boolean, Specification> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Cacheable
	@Transactional(readOnly = true, propagation = SUPPORTS)
	Iterable<Entity> findBySpec(final Specification specification, final Object... directives);

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Cacheable
	@Transactional(readOnly = true, propagation = SUPPORTS)
	Optional<Entity> findOneBySpec(final Specification specification, final Object... directives);

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Cacheable
	@Transactional(readOnly = true, propagation = SUPPORTS)
	Boolean existsBySpec(final Specification specification, final Object... directives);

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Cacheable
	@Transactional(readOnly = true, propagation = SUPPORTS)
	Long countBySpec(final Specification specification, final Object... directives);
}
