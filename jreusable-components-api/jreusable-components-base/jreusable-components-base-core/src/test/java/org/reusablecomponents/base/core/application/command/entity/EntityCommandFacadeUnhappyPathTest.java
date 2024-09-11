package org.reusablecomponents.base.core.application.command.entity;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.apache.commons.lang3.ArrayUtils.addAll;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static java.text.MessageFormat.format;
import static org.assertj.core.groups.Tuple.tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.application_example.application.DepartmentFacade;
import org.application_example.domain.Department;
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
import org.reusablecomponents.base.core.infra.exception.common.ElementAlreadyExistsException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.executable.ExecutableValidator;

@Tag("unit")
@DisplayName("Test the EntityCommandFacade entity test, unhappy path :( ")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class EntityCommandFacadeUnhappyPathTest {

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
        final DepartmentFacade defaultFacade = new DepartmentFacade(defaultData);

        Department department01;
        Department department02;

        @BeforeEach
        void setUp() {
                defaultData.clear();

                department01 = new Department("x1", "Default 01", "Peopple");
                department02 = new Department("x2", "Default 02", "Resource");

                defaultData.addAll(List.of(department01, department02));
        }

        @AfterAll
        void tearDown() {
                // No requirements yet
                VALIDATOR_FACTORY.close();
        }

        // given
        Stream<Arguments> createInvalidSaveData() {

                final Department nullDepartment = null;
                final Department repeatedDepartment = new Department("x1", "Development 01", "Technology");

                final var elementAlreadyExistsParams = List.of(
                                "Department",
                                "org.application_example.domain.Department");

                final var nullPointerParams = List.of("preSaveEntityIn");

                return Stream.of(
                                Arguments.of(nullDepartment, NullPointerException.class,
                                                "The object '%s' cannot be null", nullPointerParams),
                                Arguments.of(repeatedDepartment, ElementAlreadyExistsException.class,
                                                "The object '%s' with value(s) '%s",
                                                elementAlreadyExistsParams));
        }

        @ParameterizedTest(name = "Pos {index} : department ''{0}'', exception ''{1}''")
        @MethodSource("createInvalidSaveData")
        @Order(1)
        @DisplayName("Try to save a invalid entity test")
        void invalidSaveTest(
                        final Department department,
                        final Class<?> exceptionClass,
                        final String exceptionMessage,
                        final List<Object> exceptionParams) {

                // when
                assertThatThrownBy(() -> defaultFacade.save(department))
                                // then
                                .as(format("Check the invalid department ''{0}''", department))
                                .isInstanceOf(exceptionClass)
                                .hasMessageContaining(exceptionMessage, exceptionParams.stream().toArray(Object[]::new))

                ;
        }

        @Test
        @Order(2)
        @DisplayName("Test entity without builder creation, without exception")
        void checkEntityWithoutBuilderNoExceptionTest() throws NoSuchMethodException, SecurityException {

                final var method = defaultFacade.getClass()
                                .getMethod("save", Object.class);

                final var violations = EXECUTABLE_VALIDATOR
                                .validateParameters(defaultFacade, method, new Object[] { null });

                assertThat(violations)
                                .hasSize(1)
                                .extracting(t -> t.getPropertyPath().toString(), ConstraintViolation::getMessage)
                                .containsExactlyInAnyOrder(tuple("save.arg0", "The object cannot be null"))

                ;

        }

        // given
        Stream<Arguments> createInvalidSaveAllData() {

                final List<Department> nullList = null;
                final var repeatedDepartment = new Department("x2", "Development 01", "Technology");
                final var correctDepartment = new Department("x3", "Default 02", "Resource");

                return Stream.of(
                                Arguments.of(nullList, NullPointerException.class,
                                                "The object 'saveEntitiesIn' cannot be null"),
                                Arguments.of(List.of(repeatedDepartment, correctDepartment),
                                                ElementAlreadyExistsException.class, ""));
        }

        @ParameterizedTest(name = "Pos {index} : department ''{0}'', exception ''{1}''")
        @MethodSource("createInvalidSaveAllData")
        @Order(3)
        @DisplayName("Try to save a invalid entity test")
        void invalidSaveAllTest(
                        final List<Department> departments,
                        final Class<?> exceptionClass,
                        final String exceptionMessage,
                        final List<Object> exceptionParams) {

                // when
                assertThatThrownBy(() -> defaultFacade.saveAll(departments))
                                // then
                                .as(format("Check the invalid department ''{0}''", departments))
                                .isInstanceOf(exceptionClass)
                                .hasMessage(exceptionMessage);
        }
}
