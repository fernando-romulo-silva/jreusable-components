package org.reusablecomponents.spring.core.domain;

import org.reusablecomponents.base.core.domain.InterfaceEntity;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

@NoRepositoryBean
public interface InterfaceSpringReactiveRepository<Entity extends InterfaceEntity<Id>, Id>
		extends ReactiveCrudRepository<Entity, Id> {

}
