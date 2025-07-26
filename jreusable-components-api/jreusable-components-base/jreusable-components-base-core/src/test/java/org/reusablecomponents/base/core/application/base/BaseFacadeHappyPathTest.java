package org.reusablecomponents.base.core.application.base;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.BiFunction;

import org.apache.commons.lang3.function.TriFunction;
import org.application_example.application.TestEntiyBaseFacade;
import org.application_example.domain.Department;
import org.application_example.domain.Manager;
import org.application_example.infra.DummySecurityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reusablecomponents.base.core.infra.exception.DefaultExceptionAdapterService;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.core.infra.exception.common.BaseApplicationException;
import org.reusablecomponents.base.core.infra.util.QuadFunction;
import org.reusablecomponents.base.core.infra.util.operation.InterfaceOperation;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;

@Tag("unit")
@DisplayName("Test the EntiyBaseFacade entity test, happy Path :) ")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class BaseFacadeHappyPathTest {

	final InterfaceI18nService i18nService = (code, params) -> "translated!";
	final InterfaceSecurityService interfaceSecurityService = new DummySecurityService();
	final InterfaceExceptionAdapterService exceptionTranslatorService = new DefaultExceptionAdapterService();

	@Test
	@Order(1)
	@DisplayName("Test the constructor values")
	void constructorValuesTest() {

		// given
		final var myExceptionTranslatorService = exceptionTranslatorService;

		// when
		final var facade = new TestEntiyBaseFacade(i18nService, interfaceSecurityService, myExceptionTranslatorService);

		// then
		assertThat(facade)
				.as(format("Check the securityService, and i18nService are not null"))
				.extracting("securityService", "i18nService", "entityClazz", "idClazz")
				.doesNotContainNull();

		// then
		assertThat(facade)
				.extracting("entityClazz", "idClazz")
				.as(format("Check the entityClazz ''{0}'' and idClazz ''{1}''", Department.class, String.class))
				.containsExactly(Department.class, String.class);
	}

	@Test
	@Order(2)
	@DisplayName("Test the get values")
	void checkValuesTest() {

		// given
		final var facade = new TestEntiyBaseFacade();

		assertThat(facade)
				// when
				.extracting(TestEntiyBaseFacade::getEntityClazz)
				// then
				.isNotNull()
				.isEqualTo(Department.class);

		assertThat(facade)
				// when
				.extracting(BaseFacade::getIdClazz)
				// then
				.isNotNull()
				.isEqualTo(String.class);
	}

	@Test
	@Order(3)
	@DisplayName("Test the services instances")
	void checkServicesTest() {

		// given
		final var myDummySecurityService = new DummySecurityService() {
			@Override
			public String getUserName() {
				return "Fernando";
			}
		};

		final var facade = new TestEntiyBaseFacade(i18nService, myDummySecurityService, exceptionTranslatorService);

		assertThat(facade)
				// when
				.extracting(TestEntiyBaseFacade::getSecurityService)
				// then
				.isNotNull()
				.extracting(InterfaceSecurityService::getUserName)
				.isEqualTo("Fernando");

		assertThat(facade)
				// when
				.extracting(TestEntiyBaseFacade::getExceptionTranslatorService)
				// then
				.isNotNull();
	}

	@Test
	@Order(3)
	@DisplayName("Test execute operation, one input")
	void executeSingleInputOperationTest() {
		// given
		final var facade = new TestEntiyBaseFacade();
		final var operation = new TestOperation();
		final var company = new Manager("x2", "Business Happy");
		final var department01 = new Department("00001", "Development 01", "Technology", company);
		final var department02 = new Department("00002", "Development 02", "Technology", company);

		final BiFunction<Department, Object[], Department> preFunction = (departmentIn, directives) -> departmentIn;
		final BiFunction<Department, Object[], Department> posFunction = (departmentOut, directives) -> departmentOut;
		final TriFunction<Department, Exception, Object[], Exception> errorFunction = (
				departmentIn, exception, directives) -> new NullPointerException();

		final BiFunction<Department, Object[], Department> mainFunctionOk = (
				departmentFinal, directives) -> department02;

		final BiFunction<Department, Object[], Department> mainFunctionError = (
				departmentFinal, directives) -> {
			throw new IllegalArgumentException();
		};

		// when
		final var resultDepartment = facade.execute(
				department01, operation, preFunction, posFunction, mainFunctionOk, errorFunction);

		// then
		assertThat(resultDepartment.getId())
				.isEqualTo(department02.getId());

		assertThatThrownBy(
				() -> facade.execute(
						department01, operation, preFunction, posFunction, mainFunctionError, errorFunction))
				.isInstanceOf(BaseApplicationException.class)
				.hasRootCauseInstanceOf(NullPointerException.class);
	}

	@Test
	@Order(3)
	@DisplayName("Test execute operation, two inputs")
	void executeDoubleInputOperationTest() {
		record DepartmenDto(String departmentName, String managerName) {
		}

		// given
		final var facade = new TestEntiyBaseFacade();
		final var operation = new TestOperation();

		final var manager01 = new Manager("x1", "Mister A");
		final var department01 = new Department("00001", "Development 01", "Technology", manager01);

		final var manager02 = new Manager("x2", "Mister B");
		final var department02 = new Department("00002", "Development 02", "Technology", manager02);

		final TriFunction<Department, Manager, Object[], Entry<Department, Manager>> preFunction = (
				departmentIn, managerIn, directives) -> new AbstractMap.SimpleEntry<>(department02, manager02);

		final BiFunction<DepartmenDto, Object[], DepartmenDto> posFunction = (
				departmentDtoIn, directives) -> departmentDtoIn;
		final TriFunction<Department, Manager, Object[], DepartmenDto> mainFunctionOk = (
				departmentIn, managerIn, directives) -> new DepartmenDto(departmentIn.getName(), managerIn.getName());

		final TriFunction<Department, Manager, Object[], DepartmenDto> mainFunctionError = (
				departmentIn, managerIn, directives) -> {
			throw new IllegalArgumentException();
		};

		final QuadFunction<Department, Manager, Exception, Object[], Exception> errorFunction = (departmentIn,
				managerIn, exception, directives) -> new NullPointerException();

		// when
		final var resultDepartment = facade.execute(
				department01, manager01, operation, preFunction, posFunction, mainFunctionOk, errorFunction);

		// then
		assertThat(resultDepartment.departmentName())
				.isEqualTo(department02.getName());

		assertThatThrownBy(
				() -> facade.execute(
						department01, manager01, operation, preFunction, posFunction, mainFunctionError, errorFunction))
				.isInstanceOf(BaseApplicationException.class)
				.hasRootCauseInstanceOf(NullPointerException.class);
	}

	@Test
	@Order(4)
	@DisplayName("Test execute bi functions active and inactives")
	void executeBiFunctionsTest() {
		// given
		final var facade = new TestEntiyBaseFacade(i18nService, interfaceSecurityService, exceptionTranslatorService);
		final var company = new Manager("x2", "Business Happy");
		final var department = new Department("00001", "Development 01", "Technology", company);

		final var functions = getBiFunctions();

		// when
		final var result = facade.execute("myTest", department, functions);

		// then
		assertThat(result.getName())
				.isEqualTo("Development 01 function 02 function 04");
	}

	@Test
	@Order(5)
	@DisplayName("Test execute tri functions active and inactives")
	void executeTriFunctionsTest() {
		// given
		final var facade = new TestEntiyBaseFacade(i18nService, interfaceSecurityService, exceptionTranslatorService);
		final var company = new Manager("x2", "Business Happy");
		final var department = new Department("00001", "Development 01", "Technology", company);
		final var exception = new NullPointerException("null");

		final var functions = getTriFunctions();

		// when
		final var result = facade.execute("myTest", exception, department, functions);

		// then
		assertThat(result)
				.isInstanceOf(IllegalStateException.class);
	}

	@Test
	@Order(6)
	@DisplayName("Test execute bi functions with retrow first one")
	void executeBiFunctionsUntilThrowExceptionTest() {
		// given
		final var facade = new TestEntiyBaseFacade(i18nService, interfaceSecurityService, exceptionTranslatorService);
		final var company = new Manager("x2", "Business Happy");
		final var department = new Department("00001", "Development 01", "Technology", company);

		final var functions = new ArrayList<FacadeBiFunction<Department>>(getBiFunctions());

		final var functionThrow01 = new FacadeBiFunction<Department>() {

			@Override
			public Department apply(final Department department, final Object[] directives) {
				throw new IllegalStateException("State exception");
			}

			@Override
			public boolean reTrowException() {
				return true;
			}
		};
		functions.add(functionThrow01);

		final var functionThrow02 = new FacadeBiFunction<Department>() {

			@Override
			public Department apply(final Department department, final Object[] directives) {
				throw new IllegalArgumentException("Argument exception");
			}

			@Override
			public boolean reTrowException() {
				return true;
			}
		};
		functions.add(functionThrow02);

		// when
		assertThatThrownBy(() -> facade.execute("testException", department, functions))
				// then
				.isInstanceOf(IllegalStateException.class)
				.hasMessageContaining("State exception");
	}

	@Test
	@Order(7)
	@DisplayName("Test execute tri functions with retrow first one")
	void executeTriFunctionsUntilThrowExceptionTest() {
		// given
		final var facade = new TestEntiyBaseFacade(i18nService, interfaceSecurityService, exceptionTranslatorService);
		final var company = new Manager("x2", "Business Happy");
		final var department = new Department("00001", "Development 01", "Technology", company);
		final var exception = new NullPointerException("null");

		final var functions = new ArrayList<FacadeTriFunction<Exception, Department>>(getTriFunctions());
		final var functionThrow01 = new FacadeTriFunction<Exception, Department>() {

			@Override
			public Exception apply(final Exception exception, final Department department, final Object[] directives) {
				throw new IllegalStateException("State exception");
			}

			@Override
			public boolean reTrowException() {
				return true;
			}
		};

		final var functionThrow02 = new FacadeTriFunction<Exception, Department>() {

			@Override
			public Exception apply(final Exception exception, final Department department, final Object[] directives) {
				throw new IllegalArgumentException("Argument exception");
			}

			@Override
			public boolean reTrowException() {
				return true;
			}
		};

		functions.add(functionThrow01);
		functions.add(functionThrow02);

		// when
		assertThatThrownBy(() -> facade.execute("testException", exception, department, functions))
				// then
				.isInstanceOf(IllegalStateException.class)
				.hasMessageContaining("State exception");
	}

	private List<FacadeBiFunction<Department>> getBiFunctions() {
		final var function01 = new FacadeBiFunction<Department>() {
			@Override
			public Department apply(final Department department, final Object[] directives) {
				final var name = department.getName();
				final var sector = department.getSector();
				department.update(name + " function 01", sector);
				return department;
			}

			@Override
			public boolean isActice() {
				return false;
			}

			@Override
			public String getName() {
				return "Function 01";
			}
		};

		final var function02 = new FacadeBiFunction<Department>() {
			@Override
			public Department apply(final Department department, final Object[] directives) {
				final var name = department.getName();
				final var sector = department.getSector();
				department.update(name + " function 02", sector);
				return department;
			}
		};

		final var function03 = new FacadeBiFunction<Department>() {
			@Override
			public Department apply(final Department department, final Object[] directives) {
				throw new IllegalArgumentException("Some error");
			}
		};

		final var function04 = new FacadeBiFunction<Department>() {
			@Override
			public Department apply(final Department department, final Object[] directives) {
				final var name = department.getName();
				final var sector = department.getSector();
				department.update(name + " function 04", sector);
				return department;
			}
		};

		return List.<FacadeBiFunction<Department>>of(function01, function02, function03, function04);
	}

	private List<FacadeTriFunction<Exception, Department>> getTriFunctions() {
		final var function01 = new FacadeTriFunction<Exception, Department>() {
			@Override
			public Exception apply(
					final Exception exception,
					final Department department,
					final Object[] directives) {
				return new IllegalAccessException("Illegal Access Exception");
			}

			@Override
			public boolean isActice() {
				return false;
			}

			@Override
			public String getName() {
				return "Function 01";
			}
		};

		final FacadeTriFunction<Exception, Department> function02 = (exception, department,
				directives) -> new IllegalStateException("Illegal State Exception");

		final var function03 = new FacadeTriFunction<Exception, Department>() {
			@Override
			public Exception apply(final Exception exception, final Department department, final Object[] directives) {
				throw new IllegalArgumentException("Some error");
			}
		};

		final var function04 = new FacadeTriFunction<Exception, Department>() {
			@Override
			public Exception apply(final Exception exception, final Department department, final Object[] directives) {
				return new IllegalStateException("Illegal State Exception");
			}
		};

		return List.<FacadeTriFunction<Exception, Department>>of(function01, function02, function03, function04);
	}

	private static class TestOperation implements InterfaceOperation {
	}
}
