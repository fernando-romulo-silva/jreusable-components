package org.reusablecomponent.core.application.base;

import static java.lang.System.out;
import static java.text.MessageFormat.format;
import static java.util.Objects.nonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.function.Predicate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reusablecomponent.core.domain.Department;
import org.reusablecomponent.core.infra.i18n.DefaultI18nService;
import org.reusablecomponent.core.infra.i18n.InterfaceI18nService;
import org.reusablecomponent.core.infra.messaging.InterfacePublisherSerice;
import org.reusablecomponent.core.infra.messaging.logger.LoggerPublisherSerice;
import org.reusablecomponent.core.infra.security.InterfaceSecurityService;
import org.reusablecomponent.core.infra.security.dummy.DummySecurityService;

import jakarta.annotation.Nullable;

@Tag("unit")
@DisplayName("Test the AbstractEntiyBaseFacade entity test, happy Path :) ")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class AbstractEntiyBaseFacadeTest {
    
    static class TestEntiyBaseFacade extends AbstractEntiyBaseFacade<Department, String> {
	
	    protected TestEntiyBaseFacade(
			    @Nullable final InterfacePublisherSerice publisherService, 
			    @Nullable final InterfaceI18nService i18nService,
			    @Nullable final InterfaceSecurityService securityService) {
		super(publisherService, i18nService, securityService);
	    }
	    
	    protected TestEntiyBaseFacade() {
		super();
	    }
    }
    
    @Test
    @Order(1)
    @DisplayName("Test the constructor values")
    void constructorValuesTest() {
	
	final InterfacePublisherSerice publisherService = (event) -> out.println(event);
	final InterfaceI18nService i18nService = (code, params) -> "translated!";
	final InterfaceSecurityService interfaceSecurityService = new DummySecurityService();
	
	final var facade = new TestEntiyBaseFacade(publisherService, i18nService, interfaceSecurityService);
			
	assertThat(facade)
		.as(format("Check the publisherSerice, securityService, and i18nService are not null"))
		.extracting("publisherSerice", "securityService", "i18nService", "entityClazz", "idClazz")
		.doesNotContainNull()
	;
	
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
	
	final var facade = new TestEntiyBaseFacade();
	
	assertThat(facade)
		.extracting(AbstractEntiyBaseFacade::getEntityClazz)
		.isNotNull()
		.isEqualTo(Department.class)
	;

	assertThat(facade)
		.extracting(AbstractEntiyBaseFacade::getIdClazz)
		.isNotNull()
		.isEqualTo(String.class)
	;
    }
    
    @Test
    @Order(3)
    @DisplayName("Test the services instances")
    void checkServicesTest() {
	
	final var facade = new TestEntiyBaseFacade();
	
	assertThat(facade)
	        .extracting(TestEntiyBaseFacade::getSecurityService)
	            .isNotNull()
	            .extracting(InterfaceSecurityService::getUserName)
	            .isEqualTo("DummyUser")
	            
	;
	
	assertThat(facade)
		.extracting(AbstractEntiyBaseFacade::getPublisherSerice)
		    .isNotNull()
		    .extracting(publisherSerice -> publisherSerice.getClass())
		    .isEqualTo(LoggerPublisherSerice.class)
        ;
	
	assertThat(facade)
		.extracting(AbstractEntiyBaseFacade::getI18nService)
		    .isNotNull()
	            .extracting(i18nService -> i18nService.getClass())
	            .isEqualTo(DefaultI18nService.class)
        ;	
		
    }

    @Test
    @Order(4)
    @DisplayName("Test check Entity Exists")    
    void checkEntityExistsTest() {
	
	final var facade = new TestEntiyBaseFacade();
	
	final var department = new Department("asfdlkd1", "Dep1", "Account");
	
	final Predicate<Department> existsEntityFunction = (dep) -> nonNull(dep);
	
	final var result = facade.checkEntityExists(existsEntityFunction, department);
	
	assertThat(result).isTrue();
    }
}
