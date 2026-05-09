package org.reusablecomponents.base.core.application.query.entity.specification;

import static org.apache.commons.lang3.Strings.CI;
import static org.apache.commons.lang3.Strings.CS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.lang3.math.NumberUtils;
import org.application_example.application.query.entity.nonpaged.DeparmentQuerySpecificationFacade;
import org.application_example.domain.Department;
import org.application_example.domain.Manager;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reusablecomponents.base.core.infra.exception.common.NoResultFoundException;
import org.reusablecomponents.base.core.infra.exception.common.UnexpectedException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.executable.ExecutableValidator;

@Tag("unit")
@DisplayName("Test the EntityQuerySpecificationFacade entity test")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@SuppressWarnings("null")
class QuerySpecificationFacadeTest {

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
    final DeparmentQuerySpecificationFacade defaultQueryFacade = new DeparmentQuerySpecificationFacade(defaultData);

    Department department01;
    Department department02;
    Department department03;

    Manager manager;

    @BeforeEach
    void setUp() {
        defaultData.clear();

        manager = new Manager("x2", "Business Manager");

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
    @DisplayName("Find one by specification test")
    void given_findOneBySpecification_when_specMatches_then_returnResult() {
        // given
        final Predicate<Department> spec = department -> CI.contains(department.getName(), "Default 01");

        // when
        final var result = assertDoesNotThrow(() -> defaultQueryFacade.findOneBySpecification(spec));

        // then
        assertThat(department01).isEqualTo(result);
        assertThat(defaultData).contains(result);
        assertThat(result).extracting("operation").isEqualTo(0);
    }

    @Test
    @Order(2)
    @DisplayName("Find by specification test")
    void given_findBySpecification_when_specMatches_then_returnResults() {
        // given
        final Predicate<Department> spec01 = department -> CI.contains(department.getName(), "Default");
        final Predicate<Department> spec02 = department -> CI.contains(department.getName(), "Whatever");

        // when
        final var result = assertDoesNotThrow(() -> defaultQueryFacade.findBySpecification(spec01));

        // then
        assertThat(result)
                .containsAll(defaultData)
                .matches(e -> defaultQueryFacade.findBySpecification(spec02).isEmpty());
    }

    @Test
    @Order(3)
    @DisplayName("Count by specification test")
    void given_countBySpecification_when_specMatches_then_returnCount() {
        // given
        final Predicate<Department> spec = department -> CI.equals(department.getName(), "Default 01");

        // when
        final var result = assertDoesNotThrow(() -> defaultQueryFacade.countBySpecification(spec));

        // then
        assertThat(result).isEqualTo(NumberUtils.LONG_ONE);
    }

    @Test
    @Order(4)
    @DisplayName("Exists all test")
    void given_existsBySpecification_when_specMatches_then_returnResult() {
        // given
        final Predicate<Department> spec01 = department -> CI.equals(department.getName(), "Default 01");
        final Predicate<Department> spec02 = department -> CI.equals(department.getName(), "Whatever");

        final var existsAll = defaultData.size() > 0;

        // when
        final var result = assertDoesNotThrow(() -> defaultQueryFacade.existsBySpecification(spec01));

        // then
        assertThat(existsAll)
                .isEqualTo(result)
                .matches(e -> defaultQueryFacade.existsBySpecification(spec02) != existsAll);
    }

    @Test
    @Order(5)
    @DisplayName("Find one by specification with null specification test")
    void given_findOneBySpecification_when_specIsNull_then_throwException() {

        // given
        final Predicate<Department> specNull = null;

        // when
        assertThatThrownBy(() -> defaultQueryFacade.findOneBySpecification(specNull))
                // then
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Please pass a non-null '%s'", "in");
    }

    @Test
    @Order(6)
    @DisplayName("Find one by specification with specification not found test")
    void given_findOneBySpecification_when_specNotFound_then_throwException() {

        // given
        final Predicate<Department> spec = department -> CI.equals(department.getName(), "Whatever");

        // when
        assertThatThrownBy(() -> defaultQueryFacade.findOneBySpecification(spec))
                // then
                .isInstanceOf(NoResultFoundException.class)
                .hasMessageContaining("No result found");
    }

    @Test
    @Order(7)
    @DisplayName("Find one by specification with unexpected error test")
    void given_findOneBySpecification_when_unexpectedError_then_throwException() {

        // given
        final Predicate<Department> spec = department -> CS.equals(department.getName(), "Default 01");
        final var directives = new Object[] { "error" };

        // when
        assertThatThrownBy(() -> defaultQueryFacade.findOneBySpecification(spec, directives))
                // then
                .isInstanceOf(UnexpectedException.class)
                .hasMessageContaining("Unexpected error happened");
    }

    @Test
    @Order(8)
    @DisplayName("Find by specification with null specification test")
    void given_findBySpecification_when_specIsNull_then_throwException() {

        // given
        final Predicate<Department> specNull = null;

        // when
        assertThatThrownBy(() -> defaultQueryFacade.findBySpecification(specNull))
                // then
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Please pass a non-null '%s'", "in");
    }

    @Test
    @Order(9)
    @DisplayName("Find by specification with unexpected error test")
    void given_findBySpecification_when_unexpectedError_then_throwException() {

        // given
        final Predicate<Department> spec = department -> CS.equals(
                department.getName(), "Department 01");
        final var directives = new Object[] { "error" };

        // when
        assertThatThrownBy(() -> defaultQueryFacade.findBySpecification(spec, directives))
                // then
                .isInstanceOf(UnexpectedException.class)
                .hasMessageContaining("Unexpected error happened");
    }

    @Test
    @Order(10)
    @DisplayName("Count by specification with null specification test")
    void given_countBySpecification_when_specIsNull_then_throwException() {

        // given
        final Predicate<Department> specNull = null;

        // when
        assertThatThrownBy(() -> defaultQueryFacade.countBySpecification(specNull))
                // then
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Please pass a non-null '%s'", "in");
    }

    @Test
    @Order(11)
    @DisplayName("Count by specification with unexpected error test")
    void given_countBySpecification_when_unexpectedError_then_throwException() {

        // given
        final Predicate<Department> spec = department -> CS.equals(
                department.getName(), "Department 01");
        final var directives = new Object[] { "error" };

        // when
        assertThatThrownBy(() -> defaultQueryFacade.countBySpecification(spec, directives))
                // then
                .isInstanceOf(UnexpectedException.class)
                .hasMessageContaining("Unexpected error happened");
    }

    @Test
    @Order(12)
    @DisplayName("Exists by specification with null spec test")
    void given_existsBySpecification_when_specIsNull_then_throwException() {

        // given
        final Predicate<Department> specNull = null;

        // when
        assertThatThrownBy(() -> defaultQueryFacade.existsBySpecification(specNull))
                // then
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Please pass a non-null '%s'", "in");
    }

    @Test
    @Order(13)
    @DisplayName("Exists by specification with unexpected error test")
    void given_existsBySpecification_when_unexpectedError_then_throwException() {

        // given
        final Predicate<Department> spec = department -> CS.equals(
                department.getName(), "Department 01");

        final var directives = new Object[] { "error" };

        // when
        assertThatThrownBy(() -> defaultQueryFacade.existsBySpecification(spec, directives))
                // then
                .isInstanceOf(UnexpectedException.class)
                .hasMessageContaining("Unexpected error happened");
    }

    Stream<Arguments> methodWithNullSpecBeanValidationData() {
        return Stream.of(
                Arguments.of("findOneBySpecification"),
                Arguments.of("existsBySpecification"),
                Arguments.of("findBySpecification"),
                Arguments.of("countBySpecification"));
    }

    @Order(14)
    @ParameterizedTest(name = "Pos {index} : method ''{0}''")
    @MethodSource("methodWithNullSpecBeanValidationData")
    @DisplayName("Find one by specification with null specification test")
    void given_invalid_data_then_throwException(final String methodSring)
            throws NoSuchMethodException, SecurityException {

        final var method = defaultQueryFacade.getClass()
                .getMethod(methodSring, Object.class, Object[].class);

        final var violations = EXECUTABLE_VALIDATOR
                .validateParameters(defaultQueryFacade, method, new Object[] { null, new Object[] {} });

        assertThat(violations)
                .hasSize(1)
                .extracting(t -> t.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple(methodSring + ".arg0", "The object cannot be null"));
    }
}
