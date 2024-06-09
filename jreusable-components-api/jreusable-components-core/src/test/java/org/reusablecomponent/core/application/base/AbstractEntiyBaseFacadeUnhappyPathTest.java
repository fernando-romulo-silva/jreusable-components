package org.reusablecomponent.core.application.base;

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

@Tag("unit")
@DisplayName("Test the EntiyBaseFacade entity test, unhappy Path :( ")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class AbstractEntiyBaseFacadeUnhappyPathTest {
    
    
    @Test
    @Order(1)
    @DisplayName("Test the publish operation")
    void publishOperationTest() {
	
	// given
	final var facade = new TestEntiyBaseFacade();
	
	// when
	assertThatThrownBy(() -> {

	    facade.publish("SaveIn", null, null);

	}) // then
	.as(format("Check the null operation")) //
	.isInstanceOf(NullPointerException.class);
    }

}
