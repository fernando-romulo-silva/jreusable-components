package org.reusablecomponents.base.core.application.query.entity.simple;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
import org.reusablecomponents.base.core.infra.exception.common.ElementWithIdNotFoundException;
import org.reusablecomponents.base.core.infra.exception.common.UnexpectedException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.executable.ExecutableValidator;

@Tag("unit")
@DisplayName("Test the EntityQueryFacade entity test")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@SuppressWarnings("null")
class QueryFacadeTest {

    static final ResourceBundleMessageInterpolator INTERPOLATOR = new ResourceBundleMessageInterpolator(
            new AggregateResourceBundleLocator(Arrays.asList("ValidationMessages")));

    static final ValidatorFactory VALIDATOR_FACTORY = Validation
            .byDefaultProvider()
            .configure()
            // .messageInterpolator(new ParameterMessageInterpolator())
            .messageInterpolator(INTERPOLATOR)
            .buildValidatorFactory();

    static final Validator VALIDATOR = VALIDATOR_FACTORY.getValidator();

    static final ExecutableValidator EXECUTABLE_VALIDATOR = VALIDATOR.forExecutables();

    final List<Department> defaultData = new ArrayList<>();
    final DeparmentQueryFacade defaultQueryFacade = new DeparmentQueryFacade(defaultData);

    Department department01;
    Department department02;
    Department department03;

    Manager manager;

    @BeforeAll
    void setUpAll() {
        manager = new Manager("x2", "Business Happy");
    }

    @BeforeEach
    void setUp() {
        defaultData.clear();

        manager = new Manager("x2", "Business Happy");

        department01 = new Department("x1", "Default 01", "Peopple", manager);
        department02 = new Department("x2", "Default 02", "Resource", manager);
        department03 = new Department("x3", "Default 03", "IT", manager);

        defaultData.addAll(List.of(department01, department02, department03));
    }

    @AfterAll
    void tearDown() {
        // No requirements yet
    }

    @Test
    @Order(1)
    @DisplayName("Find by id test")
    void findByIdTest() {
        // given
        final var id = "x1";

        // when
        final var result = assertDoesNotThrow(() -> defaultQueryFacade.findById(id));

        // then
        assertThat(department01).isEqualTo(result);
        assertThat(defaultData).contains(result);
        assertThat(result).extracting("operation").isEqualTo(0);
    }

    @Test
    @Order(2)
    @DisplayName("Find all test")
    void findAllTest() {
        // given
        final var currentData = defaultData;

        // when
        final var result = assertDoesNotThrow(() -> defaultQueryFacade.findAll());

        // then
        assertThat(currentData).containsAll(result);
    }

    @Test
    @Order(3)
    @DisplayName("Count all test")
    void countAllTest() {
        // given
        final var currentSize = defaultData.size();

        // when
        final var result = assertDoesNotThrow(() -> defaultQueryFacade.countAll().intValue());

        // then
        assertThat(currentSize).isEqualTo(result);
    }

    @Test
    @Order(4)
    @DisplayName("Exists all test")
    void existsAllTest() {
        // given
        final var existsAll = defaultData.size() > 0;

        // when
        final var result = assertDoesNotThrow(() -> defaultQueryFacade.existsAll());

        // then
        assertThat(existsAll)
                .isEqualTo(result)
                .matches(e -> {
                    defaultData.clear();
                    return defaultQueryFacade.existsAll() != existsAll;
                });
    }

    @Test
    @Order(5)
    @DisplayName("Exists by id test")
    void existsByIdTest() {
        // given
        final var id = "x1";

        // when
        final var result = assertDoesNotThrow(() -> defaultQueryFacade.existsById(id));

        // then
        assertThat(result)
                .isTrue()
                .matches(e -> defaultQueryFacade.existsById("whatever") != result);
    }

    @Test
    @Order(6)
    @DisplayName("Find by id with null id test")
    void findByIdWithNullIdTest() {

        // given
        final String nullId = null;

        // when
        assertThatThrownBy(() -> defaultQueryFacade.findById(nullId))
                // then
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Please pass a non-null %s", "Department id");
    }

