package org.reusablecomponents.spring.core.application.command.entity;

import org.reusablecomponents.base.core.application.command.entity.CommandFacade;
import org.reusablecomponents.base.core.application.command.entity.CommandFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;
import org.reusablecomponents.spring.core.domain.InterfaceSpringRepository;

public class SpringCommandFacade<Entity extends AbstractEntity<Id>, Id> // basic
		// basic class
		extends CommandFacade<Entity, Id, // basic
				// save
				Entity, Entity, // save a entity
				Iterable<Entity>, Iterable<Entity>, // save entities
				// update
				Entity, Entity, // update a entity
				Iterable<Entity>, Iterable<Entity>, // update entities
				// delete entity
				Entity, Void, // delete a entity
				Iterable<Entity>, Void, // delete entities
				// delete by id
				Id, Void, // delete a entity by id
				Iterable<Id>, Void>
		// spring interface
		implements InterfaceSpringCommandFacade<Entity, Id> {

	protected final InterfaceSpringRepository<Entity, Id> repository;

	public SpringCommandFacade(
			final InterfaceSpringRepository<Entity, Id> repository,
			final InterfaceSecurityService securityService,
			final InterfaceExceptionAdapterService exceptionAdapterService,
			final InterfaceI18nService i18Service) {

		// invoke super wih builder
		super(new CommandFacadeBuilder<>($ -> {
			// save
			$.saveFunction = (entity, directives) -> repository.save(entity);
			$.saveAllFunction = (entities, directives) -> repository.saveAll(entities);

			// update
			$.updateFunction = (entity, directives) -> repository.save(entity);
			$.updateAllFunction = (entities, directives) -> repository.saveAll(entities);

			// delete
			$.deleteFunction = (entity, directives) -> {
				repository.delete(entity);
				return null;
			};
			$.deleteAllFunction = (entities, directives) -> {
				repository.deleteAll(entities);
				return null;
			};

			// delete by id
			$.deleteByIdFunction = (id, directives) -> {
				repository.deleteById(id);
				return null;
			};
			$.deleteByIdsFunction = (ids, directives) -> {
				repository.deleteAllById(ids);
				return null;
			};

			// services
			$.i18nService = i18Service;
			$.exceptionAdapterService = exceptionAdapterService;
			$.securityService = securityService;
		}));

		this.repository = repository;
	}

}
