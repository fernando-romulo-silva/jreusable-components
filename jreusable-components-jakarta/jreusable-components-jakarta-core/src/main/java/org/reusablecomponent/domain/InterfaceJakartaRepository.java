package org.reusablecomponent.domain;

import jakarta.data.repository.CrudRepository;
import jakarta.enterprise.inject.Vetoed;

@Vetoed
public interface InterfaceJakartaRepository <Entity extends AbstractEntity<Id>, Id> extends CrudRepository<Entity, Id> {

}
