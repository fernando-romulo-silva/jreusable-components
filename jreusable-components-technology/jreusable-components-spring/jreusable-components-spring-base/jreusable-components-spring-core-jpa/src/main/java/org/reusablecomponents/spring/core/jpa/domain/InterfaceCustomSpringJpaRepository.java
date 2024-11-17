package org.reusablecomponents.spring.core.jpa.domain;

import java.util.List;

import org.reusablecomponents.base.core.domain.AbstractEntity;

public interface InterfaceCustomSpringJpaRepository<Entity extends AbstractEntity<Id>, Id> {

    List<Entity> findBy(final String query);

    Entity findOneBy(final String query);
}
