package org.reusablecomponents.spring.core.jpa.domain;

import java.util.List;

import org.reusablecomponents.base.core.domain.AbstractEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SuppressWarnings("unchecked")
public class CustomSpringJpaRepository<Entity extends AbstractEntity<Id>, Id> implements
        InterfaceCustomSpringJpaRepository<Entity, Id> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Entity> findBy(final String query) {
        final var queryJpa = entityManager.createQuery(query);

        return queryJpa.getResultList();
    }

    @Override
    public Entity findOneBy(final String query) {

        final var queryJpa = entityManager.createQuery(query);

        return (Entity) queryJpa.getSingleResult();
    }
}
