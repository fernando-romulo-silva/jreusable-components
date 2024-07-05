package org.reusablecomponents.base.core.application.base;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static java.util.Objects.nonNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.reusablecomponents.base.core.application.command.entity.EntityCommandFacade;
import org.reusablecomponents.base.core.application.empty.SimpleEntiyBaseFacade;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.EntityQueryFacade;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.EntityQuerySpecificationFacade;
import org.reusablecomponents.base.core.application.query.entity.paged.EntityQueryPaginationFacade;
import org.reusablecomponents.base.core.application.query.entity.paged.EntityQueryPaginationSpecificationFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionTranslatorService;
import org.reusablecomponents.base.core.infra.exception.common.GenericException;
import org.reusablecomponents.base.messaging.InterfaceEventPublisherSerice;
import org.reusablecomponents.base.messaging.event.Event;
import org.reusablecomponents.base.messaging.event.What;
import org.reusablecomponents.base.messaging.event.When;
import org.reusablecomponents.base.messaging.event.Where;
import org.reusablecomponents.base.messaging.event.Who;
import org.reusablecomponents.base.messaging.event.Why;
import org.reusablecomponents.base.messaging.logger.LoggerPublisherSerice;
import org.reusablecomponents.base.messaging.operation.InterfaceOperationEvent;
import org.reusablecomponents.base.security.DefaultSecurityService;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;
import org.reusablecomponents.base.translation.JavaSEI18nService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.TypeToken;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 */
public sealed class EntiyBaseFacade<Entity extends AbstractEntity<Id>, Id> 
	implements InterfaceEntityBaseFacade<Entity, Id> 
	permits SimpleEntiyBaseFacade, EntityCommandFacade, EntityQueryFacade, 
		EntityQuerySpecificationFacade, EntityQueryPaginationFacade, EntityQueryPaginationSpecificationFacade {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EntiyBaseFacade.class);

    // -------
    
    protected final InterfaceEventPublisherSerice publisherService;

    protected final InterfaceSecurityService securityService;

    protected final InterfaceI18nService i18nService;
    
    protected final InterfaceExceptionTranslatorService exceptionTranslatorService;
    
    protected final Class<Entity> entityClazz;

    protected final Class<Id> idClazz;

    // ------

    protected EntiyBaseFacade(
		    @Nullable final InterfaceEventPublisherSerice publisherService, 
		    @Nullable final InterfaceI18nService i18nService,
		    @Nullable final InterfaceSecurityService securityService,
		    @Nullable final InterfaceExceptionTranslatorService exceptionTranslatorService) {

	super();

	this.entityClazz = retrieveEntityClazz();
	this.idClazz = retrieveIdClazz();

	this.publisherService = nonNull(publisherService) ? publisherService : new LoggerPublisherSerice();
	this.i18nService = nonNull(i18nService) ? i18nService : new JavaSEI18nService();
	this.securityService = nonNull(securityService) ? securityService : new DefaultSecurityService();
	this.exceptionTranslatorService = nonNull(exceptionTranslatorService) ? exceptionTranslatorService : (paramException, paramI18nService) -> new GenericException(paramException);
    }

    protected EntiyBaseFacade() {
	this(null, null, null, null);
    }

    // ------

    @SuppressWarnings("unchecked")
    private final Class<Entity> retrieveEntityClazz() {

	final var entityTypeToken = new TypeToken<Entity>(getClass()) {
	    private static final long serialVersionUID = 1L;
	};

	return (Class<Entity>) entityTypeToken.getRawType();
    }

    @SuppressWarnings("unchecked")
    private final Class<Id> retrieveIdClazz() {

	final var idTypeToken = new TypeToken<Id>(getClass()) {
	    private static final long serialVersionUID = 1L;
	};

	return (Class<Id>) idTypeToken.getRawType();
    }
    
    /**
     * @param directives 
     * @return
     */
    protected boolean isPublishEvents(final Object... directives) {
	return true;
    }
    
    protected Event createEvent(final String dataIn, final String dataOut, final InterfaceOperationEvent operation) {
	
	LOGGER.debug("Creating event with '{}' operation", operation);
	
	checkNotNull(operation, "The argument 'operation' cannot be null");
	
	final var user = securityService.getUserName();
	final var realm = securityService.getUserRealm();
	final var session = securityService.getSession();
	final var application = securityService.getApplication();
	final var machineName = securityService.getMachineName();

	final var event = new Event.Builder().with($ -> {
	    $.what = new What(dataIn, dataOut);
	    $.when = new When();
	    $.where = new Where(application, machineName);
	    $.who = new Who(realm, user, session);
	    $.why = new Why(operation.getName(), operation.getDescription());
	}).build();

	LOGGER.debug("Event '{}' created with operation '{}'", event.getId(), operation);
	
	return event;
    }
    
    protected void publishEvent(final Event event, final Object... directives) {
	
	checkNotNull(event, "The argument 'event' cannot be null");	
	
	LOGGER.debug("Publishing event '{}'", event.getId());
	
	if (!isPublishEvents(directives)) {
	    LOGGER.debug("Event publishing '{}' avoided", event.getId());
	    return;
	}
	
	final var eventToPublish = prepareEventToPublisher(event);
	
	try {
	    publisherService.publish(eventToPublish);
	} catch (final Exception ex) {
	    LOGGER.error(ExceptionUtils.getRootCauseMessage(ex), ex);
	    return;
	}
	
	LOGGER.debug("Published event '{}'", event.getId());
    }
    
    protected void publishEvent(final String event, final Object... directives) {
	
	checkArgument(StringUtils.isBlank(event), "The argument 'event' cannot be null or blank");
	
	LOGGER.debug("Publishing event '{}'", event);
	
	if (!isPublishEvents(directives)) {
	    LOGGER.debug("Event publishing {} avoided", event);
	    return;
	}
	
	try {
	    publisherService.publish(event);
	} catch (final Exception ex) {
	    LOGGER.error(ExceptionUtils.getRootCauseMessage(ex), ex);
	}
	
	LOGGER.debug("Published event '{}'", event);
    }
    
    /**
     * @param dataIn
     * @param dataOut
     * @param operation
     */
    protected void publishEvent(final String dataIn, final String dataOut, final InterfaceOperationEvent operation, final Object... directives) {
	
	LOGGER.debug("Publishing event with operation '{}'", operation);
	
	if (!isPublishEvents(directives)) {
	    LOGGER.debug("Event publishing with operation '{}' avoided", operation);
	    return;
	}
	
	final var event = createEvent(dataIn, dataOut, operation);
	
	final var eventToPublish = prepareEventToPublisher(event);
	
	try {
	    publisherService.publish(eventToPublish);
	} catch (final Exception ex) {
	    LOGGER.error(ExceptionUtils.getRootCauseMessage(ex), ex);
	    return;
	}
	
	LOGGER.debug("Published event '{}' with operation '{}'", event.getId(), operation);
	
	return;
    }
    
    /**
     * @param event
     * @return
     */
    protected String prepareEventToPublisher(final Event event) {
	
	final var layout = """
		{
		   "id": "${id}",
		   "what": {
			"dataIn" : "${dataIn}",
			"dataOut" : "${dataOut}"
		   },
		   "when": {
			"dateTime" : "${dateTime}",
			"zoneId" : "${zoneId}"			   
		   },
		   "where": {
			"application" : "${application}",
			"machine" : "${machine}"
		   },
		   "who": {
			"login" : "${login}",
			"session" : "${session}",
			"realm" : "${realm}"
		   },
		   "why": {
		        "reason" : "${reason}",
		        "description" : "${description}"
		   }
	        }""";
	
	var msg = StringUtils.deleteWhitespace(layout);

	// -----------------------------------------
	final var id = event.getId();
	msg = StringUtils.replace(msg, "${id}", id);

	// ------------------------------------------
	final var what = event.getWhat();
	final var dataIn = what.dataIn();
	final var dataOut = what.dataOut();

	msg = StringUtils.replaceEach(msg, 
			new String[] { "${dataIn}", "${dataOut}" }, 
			new String[] { dataIn, dataOut });

	// ------------------------------------------
	final var when = event.getWhen();
	final var dateTime =  ISO_DATE_TIME.format(when.dateTime());
	final var zoneId = when.zoneId().toString();
	
	msg = StringUtils.replaceEach(msg, 
			new String[] { "${dateTime}", "${zoneId}" }, 
			new String[] { dateTime, zoneId });
	
	// ------------------------------------------
	final var where = event.getWhere();
	final var application = where.application();
	final var machine = where.machine();
	
	msg = StringUtils.replaceEach(msg, 
			new String[] { "${application}", "${machine}" }, 
			new String[] { application, machine });	
	
	// ------------------------------------------
	final var who = event.getWho();
	final var login = who.login();
	final var session = who.session();
	final var realm = who.realm();
	
	msg = StringUtils.replaceEach(msg, 
			new String[] { "${login}", "${session}", "${realm}" }, 
			new String[] { login, session, realm });		
	
	// ------------------------------------------
	final var why = event.getWhy();
	final var reason = why.reason();
	final var description = why.description();
	
	msg = StringUtils.replaceEach(msg, 
			new String[] { "${reason}", "${description}" }, 
			new String[] { reason, description});
	
	return msg;
    }

    // ------
    
    /**
     * @return
     */
    @NotNull
    public final Class<Id> getIdClazz() {
	return idClazz;
    }

    /**
     * @return
     */
    @NotNull
    public final Class<Entity> getEntityClazz() {
	return entityClazz;
    }

    /**
     * @return
     */
    @NotNull
    public final InterfaceEventPublisherSerice getPublisherService() {
	return publisherService;
    }

    /**
     * @return
     */
    @NotNull
    public final InterfaceI18nService getI18nService() {
	return i18nService;
    }

    /**
     * @return
     */
    @NotNull
    public final InterfaceSecurityService getSecurityService() {
	return securityService;
    }
    
    /**
     * @return
     */
    @NotNull
    public final InterfaceExceptionTranslatorService getExceptionTranslatorService() {
        return exceptionTranslatorService;
    }
}
