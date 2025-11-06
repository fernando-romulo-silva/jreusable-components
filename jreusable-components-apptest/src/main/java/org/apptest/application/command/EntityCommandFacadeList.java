package org.apptest.application.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apptest.infra.ExceptionAdapterListService;
import org.reusablecomponents.base.core.application.command.entity.CommandFacade;
import org.reusablecomponents.base.core.application.command.entity.CommandFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;

public class EntityCommandFacadeList<Entity extends AbstractEntity<Id>, Id>
		//
		extends CommandFacade< // Basic Command Facade
				// basic
				Entity, Id, //
				// Save
				Entity, Entity, // save entity
				List<Entity>, List<Entity>, // save entities
				// update
				Entity, Entity, // update entity
				List<Entity>, List<Entity>, // update entities
				// delete
				Entity, Boolean, // delete entity
				List<Entity>, List<Boolean>, // delete entities
				//
				Id, Boolean, // delete entity by id
				List<Id>, List<Boolean>> { // delete entities by id

	private static final String ENTITY_NOT_FOUND = "Entity not found: ";

	private static final String ENTITY_WITH_INVALID_ID = "Entity with invalid id: ";

	private static final String THERE_ARE_INVALID_ENTITY = "There are invalid entity ";

	private final List<Entity> repository;

	public EntityCommandFacadeList(
			final List<Entity> repository,
			final InterfaceI18nService i18nService,
			final InterfaceSecurityService securityService) {
		super(new CommandFacadeBuilder<>($ -> {

			// save --------------------------------
			$.saveFunction = (entity, directives) -> {

				if (Objects.isNull(entity.getId())) {
					throw new IllegalStateException(ENTITY_WITH_INVALID_ID + entity);
				}

				if (repository.contains(entity)) {
					throw new IllegalArgumentException("Entity already stored: " + entity);
				}

				repository.add(entity);

				return entity;

			};

			$.saveAllFunction = (entities, directives) -> {

				if (entities.stream().anyMatch(e -> Objects.isNull(e.getId()))) {
					throw new IllegalStateException(THERE_ARE_INVALID_ENTITY + entities);
				}

				if (CollectionUtils.containsAny(repository, entities)) {
					throw new IllegalArgumentException("Some entities already stored");
				}

				repository.addAll(entities);
				return entities;
			};

			// update --------------------------------
			$.updateFunction = (entity, directives) -> {

				final var index = repository.indexOf(entity);

				if (Objects.isNull(entity.getId())) {
					throw new IllegalStateException(ENTITY_WITH_INVALID_ID + entity);
				}

				if (index < 0) {
					throw new IllegalArgumentException(ENTITY_NOT_FOUND + entity);
				}

				repository.set(index, entity);

				return entity;
			};

			$.updateAllFunction = (entities, directives) -> {

				if (entities.stream().anyMatch(e -> Objects.isNull(e.getId()))) {
					throw new IllegalStateException(THERE_ARE_INVALID_ENTITY + entities);
				}

				if (!CollectionUtils.containsAll(repository, entities)) {
					throw new IllegalArgumentException(ENTITY_NOT_FOUND + entities);
				}

				repository.addAll(entities);
				return entities;
			};

			// delete --------------------------------
			$.deleteFunction = (entity, directives) -> {

				if (Objects.isNull(entity.getId())) {
					throw new IllegalStateException(ENTITY_WITH_INVALID_ID + entity);
				}

				if (!repository.remove(entity)) {
					throw new IllegalArgumentException(ENTITY_NOT_FOUND + entity);
				}

				return Boolean.TRUE;
			};

			$.deleteAllFunction = (entities, directives) -> {

				if (entities.stream()
						.anyMatch(e -> e instanceof AbstractEntity<?> deparment
								&& Objects.isNull(deparment.getId()))) {
					throw new IllegalStateException(THERE_ARE_INVALID_ENTITY + entities);
				}

				if (!repository.removeAll(entities)) {
					throw new IllegalArgumentException("Invalid entity: " + entities);
				}

				final var result = new ArrayList<Boolean>(entities.size());
				Collections.fill(result, Boolean.TRUE);

				return result;
			};

			// delete id --------------------------------
			$.deleteByIdFunction = (id, directives) -> {

				if (id instanceof String idString && StringUtils.isBlank(idString)) {
					throw new IllegalStateException(THERE_ARE_INVALID_ENTITY + id);
				}

				final var entityFound = repository.stream()
						.filter(entity -> Objects.equals(entity.getId(), id))
						.findFirst()
						.orElseThrow(() -> new IllegalArgumentException("Id not found: " + id));

				repository.remove(entityFound);

				return Boolean.TRUE;
			};

			$.deleteByIdsFunction = (ids, directives) -> {

				ids.stream().forEach(id -> {
					if (id instanceof String idString && StringUtils.isBlank(idString)) {
						throw new IllegalStateException(THERE_ARE_INVALID_ENTITY + id);
					}
				});

				final var entities = repository.stream()
						.filter(entity -> ids.contains(entity.getId()))
						.toList();

				if (entities.size() != ids.size()) {
					throw new IllegalArgumentException("Invalid entity: " + entities);
				}

				repository.removeAll(entities);

				final var result = new ArrayList<Boolean>(entities.size());
				Collections.fill(result, Boolean.TRUE);

				return result;
			};

			// others --------------------------------
			$.securityService = securityService;
			$.exceptionAdapterService = new ExceptionAdapterListService();
			$.i18nService = i18nService;
		}));

		this.repository = repository;
	}

	public List<Entity> getData() {
		return repository;
	}
}
