package org.reusablecomponents.spring.core.application.mix.entity.paged;

import java.util.Optional;

import org.reusablecomponents.base.core.application.mix.entity.InterfaceEntityPagedFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface InterfaceSpringEntityPaginationFacade<Entity extends AbstractEntity<Id>, Id, Specification>

		extends InterfaceEntityPagedFacade<Entity, Id,
				// ------------ command
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
				Iterable<Id>, Void,
				// ------------ query
				// results
				Optional<Entity>, // one result type
				Page<Entity>, // multiple result type
				// Pagination
				Pageable, // pageable type
				Sort, // sort type
				Specification> {

}
