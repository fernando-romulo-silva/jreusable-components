package org.reusablecomponents.jakarta.domain;

import org.reusablecomponents.base.core.domain.InterfaceEntity;

import jakarta.data.repository.BasicRepository;
import jakarta.enterprise.inject.Vetoed;

@Vetoed
public interface InterfaceJakartaPaginationRepository<Entity extends InterfaceEntity<Id>, Id>
        extends BasicRepository<Entity, Id> {

}
