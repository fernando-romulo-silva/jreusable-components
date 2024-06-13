package org.reusablecomponents.spring.core.domain;

import org.reusablecomponents.core.domain.AbstractEntity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface InterfaceSpringSpecificationRepository <Entity extends AbstractEntity<Id>, Id, Specification> {

}
