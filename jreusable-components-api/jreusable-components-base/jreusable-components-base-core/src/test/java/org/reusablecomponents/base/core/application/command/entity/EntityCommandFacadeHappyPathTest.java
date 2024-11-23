package org.reusablecomponents.base.core.application.command.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.application_example.domain.Manager;
import org.application_example.application.command.DepartmentCommandFacade;
import org.application_example.domain.Department;
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
import org.mockito.junit.jupiter.MockitoExtension;

@Tag("application")
@DisplayName("Test the EntityCommandFacade entity test, happy path :) ")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class EntityCommandFacadeHappyPathTest {

	final List<Department> defaultData = new ArrayList<>();
	final DepartmentCommandFacade defaultFacade = new DepartmentCommandFacade(defaultData);

	Department department01;
	Department department02;

	Manager company;

	@BeforeEach
	void setUp() {
		defaultData.clear();

		company = new Manager("x2", "Business Happy");

		department01 = new Department("x1", "Default 01", "Peopple", company);
		department02 = new Department("x2", "Default 02", "Resource", company);

		defaultData.addAll(List.of(department01, department02));
	}

	@AfterAll
	void tearDown() {
		// No requirements yet
	}

	@Test
	@Order(1)
	@DisplayName("save a entity test")
	void saveTest() {
		// given
		final var data = new ArrayList<Department>();

		final var departmentFacade = new DepartmentCommandFacade(data);
		final var department = new Department("00001", "Development 01", "Technology", company);

		// when
		final var result = departmentFacade.save(department);

		// then
		assertThat(department).isEqualTo(result);
		assertThat(data).contains(department);
		assertThat(result).extracting("operation").isEqualTo(2);
	}

	@Test
	@Order(2)
	@DisplayName("save a collection of entities test")
	void saveAllTest() {
		// given
		final var data = new ArrayList<Department>();

		final var departmentFacade = new DepartmentCommandFacade(data);

		final var departments = List.of(
				new Department("00001", "Development 01", "Technology", company),
				new Department("00002", "Development 02", "HR", company));

		// when
		final var result = departmentFacade.saveAll(departments);

		// then
		assertThat(data).hasSize(2);
		assertThat(data).hasSameElementsAs(result);
	}

	@Test
	@Order(3)
	@DisplayName("update a entity test")
	void updateTest() {
		// given
		final var newName = "new Name";
		final var newSector = "new sector";

		assertThat(defaultData)
				.noneMatch(dep -> Objects.equals(dep.getName(), newName))
				.noneMatch(dep -> Objects.equals(dep.getSector(), newSector));

		// when
		department01.update(newName, newSector);
		final var result = defaultFacade.update(department01);

		// then
		assertThat(defaultData)
				.contains(result)
				.anyMatch(dep -> Objects.equals(dep.getName(), newName) && Objects.equals(dep.getSector(), newSector));
	}

	@Test
	@Order(4)
	@DisplayName("update a collection of entities test")
	void updateAllTest() {
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

		final var result = defaultFacade.updateAll(List.of(department01, department02));

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
	@DisplayName("delete a entity test")
	void deleteTest() {
		// given
		assertThat(defaultData)
				.contains(department01);

		department01.removeManager();

		// when
		defaultFacade.delete(department01);

		// then
		assertThat(defaultData)
				.hasSize(1)
				.contains(department02)
				.doesNotContain(department01);
	}

	@Test
	@Order(6)
	@DisplayName("delete a collection of entities test")
	void deleteAllTest() {
		// given
		assertThat(defaultData)
				.contains(department01, department02);

		department01.removeManager();
		department02.removeManager();

		// when
		defaultFacade.deleteAll(List.of(department01, department02));

		// then
		assertThat(defaultData)
				.doesNotContain(department01, department02)
				.isEmpty();
	}

	@Test
	@Order(5)
	@DisplayName("delete a entity by id test")
	void deleteByIdTest() {
		// given
		assertThat(defaultData)
				.contains(department01);

		department01.removeManager();

		// when
		defaultFacade.deleteBy(department01.getId());

		// then
		assertThat(defaultData)
				.hasSize(1)
				.contains(department02)
				.doesNotContain(department01);
	}

	@Test
	@Order(6)
	@DisplayName("delete a collection of entities by id test")
	void deleteAllByIdTest() {
		// given
		assertThat(defaultData)
				.contains(department01, department02);

		department01.removeManager();
		department02.removeManager();

		// when
		defaultFacade.deleteAllBy(List.of(department01.getId(), department02.getId()));

		// then
		assertThat(defaultData)
				.doesNotContain(department01, department02)
				.isEmpty();
	}
}
