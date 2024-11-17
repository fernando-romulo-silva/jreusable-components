package org.reusablecomponents.spring.core.jpa.domain;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface InterfaceSpringR2DbcReactiveRepository<Entity extends AbstractEntity<Id>, Id>
        extends R2dbcRepository<Entity, Id> {

}
