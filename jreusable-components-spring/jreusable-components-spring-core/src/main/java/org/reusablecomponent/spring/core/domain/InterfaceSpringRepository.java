package org.reusablecomponent.spring.core.domain;

import org.reusablecomponent.core.domain.AbstractEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface InterfaceSpringRepository <Entity extends AbstractEntity<Id>, Id> 
	extends CrudRepository<Entity, Id> {
    
    
}
