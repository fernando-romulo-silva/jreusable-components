package org.reusablecomponents.jakarta.domain;

import org.reusablecomponents.base.core.domain.InterfaceEntity;

import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.data.repository.BasicRepository;
import jakarta.enterprise.inject.Vetoed;

@Vetoed
public interface InterfaceJakartaPaginationSpecificationRepository<Entity extends InterfaceEntity<Id>, Id, Specification>
        extends BasicRepository<Entity, Id> {

    Page<Entity> findAll(final Specification specification, final PageRequest pageable);
}
