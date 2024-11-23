package org.reusablecomponents.spring.core.jpa.domain.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class CustomPaginationSpecificationSpringJpaRepository<Entity extends AbstractEntity<Id>, Id>
        implements InterfaceCustomPaginationSpecificationSpringJpaRepository<Entity, Id> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public Page<Entity> findBy(final String query, final Pageable pageable) {

        final var queryJpa = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());

        return new PageImpl<>(queryJpa.getResultList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<Entity> findOneBy(final String query, final Sort sort) {

        final var queryJpa = entityManager.createQuery(query);
        return Optional.ofNullable((Entity) queryJpa.getSingleResult());
    }

    Sort convertToSort(List<String> sortFields) {
        List<Sort.Order> orders = new ArrayList<>();
        for (String field : sortFields) {
            String[] propertyAndDirection = field.split(",");
            String property = propertyAndDirection[0];
            Sort.Direction direction = Sort.DEFAULT_DIRECTION;
            if (propertyAndDirection.length > 1) {
                String directionString = propertyAndDirection[1];
                direction = Sort.Direction.fromOptionalString(directionString)
                        .orElse(Sort.DEFAULT_DIRECTION);
            }
            Sort.Order order = new Sort.Order(direction, property);
            orders.add(order);
        }
        return Sort.by(orders);
    }

}
