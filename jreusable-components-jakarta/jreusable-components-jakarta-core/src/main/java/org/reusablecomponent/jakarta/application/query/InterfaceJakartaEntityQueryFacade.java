package org.reusablecomponent.jakarta.application.query;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.reusablecomponent.core.application.query.entity.nonpaged.InterfaceEntityQueryFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

import jakarta.transaction.Transactional;

public interface InterfaceJakartaEntityQueryFacade<Entity extends AbstractEntity<Id>, Id>
	//
	extends InterfaceEntityQueryFacade<Entity, Id, Optional<Entity>, List<Entity>, Long, Boolean> {
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = SUPPORTS)
    Optional<Entity> findBy(final Id id);
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = SUPPORTS)
    List<Entity> findAll(final Map<String, String[]> directives);

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = SUPPORTS)
    Boolean existsBy(final Id id);
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = SUPPORTS)
    Long count();
}
