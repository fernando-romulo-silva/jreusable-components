package org.reusablecomponents.spring.core.jpa.domain;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.spring.core.domain.InterfaceSpringRepository;
import org.reusablecomponents.spring.core.domain.InterfaceSpringSpecificationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface InterfaceSpringJpaRepository<Entity extends AbstractEntity<Id>, Id>
        extends
        InterfaceSpringRepository<Entity, Id>,
        JpaRepository<Entity, Id>,
        InterfaceSpringSpecificationRepository<Entity, Id, String> {

}
