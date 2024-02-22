package org.reusablecomponent.spring.domain;

import org.reusablecomponent.domain.AbstractEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface InterfaceSpringPaginationRepository<Entity extends AbstractEntity<Id>, Id> 
	extends PagingAndSortingRepository<Entity, Id>, CrudRepository<Entity, Id>  {

    
}
