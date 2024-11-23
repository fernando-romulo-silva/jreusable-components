package org.reusablecomponents.spring.core.jpa.application.query;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;
import org.reusablecomponents.spring.core.application.query.entity.nonpaged.SpringQuerySpecificationFacade;
import org.reusablecomponents.spring.core.jpa.domain.InterfaceSpecificationJpqlRepository;

/**
 * 
 */
public class SpringQuerySpecificationJpqlFacade<Entity extends AbstractEntity<Id>, Id>
        extends SpringQuerySpecificationFacade<Entity, Id, String> {

    /**
     * 
     * @param repository
     * @param securityService
     * @param exceptionAdapterService
     * @param i18Service
     */
    public SpringQuerySpecificationJpqlFacade(
            final InterfaceSpecificationJpqlRepository<Entity, Id> repository,
            final InterfaceSecurityService securityService,
            final InterfaceExceptionAdapterService exceptionAdapterService,
            final InterfaceI18nService i18Service) {

        super(repository, securityService, exceptionAdapterService, i18Service);
    }

}
