package org.reusablecomponents.spring.core.domain;

import java.util.Optional;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface InterfaceSpringSpecificationRepository<Entity extends AbstractEntity<Id>, Id, Specification>
        extends CrudRepository<Entity, Id> {

    Iterable<Entity> findBy(final Specification specification);

    Optional<Entity> findOneBy(final Specification specification);
}
