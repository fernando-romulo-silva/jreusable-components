package org.reusablecomponents.base.core.application.command.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.application_example.application.command.DepartmentCommandFacade;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reusablecomponents.base.core.infra.exception.common.ElementAlreadyExistsException;
import org.reusablecomponents.base.core.infra.exception.common.ElementConflictException;
import org.reusablecomponents.base.core.infra.exception.common.ElementInvalidException;
import org.reusablecomponents.base.core.infra.exception.common.ElementNotFoundException;
import org.reusablecomponents.base.core.infra.exception.common.ElementWithIdNotFoundException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.executable.ExecutableValidator;

@Tag("application")
@DisplayName("Test the EntityCommandFacade entity test")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@SuppressWarnings("null")
class CommandFacadeTest {

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
	final DepartmentCommandFacade defaultFacade = new DepartmentCommandFacade(defaultData);

	Department department01;
	Department department02;

	Manager manager;
	Manager company;

	@BeforeAll
	void setUpAll() {
		// This method is used to initialize any resources or configurations needed for
		// the tests.
	}

	@AfterAll
	void tearDown() {
		VALIDATOR_FACTORY.close();
	}

	@BeforeEach
	void setUp() {
		defaultData.clear();

		company = new Manager("x2", "Business Happy");

		department01 = new Department("x1", "Default 01", "Peopple", company);
		department02 = new Department("x2", "Default 02", "Resource", company);

		defaultData.addAll(List.of(department01, department02));

		manager = new Manager("x2", "Business Happy");
	}

	@Test
	@Order(1)
	@DisplayName("Given an entity when save then entity is saved")
	void givenEntity_whenSave_thenEntityIsSaved() {
		// given
		final var data = new ArrayList<Department>();

		final var departmentFacade = new DepartmentCommandFacade(data);
		final var department = new Department("00001", "Development 01", "Technology", company);

		// when
		final var result = assertDoesNotThrow(() -> departmentFacade.save(department));

		// then
		assertThat(result).isEqualTo(department);
		assertThat(data).contains(department);
		assertThat(result).extracting("operation").isEqualTo(0);
	}

	@Test
	@Order(2)
	@DisplayName("Given a collection of entities when save all then entities are saved")
	void givenEntities_whenSaveAll_thenEntitiesAreSaved() {
		// given
		final var data = new ArrayList<Department>();

		final var departmentFacade = new DepartmentCommandFacade(data);

		final var departments = List.of(
				new Department("00001", "Development 01", "Technology", company),
				new Department("00002", "Development 02", "HR", company));

		// when
		final var result = assertDoesNotThrow(() -> departmentFacade.saveAll(departments));

		// then
		assertThat(data).hasSize(2);
		assertThat(data).hasSameElementsAs(result);
	}

	@Test
	@Order(3)
	@DisplayName("Given an entity when update then entity is updated")
	void givenEntity_whenUpdate_thenEntityIsUpdated() {
		// given
		final var newName = "new Name";
		final var newSector = "new sector";

		assertThat(defaultData)
				.noneMatch(dep -> Objects.equals(dep.getName(), newName))
				.noneMatch(dep -> Objects.equals(dep.getSector(), newSector));

		// when
		department01.update(newName, newSector);
		final var result = assertDoesNotThrow(() -> defaultFacade.update(department01));

		// then
		assertThat(defaultData)
				.contains(result)
				.anyMatch(dep -> Objects.equals(dep.getName(), newName) && Objects.equals(dep.getSector(), newSector));
	}

	@Test
	@Order(4)
	@DisplayName("Given a collection of entities when update all then entities are updated")
	void givenEntities_whenUpdateAll_thenEntitiesAreUpdated() {
		// given
		final var newName01 = "new Name 01";
		final var newSector01 = "new sector 01";

		final var newName02 = "new Name 02";
		final var newSector02 = "new sector 02";

		assertThat(defaultData)
				.noneMatch(dep -> Objects.equals(dep.getName(), newName01))
				.noneMatch(dep -> Objects.equals(dep.getSector(), newSector01))
				.noneMatch(dep -> Objects.equals(dep.getName(), newName02))
				.noneMatch(dep -> Objects.equals(dep.getSector(), newSector02));

		// when
		department01.update(newName01, newSector01);
		department02.update(newName02, newSector02);

		final var result = assertDoesNotThrow(() -> defaultFacade.updateAll(List.of(department01, department02)));

		// then
		assertThat(defaultData)
				.containsAll(result)
				.anyMatch(
						dep -> Objects.equals(dep.getName(), newName01)
								&& Objects.equals(dep.getSector(), newSector01))
				.anyMatch(dep -> Objects.equals(dep.getName(), newName02)
						&& Objects.equals(dep.getSector(), newSector02));
	}

