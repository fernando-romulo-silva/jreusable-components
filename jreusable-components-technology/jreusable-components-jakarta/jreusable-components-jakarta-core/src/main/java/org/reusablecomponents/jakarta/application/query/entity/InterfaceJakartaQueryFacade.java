package org.reusablecomponents.jakarta.application.query.entity;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;

import java.util.Optional;
import java.util.stream.Stream;

import org.reusablecomponents.base.core.application.query.entity.simple.InterfaceQueryFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;

import jakarta.transaction.Transactional;

/**
 * @param <Entity>
 * @param <Id>
 */
public interface InterfaceJakartaQueryFacade<Entity extends AbstractEntity<Id>, Id>
                //
                extends InterfaceQueryFacade<Entity, Id, // base
                                Id, // by id arg
                                Optional<Entity>, // One result
                                Stream<Entity>, // multiple result
                                Long, // count result
                                Boolean> { // exists result

        /**
         * {@inheritDoc}
         */
        @Override
        @Transactional(value = SUPPORTS)
        Optional<Entity> findById(final Id id, final Object... directives);

        /**
         * {@inheritDoc}
         */
        @Override
        @Transactional(value = SUPPORTS)
        Stream<Entity> findAll(final Object... directives);

        /**
         * {@inheritDoc}
         */
        @Override
        @Transactional(value = SUPPORTS)
        Long countAll(final Object... directives);

        /**
         * {@inheritDoc}
         */
        @Override
        @Transactional(value = SUPPORTS)
        Boolean existsById(final Id id, final Object... directives);
}
