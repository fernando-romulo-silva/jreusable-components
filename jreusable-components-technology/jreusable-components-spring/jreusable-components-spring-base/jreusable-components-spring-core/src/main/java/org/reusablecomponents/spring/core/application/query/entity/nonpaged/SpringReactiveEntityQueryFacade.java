package org.reusablecomponents.spring.core.application.query.entity.nonpaged;

import org.reactivestreams.Publisher;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.EntityQueryFacade;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.EntityQueryFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;
import org.reusablecomponents.spring.core.domain.InterfaceSpringReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class SpringReactiveEntityQueryFacade<Entity extends AbstractEntity<Id>, Id>
		// base class
		extends EntityQueryFacade<Entity, Id, Publisher<Id>, // by id arg
				Mono<Entity>, // One result
				Flux<Entity>, // multiple result
				Mono<Long>, // count result
				Mono<Boolean>> // exists result
		implements InterfaceSpringReactiveEntityQueryFacade<Entity, Id> {

	protected final InterfaceSpringReactiveRepository<Entity, Id> repository;

	public SpringReactiveEntityQueryFacade(
			final InterfaceSpringReactiveRepository<Entity, Id> repository,
			final InterfaceSecurityService securityService,
			final InterfaceExceptionAdapterService exceptionAdapterService,
			final InterfaceI18nService i18Service) {

		super(new EntityQueryFacadeBuilder<>($ -> {

			$.existsByIdFunction = repository::existsById;
			$.findByIdFunction = (id, directives) -> repository.findById(id);
			$.findAllFunction = directives -> repository.findAll();
			$.countAllFunction = repository::count;
			$.existsAllFunction = () -> repository.count().map(m -> m.longValue() > 0);

			// services
			$.i18nService = i18Service;
			$.exceptionAdapterService = exceptionAdapterService;
			$.securityService = securityService;
		}));

		this.repository = repository;
	}
}
