package org.reusablecomponents.spring.core.jpa.application.query;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;
import org.reusablecomponents.spring.core.application.query.entity.paged.SpringQueryPaginationSpecificationFacade;
import org.reusablecomponents.spring.core.jpa.domain.InterfacePaginationSpecificationJpqlRepository;

public class SpringQueryPaginationSpecificationJpqlFacade<Entity extends AbstractEntity<Id>, Id>
        extends SpringQueryPaginationSpecificationFacade<Entity, Id, String> {

    protected SpringQueryPaginationSpecificationJpqlFacade(
            final InterfacePaginationSpecificationJpqlRepository<Entity, Id> repository,
            final InterfaceSecurityService securityService,
            final InterfaceExceptionAdapterService exceptionAdapterService,
            final InterfaceI18nService i18Service) {

        super(repository, securityService, exceptionAdapterService, i18Service);
    }
}
