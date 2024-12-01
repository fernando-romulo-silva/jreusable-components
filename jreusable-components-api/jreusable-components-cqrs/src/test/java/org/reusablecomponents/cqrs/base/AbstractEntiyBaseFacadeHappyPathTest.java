package org.reusablecomponents.cqrs.base;

import static java.lang.System.out;
import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import org.apache.commons.lang3.concurrent.ConcurrentUtils;
import org.application_example.infra.LoggerPublisherSerice;
import org.apptest.domain.Guest;
import org.apptest.domain.Hotel;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reusablecomponents.base.core.application.base.BaseFacade;
import org.reusablecomponents.base.core.application.empty.EmptyFacade;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;
import org.reusablecomponents.base.translation.JavaSEI18nService;
import org.reusablecomponents.messaging.InterfaceEventPublisherSerice;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

@Disabled
@Tag("unit")
@DisplayName("Test the EntiyBaseFacade entity test, happy Path :) ")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class AbstractEntiyBaseFacadeHappyPathTest {

	@Test
	@Order(1)
	@DisplayName("Test the constructor values")
	void constructorValuesTest() {

		// given
		final InterfaceEventPublisherSerice<String> publisherService = event -> {
			out.println(event);
			return ConcurrentUtils.constantFuture(event);
		};

		final InterfaceI18nService i18nService = (code, params) -> "translated!";
		final InterfaceSecurityService interfaceSecurityService = null; // new DummySecurityService();
		final InterfaceExceptionAdapterService exceptionTranslatorService = null; // (ex, i18n, directives) -> new
																					// UnexpectedException(ex);

		// when
		final BaseFacade facade = null;
		// new TestEntiyBaseFacade(
		// publisherService,
		// i18nService,
		// interfaceSecurityService,
		// exceptionTranslatorService);

		// then
		assertThat(facade)
				.as(format("Check the publisherService, securityService, and i18nService are not null"))
				.extracting("publisherService", "securityService", "i18nService", "entityClazz", "idClazz")
				.doesNotContainNull();

		// then
		assertThat(facade)
				.extracting("entityClazz", "idClazz")
				.as(format("Check the entityClazz ''{0}'' and idClazz ''{1}''",
						Guest.class, String.class))
				.containsExactly(Guest.class, String.class);
	}

	@Test
	@Order(2)
	@DisplayName("Test the get values")
	void checkValuesTest() {

		// given
		final BaseFacade<Guest, Long> facade = null; // new TestEntiyBaseFacade();

		assertThat(facade)
				// when
				.extracting(BaseFacade::getEntityClazz)
				// then
				.isNotNull()
				.isEqualTo(Guest.class);

		assertThat(facade)
				// when
				.extracting(BaseFacade::getIdClazz)
				// then
				.isNotNull()
				.isEqualTo(Long.class);
	}

	@Test
	@Order(3)
	@DisplayName("Test the services instances")
	void checkServicesTest() {

		// given
		final BaseFacade<Guest, Long> facade = null;

		assertThat(facade)
				// when
				.extracting(
						BaseFacade::getSecurityService)
				// then
				.isNotNull()
				.extracting(InterfaceSecurityService::getUserName)
				.isEqualTo("fernando");

		// assertThat(facade)
		// // when
		// .extracting(BaseFacade::getPublisherService)
		// // then
		// .isNotNull();

		assertThat(facade)
				// when
				.extracting(BaseFacade::getI18nService)
				// then
				.isNotNull()
				.extracting(i18nService -> i18nService.getClass())
				.isEqualTo(JavaSEI18nService.class);

		assertThat(facade)
				// when
				.extracting(BaseFacade::getExceptionTranslatorService)
				// then
				.isNotNull();
	}

	@Test
	@Order(4)
	@DisplayName("Test if publish operation")
	void publishOperationTest() {

		// given
		final var listAppender = new ListAppender<ILoggingEvent>();
		listAppender.start();

		final var facadeLogger = (Logger) LoggerFactory.getLogger(LoggerPublisherSerice.class);
		facadeLogger.addAppender(listAppender);

		final EmptyFacade<Guest, Long> facade = null;

		// when
		// facade.publishEvent(() -> "SaveIn", () -> null, SAVE_ENTITY);

		// then
		assertThat(listAppender.list)
				.extracting(ILoggingEvent::getFormattedMessage)
				.first()
				.asInstanceOf(InstanceOfAssertFactories.STRING)
				.contains("Publish event")
				.contains("\"what\":{\"dataIn\":\"SaveIn\",\"dataOut\":\"\"}");
	}

	@Test
	@Order(5)
	@DisplayName("Test if ignore publish operation")
	void ignorePublishOperationTest() {

		// given
		final var listAppender = new ListAppender<ILoggingEvent>();
		listAppender.start();

		final var publishServiceLogger = (Logger) LoggerFactory.getLogger(LoggerPublisherSerice.class);
		final var facadeLogger = (Logger) LoggerFactory.getLogger(BaseFacade.class);

		publishServiceLogger.addAppender(listAppender);
		facadeLogger.addAppender(listAppender);

		final BaseFacade facade = null;

		// when
		// facade.publishEvent(() -> "SaveIn", () -> "SaveOut", SAVE_ENTITY);

		// then
		assertThat(listAppender.list)
				.extracting(ILoggingEvent::getFormattedMessage, ILoggingEvent::getLevel)
				.doesNotContain(tuple("Publish event [in:SaveIn],[out:SaveOut]", Level.DEBUG))
				.containsExactly(
						tuple("Publishing event with operation 'SAVE_ENTITY'", Level.DEBUG),
						tuple("Event publishing with operation 'SAVE_ENTITY' avoided", Level.DEBUG));

	}

	@Test
	@Order(6)
	@DisplayName("Test if ignore publish operation by directives")
	void ignorePublishByDirectivesOperationTest() {

		// given
		final var listAppender = new ListAppender<ILoggingEvent>();
		listAppender.start();

		final var publishServiceLogger = (Logger) LoggerFactory.getLogger(LoggerPublisherSerice.class);
		final var facadeLogger = (Logger) LoggerFactory.getLogger(BaseFacade.class);

		publishServiceLogger.addAppender(listAppender);
		facadeLogger.addAppender(listAppender);

		final Hotel project = null; // new Hotel(1L, "Project 1", null);

		assertThat(project)
				.extracting(
						Hotel::isPublishable)
				.isEqualTo(Boolean.FALSE);

		final var facade = new EmptyFacade<>() {

			// @Override
			// protected boolean isPublishEvents(final Object... directives) {

			// return Stream.of(directives)
			// .filter(p -> p instanceof AbstractEntity)
			// .map(p -> AbstractEntity.class.cast(p))
			// .allMatch(e -> e.isPublishable());
			// }
		};

		// when
		// facade.publishEvent(() -> "SaveIn", () -> "SaveOut", SAVE_ENTITY, project);

		// then
		assertThat(listAppender.list)
				.extracting(ILoggingEvent::getFormattedMessage, ILoggingEvent::getLevel)
				.doesNotContain(tuple("Publish event [in:SaveIn],[out:SaveOut]", Level.DEBUG))
				.containsExactly(
						tuple("Publishing event with operation 'SAVE_ENTITY'", Level.DEBUG),
						tuple("Event publishing with operation 'SAVE_ENTITY' avoided", Level.DEBUG));

	}
}