    @Test
    @Order(7)
    @DisplayName("Find by id with null id bean validation test")
    void findByIdWithNullIdBeanValidationTest() throws NoSuchMethodException, SecurityException {

        final var method = defaultQueryFacade.getClass()
                .getMethod("findById", Object.class, Object[].class);

        final var violations = EXECUTABLE_VALIDATOR
                .validateParameters(defaultQueryFacade, method, new Object[] { null, new Object[] {} });

        assertThat(violations)
                .hasSize(1)
                .extracting(t -> t.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("findById.arg0", "The object cannot be null"));
    }

    @Test
    @Order(8)
    @DisplayName("Find by id test with id not found test")
    void findByIdWithIdNotFoundTest() {

        // given
        final var id = "notExists";

        // when
        assertThatThrownBy(() -> defaultQueryFacade.findById(id))
                // then
                .isInstanceOf(ElementWithIdNotFoundException.class)
                .hasMessageContaining("The id 'notExists' not found for 'Department' type");
    }

    @Test
    @Order(9)
    @DisplayName("Find by id test with unexpected error test")
    void findByIdWithUnexpectedErrorTest() {

        // given
        final var directives = new Object[] { "error" };
        final var queryIdIn = "3434j3";

        // when
        assertThatThrownBy(() -> defaultQueryFacade.findById(queryIdIn, directives))
                // then
                .isInstanceOf(UnexpectedException.class)
                .hasMessageContaining("Unexpecte error happened");
    }

    @Test
    @Order(10)
    @DisplayName("Find all test with unexpected error test")
    void findAllWithUnexpectedErrorTest() {

        // given
        final var directives = new Object[] { "error" };

        // when
        assertThatThrownBy(() -> defaultQueryFacade.findAll(directives))
                // then
                .isInstanceOf(UnexpectedException.class)
                .hasRootCauseInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Unexpecte error happened");
    }

    @Test
    @Order(11)
    @DisplayName("Count all with unexpected error test")
    void countAllWithUnexpectedErrorTest() {

        // given
        final var directives = new Object[] { "error" };

        // when
        assertThatThrownBy(() -> defaultQueryFacade.countAll(directives))
                // then
                .isInstanceOf(UnexpectedException.class)
                .hasMessageContaining("Unexpecte error happened");
    }

    @Test
    @Order(12)
    @DisplayName("Exist all with unexpected error test")
    void existAllWithUnexpectedErrorTest() {

        // given
        final var directives = new Object[] { "error" };

        // when
        assertThatThrownBy(() -> defaultQueryFacade.existsAll(directives))
                // then
                .isInstanceOf(UnexpectedException.class)
                .hasMessageContaining("Unexpecte error happened");
    }

    @Test
    @Order(13)
    @DisplayName("Exists by id with null id test")
    void existsByIdWithNullIdTest() {

        // given
        final String nullId = null;

        // given
        assertThatThrownBy(() -> defaultQueryFacade.existsById(nullId))
                // then
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Please pass a non-null %s id", "Department");
    }

    @Test
    @Order(14)
    @DisplayName("Exists by id with null id bean validation test")
    void existsByIdWithNullIdBeanValidationTest() throws NoSuchMethodException, SecurityException {

        final var method = defaultQueryFacade.getClass()
                .getMethod("existsById", Object.class, Object[].class);

        final var violations = EXECUTABLE_VALIDATOR
                .validateParameters(defaultQueryFacade, method, new Object[] { null, new Object[] {} });

        assertThat(violations)
                .hasSize(1)
                .extracting(t -> t.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("existsById.arg0", "The object cannot be null"));
    }

    @Test
    @Order(15)
    @DisplayName("Exists by id with unexpected error test")
    void existsByIdWithUnexpectedErrorTest() {

        // given
        final var directives = new Object[] { "error" };

        // given
        assertThatThrownBy(() -> defaultQueryFacade.existsById("some Id", directives))
                // then
                .isInstanceOf(UnexpectedException.class)
                .hasMessageContaining("Unexpecte error happened");
    }

}
