package org.reusablecomponents.jakarta.application.query.entity;

import java.util.Optional;
import java.util.stream.Stream;

import org.reusablecomponents.base.core.application.query.entity.simple.QueryFacade;
import org.reusablecomponents.base.core.application.query.entity.simple.QueryFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.jakarta.domain.InterfaceJakartaRepository;

public class JakartaQueryFacade<Entity extends AbstractEntity<Id>, Id>
		extends QueryFacade<Entity, Id, // basic
				Id, // by id arg
				Optional<Entity>, // One result
				Stream<Entity>, // multiple result
				Long, // count result
				Boolean> // boolean result
		implements InterfaceJakartaQueryFacade<Entity, Id> { // exists result

	protected final InterfaceJakartaRepository<Entity, Id> repository;

	protected JakartaQueryFacade(final InterfaceJakartaRepository<Entity, Id> repository) {

		super(new QueryFacadeBuilder<>($ -> {

			$.existsByIdFunction = (id, directives) -> repository.findById(id).isPresent();
			$.findByIdFunction = (id, directives) -> repository.findById(id);
			$.findAllFunction = directives -> repository.findAll();
			$.countAllFunction = directives -> repository.findAll().count();
			$.existsAllFunction = directives -> repository.findAll().count() > 0;

		}));

		this.repository = repository;
	}

}
