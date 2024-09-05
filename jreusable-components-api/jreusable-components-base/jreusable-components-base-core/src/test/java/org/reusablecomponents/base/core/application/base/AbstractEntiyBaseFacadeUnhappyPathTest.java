package org.reusablecomponents.base.core.application.base;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import org.application_example.application.TestEntiyBaseFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reusablecomponents.base.messaging.operation.CommandOperation;
import org.reusablecomponents.base.messaging.operation.QueryOperation;

@Tag("unit")
@DisplayName("Test the EntiyBaseFacade entity test, unhappy Path :( ")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class AbstractEntiyBaseFacadeUnhappyPathTest {

	@Test
	@Order(1)
	@DisplayName("Test the publish operation")
	void publishInvalidOperationTest() {

		// given
		final var facade = new TestEntiyBaseFacade();

		// when
		assertThatThrownBy(() -> {

			facade.publishEvent(() -> "SaveIn", () -> "SaveOut", null);

		}) // then
				.as(format("Check the null operation")) //
				.isInstanceOf(NullPointerException.class);
	}

	@Test
	@Order(2)
	@DisplayName("Test the publish operation")
	void publishInvalidDirectivesTest() {

		// given
		final var facade = new TestEntiyBaseFacade();

		final Object[] nullArray = null;

		// when
		assertThatThrownBy(() -> {

			facade.publishEvent(() -> "SaveIn", () -> "SaveOut", CommandOperation.SAVE_ENTITY, nullArray);

		}) // then
				.as(format("Check the null operation")) //
				.isInstanceOf(NullPointerException.class);
	}
}
