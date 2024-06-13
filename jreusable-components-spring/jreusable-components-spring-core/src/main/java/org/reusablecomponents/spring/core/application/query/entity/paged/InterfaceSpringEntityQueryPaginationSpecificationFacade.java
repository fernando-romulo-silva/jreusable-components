package org.reusablecomponents.spring.core.application.query.entity.paged;

import java.util.Optional;

import org.reusablecomponents.core.application.query.entity.paged.InterfaceEntityQueryPaginationSpecificationFacade;
import org.reusablecomponents.core.domain.AbstractEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @param <Entity>
 * @param <Id>
 * @param <Specification>
 */
public interface InterfaceSpringEntityQueryPaginationSpecificationFacade<Entity extends AbstractEntity<Id>, Id, Specification>
	extends InterfaceEntityQueryPaginationSpecificationFacade<Entity, Id, // basic
		// results
		Optional<Entity>, // one result type
		Page<Entity>, // multiple result type
		// Pagination
		Pageable, // pageable type
		Sort, // sort type
		Specification> { // query spec

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable
    Page<Entity> findBy(final Pageable pageable, final Specification specification, final Object... directives);

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable
    Optional<Entity> findOneBy(final Specification specification, final Sort sort);
}
