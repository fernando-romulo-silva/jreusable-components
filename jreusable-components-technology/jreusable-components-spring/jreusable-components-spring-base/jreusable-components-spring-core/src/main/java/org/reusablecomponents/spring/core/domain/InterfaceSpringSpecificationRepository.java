package org.reusablecomponents.spring.core.domain;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

@NoRepositoryBean
public interface InterfaceSpringSpecificationRepository<Entity extends AbstractEntity<Id>, Id, Specification>
        extends PagingAndSortingRepository<Entity, Id>, CrudRepository<Entity, Id>, QueryByExampleExecutor<Entity> {

}
