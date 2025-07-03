package org.reusablecomponents.spring.core.application.query.entity.nonpaged;

import java.util.Optional;

import org.reusablecomponents.base.core.application.query.entity.simple.QueryFacade;
import org.reusablecomponents.base.core.application.query.entity.simple.QueryFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;
import org.reusablecomponents.spring.core.domain.InterfaceSpringRepository;
import org.springframework.cache.annotation.Cacheable;

@Cacheable
public class SpringQueryFacade<Entity extends AbstractEntity<Id>, Id>
		// base class
		extends QueryFacade<Entity, Id, // basic
				Id, // by id arg
				Optional<Entity>, // One result
				Iterable<Entity>, // multiple result
				Long, // count result
				Boolean> // boolean result
		// interface
		implements InterfaceSpringQueryFacade<Entity, Id> {

	protected final InterfaceSpringRepository<Entity, Id> repository;

	public SpringQueryFacade(
			final InterfaceSpringRepository<Entity, Id> repository,
			final InterfaceSecurityService securityService,
			final InterfaceExceptionAdapterService exceptionAdapterService,
			final InterfaceI18nService i18Service) {

		super(new QueryFacadeBuilder<>($ -> {

			$.existsByIdFunction = (id, directives) -> repository.existsById(id);
			$.findByIdFunction = (id, directives) -> repository.findById(id);
			$.findAllFunction = directives -> repository.findAll();
			$.countAllFunction = directives -> repository.count();
			$.existsAllFunction = directives -> repository.count() > 0;

			// services
			$.i18nService = i18Service;
			$.exceptionAdapterService = exceptionAdapterService;
			$.securityService = securityService;
		}));

		this.repository = repository;
	}
}