	@Test
	@Order(5)
	@DisplayName("Given an entity when delete then entity is deleted")
	void givenEntity_whenDelete_thenEntityIsDeleted() {
		// given
		assertThat(defaultData)
				.contains(department01);

		department01.removeManager();

		// when
		assertDoesNotThrow(() -> defaultFacade.delete(department01));

		// then
		assertThat(defaultData)
				.hasSize(1)
				.contains(department02)
				.doesNotContain(department01);
	}

	@Test
	@Order(6)
	@DisplayName("Given a collection of entities when delete all then entities are deleted")
	void givenEntities_whenDeleteAll_thenEntitiesAreDeleted() {
		// given
		assertThat(defaultData)
				.contains(department01, department02);

		department01.removeManager();
		department02.removeManager();

		// when
		assertDoesNotThrow(() -> defaultFacade.deleteAll(List.of(department01, department02)));

		// then
		assertThat(defaultData)
				.doesNotContain(department01, department02)
				.isEmpty();
	}

	@Test
	@Order(5)
	@DisplayName("Given an entity when delete by id then entity is deleted")
	void givenEntity_whenDeleteById_thenEntityIsDeleted() {
		// given
		assertThat(defaultData)
				.contains(department01);

		department01.removeManager();

		// when
		assertDoesNotThrow(() -> defaultFacade.deleteBy(department01.getId()));

		// then
		assertThat(defaultData)
				.hasSize(1)
				.contains(department02)
				.doesNotContain(department01);
	}

	@Test
	@Order(6)
	@DisplayName("Given a collection of entities when delete all by ids then entities are deleted")
	void givenEntities_whenDeleteAllByIds_thenEntitiesAreDeleted() {
		// given
		assertThat(defaultData)
				.contains(department01, department02);

		department01.removeManager();
		department02.removeManager();

		// when
		assertDoesNotThrow(() -> defaultFacade.deleteAllBy(List.of(department01.getId(), department02.getId())));

		// then
		assertThat(defaultData)
				.doesNotContain(department01, department02)
				.isEmpty();
	}

	// given
	Stream<Arguments> createInvalidSaveData() {
		final Department nullDepartment = null;
		final Department repeatedDepartment = new Department("x1", "Development 01", "Technology", manager);
		final Department invalidDepartment = new Department(null, "Development 01", "Technology", manager);

		final var elementAlreadyExistsParams = List.of(
				"org.application_example.domain.Department");

		return Stream.of(
				Arguments.of(nullDepartment, NullPointerException.class, "Please pass a non-null %s entity",
						new Object[] {}, List.of("Department")),

				Arguments.of(repeatedDepartment, ElementAlreadyExistsException.class,
						"The object '%s", new Object[] {}, elementAlreadyExistsParams),

				Arguments.of(invalidDepartment, ElementInvalidException.class,
						"The object '%s", new Object[] {}, elementAlreadyExistsParams));
	}

	@Order(7)
	@ParameterizedTest(name = "Pos {index} : department ''{0}'', exception ''{1}''")
	@MethodSource("createInvalidSaveData")
	@DisplayName("Given an entity with invalid data when save then throw exception")
	void givenEntityWithInvalidData_whenSave_thenThrowException(
			final Department department,
			final Class<?> exceptionClass,
			final String exceptionMessage,
			final Object[] directives,
			final List<Object> exceptionParams) {

		// when
		assertThatThrownBy(() -> defaultFacade.save(department, directives))
				// then
				.isInstanceOf(exceptionClass)
				.hasMessageContaining(exceptionMessage, exceptionParams.stream().toArray(Object[]::new));
	}

