package org.reusablecomponents.spring.core.domain;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface InterfaceSpringSpecificationRepository <Entity extends AbstractEntity<Id>, Id, Specification> {

}
