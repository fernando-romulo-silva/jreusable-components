package org.reusablecomponents.spring.core.jpa.domain.custom;

import java.util.List;
import java.util.Optional;

import org.reusablecomponents.base.core.domain.AbstractEntity;

public interface InterfaceCustomSpecificationJpqlRepository<Entity extends AbstractEntity<Id>, Id> {

    List<Entity> findBy(final String query);

    Optional<Entity> findOneBy(final String query);
}
