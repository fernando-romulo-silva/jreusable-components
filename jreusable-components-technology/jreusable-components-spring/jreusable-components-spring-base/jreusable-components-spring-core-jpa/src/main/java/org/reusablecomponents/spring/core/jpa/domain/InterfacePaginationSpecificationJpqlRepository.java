package org.reusablecomponents.spring.core.jpa.domain;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.spring.core.domain.InterfaceSpringPaginationSpecificationRepository;
import org.reusablecomponents.spring.core.jpa.domain.custom.InterfaceCustomPaginationSpecificationSpringJpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface InterfacePaginationSpecificationJpqlRepository<Entity extends AbstractEntity<Id>, Id>
        extends
        InterfaceSpringPaginationSpecificationRepository<Entity, Id, String>,
        InterfaceCustomPaginationSpecificationSpringJpaRepository<Entity, Id>,
        JpaRepository<Entity, Id> {

}
