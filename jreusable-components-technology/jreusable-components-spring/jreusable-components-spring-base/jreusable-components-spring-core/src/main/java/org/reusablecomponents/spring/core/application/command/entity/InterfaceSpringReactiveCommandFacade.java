package org.reusablecomponents.spring.core.application.command.entity;

import org.reactivestreams.Publisher;
import org.reusablecomponents.base.core.application.command.entity.InterfaceCommandFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface InterfaceSpringReactiveCommandFacade<Entity extends AbstractEntity<Id>, Id>
		//
		extends InterfaceCommandFacade<Entity, Id,
				// save
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
				Publisher<Id>, Mono<Void>> { // delete entities by id

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	Mono<Entity> save(final Entity entity, final Object... directives);

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	Flux<Entity> saveAll(final Publisher<Entity> entities, final Object... directives);

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	Mono<Entity> update(final Entity entity, final Object... directives);

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	Flux<Entity> updateAll(final Publisher<Entity> entities, final Object... directives);

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	Mono<Void> delete(final Entity entity, final Object... directives);

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	Mono<Void> deleteAll(final Publisher<Entity> entities, final Object... directives);

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	Mono<Void> deleteBy(final Id id, final Object... directives);

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	Mono<Void> deleteAllBy(final Publisher<Id> ids, final Object... directives);
}
