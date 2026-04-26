package org.reusablecomponents.spring.core.domain;

import org.reusablecomponents.base.core.domain.InterfaceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface InterfaceSpringPaginationRepository<Entity extends InterfaceEntity<Id>, Id>
		extends PagingAndSortingRepository<Entity, Id>, CrudRepository<Entity, Id> {

}
