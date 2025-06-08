package org.reusablecomponents.jakarta.application.query.entity;

import java.util.Optional;
import java.util.stream.Stream;

import org.reusablecomponents.base.core.application.query.entity.specification.InterfaceQuerySpecificationFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import static jakarta.transaction.Transactional.TxType.SUPPORTS;

import jakarta.transaction.Transactional;

/**
 * 
 */
public interface InterfaceJakartaQuerySpecificationFacade<Entity extends AbstractEntity<Id>, Id, Specification>
		extends InterfaceQuerySpecificationFacade<Entity, Id, // base
				Optional<Entity>, // One result
				Stream<Entity>, // multiple result
				Long, // count result
				Boolean, // exists result
				Specification> { // spec

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(value = SUPPORTS)
	Stream<Entity> findBySpec(final Specification specification, final Object... directives);

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(value = SUPPORTS)
	Optional<Entity> findOneBySpec(final Specification specification, final Object... directives);

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(value = SUPPORTS)
	Boolean existsBySpec(final Specification specification, final Object... directives);

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(value = SUPPORTS)
	Long countBySpec(final Specification specification, final Object... directives);

}
