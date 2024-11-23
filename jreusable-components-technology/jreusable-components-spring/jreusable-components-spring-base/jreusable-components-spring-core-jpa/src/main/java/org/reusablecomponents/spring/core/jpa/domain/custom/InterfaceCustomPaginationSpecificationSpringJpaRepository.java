package org.reusablecomponents.spring.core.jpa.domain.custom;

import java.util.Optional;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface InterfaceCustomPaginationSpecificationSpringJpaRepository<Entity extends AbstractEntity<Id>, Id> {

    Optional<Entity> findOneBy(final String query, final Sort sort);

    Page<Entity> findBy(final String query, final Pageable pageable);
}
