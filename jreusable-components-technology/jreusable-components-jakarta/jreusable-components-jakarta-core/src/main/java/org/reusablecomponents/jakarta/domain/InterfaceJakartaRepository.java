package org.reusablecomponents.jakarta.domain;

import org.reusablecomponents.base.core.domain.InterfaceEntity;

import jakarta.data.repository.CrudRepository;
import jakarta.enterprise.inject.Vetoed;

@Vetoed
public interface InterfaceJakartaRepository<Entity extends InterfaceEntity<Id>, Id>
        extends CrudRepository<Entity, Id> {

}
