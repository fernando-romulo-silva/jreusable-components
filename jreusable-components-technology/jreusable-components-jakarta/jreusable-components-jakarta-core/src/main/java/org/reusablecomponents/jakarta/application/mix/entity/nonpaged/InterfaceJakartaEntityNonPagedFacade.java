package org.reusablecomponents.jakarta.application.mix.entity.nonpaged;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.reusablecomponents.base.core.application.mix.entity.InterfaceNonPagedFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;

import jakarta.transaction.Transactional;

/**
 * @param <Entity>
 * @param <Id>
 */
@Transactional(value = SUPPORTS)
public interface InterfaceJakartaEntityNonPagedFacade<Entity extends AbstractEntity<Id>, Id, Specification>
		//
		extends InterfaceNonPagedFacade<Entity, Id,
				// ------------ command
				Entity, Entity, // save a entity
				List<Entity>, List<Entity>, // save entities
				// update
				Entity, Entity, // update a entity
				List<Entity>, List<Entity>, // update entities
				// delete entity
				Entity, Void, // delete a entity
				List<Entity>, Void, // delete entities
				// delete by id
				Id, Void, // delete a entity by id
				List<Id>, Void, // delete entities by id
				// ------------ query
				Id, // by id arg
				Optional<Entity>, // One result
				Stream<Entity>, // multiple result
				Long, // count result
				Boolean, // exists result
				Specification> { // Query Specification

}
