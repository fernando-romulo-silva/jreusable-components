package org.reusablecomponents.base.core.application.base;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;

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
import org.reusablecomponents.base.core.infra.util.function.compose.ComposeFunction2Args;
import org.reusablecomponents.base.core.infra.util.function.compose.ComposeFunction3Args;
import org.reusablecomponents.base.security.InterfaceSecurityService;

@Tag("unit")
@DisplayName("Test the EntiyBaseFacade entity test, happy Path :) ")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class BaseFacadeHappyPathTest extends AbstractBaseFacadeTest {

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
	@Order(4)
	@DisplayName("Test execute operation, no inputs")
	void executeNoInputOperationTest() {
		// given
		final Function<Object[], Department> mainFunctionNoInputOk = directives -> department01;

		// when
		final var resultDepartment = facade.execute(
				operation, preFunctionNoInput, posFunctionNoInput, mainFunctionNoInputOk, errorFunctionNoInput);

		// then
		assertThat(resultDepartment.getId())
				.isEqualTo(department01.getId());
	}

	@Test
	@Order(5)
	@DisplayName("Test execute operation, one input")
	void executeSingleInputOperationTest() {
		// given
		final BiFunction<Department, Object[], Department> mainFunctionOneInputOk = (
				departmentFinal, directives) -> department02;

		// when
		final var resultDepartment = facade.execute(
				department01, operation, preFunctionOneInput,
				posFunctionOneInput, mainFunctionOneInputOk, errorFunctionOneInput);

		// then
		assertThat(resultDepartment.getId())
				.isEqualTo(department02.getId());
	}

	@Test
	@Order(6)
	@DisplayName("Test execute operation, two inputs")
	void executeDoubleInputOperationTest() {
		// given
		final TriFunction<Department, Manager, Object[], DepartmenDto> mainFunctionTwoInputsOk = (
				departmentIn, managerIn, directives) -> new DepartmenDto(departmentIn.getName(), managerIn.getName());

		// when
		final var resultDepartment = facade.execute(
				department01, manager01, operation, preFunctionTwoInputs,
				posFunctionTwoInputs, mainFunctionTwoInputsOk, errorFunctionTwoInputs);

		// then
		assertThat(resultDepartment.departmentName())
				.isEqualTo(department02.getName());
	}

	@Test
	@Order(7)
	@DisplayName("Test compose bi functions active and inactives")
	void composeBiFunctionsTest() {
		// given
		final var facade = new TestEntiyBaseFacade(i18nService, interfaceSecurityService, exceptionTranslatorService);
		final var functions = getBiFunctions();

		// when
		final var result = facade.compose(department01, functions);

		// then
		assertThat(result.getName())
				.isEqualTo("Development 01 function 02 function 04");
	}

	@Test
	@Order(8)
	@DisplayName("Test compose empty bi functions active and inactives")
	void composeEmptyBiFunctionsTest() {
		// given
		final var facade = new TestEntiyBaseFacade(i18nService, interfaceSecurityService, exceptionTranslatorService);
		final var functions = new ArrayList<ComposeFunction2Args<Department>>();

		// when
		final var result = facade.compose(department01, functions);

		// then
		assertThat(result.getName())
				.isEqualTo("Development 01");
	}

	@Test
	@Order(9)
	@DisplayName("Test compose tri functions active and inactives")
	void composeTriFunctionsTest() {
		// given
		final var facade = new TestEntiyBaseFacade(i18nService, interfaceSecurityService, exceptionTranslatorService);
		final var exception = new NullPointerException("null");

		final var functions = getTriFunctions();

		// when
		final var result = facade.compose(exception, department01, functions);

		// then
		assertThat(result)
				.isInstanceOf(IllegalStateException.class);
	}

	@Test
	@Order(10)
	@DisplayName("Test compose tri functions active and inactives")
	void composeEmptyTriFunctionsTest() {
		// given
		final var facade = new TestEntiyBaseFacade(i18nService, interfaceSecurityService, exceptionTranslatorService);
		final var exception = new NullPointerException("null");

		final var functions = new ArrayList<ComposeFunction3Args<Exception, Department>>();

		// when
		final var result = facade.compose(exception, department01, functions);

		// then
		assertThat(result)
				.isInstanceOf(NullPointerException.class);
	}

}
