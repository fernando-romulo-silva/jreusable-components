package org.reusablecomponents.cqrs.base;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import org.apptest.domain.Guest;
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
import org.reusablecomponents.base.core.application.empty.EmptyFacade;

@Disabled
@Tag("unit")
@DisplayName("Test the EntiyBaseFacade entity test, unhappy Path :( ")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@SuppressWarnings("null")
class AbstractEntiyBaseFacadeUnhappyPathTest {

	@Test
	@Order(1)
	@DisplayName("Test the publish operation")
	void publishInvalidOperationTest() {

		// given
		final EmptyFacade<Guest, Long> facade = null;

		// when
		assertThatThrownBy(() -> {

			// facade.publishEvent(() -> "SaveIn", () -> "SaveOut", null);

		}) // then
				.as(format("Check the null operation")) //
				.isInstanceOf(NullPointerException.class);
	}

	@Test
	@Order(2)
	@DisplayName("Test the publish operation")
	void publishInvalidDirectivesTest() {

		// given
		final EmptyFacade<Guest, Long> facade = null;

		final Object[] nullArray = null;

		// when
		assertThatThrownBy(() -> {

			// facade.publishEvent(() -> "SaveIn", () -> "SaveOut",
			// CommandOperation.SAVE_ENTITY, nullArray);

		}) // then
				.as(format("Check the null operation")) //
				.isInstanceOf(NullPointerException.class);
	}
}
