package org.reusablecomponents.spring.core.jpa.domain.custom;

import java.util.List;
import java.util.Optional;

import org.reusablecomponents.base.core.domain.AbstractEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

public class CustomSpecificationJpqlRepository<Entity extends AbstractEntity<Id>, Id>
        implements InterfaceCustomSpecificationJpqlRepository<Entity, Id> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<Entity> findBy(final String query) {
        final var queryJpa = entityManager.createQuery(query);

        return queryJpa.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<Entity> findOneBy(final String query) {

        final var queryJpa = entityManager.createQuery(query);

        try {
            return Optional.of((Entity) queryJpa.getSingleResult());
        } catch (final NoResultException ex) {
            return Optional.empty();
        }
    }

}
