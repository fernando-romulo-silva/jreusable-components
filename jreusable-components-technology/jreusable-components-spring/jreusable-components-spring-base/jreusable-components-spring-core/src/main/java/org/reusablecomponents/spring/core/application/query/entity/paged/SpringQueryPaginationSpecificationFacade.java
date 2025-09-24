package org.reusablecomponents.spring.core.application.query.entity.paged;

import java.util.Optional;

import org.reusablecomponents.base.core.application.query.entity.paginationspecification.QueryPaginationSpecificationFacade;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.QueryPaginationSpecificationFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;
import org.reusablecomponents.spring.core.domain.InterfaceSpringPaginationSpecificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 
 */
public class SpringQueryPaginationSpecificationFacade<Entity extends AbstractEntity<Id>, Id, Specification>
		extends QueryPaginationSpecificationFacade<Entity, Id, // basic
				Optional<Entity>, // one result type
				Page<Entity>, // multiple result type
				// Pagination
				Pageable, // pageable type
				Sort, // sort type
				Specification> // query spec
		//
		implements InterfaceSpringQueryPaginationSpecificationFacade<Entity, Id, Specification> {

	protected final InterfaceSpringPaginationSpecificationRepository<Entity, Id, Specification> repository;

	protected SpringQueryPaginationSpecificationFacade(
			final InterfaceSpringPaginationSpecificationRepository<Entity, Id, Specification> repository,
			final InterfaceSecurityService securityService,
			final InterfaceExceptionAdapterService exceptionAdapterService,
			final InterfaceI18nService i18Service) {
		super(new QueryPaginationSpecificationFacadeBuilder<>($ -> {

			$.findByPagAndSpecFunction = (pageable, specification, directives) -> repository.findBy(
					specification, pageable);

			$.findOneByPagAndSpecFunction = (sort, specification, directives) -> repository.findOneBy(
					specification,
					sort);

			// services
			$.i18nService = i18Service;
			$.exceptionAdapterService = exceptionAdapterService;
			$.securityService = securityService;
		}));

		this.repository = repository;
	}
}
