package org.reusablecomponent.spring.core.domain;

import org.reusablecomponent.core.domain.AbstractEntity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface InterfaceSpringSpecificationRepository <Entity extends AbstractEntity<Id>, Id, Specification> {

}
