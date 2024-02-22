package org.reusablecomponent.domain;

import jakarta.data.repository.PageableRepository;
import jakarta.enterprise.inject.Vetoed;

@Vetoed
public interface InterfaceJakartaPaginationRepository <Entity extends AbstractEntity<Id>, Id> extends PageableRepository<Entity, Id> {

}
