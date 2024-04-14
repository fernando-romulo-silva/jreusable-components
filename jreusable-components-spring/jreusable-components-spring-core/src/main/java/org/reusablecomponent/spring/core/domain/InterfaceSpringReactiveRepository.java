package org.reusablecomponent.spring.core.domain;

import org.reusablecomponent.core.domain.AbstractEntity;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

@NoRepositoryBean
public interface InterfaceSpringReactiveRepository <Entity extends AbstractEntity<Id>, Id> 
	extends ReactiveCrudRepository<Entity, Id> {

}
