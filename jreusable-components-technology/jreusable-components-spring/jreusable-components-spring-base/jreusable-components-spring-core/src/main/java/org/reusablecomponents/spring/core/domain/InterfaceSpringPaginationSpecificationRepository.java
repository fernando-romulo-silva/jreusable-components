package org.reusablecomponents.spring.core.domain;

import java.util.Optional;

import org.reusablecomponents.base.core.domain.InterfaceEntity;
import org.springframework.data.repository.NoRepositoryBean;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface InterfaceSpringPaginationSpecificationRepository<Entity extends InterfaceEntity<Id>, Id, Specification>
                extends PagingAndSortingRepository<Entity, Id>, CrudRepository<Entity, Id> {

        Page<Entity> findBy(final Specification specification, final Pageable pageable);

        Optional<Entity> findOneBy(final Specification specification, final Sort sort);
}
