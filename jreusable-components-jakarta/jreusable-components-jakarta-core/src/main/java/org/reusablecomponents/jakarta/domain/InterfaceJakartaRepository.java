package org.reusablecomponents.jakarta.domain;

import org.reusablecomponents.core.domain.AbstractEntity;

import jakarta.data.repository.CrudRepository;
import jakarta.enterprise.inject.Vetoed;

@Vetoed
public interface InterfaceJakartaRepository <Entity extends AbstractEntity<Id>, Id> extends CrudRepository<Entity, Id> {

}
