package org.reusablecomponents.jakarta.application.query.entity;

import java.util.Optional;
import java.util.stream.Stream;

import org.reusablecomponents.base.core.application.query.entity.nonpaged.InterfaceEntityQuerySpecificationFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import static jakarta.transaction.Transactional.TxType.SUPPORTS;

import jakarta.transaction.Transactional;

/**
 * 
 */
public interface InterfaceJakartaEntityQuerySpecificationFacade<Entity extends AbstractEntity<Id>, Id>

        extends InterfaceEntityQuerySpecificationFacade<Entity, Id, // base
                Optional<Entity>, // One result
                Stream<Entity>, // multiple result
                Long, // count result
                Boolean, // exists result
                String> { // spec

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = SUPPORTS)
    Stream<Entity> findBySpec(final String specification, final Object... directives);

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = SUPPORTS)
    Optional<Entity> findOneBySpec(final String specification, final Object... directives);

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = SUPPORTS)
    Boolean existsBySpec(final String specification);

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = SUPPORTS)
    Long countBySpec(final String specification);

}
