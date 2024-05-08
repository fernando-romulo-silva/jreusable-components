package org.reusablecomponent.core.application.base;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.nonNull;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.core.infra.exception.ExceptionTranslatorService;
import org.reusablecomponent.core.infra.i18n.InterfaceI18nService;
import org.reusablecomponent.core.infra.i18n.JavaSEI18nService;
import org.reusablecomponent.core.infra.messaging.InterfacePublisherSerice;
import org.reusablecomponent.core.infra.messaging.event.Event;
import org.reusablecomponent.core.infra.messaging.event.InterfaceOperationEvent;
import org.reusablecomponent.core.infra.messaging.event.What;
import org.reusablecomponent.core.infra.messaging.event.When;
import org.reusablecomponent.core.infra.messaging.event.Where;
import org.reusablecomponent.core.infra.messaging.event.Who;
import org.reusablecomponent.core.infra.messaging.event.Why;
import org.reusablecomponent.core.infra.messaging.logger.LoggerPublisherSerice;
import org.reusablecomponent.core.infra.security.InterfaceSecurityService;
import org.reusablecomponent.core.infra.security.JavaSecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.TypeToken;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 */
public abstract class AbstractEntiyBaseFacade<Entity extends AbstractEntity<Id>, Id> 
	implements InterfaceEntityBaseFacade<Entity, Id> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEntiyBaseFacade.class);

    // -------
    
    protected final InterfacePublisherSerice publisherSerice;

    protected final InterfaceSecurityService securityService;

    protected final InterfaceI18nService i18nService;
    
    protected final ExceptionTranslatorService exceptionTranslatorService;

    protected final Class<Entity> entityClazz;

    protected final Class<Id> idClazz;

    // ------

    protected AbstractEntiyBaseFacade(
		    @Nullable final InterfacePublisherSerice publisherService, 
		    @Nullable final InterfaceI18nService i18nService,
		    @Nullable final InterfaceSecurityService securityService,
		    @Nullable final ExceptionTranslatorService exceptionTranslatorService) {

	super();

	this.entityClazz = retrieveEntityClazz();
	this.idClazz = retrieveIdClazz();

	this.publisherSerice = nonNull(publisherService) ? publisherService : new LoggerPublisherSerice();
	this.i18nService = nonNull(i18nService) ? i18nService : new JavaSEI18nService();
	this.securityService = nonNull(securityService) ? securityService : new JavaSecurityService();
	this.exceptionTranslatorService = nonNull(exceptionTranslatorService) ? exceptionTranslatorService : (paramException, paramI18nService) -> new RuntimeException(paramException);
    }

    protected AbstractEntiyBaseFacade() {
	this(null, null, null, null);
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
    
    /**
     * @return
     */
    protected boolean publishEvents() {
	return true;
    }
    
    /**
     * @param dataIn
     * @param dataOut
     * @param operation
     */
    protected void publish(final String dataIn, final String dataOut, final InterfaceOperationEvent operation) {
	
	checkNotNull(operation, "Operation argument cannot be null");
	
	LOGGER.debug("Publishing {} operation", operation);
	
	if (!publishEvents()) {
	    LOGGER.debug("Published {} operation avoided", operation);
	    return;
	}
	
	final var data = StringUtils.deleteWhitespace(""" 
	    		    {		
	    			in: ${in},
	    			out: ${out}
	    		    }	
	    		""").replace("${in}", dataIn)
			    .replace("${out}", dataOut);
	
	final var user = securityService.getUserName();
	final var realm = securityService.getUserRealm();
	final var session = securityService.getSession();
	final var application = securityService.getApplication();

	final var event = new Event.Builder().with($ -> {
	    $.what = new What(data);
	    $.when = new When(LocalDateTime.now(), ZoneId.systemDefault());
	    $.where = new Where(application, session);
	    $.who = new Who(realm, user);
	    $.why = new Why(operation.toString());
	}).build();

	try {
	    publisherSerice.publish(event);
	} catch (final Exception ex) {
	    LOGGER.error(ExceptionUtils.getRootCauseMessage(ex), ex);
	}
	
	LOGGER.debug("Published '{}' operation, event '{}'", event.getId());
    }

    // ------

    @NotNull
    protected final Class<Id> getIdClazz() {
	return idClazz;
    }

    /**
     */
    @NotNull
    protected Class<Entity> getEntityClazz() {
	return entityClazz;
    }

    /**
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
     * @return
     */
    @NotNull
    protected ExceptionTranslatorService getExceptionTranslatorService() {
        return exceptionTranslatorService;
    }
}
