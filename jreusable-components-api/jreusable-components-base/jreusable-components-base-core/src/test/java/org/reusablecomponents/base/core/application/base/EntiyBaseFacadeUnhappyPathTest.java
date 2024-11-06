package org.reusablecomponents.base.core.application.base;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import org.application_example.domain.Department;
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
class EntiyBaseFacadeUnhappyPathTest {

        @Test
        @Order(1)
        @DisplayName("Test the constructor values")
        void constructorValuesTest() {

                assertThatThrownBy(() -> new EntiyBaseFacade<Department, String>(null))
                                // then
                                .isInstanceOf(NullPointerException.class)
                                .hasMessageContaining("The object 'builder' cannot be null");

        }
}
