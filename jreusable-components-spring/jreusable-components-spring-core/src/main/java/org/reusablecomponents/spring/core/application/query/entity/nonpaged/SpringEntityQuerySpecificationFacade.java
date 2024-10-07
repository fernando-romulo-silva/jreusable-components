package org.reusablecomponents.spring.core.application.query.entity.nonpaged;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.reusablecomponents.base.core.application.query.entity.nonpaged.EntityQuerySpecificationFacade;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.EntityQuerySpecificationFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.spring.core.domain.InterfaceSpringSpecificationRepository;

import jakarta.validation.constraints.NotNull;

public class SpringEntityQuerySpecificationFacade<Entity extends AbstractEntity<Id>, Id, Specification>
		// base class
		extends EntityQuerySpecificationFacade<Entity, Id, Optional<Entity>, // One result
				Iterable<Entity>, // multiple result
				Long, // count result
				Boolean, Specification>

		implements InterfaceSpringEntityQuerySpecificationFacade<Entity, Id, Specification> {

	protected InterfaceSpringSpecificationRepository<Entity, Id, Specification> repository;

	/**
	 * 
	 * @param builder
	 */
	public SpringEntityQuerySpecificationFacade(
			final InterfaceSpringSpecificationRepository<Entity, Id, Specification> repository) {

		super(new EntityQuerySpecificationFacadeBuilder<>($ -> {

		}));

		this.repository = repository;
		// BiFunction<Specification, Object[], Iterable<Entity>>
		// findBySpecificationFunction,
		// BiFunction<Specification, Object[], Optional<Entity>> findOneByFunction,
		// Function<Specification, Boolean> existsBySpecificationFunction,
		// Function<Specification, Long> countBySpecificationFunction
	}

}
