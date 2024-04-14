package org.reusablecomponent.core.application.base;

import static java.util.Objects.nonNull;

import java.util.function.Predicate;

import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.core.infra.i18n.DefaultI18nService;
import org.reusablecomponent.core.infra.i18n.InterfaceI18nService;
import org.reusablecomponent.core.infra.messaging.InterfacePublisherSerice;
import org.reusablecomponent.core.infra.messaging.logger.LoggerPublisherSerice;
import org.reusablecomponent.core.infra.security.InterfaceSecurityService;
import org.reusablecomponent.core.infra.security.dummy.DummySecurityService;

import com.google.common.reflect.TypeToken;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 */
public abstract class AbstractEntiyBaseFacade<Entity extends AbstractEntity<Id>, Id> implements InterfaceEntityBaseFacade<Entity, Id> {

    protected final InterfacePublisherSerice publisherSerice;
    
    protected final InterfaceSecurityService securityService;
    
    protected final InterfaceI18nService i18nService;
    
    // -------
    
    protected final Class<Entity> entityClazz;

    protected final Class<Id> idClazz;
    
    // ------
    
    protected AbstractEntiyBaseFacade(
		    @Nullable final InterfacePublisherSerice publisherService, 
		    @Nullable final InterfaceI18nService i18nService,
		    @Nullable final InterfaceSecurityService securityService) {
	
	super();
	
	this.entityClazz = retrieveEntityClazz();
	this.idClazz = retrieveIdClazz();
	
	this.publisherSerice = nonNull(publisherService) ? publisherService : new LoggerPublisherSerice();
	this.i18nService = nonNull(i18nService) ? i18nService : new DefaultI18nService();
	this.securityService = nonNull(securityService) ? securityService  : new DummySecurityService();   
    }
    
    protected AbstractEntiyBaseFacade() {
	this(null, null, null);
    }
    
    // ------

    @SuppressWarnings("unchecked")
    private Class<Entity> retrieveEntityClazz() {
	final var entityTypeToken = new TypeToken<Entity>(getClass()) {
	    private static final long serialVersionUID = 1L;
	};

	return (Class<Entity>) entityTypeToken.getRawType();
    }
    
    @SuppressWarnings("unchecked")
    private Class<Id> retrieveIdClazz() {
	
	final var idTypeToken = new TypeToken<Id>(getClass()) {
	    private static final long serialVersionUID = 1L;
	};

	return (Class<Id>) idTypeToken.getRawType();
    }
    
    // ------
    @NotNull
    protected Class<Id> getIdClazz() {
        return idClazz;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    protected Class<Entity> getEntityClazz() {
	return entityClazz;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    protected InterfacePublisherSerice getPublisherSerice() {
        return publisherSerice;
    }
    
    /**
     * @return
     */
    @NotNull
    protected InterfaceI18nService getI18nService() {
	return i18nService;
    }
    
    /**
     * @return
     */
    @NotNull
    protected InterfaceSecurityService getSecurityService() {
        return securityService;
    }


    /**
     * @param <BooleanResult>
     * @param existsEntityFunction
     * @param booleanResult
     * @return
     */
    protected final <BooleanResult> boolean checkEntityExists(final Predicate<BooleanResult> existsEntityFunction, final BooleanResult booleanResult) {
	return existsEntityFunction.test(booleanResult);
    }
}
