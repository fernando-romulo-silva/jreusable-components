package org.reusablecomponents.spring.core.application.mix.entity;

import java.util.Optional;

import org.reusablecomponents.base.core.application.mix.entity.InterfaceFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface InterfaceSpringFacade<Entity extends AbstractEntity<Id>, Id, Specification>
        extends InterfaceFacade<Entity, Id, // basic
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
                Id, // by id arg
                    // results
                Optional<Entity>, // One result
                Iterable<Entity>, // multiple result
                Long, // count result
                Boolean, // boolean result
                Page<Entity>, // multiple result type
                // Pagination
                Pageable, // pageable type
                Sort, // sort type
                Specification> {

}