	// given
	Stream<Arguments> createInvalidSaveAllData() {
		final List<Department> nullList = null;
		final var repeatedDepartment = new Department("x2", "Development 01", "Technology", manager);
		final var correctDepartment = new Department("x3", "Default 02", "Resource", manager);
		final var invalidDepartment = new Department(null, "Development 01", "Technology", manager);

		final var elementAlreadyExistsParams = List.of(
				"org.application_example.domain.Department");

		return Stream.of(
				Arguments.of(nullList, NullPointerException.class,
						"Please pass a non-null group of %s entities",
						List.of("Department")),

				Arguments.of(List.of(repeatedDepartment, correctDepartment),
						ElementAlreadyExistsException.class,
						"The object '[%s", elementAlreadyExistsParams),

				Arguments.of(List.of(invalidDepartment, correctDepartment),
						ElementInvalidException.class,
						"The object '[%s", elementAlreadyExistsParams));
	}

	@Order(8)
	@ParameterizedTest(name = "Pos {index} : department ''{0}'', exception ''{1}''")
	@MethodSource("createInvalidSaveAllData")
	@DisplayName("Given a collection of entities with invalid data when save all then throw exception")
	void givenEntitiesWithInvalidData_whenSaveAll_thenThrowException(
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

	@Test
	@Order(9)
	@DisplayName("Given an entity with errors when save then throw exception")
	void saveEntityWithErrorsBeanValidationTest() throws NoSuchMethodException, SecurityException {

		final var method = defaultFacade.getClass()
				.getMethod("save", Object.class, Object[].class);

		final var violations = EXECUTABLE_VALIDATOR
				.validateParameters(defaultFacade, method, new Object[] { null, new Object[] {} });

		assertThat(violations)
				.hasSize(1)
				.extracting(t -> t.getPropertyPath().toString(), ConstraintViolation::getMessage)
				.containsExactlyInAnyOrder(tuple("save.arg0", "The object cannot be null"));
	}

	// given
	Stream<Arguments> createInvalidUpdateData() {
		final Department nullDepartment = null;
		final Department unknownDepartment = new Department("x34", "Development 01", "Technology", manager);
		final Department invalidDepartment = new Department(null, "Development 01", "Technology", manager);

		final var elementNotExistsParams = List.of(
				"org.application_example.domain.Department");

		return Stream.of(
				Arguments.of(nullDepartment, NullPointerException.class,
						"Please pass a non-null %s entity", List.of("Department")),

				Arguments.of(unknownDepartment, ElementNotFoundException.class,
						"The object '%s", elementNotExistsParams),

				Arguments.of(invalidDepartment, ElementInvalidException.class,
						"The object '%s", elementNotExistsParams));
	}

	@Order(10)
	@ParameterizedTest(name = "Pos {index} : department ''{0}'', exception ''{1}''")
	@MethodSource("createInvalidUpdateData")
	@DisplayName("Given an entity with invalid data when update then throw exception")
	void givenEntityWithInvalidData_whenUpdate_thenThrowException(
			final Department department,
			final Class<?> exceptionClass,
			final String exceptionMessage,
			final List<Object> exceptionParams) {

		// when
		assertThatThrownBy(() -> defaultFacade.update(department))
				// then
				.isInstanceOf(exceptionClass)
				.hasMessageContaining(exceptionMessage, exceptionParams.stream().toArray(Object[]::new));
	}

	// given
	Stream<Arguments> createInvalidUpdateAllData() {

		final List<Department> nullList = null;
		final var unknownDepartment = new Department("x34", "Development 01", "Technology", manager);
		final var correctDepartment = new Department("x3", "Default 02", "Resource", manager);
		final var invalidDepartment = new Department(null, "Development 01", "Technology", manager);

		final var elementAlreadyExistsParams = List.of(
				"org.application_example.domain.Department");

		return Stream.of(
				Arguments.of(nullList, NullPointerException.class,
						"Please pass a non-null group of %s entities", List.of("Department")),

				Arguments.of(List.of(unknownDepartment, correctDepartment),
						ElementNotFoundException.class,
						"The object '[%s", elementAlreadyExistsParams),

				Arguments.of(List.of(invalidDepartment, correctDepartment),
						ElementInvalidException.class,
						"The object '[%s", elementAlreadyExistsParams));
	}

	@Order(11)
	@ParameterizedTest(name = "Pos {index} : department ''{0}'', exception ''{1}''")
	@MethodSource("createInvalidUpdateAllData")
	@DisplayName("Given a collection of entities with invalid data when update all then throw exception")
	void givenEntitiesWithInvalidData_whenUpdateAll_thenThrowException(
			final List<Department> departments,
			final Class<?> exceptionClass,
			final String exceptionMessage,
			final List<Object> exceptionParams) {

		// when
		assertThatThrownBy(() -> defaultFacade.updateAll(departments))
				// then
				.isInstanceOf(exceptionClass)
				.hasMessageContaining(exceptionMessage, exceptionParams.stream().toArray(Object[]::new));
	}

	// given
	Stream<Arguments> createInvalidDeleteData() {
		final Department nullDepartment = null;
		final Department unknownDepartment = new Department("x34", "Development 01", "Technology", manager);
		unknownDepartment.removeManager();

		final var elementNotExistsParams = List.of("org.application_example.domain.Department");

		final var invalidDepartment = new Department(null, "Development 01", "Technology", manager);
		invalidDepartment.removeManager();

		final var conflictDepartment = new Department("x1", "Development 01", "Technology", manager);

		return Stream.of(
				Arguments.of(nullDepartment, NullPointerException.class,
						"Please pass a non-null %s entity", List.of("Department")),

				Arguments.of(unknownDepartment, ElementNotFoundException.class,
						"The object '%s", elementNotExistsParams),

				Arguments.of(invalidDepartment, ElementInvalidException.class,
						"The object '%s", elementNotExistsParams),

				Arguments.of(conflictDepartment, ElementConflictException.class,
						"The object '%s", elementNotExistsParams));
	}

	@Order(12)
	@ParameterizedTest(name = "Pos {index} : department ''{0}'', exception ''{1}''")
	@MethodSource("createInvalidDeleteData")
	@DisplayName("Given an entity with invalid data when delete then throw exception")
	void givenEntityWithInvalidData_whenDelete_thenThrowException(
			final Department department,
			final Class<?> exceptionClass,
			final String exceptionMessage,
			final List<Object> exceptionParams) {

		// when
		assertThatThrownBy(() -> defaultFacade.delete(department))
				// then
				.isInstanceOf(exceptionClass)
				.hasMessageContaining(exceptionMessage, exceptionParams.stream().toArray(Object[]::new));
	}

	// given
	Stream<Arguments> createInvalidDeleteAllData() {
		final List<Department> nullList = null;
		final Department unknownDepartment = new Department("x34", "Development 01", "Technology", manager);
		unknownDepartment.removeManager();

		final var correctDepartment = new Department("x3", "Default 02", "Resource", manager);

		final var elementNotExistsParams = List.of("org.application_example.domain.Department");

		final var invalidDepartment = new Department(null, "Development 01", "Technology", manager);
		invalidDepartment.removeManager();

		final var conflictDepartment = new Department("x1", "Development 01", "Technology", manager);

		final var elementAlreadyExistsParams = List.of("org.application_example.domain.Department");

		return Stream.of(
				Arguments.of(nullList,
						NullPointerException.class,
						"Please pass a non-null group of %s entities",
						List.of("Department")),

				Arguments.of(List.of(unknownDepartment, invalidDepartment),
						ElementInvalidException.class,
						"The object '[%s",
						elementAlreadyExistsParams),

				Arguments.of(List.of(invalidDepartment, correctDepartment),
						ElementInvalidException.class,
						"The object '[%s", elementNotExistsParams),

				Arguments.of(List.of(conflictDepartment, correctDepartment),
						ElementConflictException.class,
						"The object '[%s", elementNotExistsParams));
	}

	@Order(13)
	@ParameterizedTest(name = "Pos {index} : department ''{0}'', exception ''{1}''")
	@MethodSource("createInvalidDeleteAllData")
	@DisplayName("Given a collection of entities with invalid data when delete all then throw exception")
	void givenEntitiesWithInvalidData_whenDeleteAll_thenThrowException(
			final List<Department> departments,
			final Class<?> exceptionClass,
			final String exceptionMessage,
			final List<Object> exceptionParams) {

		// when
		assertThatThrownBy(() -> defaultFacade.deleteAll(departments))
				// then
				.isInstanceOf(exceptionClass)
				.hasMessageContaining(exceptionMessage, exceptionParams.stream().toArray(Object[]::new));
	}

	// given
	Stream<Arguments> createInvalidDeleteByIdData() {
		final String nullDepartmentId = null;
		final var unknownDepartment = new Department("x34", "Development 01", "Technology", manager);
		unknownDepartment.removeManager();

		final var elementNotExistsParams = List.of(unknownDepartment.getId(), "Department");

		final var invalidDepartment = new Department("", "Development 01", "Technology", manager);
		invalidDepartment.removeManager();

		final var conflictDepartment = new Department("x1", "Development 01", "Technology", manager);
		final var elementHasConflictParams = List.of(conflictDepartment.getId());

		return Stream.of(
				Arguments.of(nullDepartmentId, NullPointerException.class,
						"Please pass a non-null %s id", List.of("Department")),

				Arguments.of(unknownDepartment.getId(), ElementWithIdNotFoundException.class,
						"The id '%s' not found for '%s' type", elementNotExistsParams),

				Arguments.of(invalidDepartment.getId(), ElementInvalidException.class,
						"The object '%s' is invalid", List.of(invalidDepartment.getId())),

				Arguments.of(conflictDepartment.getId(), ElementConflictException.class,
						"The object '%s' has conflict(s)", elementHasConflictParams));
	}

	@Order(14)
	@ParameterizedTest(name = "Pos {index} : id department ''{0}'', exception ''{1}''")
	@MethodSource("createInvalidDeleteByIdData")
	@DisplayName("Given an entity with invalid data when delete by id then throw exception")
	void givenEntityWithInvalidData_whenDeleteById_thenThrowException(
			final String departmentId,
			final Class<?> exceptionClass,
			final String exceptionMessage,
			final List<Object> exceptionParams) {

		// when
		assertThatThrownBy(() -> defaultFacade.deleteBy(departmentId))
				// then
				.isInstanceOf(exceptionClass)
				.hasMessageContaining(exceptionMessage, exceptionParams.stream().toArray(Object[]::new));
	}

	// given
	Stream<Arguments> createInvalidDeleteAllByIdData() {
		final var correctDepartment = new Department("x3", "Default 02", "Resource", manager);

		final List<Department> nullList = null;

		final var unknownDepartment = new Department("x34", "Development 01", "Technology", manager);
		unknownDepartment.removeManager();

		final var elementNotExistsParams = List.of(unknownDepartment.getId() + ", " + correctDepartment.getId(),
				"Department");

		final var invalidDepartment = new Department("", "Development 01", "Technology", manager);
		invalidDepartment.removeManager();

		final var conflictDepartment = new Department("x1", "Development 01", "Technology", manager);

		final var elementHasConflictParams = List
				.of(conflictDepartment.getId() + ", " + correctDepartment.getId());

		final var elementInvalidParams = List.of(invalidDepartment.getId() + ", " + correctDepartment.getId());

		return Stream.of(
				Arguments.of(nullList, NullPointerException.class,
						"Please pass a non-null group of %s ids", List.of("Department")),

				Arguments.of(List.of(unknownDepartment.getId(),
						correctDepartment.getId()),
						ElementWithIdNotFoundException.class,
						"The id '[%s]' not found for '%s' type",
						elementNotExistsParams),

				Arguments.of(List.of(invalidDepartment.getId(), correctDepartment.getId()),
						ElementInvalidException.class,
						"The object '[%s]' is invalid", elementInvalidParams),

				Arguments.of(List.of(conflictDepartment.getId(), correctDepartment.getId()),
						ElementConflictException.class, "The object '[%s]' has conflict(s)",
						elementHasConflictParams));
	}

	@Order(15)
	@ParameterizedTest(name = "Pos {index} : id department ''{0}'', exception ''{1}''")
	@MethodSource("createInvalidDeleteAllByIdData")
	@DisplayName("Given a collection of entities with invalid data when delete all by id then throw exception")
	void givenEntitiesWithInvalidData_whenDeleteAllById_thenThrowException(
			final List<String> departmentIds,
			final Class<?> exceptionClass,
			final String exceptionMessage,
			final List<Object> exceptionParams) {
		// when
		assertThatThrownBy(() -> defaultFacade.deleteAllBy(departmentIds))
				// then
				.isInstanceOf(exceptionClass)
				.hasMessageContaining(exceptionMessage, exceptionParams.stream().toArray(Object[]::new));
	}
}
