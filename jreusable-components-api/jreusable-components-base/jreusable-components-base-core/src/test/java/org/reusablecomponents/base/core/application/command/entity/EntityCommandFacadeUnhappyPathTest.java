package org.reusablecomponents.base.core.application.command.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.application_example.application.DepartmentFacade;
import org.application_example.domain.Manager;
import org.application_example.domain.Department;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reusablecomponents.base.core.infra.exception.common.ElementAlreadyExistsException;
import org.reusablecomponents.base.core.infra.exception.common.ElementConflictException;
import org.reusablecomponents.base.core.infra.exception.common.ElementInvalidException;
import org.reusablecomponents.base.core.infra.exception.common.ElementNotFoundException;

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

        // given
        Stream<Arguments> createInvalidSaveData() {

                final Department nullDepartment = null;
                final Department repeatedDepartment = new Department("x1", "Development 01", "Technology", manager);
                final Department invalidDepartment = new Department(null, "Development 01", "Technology", manager);

                final var elementAlreadyExistsParams = List.of(
                                "org.application_example.domain.Department");

                final var nullPointerParams = List.of("preSaveEntityIn");

                return Stream.of(
                                Arguments.of(nullDepartment, NullPointerException.class,
                                                "The object '%s' cannot be null", nullPointerParams),
                                Arguments.of(repeatedDepartment, ElementAlreadyExistsException.class,
                                                "The object '%s", elementAlreadyExistsParams),
                                Arguments.of(invalidDepartment, ElementInvalidException.class,
                                                "The object '%s", elementAlreadyExistsParams)

                );
        }

        @Order(1)
        @ParameterizedTest(name = "Pos {index} : department ''{0}'', exception ''{1}''")
        @MethodSource("createInvalidSaveData")
        @DisplayName("Try to save an entity test")
        void invalidSaveTest(
                        final Department department,
                        final Class<?> exceptionClass,
                        final String exceptionMessage,
                        final List<Object> exceptionParams) {

                // when
                assertThatThrownBy(() -> defaultFacade.save(department))
                                // then
                                .isInstanceOf(exceptionClass)
                                .hasMessageContaining(exceptionMessage, exceptionParams.stream().toArray(Object[]::new))

                ;
        }

        @Test
        @Order(2)
        @DisplayName("Test save entity beans validation")
        void saveEntityWithErrorsBeanValidationTest() throws NoSuchMethodException, SecurityException {

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
                final var repeatedDepartment = new Department("x2", "Development 01", "Technology", manager);
                final var correctDepartment = new Department("x3", "Default 02", "Resource", manager);
                final var invalidDepartment = new Department(null, "Development 01", "Technology", manager);

                final var nullListParams = List.of("saveEntitiesIn");

                final var elementAlreadyExistsParams = List.of(
                                "org.application_example.domain.Department");

                return Stream.of(
                                Arguments.of(nullList, NullPointerException.class,
                                                "The object '%s' cannot be null",
                                                nullListParams),

                                Arguments.of(List.of(repeatedDepartment, correctDepartment),
                                                ElementAlreadyExistsException.class,
                                                "The object '[%s", elementAlreadyExistsParams),

                                Arguments.of(List.of(invalidDepartment, correctDepartment),
                                                ElementInvalidException.class,
                                                "The object '[%s", elementAlreadyExistsParams)

                );
        }

        @Order(3)
        @ParameterizedTest(name = "Pos {index} : department ''{0}'', exception ''{1}''")
        @MethodSource("createInvalidSaveAllData")
        @DisplayName("Try to save invalid entities test")
        void invalidSaveAllTest(
                        final List<Department> departments,
                        final Class<?> exceptionClass,
                        final String exceptionMessage,
                        final List<Object> exceptionParams) {

                // when
                assertThatThrownBy(() -> defaultFacade.saveAll(departments))
                                // then
                                .isInstanceOf(exceptionClass)
                                .hasMessageContaining(exceptionMessage,
                                                exceptionParams.stream().toArray(Object[]::new));
        }

        // given
        Stream<Arguments> createInvalidUpdateData() {

                final Department nullDepartment = null;
                final Department unknownDepartment = new Department("x34", "Development 01", "Technology", manager);
                final Department invalidDepartment = new Department(null, "Development 01", "Technology", manager);

                final var elementNotExistsParams = List.of(
                                "org.application_example.domain.Department");

                final var nullPointerParams = List.of("preUpdateEntityIn");

                return Stream.of(
                                Arguments.of(nullDepartment, NullPointerException.class,
                                                "The object '%s' cannot be null", nullPointerParams),

                                Arguments.of(unknownDepartment, ElementNotFoundException.class,
                                                "The object '%s", elementNotExistsParams),

                                Arguments.of(invalidDepartment, ElementInvalidException.class,
                                                "The object '%s", elementNotExistsParams));
        }

        @Order(4)
        @ParameterizedTest(name = "Pos {index} : department ''{0}'', exception ''{1}''")
        @MethodSource("createInvalidUpdateData")
        @DisplayName("Try to update an invalid entity test")
        void invalidUpdateTest(
                        final Department department,
                        final Class<?> exceptionClass,
                        final String exceptionMessage,
                        final List<Object> exceptionParams) {

                // when
                assertThatThrownBy(() -> defaultFacade.update(department))
                                // then
                                .isInstanceOf(exceptionClass)
                                .hasMessageContaining(exceptionMessage, exceptionParams.stream().toArray(Object[]::new))

                ;
        }

        // given
        Stream<Arguments> createInvalidUpdateAllData() {

                final List<Department> nullList = null;
                final var unknownDepartment = new Department("x34", "Development 01", "Technology", manager);
                final var correctDepartment = new Department("x3", "Default 02", "Resource", manager);
                final var invalidDepartment = new Department(null, "Development 01", "Technology", manager);

                final var nullListParams = List.of("preUpdateEntitiesIn");

                final var elementAlreadyExistsParams = List.of(
                                "org.application_example.domain.Department");

                return Stream.of(
                                Arguments.of(nullList,
                                                NullPointerException.class,
                                                "The object '%s' cannot be null",
                                                nullListParams),

                                Arguments.of(List.of(unknownDepartment, correctDepartment),
                                                ElementNotFoundException.class,
                                                "The object '[%s",
                                                elementAlreadyExistsParams),

                                Arguments.of(List.of(invalidDepartment, correctDepartment),
                                                ElementInvalidException.class,
                                                "The object '[%s", elementAlreadyExistsParams)

                );
        }

        @Order(5)
        @ParameterizedTest(name = "Pos {index} : department ''{0}'', exception ''{1}''")
        @MethodSource("createInvalidUpdateAllData")
        @DisplayName("Try to update invalid entities test")
        void invalidUpdateAllTest(
                        final List<Department> departments,
                        final Class<?> exceptionClass,
                        final String exceptionMessage,
                        final List<Object> exceptionParams) {

                // when
                assertThatThrownBy(() -> defaultFacade.updateAll(departments))
                                // then
                                .isInstanceOf(exceptionClass)
                                .hasMessageContaining(exceptionMessage,
                                                exceptionParams.stream().toArray(Object[]::new));
        }

        // given
        Stream<Arguments> createInvalidDeleteData() {

                final Department nullDepartment = null;
                final Department unknownDepartment = new Department("x34", "Development 01", "Technology", manager);
                unknownDepartment.removeManager();

                final var elementNotExistsParams = List.of("org.application_example.domain.Department");

                final var nullPointerParams = List.of("preDeleteEntityIn");

                final var invalidDepartment = new Department(null, "Development 01", "Technology", manager);
                invalidDepartment.removeManager();

                final var conflictDepartment = new Department("x1", "Development 01", "Technology", manager);

                return Stream.of(
                                Arguments.of(nullDepartment, NullPointerException.class,
                                                "The object '%s' cannot be null", nullPointerParams),

                                Arguments.of(unknownDepartment, ElementNotFoundException.class,
                                                "The object '%s", elementNotExistsParams),

                                Arguments.of(invalidDepartment, ElementInvalidException.class,
                                                "The object '%s", elementNotExistsParams),

                                Arguments.of(conflictDepartment, ElementConflictException.class,
                                                "The object '%s", elementNotExistsParams));

        }

        @Order(6)
        @ParameterizedTest(name = "Pos {index} : department ''{0}'', exception ''{1}''")
        @MethodSource("createInvalidDeleteData")
        @DisplayName("Try to delete entity test")
        void invalidDeleteTest(
                        final Department department,
                        final Class<?> exceptionClass,
                        final String exceptionMessage,
                        final List<Object> exceptionParams) {

                // when
                assertThatThrownBy(() -> defaultFacade.delete(department))
                                // then
                                .isInstanceOf(exceptionClass)
                                .hasMessageContaining(exceptionMessage, exceptionParams.stream().toArray(Object[]::new))

                ;
        }

        // given
        Stream<Arguments> createInvalidDeleteAllData() {

                final List<Department> nullList = null;
                final Department unknownDepartment = new Department("x34", "Development 01", "Technology", manager);
                unknownDepartment.removeManager();

                final var correctDepartment = new Department("x3", "Default 02", "Resource", manager);

                final var elementNotExistsParams = List.of("org.application_example.domain.Department");

                final var nullPointerParams = List.of("preDeleteEntitiesIn");

                final var invalidDepartment = new Department(null, "Development 01", "Technology", manager);
                invalidDepartment.removeManager();

                final var conflictDepartment = new Department("x1", "Development 01", "Technology", manager);

                final var elementAlreadyExistsParams = List.of(
                                "org.application_example.domain.Department");

                return Stream.of(
                                Arguments.of(nullList,
                                                NullPointerException.class,
                                                "The object '%s' cannot be null",
                                                nullPointerParams),

                                Arguments.of(List.of(unknownDepartment, invalidDepartment),
                                                ElementInvalidException.class,
                                                "The object '[%s",
                                                elementAlreadyExistsParams),

                                Arguments.of(List.of(invalidDepartment, correctDepartment),
                                                ElementInvalidException.class,
                                                "The object '[%s", elementNotExistsParams),

                                Arguments.of(List.of(conflictDepartment, correctDepartment),
                                                ElementConflictException.class,
                                                "The object '[%s", elementNotExistsParams)

                );
        }

        @Order(7)
        @ParameterizedTest(name = "Pos {index} : department ''{0}'', exception ''{1}''")
        @MethodSource("createInvalidDeleteAllData")
        @DisplayName("Try to delete all entities test")
        void invalidDeleteAllTest(
                        final List<Department> departments,
                        final Class<?> exceptionClass,
                        final String exceptionMessage,
                        final List<Object> exceptionParams) {

                // when
                assertThatThrownBy(() -> defaultFacade.deleteAll(departments))
                                // then
                                .isInstanceOf(exceptionClass)
                                .hasMessageContaining(exceptionMessage,
                                                exceptionParams.stream().toArray(Object[]::new));
        }

}
