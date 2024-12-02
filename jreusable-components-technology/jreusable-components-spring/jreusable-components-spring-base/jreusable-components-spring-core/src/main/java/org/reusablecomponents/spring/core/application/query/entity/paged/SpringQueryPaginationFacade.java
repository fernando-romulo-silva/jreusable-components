package org.reusablecomponents.spring.core.application.query.entity.paged;

import java.util.Optional;

import org.reusablecomponents.base.core.application.query.entity.paged.QueryPaginationFacade;
import org.reusablecomponents.base.core.application.query.entity.paged.QueryPaginationFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;
import org.reusablecomponents.spring.core.domain.InterfaceSpringPaginationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class SpringQueryPaginationFacade<Entity extends AbstractEntity<Id>, Id>
        // base class
        extends QueryPaginationFacade<Entity, Id, // basic
                // results
                Optional<Entity>, // one result type
                Page<Entity>, // multiple result type
                // Pagination
                Pageable, // pageable type
                Sort> // sort type

        implements InterfaceSpringQueryPaginationFacade<Entity, Id> {

    protected final InterfaceSpringPaginationRepository<Entity, Id> repository;

    public SpringQueryPaginationFacade(
            final InterfaceSpringPaginationRepository<Entity, Id> repository,
            final InterfaceSecurityService securityService,
            final InterfaceExceptionAdapterService exceptionAdapterService,
            final InterfaceI18nService i18Service) {

        super(new QueryPaginationFacadeBuilder<>($ -> {

            $.findAllFunction = (pageable, directives) -> repository.findAll(pageable);
            $.findFirstFunction = (sort, directives) -> Optional.ofNullable(repository.findAll(sort).iterator().next());

            // services
            $.i18nService = i18Service;
            $.exceptionAdapterService = exceptionAdapterService;
            $.securityService = securityService;
        }));

        this.repository = repository;
    }

}
