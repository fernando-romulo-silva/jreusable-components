package org.reusablecomponents.spring.core.application.command.entity;

import org.reactivestreams.Publisher;
import org.reusablecomponents.base.core.application.command.entity.EntityCommandFacade;
import org.reusablecomponents.base.core.application.command.entity.EntityCommandFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.spring.core.domain.InterfaceSpringReactiveRepository;
import org.reusablecomponents.spring.core.infra.i18n.SpringI18nService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class SpringReactiveEntityCommandFacade<Entity extends AbstractEntity<Id>, Id> // basic
		// basic class
		extends EntityCommandFacade<Entity, Id, // basic
				Entity, Mono<Entity>, // save a entity
				Publisher<Entity>, Flux<Entity>, // save entities
				// update
				Entity, Mono<Entity>, // update a entity
				Publisher<Entity>, Flux<Entity>, // update entities
				// delete entity
				Entity, Mono<Void>, // delete a entity
				Publisher<Entity>, Mono<Void>, // delete entities
				// delete by id
				Id, Mono<Void>, // delete a entity by id
				Publisher<Id>, Mono<Void>> // delete all entities by id
		// spring reactive interface
		implements InterfaceSpringReactiveEntityCommandFacade<Entity, Id> {

	public SpringReactiveEntityCommandFacade(
			final InterfaceSpringReactiveRepository<Entity, Id> repository,
			final SpringI18nService i18Service) {

		// invoke super wih builder
		super(new EntityCommandFacadeBuilder<>($ -> {
			// save
			$.saveFunction = (entity, directives) -> repository.save(entity);
			$.saveAllFunction = repository::saveAll;

			// update
			$.updateFunction = repository::save;
			$.updateAllFunction = repository::saveAll;

			// delete
			$.deleteFunction = entity -> {
				repository.delete(entity);
				return null;
			};
			$.deleteAllFunction = entities -> {
				repository.deleteAll(entities);
				return null;
			};

			// delete by id
			$.deleteByIdFunction = id -> {
				repository.deleteById(id);
				return null;
			};
			$.deleteAllByIdFunction = ids -> {
				repository.deleteById(ids);
				return null;
			};

			// services
			$.i18nService = i18Service;
			// $.publisherSerice =
			// $.exceptionTranslatorService =
			// $.securityService =
		}));
	}
}
