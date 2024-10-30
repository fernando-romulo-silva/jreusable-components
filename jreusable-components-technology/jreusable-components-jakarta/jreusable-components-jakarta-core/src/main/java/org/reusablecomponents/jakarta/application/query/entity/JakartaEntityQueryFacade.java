package org.reusablecomponents.jakarta.application.query.entity;

import java.util.Optional;
import java.util.stream.Stream;

import org.reusablecomponents.base.core.application.query.entity.nonpaged.EntityQueryFacade;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.EntityQueryFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.jakarta.domain.InterfaceJakartaRepository;

public class JakartaEntityQueryFacade<Entity extends AbstractEntity<Id>, Id>
		extends EntityQueryFacade<Entity, Id, // basic
				Id, // by id arg
				Optional<Entity>, // One result
				Stream<Entity>, // multiple result
				Long, // count result
				Boolean> // boolean result
		implements InterfaceJakartaEntityQueryFacade<Entity, Id> { // exists result

	protected final InterfaceJakartaRepository<Entity, Id> repository;

	protected JakartaEntityQueryFacade(final InterfaceJakartaRepository<Entity, Id> repository) {

		super(new EntityQueryFacadeBuilder<>($ -> {

			$.existsByIdFunction = id -> repository.findById(id).isPresent();
			$.findByIdFunction = (id, directives) -> repository.findById(id);
			$.findAllFunction = directives -> repository.findAll();
			$.countAllFunction = () -> repository.findAll().count();
			$.existsAllFunction = () -> repository.findAll().count() > 0;

		}));

		this.repository = repository;
	}

}
