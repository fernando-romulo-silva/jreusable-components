package org.reusablecomponents.spring.core.domain;

import org.reusablecomponents.base.core.domain.InterfaceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface InterfaceSpringRepository<Entity extends InterfaceEntity<Id>, Id>
        extends CrudRepository<Entity, Id> {

}
