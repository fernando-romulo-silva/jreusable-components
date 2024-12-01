package org.reusablecomponents.spring.core.application.query.entity.nonpaged;

import org.reactivestreams.Publisher;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.QueryFacade;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.QueryFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;
import org.reusablecomponents.spring.core.domain.InterfaceSpringReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class SpringReactiveQueryFacade<Entity extends AbstractEntity<Id>, Id>
		// base class
		extends QueryFacade<Entity, Id, Publisher<Id>, // by id arg
				Mono<Entity>, // One result
				Flux<Entity>, // multiple result
				Mono<Long>, // count result
				Mono<Boolean>> // exists result
		implements InterfaceSpringReactiveQueryFacade<Entity, Id> {

	protected final InterfaceSpringReactiveRepository<Entity, Id> repository;

	public SpringReactiveQueryFacade(
			final InterfaceSpringReactiveRepository<Entity, Id> repository,
			final InterfaceSecurityService securityService,
			final InterfaceExceptionAdapterService exceptionAdapterService,
			final InterfaceI18nService i18Service) {

		super(new QueryFacadeBuilder<>($ -> {

			$.existsByIdFunction = (id, directives) -> repository.existsById(id);
			$.findByIdFunction = (id, directives) -> repository.findById(id);
			$.findAllFunction = directives -> repository.findAll();
			$.countAllFunction = directives -> repository.count();
			$.existsAllFunction = directives -> repository.count().map(m -> m.longValue() > 0);

			// services
			$.i18nService = i18Service;
			$.exceptionAdapterService = exceptionAdapterService;
			$.securityService = securityService;
		}));

		this.repository = repository;
	}
}
