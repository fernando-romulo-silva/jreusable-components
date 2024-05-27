package org.reusablecomponent.spring.core.application.full.entity.nonpaged;

import java.util.Optional;

import org.reusablecomponent.core.application.full.entity.nonpaged.InterfaceEntityFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

public interface InterfaceSpringEntityFacade <Entity extends AbstractEntity<Id>, Id>
		extends InterfaceEntityFacade<Entity, Id, // basic
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
				//
				Id, // by id arg
				// results
				Optional<Entity>, // One result
				Iterable<Entity>, // multiple result
				Long, // count result
				Boolean> {
}
