package org.reusablecomponents.base.core.application.query.entity.nonpaged;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.application_example.application.query.entity.nonpaged.DeparmentQueryFacade;
import org.application_example.domain.Department;
import org.application_example.domain.Manager;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.executable.ExecutableValidator;

@Tag("unit")
@DisplayName("Test the EntityQueryFacade entity test, unhappy path :( ")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class EntityQueryFacadeUnhappyPathTest {

    static final ResourceBundleMessageInterpolator INTERPOLATOR = new ResourceBundleMessageInterpolator(
            new AggregateResourceBundleLocator(Arrays.asList("ValidationMessages")));

    static final ValidatorFactory VALIDATOR_FACTORY = Validation
            .byDefaultProvider()
            .configure()
            // .messageInterpolator(new ParameterMessageInterpolator())
            .messageInterpolator(INTERPOLATOR)
            .buildValidatorFactory();

    static final Validator VALIDATOR = VALIDATOR_FACTORY
            .getValidator();

    static final ExecutableValidator EXECUTABLE_VALIDATOR = VALIDATOR_FACTORY
            .getValidator().forExecutables();

    final List<Department> defaultData = new ArrayList<>();
    final DeparmentQueryFacade defaultQueryFacade = new DeparmentQueryFacade(defaultData);

    Department department01;
    Department department02;

    Manager manager;

    @BeforeAll
    void setUpAll() {
        manager = new Manager("x2", "Business Happy");
    }

    @BeforeEach
    void setUp() {
        defaultData.clear();

        department01 = new Department("x1", "Default 01", "Peopple", manager);
        department02 = new Department("x2", "Default 02", "Resource", manager);

        defaultData.addAll(List.of(department01, department02));
    }

    @AfterAll
    void tearDown() {
        // No requirements yet
        VALIDATOR_FACTORY.close();
    }

    @Test
    @Order(1)
    @DisplayName("Find by id test")
    void findByIdTest() {
        // given
        assertThatThrownBy(() -> defaultQueryFacade.findById(null))
                // then
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("The object '%s' cannot be null", "preIdIn")

        ;
    }

    @Test
    @Order(2)
    @DisplayName("Exists by id test")
    void existsByTest() {
        // given
        assertThatThrownBy(() -> defaultQueryFacade.existsById(null))
                // then
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("The object '%s' cannot be null", "preIdIn")

        ;
    }
}
