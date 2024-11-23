package org.reusablecomponents.spring.core.jpa.domain;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.spring.core.domain.InterfaceSpringSpecificationRepository;
import org.reusablecomponents.spring.core.jpa.domain.custom.InterfaceCustomSpecificationJpqlRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface InterfaceSpecificationJpqlRepository<Entity extends AbstractEntity<Id>, Id>
                extends
                InterfaceSpringSpecificationRepository<Entity, Id, String>,
                InterfaceCustomSpecificationJpqlRepository<Entity, Id>,
                JpaRepository<Entity, Id> {

}
