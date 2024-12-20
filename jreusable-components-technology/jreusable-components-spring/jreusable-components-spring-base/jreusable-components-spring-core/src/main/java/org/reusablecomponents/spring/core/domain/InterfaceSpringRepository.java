package org.reusablecomponents.spring.core.domain;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface InterfaceSpringRepository<Entity extends AbstractEntity<Id>, Id> extends CrudRepository<Entity, Id> {

}
