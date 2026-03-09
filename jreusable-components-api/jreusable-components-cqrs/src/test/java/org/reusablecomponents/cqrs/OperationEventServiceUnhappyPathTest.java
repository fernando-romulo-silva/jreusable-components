package org.reusablecomponents.cqrs;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.custom.CustomOperation1Args;
import org.reusablecomponents.base.security.DefaultSecurityService;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;
import org.reusablecomponents.base.translation.JavaSEI18nService;
import org.reusablecomponents.messaging.InterfaceEventPublisherSerice;
import org.reusablecomponents.messaging.event.EventStatus;
import org.reusablecomponents.messaging.log.LoggerPublisherSerice;

@Tag("unit")
@DisplayName("Test the EntiyBaseFacade entity test, unhappy Path :( ")
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@SuppressWarnings("null")
class OperationEventServiceUnhappyPathTest {

	final InterfaceEventPublisherSerice<?> publisherService = new LoggerPublisherSerice();
	final InterfaceI18nService i18nService = new JavaSEI18nService();
	final InterfaceSecurityService securityService = new DefaultSecurityService();

	static class MyOperation implements CustomOperation1Args<Object[], String> {

		@Override
		public String apply(final Object[] directives) {
			return "operationResult";
		}
	}

	// given
	Stream<Arguments> createInvalidTestData() {
		return Stream.of(
				Arguments.of(null, new JavaSEI18nService(), new DefaultSecurityService(), "publisherService"),
				Arguments.of(new LoggerPublisherSerice(), null, new DefaultSecurityService(), "i18nService"),
				Arguments.of(new LoggerPublisherSerice(), new JavaSEI18nService(), null, "securityService"));
	}

	@Order(1)
	@DisplayName("Test the publish operation")
	@ParameterizedTest(name = "{index} => publisherService={0}, i18nService={1}, securityService={2}, msg={3}")
	@MethodSource("createInvalidTestData")
	void createInvalidTest(
			// given
			final InterfaceEventPublisherSerice<?> publisherService,
			final InterfaceI18nService i18nService,
			final InterfaceSecurityService securityService,
			final String field) {

		// when
		assertThatThrownBy(() -> new OperationEventService(publisherService, i18nService, securityService))
				// then
				.isInstanceOf(NullPointerException.class)
				.hasMessageContaining(format("The object ''{0}'' cannot be null", field));
	}

	Stream<Arguments> createEventTestData() {
		return Stream.of(
				Arguments.of("dataIn", "dataOut", "origin", null, new MyOperation(), "status"),
				Arguments.of("dataIn", "dataOut", "origin", EventStatus.SUCCESS, null, "operation"));
	}

	@Order(2)
	@ParameterizedTest(name = "{index} => dataIn={0}, dataOut={1}, origin={2}, status={3}, operation={4}, field={5}")
	@MethodSource("createEventTestData")
	@DisplayName("Test the publish operation")
	void createEventTest(
			final String dataIn,
			final String dataOut,
			final String origin,
			final EventStatus status,
			final OperationFunction operation,
			final String field) {
		// given
		final var operationEventService = new OperationEventService(publisherService, i18nService, securityService);

		// when
		assertThatThrownBy(() -> {
			operationEventService.createEvent(dataIn, dataOut, origin, status, operation);

		}) // then
				.as(format("Check the null operation")) //
				.hasMessageContaining(format("The object ''{0}'' cannot be null", field));
	}

	@Test
	void publishEventObjectTest() {
		// given
		final var operationEventService = new OperationEventService(publisherService, i18nService, securityService);

		// when
		assertThatThrownBy(() -> operationEventService.publishEvent(null))
				// then
				.as(format("Check the null event")) //
				.hasMessageContaining("The object 'event' cannot be null");
	}

	@Order(2)
	@ParameterizedTest(name = "{index} => dataIn={0}, dataOut={1}, origin={2}, status={3}, operation={4}, field={5}")
	@MethodSource("createEventTestData")
	@DisplayName("Test the publish operation")
	void publishEventParamTest(
			final String dataIn,
			final String dataOut,
			final String origin,
			final EventStatus status,
			final OperationFunction operation,
			final String field) {

		// given
		final var operationEventService = new OperationEventService(publisherService, i18nService, securityService);

		// when
		assertThatThrownBy(() -> {
			operationEventService.publishEvent(dataIn, dataOut, origin, status, operation);

		}) // then
				.as(format("Check the null operation")) //
				.hasMessageContaining(format("The object ''{0}'' cannot be null", field));
	}
}
