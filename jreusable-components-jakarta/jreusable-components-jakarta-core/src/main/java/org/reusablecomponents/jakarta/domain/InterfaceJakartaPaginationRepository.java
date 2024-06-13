package org.reusablecomponents.jakarta.domain;

import org.reusablecomponents.core.domain.AbstractEntity;

import jakarta.data.repository.BasicRepository;
import jakarta.enterprise.inject.Vetoed;

@Vetoed
public interface InterfaceJakartaPaginationRepository <Entity extends AbstractEntity<Id>, Id> extends BasicRepository<Entity, Id> {

}
