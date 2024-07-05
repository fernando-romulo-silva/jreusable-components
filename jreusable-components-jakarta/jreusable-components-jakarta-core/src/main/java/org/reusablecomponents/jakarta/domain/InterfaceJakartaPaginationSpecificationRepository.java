package org.reusablecomponents.jakarta.domain;

import org.reusablecomponents.base.core.domain.AbstractEntity;

import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.data.repository.BasicRepository;
import jakarta.enterprise.inject.Vetoed;

@Vetoed
public interface InterfaceJakartaPaginationSpecificationRepository <Entity extends AbstractEntity<Id>, Id, Specification> extends BasicRepository<Entity, Id> {

    Page<Entity> findAll(final Specification specification, final PageRequest pageable);
}
