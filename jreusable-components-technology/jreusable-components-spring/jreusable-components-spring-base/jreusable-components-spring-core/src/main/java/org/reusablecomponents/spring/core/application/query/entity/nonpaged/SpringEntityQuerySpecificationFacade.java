package org.reusablecomponents.spring.core.application.query.entity.nonpaged;

import java.util.List;
import java.util.Optional;

import org.reusablecomponents.base.core.application.query.entity.nonpaged.QuerySpecificationFacade;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.QuerySpecificationFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;
import org.reusablecomponents.spring.core.domain.InterfaceSpringSpecificationRepository;

public class SpringEntityQuerySpecificationFacade<Entity extends AbstractEntity<Id>, Id, Specification>
		// base class
		extends QuerySpecificationFacade<Entity, Id, Optional<Entity>, // One result
				Iterable<Entity>, // multiple result
				Long, // count result
				Boolean, Specification>

		implements InterfaceSpringQuerySpecificationFacade<Entity, Id, Specification> {

	protected InterfaceSpringSpecificationRepository<Entity, Id, Specification> repository;

	/**
	 * 
	 * @param builder
	 */
	public SpringEntityQuerySpecificationFacade(
			final InterfaceSpringSpecificationRepository<Entity, Id, Specification> repository,
			final InterfaceSecurityService securityService,
			final InterfaceExceptionAdapterService exceptionAdapterService,
			final InterfaceI18nService i18Service) {

		super(new QuerySpecificationFacadeBuilder<>($ -> {
			$.findBySpecificationFunction = (specification, directives) -> List.<Entity>of();
			$.findOneByFunction = (specification, directives) -> Optional.empty();
			$.existsBySpecificationFunction = specification -> Boolean.FALSE;
			$.countBySpecificationFunction = specification -> 0L;

			// services
			$.i18nService = i18Service;
			$.exceptionAdapterService = exceptionAdapterService;
			$.securityService = securityService;
		}));

		this.repository = repository;
	}

}
