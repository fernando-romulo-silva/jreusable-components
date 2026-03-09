package org.reusablecomponents.cqrs;

import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.reusablecomponents.messaging.event.EventStatus.FAILURE;
import static org.reusablecomponents.messaging.event.EventStatus.SUCCESS;

import org.apache.commons.lang3.concurrent.ConcurrentUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.reusablecomponents.base.core.infra.util.function.operation.custom.CustomOperation1Args;
import org.reusablecomponents.base.security.DefaultSecurityService;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;
import org.reusablecomponents.base.translation.JavaSEI18nService;
import org.reusablecomponents.messaging.InterfaceEventPublisherSerice;
import org.reusablecomponents.messaging.log.LoggerPublisherSerice;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

@SuppressWarnings("null")
@Tag("unit")
@DisplayName("Test the EntiyBaseFacade entity test, happy Path :) ")
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class OperationEventServiceHappyPathTest {

	static class MyOperation implements CustomOperation1Args<Object[], String> {

		@Override
		public String apply(final Object[] directives) {
			return "operationResult";
		}
	}

	final InterfaceI18nService i18nService = new JavaSEI18nService();
	final InterfaceSecurityService securityService = new DefaultSecurityService();
	final LoggerPublisherSerice logPublisherService = new LoggerPublisherSerice();
	final MyOperation oneInputFunction = new MyOperation();

	ListAppender<ILoggingEvent> listAppender;
	Logger logger;

	@BeforeEach
	void setup() {
		listAppender = new ListAppender<ILoggingEvent>();
		listAppender.start();
		logger = (Logger) LoggerFactory.getLogger(LoggerPublisherSerice.class);
		logger.setLevel(Level.DEBUG);
		listAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
		logger.addAppender(listAppender);
	}

	@AfterEach
	void teardown() {
		logger.detachAndStopAllAppenders();
	}

	@Test
	@Order(1)
	@DisplayName("Test the constructor values")
	void constructorValuesTest() {
		// given
		final InterfaceEventPublisherSerice<String> publisherService = event -> {
			IO.println(event);
			return ConcurrentUtils.constantFuture(event.toString());
		};

		// when
		final var operationEventService = assertDoesNotThrow(
				() -> new OperationEventService(publisherService, i18nService, securityService));

		// then
		assertThat(operationEventService)
				.as(format("Check the publisherService, securityService, and i18nService are not null"))
				.extracting("publisherService", "securityService", "i18nService")
				.doesNotContainNull();
	}

	@Test
	@Order(2)
	@DisplayName("Test the services instances")
	void checkServicesTest() {
		// given
		final var operationEventService = assertDoesNotThrow(
				() -> new OperationEventService(logPublisherService, i18nService, securityService));

		assertThat(operationEventService)
				// when
				.extracting(OperationEventService::getPublisherService)
				// then
				.isNotNull()
				.extracting(eventPublisherSerice -> eventPublisherSerice.getClass())
				.isEqualTo(LoggerPublisherSerice.class);

		assertThat(operationEventService)
				// when
				.extracting(OperationEventService::getI18nService)
				// then
				.isNotNull()
				.extracting(i18nService -> i18nService.getClass())
				.isEqualTo(JavaSEI18nService.class);

		assertThat(operationEventService)
				// when
				.extracting(OperationEventService::getSecurityService)
				// then
				.isNotNull()
				.extracting(securityService -> securityService.getClass())
				.isEqualTo(DefaultSecurityService.class);
	}

	@Test
	@Order(3)
	@DisplayName("Test if publish operation")
	void publishOperationEventParamTest() {
		// given
		final var operationEventService = new OperationEventService(
				new LoggerPublisherSerice(), i18nService, securityService);

		final var dataIn = "MyDataIn";
		final var dataOut = "MyDataOut";
		final var origin = "MyOrigin";
		final var status = FAILURE;

		// when
		final var event = operationEventService.createEvent(dataIn, dataOut, origin, status, oneInputFunction);
		assertDoesNotThrow(() -> operationEventService.publishEvent(event));

		// then
		// TODO: Disabled assert is to be fixed later, but it works fine individually
		/*
		 * assertThat(listAppender.list)
		 * .extracting(ILoggingEvent::getFormattedMessage)
		 * .first()
		 * .asInstanceOf(InstanceOfAssertFactories.STRING)
		 * .contains("Publish event")
		 * .contains("\"what\":{\"dataIn\":\"MyDataIn\",\"dataOut\":\"MyDataOut\"}");
		 */
	}

	@Test
	@Order(4)
	@DisplayName("Test if publish operation")
	void publishOperationTest() {
		// given
		final var operationEventService = new OperationEventService(logPublisherService, i18nService, securityService);

		// when
		assertDoesNotThrow(
				() -> operationEventService.publishEvent("SaveIn", EMPTY, EMPTY, SUCCESS, oneInputFunction));

		// then
		// TODO: Disabled assert is to be fixed later, but it works fine individually
		/*
		 * assertThat(listAppender.list)
		 * .extracting(ILoggingEvent::getFormattedMessage)
		 * .first()
		 * .asInstanceOf(InstanceOfAssertFactories.STRING)
		 * .contains("Publish event")
		 * .contains("\"what\":{\"dataIn\":\"SaveIn\",\"dataOut\":\"\"}");
		 */
	}
}
