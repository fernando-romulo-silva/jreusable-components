package org.reusablecomponent.jakarta.domain;

import org.reusablecomponent.core.domain.AbstractEntity;

import jakarta.data.repository.Page;
import jakarta.data.repository.Pageable;
import jakarta.data.repository.PageableRepository;
import jakarta.enterprise.inject.Vetoed;

@Vetoed
public interface InterfaceJakartaPaginationSpecificationRepository <Entity extends AbstractEntity<Id>, Id, Specification> extends PageableRepository<Entity, Id> {

    Page<Entity> findAll(final Specification specification, final Pageable pageable);
}
