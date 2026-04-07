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
    @DisplayName("Given id when find by id then return entity test")
    void givenId_whenFindById_thenReturnEntity() {
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
    @DisplayName("Given no criteria when find all then return all entities test")
    void givenNoCriteria_whenFindAll_thenReturnAllEntities() {
        // given
        final var currentData = defaultData;

        // when
        final var result = assertDoesNotThrow(() -> defaultQueryFacade.findAll());

        // then
        assertThat(currentData).containsAll(result);
    }

    @Test
    @Order(3)
    @DisplayName("Given no criteria when count all then return count all entities test")
    void givenNoCriteria_whenCountAll_thenReturnCountAllEntities() {
        // given
        final var currentSize = defaultData.size();

        // when
        final var result = assertDoesNotThrow(() -> defaultQueryFacade.countAll().intValue());

        // then
        assertThat(currentSize).isEqualTo(result);
    }

    @Test
    @Order(4)
    @DisplayName("Given no criteria when exists all then return if exists any entities test")
    void givenNoCriteria_whenExistsAll_thenReturnIfExistsAnyEntities() {
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
    @DisplayName("Given id when exists by id then return if exists entity test")
    void givenId_whenExistsById_thenReturnIfExistsEntity() {
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
    void givenNullId_whenFindById_thenThrowNullPointerException() {

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
    void givenNullId_whenFindByIdWithBeanValidation_thenThrowConstraintViolationException() throws Exception {

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
    @DisplayName("Given id not found when find by id then throw ElementWithIdNotFoundException test")
    void givenIdNotFound_whenFindById_thenThrowElementWithIdNotFoundException() {

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
    @DisplayName("Given unexpected error when find by id then throw UnexpectedException test")
    void givenUnexpectedError_whenFindById_thenThrowUnexpectedException() {

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
    @DisplayName("Given unexpected error when find all then throw UnexpectedException test")
    void givenUnexpectedError_whenFindAll_thenThrowUnexpectedException() {

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
    @DisplayName("Given unexpected error when count all then throw UnexpectedException test")
    void givenUnexpectedError_whenCountAll_thenThrowUnexpectedException() {

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
    @DisplayName("Given unexpected error when exist all then throw UnexpectedException test")
    void givenUnexpectedError_whenExistAll_thenThrowUnexpectedException() {

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
    @DisplayName("Given null id when exists by id then throw NullPointerException test")
    void givenNullId_whenExistsById_thenThrowNullPointerException() {

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
    @DisplayName("Given null id when exists by id then throw NullPointerException with bean validation test")
    void givenNullId_whenExistsById_thenThrowNullPointerExceptionWithBeanValidation() throws Exception {

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
    @DisplayName("Given unexpected error when exists by id then throw UnexpectedException test")
    void givenUnexpectedError_whenExistsById_thenThrowUnexpectedException() {

        // given
        final var directives = new Object[] { "error" };

        // given
        assertThatThrownBy(() -> defaultQueryFacade.existsById("some Id", directives))
                // then
                .isInstanceOf(UnexpectedException.class)
                .hasMessageContaining("Unexpecte error happened");
    }

}
