package org.reusablecomponents.spring.core.application.mix.entity.nonpaged;

import org.reactivestreams.Publisher;
import org.reusablecomponents.base.core.application.mix.entity.InterfaceNonPagedFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface InterfaceSpringReactiveNonPagedEntityFacade<Entity extends AbstractEntity<Id>, Id, Specification>
		extends InterfaceNonPagedFacade<Entity, Id, // basic
				// ------------ command
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
				Publisher<Id>, Mono<Void>,
				// ------------ query
				Publisher<Id>, // by id arg
				Mono<Entity>, // One result
				Flux<Entity>, // multiple result
				Mono<Long>, // count result
				Mono<Boolean>, // exists result,
				Specification> {
}
