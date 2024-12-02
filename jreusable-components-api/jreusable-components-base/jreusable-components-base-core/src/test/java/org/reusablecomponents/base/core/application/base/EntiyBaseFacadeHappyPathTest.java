package org.reusablecomponents.base.core.application.base;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
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
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.core.infra.exception.common.BaseApplicationException;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;

@Tag("unit")
@DisplayName("Test the EntiyBaseFacade entity test, happy Path :) ")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class EntiyBaseFacadeHappyPathTest {
	static class GenericError extends BaseApplicationException {

		public GenericError(final Exception exception) {
			super("Generic error", exception);
		}
	}

	final InterfaceI18nService i18nService = (code, params) -> "translated!";
	final InterfaceSecurityService interfaceSecurityService = new DummySecurityService();
	final InterfaceExceptionAdapterService exceptionTranslatorService = (ex, i18n,
			directives) -> new GenericError(ex);

	@Test
	@Order(1)
	@DisplayName("Test the constructor values")
	void constructorValuesTest() {

		// given
		final var myExceptionTranslatorService = exceptionTranslatorService;

		// when
		final var facade = new TestEntiyBaseFacade(i18nService, interfaceSecurityService, myExceptionTranslatorService);

		// then
		assertThat(facade)
				.as(format("Check the securityService, and i18nService are not null"))
				.extracting("securityService", "i18nService", "entityClazz", "idClazz")
				.doesNotContainNull();

		// then
		assertThat(facade)
				.extracting("entityClazz", "idClazz")
				.as(format("Check the entityClazz ''{0}'' and idClazz ''{1}''", Department.class, String.class))
				.containsExactly(Department.class, String.class);
	}

	@Test
	@Order(2)
	@DisplayName("Test the get values")
	void checkValuesTest() {

		// given
		final var facade = new TestEntiyBaseFacade(i18nService, interfaceSecurityService,
				exceptionTranslatorService);

		assertThat(facade)
				// when
				.extracting(TestEntiyBaseFacade::getEntityClazz)
				// then
				.isNotNull()
				.isEqualTo(Department.class);

		assertThat(facade)
				// when
				.extracting(BaseFacade::getIdClazz)
				// then
				.isNotNull()
				.isEqualTo(String.class);
	}

	@Test
	@Order(3)
	@DisplayName("Test the services instances")
	void checkServicesTest() {

		// given
		final var myDummySecurityService = new DummySecurityService() {
			@Override
			public String getUserName() {
				return "Fernando";
			}
		};

		final var facade = new TestEntiyBaseFacade(i18nService, myDummySecurityService, exceptionTranslatorService);

		assertThat(facade)
				// when
				.extracting(TestEntiyBaseFacade::getSecurityService)
				// then
				.isNotNull()
				.extracting(InterfaceSecurityService::getUserName)
				.isEqualTo("Fernando");

		assertThat(facade)
				// when
				.extracting(TestEntiyBaseFacade::getExceptionTranslatorService)
				// then
				.isNotNull();
	}
}
