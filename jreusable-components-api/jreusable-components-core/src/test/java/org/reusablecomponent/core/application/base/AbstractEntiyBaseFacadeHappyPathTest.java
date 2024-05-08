package org.reusablecomponent.core.application.base;

import static java.lang.System.out;
import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import org.application_example.application.TestEntiyBaseFacade;
import org.application_example.domain.Department;
import org.application_example.infra.DummySecurityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reusablecomponent.core.infra.exception.ExceptionTranslatorService;
import org.reusablecomponent.core.infra.i18n.InterfaceI18nService;
import org.reusablecomponent.core.infra.i18n.JavaSEI18nService;
import org.reusablecomponent.core.infra.messaging.InterfacePublisherSerice;
import org.reusablecomponent.core.infra.messaging.event.CommonEvent;
import org.reusablecomponent.core.infra.messaging.logger.LoggerPublisherSerice;
import org.reusablecomponent.core.infra.security.InterfaceSecurityService;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

@Tag("unit")
@DisplayName("Test the AbstractEntiyBaseFacade entity test, happy Path :) ")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class AbstractEntiyBaseFacadeHappyPathTest {
    
    @Test
    @Order(1)
    @DisplayName("Test the constructor values")
    void constructorValuesTest() {
	
	// given
	final InterfacePublisherSerice publisherService = (event) -> out.println(event);
	final InterfaceI18nService i18nService = (code, params) -> "translated!";
	final InterfaceSecurityService interfaceSecurityService = new DummySecurityService();
	final ExceptionTranslatorService exceptionTranslatorService = (ex, i18n) -> new RuntimeException(ex);
	
	// when
	final var facade = new TestEntiyBaseFacade(publisherService, i18nService, interfaceSecurityService, exceptionTranslatorService);
		
	// then
	assertThat(facade)
		.as(format("Check the publisherSerice, securityService, and i18nService are not null"))
		.extracting("publisherSerice", "securityService", "i18nService", "entityClazz", "idClazz")
		.doesNotContainNull()
	;

	// then
	assertThat(facade)
		.extracting("entityClazz", "idClazz")
		.as(format("Check the entityClazz ''{0}'' and idClazz ''{1}''", Department.class, String.class))
		.containsExactly(Department.class, String.class)
	;
    }
    
    @Test
    @Order(2)
    @DisplayName("Test the get values")
    void checkValuesTest() {
	
	// given
	final var facade = new TestEntiyBaseFacade();
	
	assertThat(facade)
	        // when
		.extracting(AbstractEntiyBaseFacade::getEntityClazz)
		// then
		.isNotNull()
		.isEqualTo(Department.class)
	;

	assertThat(facade)
		// when
		.extracting(AbstractEntiyBaseFacade::getIdClazz)
		// then
		.isNotNull()
		.isEqualTo(String.class)
	;
    }
    
    @Test
    @Order(3)
    @DisplayName("Test the services instances")
    void checkServicesTest() {
	
	// given
	final var facade = new TestEntiyBaseFacade();
	
	assertThat(facade)
	        // when
	        .extracting(TestEntiyBaseFacade::getSecurityService)
	            // then
	            .isNotNull()
	            .extracting(InterfaceSecurityService::getUserName)
	            .isEqualTo("fernando");
	
	assertThat(facade)
                // when	
		.extracting(AbstractEntiyBaseFacade::getPublisherSerice)
		    // then 
		    .isNotNull()
		    .extracting(publisherSerice -> publisherSerice.getClass())
		    .isEqualTo(LoggerPublisherSerice.class);
	
	assertThat(facade)
		// when	
		.extracting(AbstractEntiyBaseFacade::getI18nService)
		    // then
		    .isNotNull()
	            .extracting(i18nService -> i18nService.getClass())
	            .isEqualTo(JavaSEI18nService.class);
	
	assertThat(facade)
	// when	
	      .extracting(AbstractEntiyBaseFacade::getExceptionTranslatorService)
	      	  // then
	      	  .isNotNull();
    }

    @Test
    @Order(4)
    @DisplayName("Test the publish operation")
    void publishOperationTest() {

	// given
        final var listAppender = new ListAppender<ILoggingEvent>();
        listAppender.start();
        
        final var facadeLogger = (Logger) LoggerFactory.getLogger(LoggerPublisherSerice.class);
        facadeLogger.addAppender(listAppender);
	
	final var facade = new TestEntiyBaseFacade();
	
	// when
	facade.publish("Save", "Out", CommonEvent.SAVE_ITEM);
	
	// then
        assertThat(listAppender.list)
        	.extracting(ILoggingEvent::getFormattedMessage, ILoggingEvent::getLevel)
        	.containsExactly(tuple("Publish event: Save", Level.DEBUG));
	
    }
}
