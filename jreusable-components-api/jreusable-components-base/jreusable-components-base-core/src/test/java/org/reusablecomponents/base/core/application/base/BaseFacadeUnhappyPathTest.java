package org.reusablecomponents.base.core.application.base;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import org.apache.commons.lang3.function.TriFunction;
import org.application_example.application.TestEntiyBaseFacade;
import org.application_example.domain.Department;
import org.application_example.domain.Manager;
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
import org.reusablecomponents.base.core.application.base.functions.FacadeFunctionOneArg;
import org.reusablecomponents.base.core.application.base.functions.FacadeFunctionTwoArgs;
import org.reusablecomponents.base.core.infra.exception.common.BaseApplicationException;
import org.reusablecomponents.base.core.infra.util.function.QuadFunction;
import org.reusablecomponents.base.core.infra.util.operation.CommandOperation;
import org.reusablecomponents.base.core.infra.util.operation.InterfaceOperation;

@Tag("unit")
@DisplayName("Test the EntiyBaseFacade entity test, unhappy Path :( ")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class BaseFacadeUnhappyPathTest extends AbstractBaseFacadeTest {

	private final String msg = "Please pass a non-null '%s'";

	@Test
	@Order(1)
	@DisplayName("Test the constructor values")
	void constructorValuesTest() {
		assertThatThrownBy(() -> new BaseFacade<Department, String>(null))
				// then
				.isInstanceOf(NullPointerException.class)
				.hasMessageContaining("The object 'builder' cannot be null");

	}

	// given
	Stream<Arguments> createInvalidExecuteData01() {
		final var in = new Department("x1", "Development 01", "Technology", manager01);
		final InterfaceOperation operation = CommandOperation.SAVE_ENTITY;
		final BiFunction<Department, Object[], Department> preFunction = (department, directives) -> department;
		final BiFunction<Department, Object[], Department> posFunction = (department, directives) -> department;
		final BiFunction<Department, Object[], Department> mainFunction = (department,
				directives) -> department;
		final TriFunction<Department, Exception, Object[], Exception> errorFunction = (department, exception,
				directives) -> exception;
		final Object[] directives = new Object[] {};

		return Stream.of(
				Arguments.of(null, operation, preFunction, posFunction, mainFunction, errorFunction,
						directives,
						msg.formatted("in")),
				Arguments.of(in, null, preFunction, posFunction, mainFunction, errorFunction,
						directives,
						msg.formatted("operation")),
				Arguments.of(in, operation, null, posFunction, mainFunction, errorFunction, directives,
						msg.formatted("preFunction")),
				Arguments.of(in, operation, preFunction, null, mainFunction, errorFunction, directives,
						msg.formatted("posFunction")),
				Arguments.of(in, operation, preFunction, posFunction, null, errorFunction, directives,
						msg.formatted("mainFunction")),
				Arguments.of(in, operation, preFunction, posFunction, mainFunction, null, directives,
						msg.formatted("errorFunction")),
				Arguments.of(in, operation, preFunction, posFunction, mainFunction, errorFunction, null,
						msg.formatted("directives")));
	}

	@Order(2)
	@ParameterizedTest(name = "Pos {index} : department ''{0}'', exception ''{1}''")
	@MethodSource("createInvalidExecuteData01")
	@DisplayName("Try to execute 01 invalid test")
	void invalidExecuteTest01(
			final Department in,
			final InterfaceOperation operation,
			final BiFunction<Department, Object[], Department> preFunction,
			final BiFunction<Department, Object[], Department> posFunction,
			final BiFunction<Department, Object[], Department> mainFunction,
			final TriFunction<Department, Exception, Object[], Exception> errorFunction,
			final Object[] directives,
			final String exceptionMessage) {
		// when
		assertThatThrownBy(
				() -> facade.execute(in, operation, preFunction, posFunction,
						mainFunction, errorFunction, directives))
				// then
				.isInstanceOf(NullPointerException.class)
				.hasMessageContaining(exceptionMessage);
	}

	// given
	Stream<Arguments> createInvalidExecuteData02() {
		final var in1 = new Department("x1", "Development 01", "Technology", manager01);
		final var in2 = new Manager("x3", "Business Happy");
		final InterfaceOperation operation = CommandOperation.SAVE_ENTITY;

		final TriFunction<Department, Manager, Object[], Entry<Department, Manager>> preFunction = (department,
				manager, directives) -> new AbstractMap.SimpleEntry<>(department, manager);

		final BiFunction<Department, Object[], Department> posFunction = (department, directives) -> department;

		final TriFunction<Department, Manager, Object[], Department> mainFunction = (department, manager,
				directives) -> department;

		final QuadFunction<Department, Manager, Exception, Object[], Exception> errorFunction = (department,
				manager, exception, directives) -> exception;

		final Object[] directives = new Object[] {};

		return Stream.of(
				Arguments.of(null, in2, operation, preFunction, posFunction, mainFunction,
						errorFunction, directives, msg.formatted("in1")),
				Arguments.of(in1, null, operation, preFunction, posFunction, mainFunction,
						errorFunction, directives, msg.formatted("in2")),
				Arguments.of(in1, in2, null, preFunction, posFunction, mainFunction, errorFunction,
						directives, msg.formatted("operation")),
				Arguments.of(in1, in2, operation, null, posFunction, mainFunction, errorFunction,
						directives, msg.formatted("preFunction")),
				Arguments.of(in1, in2, operation, preFunction, null, mainFunction, errorFunction,
						directives, msg.formatted("posFunction")),
				Arguments.of(in1, in2, operation, preFunction, posFunction, null, errorFunction,
						directives, msg.formatted("mainFunction")),
				Arguments.of(in1, in2, operation, preFunction, posFunction, mainFunction, null,
						directives, msg.formatted("errorFunction")),
				Arguments.of(in1, in2, operation, preFunction, posFunction, mainFunction, errorFunction,
						null, msg.formatted("directives")));
	}

	@Order(3)
	@ParameterizedTest(name = "Pos {index} : department ''{0}'', exception ''{1}''")
	@MethodSource("createInvalidExecuteData02")
	@DisplayName("Try to execute 02 invalid test 2")
	void invalidExecuteTest02(
			final Department in1,
			final Manager in2,
			final InterfaceOperation operation,
			final TriFunction<Department, Manager, Object[], Entry<Department, Manager>> preFunction,
			final BiFunction<Department, Object[], Department> posFunction,
			final TriFunction<Department, Manager, Object[], Department> mainFunction,
			final QuadFunction<Department, Manager, Exception, Object[], Exception> errorFunction,
			final Object[] directives,
			final String exceptionMessage) {
		// when
		assertThatThrownBy(
				() -> facade.execute(in1, in2, operation, preFunction,
						posFunction, mainFunction, errorFunction, directives))
				// then
				.isInstanceOf(NullPointerException.class)
				.hasMessageContaining(exceptionMessage);
	}

	// given
	Stream<Arguments> createInvalidExecuteData03() {
		final InterfaceOperation operation = CommandOperation.SAVE_ENTITY;
		final Consumer<Object[]> preFunction = System.out::println;
		final BiFunction<Department, Object[], Department> posFunction = (department, directives) -> department;
		final Function<Object[], Department> mainFunction = directives -> null;
		final BiFunction<Exception, Object[], Exception> errorFunction = (exception, directives) -> exception;

		final Object[] directives = new Object[] {};

		return Stream.of(
				Arguments.of(null, preFunction, posFunction, mainFunction, errorFunction, directives,
						msg.formatted("operation")),
				Arguments.of(operation, null, posFunction, mainFunction, errorFunction, directives,
						msg.formatted("preFunction")),
				Arguments.of(operation, preFunction, null, mainFunction, errorFunction, directives,
						msg.formatted("posFunction")),
				Arguments.of(operation, preFunction, posFunction, null, errorFunction, directives,
						msg.formatted("mainFunction")),
				Arguments.of(operation, preFunction, posFunction, mainFunction, null, directives,
						msg.formatted("errorFunction")),
				Arguments.of(operation, preFunction, posFunction, mainFunction, errorFunction, null,
						msg.formatted("directives")));
	}

	@Order(4)
	@ParameterizedTest(name = "Pos {index} : department ''{0}'', exception ''{1}''")
	@MethodSource("createInvalidExecuteData03")
	@DisplayName("Try to execute 03 invalid test")
	void invalidExecuteTest03(
			final InterfaceOperation operation,
			final Consumer<Object[]> preFunction,
			final BiFunction<Department, Object[], Department> posFunction,
			final Function<Object[], Department> mainFunction,
			final BiFunction<Exception, Object[], Exception> errorFunction,
			final Object[] directives,
			final String exceptionMessage) {
		// when
		assertThatThrownBy(
				() -> facade.execute(operation, preFunction, posFunction, mainFunction, errorFunction, directives))
				// then
				.isInstanceOf(NullPointerException.class)
				.hasMessageContaining(exceptionMessage);
	}

	@Test
	@Order(5)
	@DisplayName("Test execute pre operation error, no inputs")
	void executeNoInputOperationPreFunctionErrorTest() {
		// given
		final String errorMsg = "Pre function error, no inputs";

		final Consumer<Object[]> preFunctionNoInputError = directives -> {
			throw new IllegalArgumentException(errorMsg);
		};

		assertThatThrownBy(
				// when
				() -> facade.execute(
						operation, preFunctionNoInputError, posFunctionNoInput,
						directives -> department01, errorFunctionNoInput))
				// then
				.isInstanceOf(BaseApplicationException.class)
				.hasRootCauseInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining(errorMsg);
	}

	@Test
	@Order(6)
	@DisplayName("Test execute operation, no inputs")
	void executeNoInputOperationMainFunctionErrorTest() {
		// given
		final String errorMsg = "main function error, no inputs";

		final Function<Object[], Department> mainFunctionNoInputError = directives -> {
			throw new IllegalArgumentException(errorMsg);
		};

		assertThatThrownBy(
				// when
				() -> facade.execute(
						operation, preFunctionNoInput, posFunctionNoInput,
						mainFunctionNoInputError, errorFunctionNoInput))
				// then
				.isInstanceOf(BaseApplicationException.class)
				.hasRootCauseInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining(errorMsg);
	}

	@Test
	@Order(7)
	@DisplayName("Test execute pos operation error, no inputs")
	void executeNoInputOperationPosFunctionErrorTest() {
		// given
		final String errorMsg = "pos function error, no inputs";

		final BiFunction<Department, Object[], Department> posFunctionNoInputError = (
				department, directives) -> {
			throw new IllegalArgumentException(errorMsg);
		};

		assertThatThrownBy(
				// when
				() -> facade.execute(
						operation, preFunctionNoInput, posFunctionNoInputError,
						directives -> department01, errorFunctionNoInput))
				// then
				.isInstanceOf(BaseApplicationException.class)
				.hasRootCauseInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining(errorMsg);
	}

	@Test
	@Order(8)
	@DisplayName("Test execute pre operation, one input")
	void executeSingleInputOperationPreFunctionErrorTest() {
		// given
		final String errorMsg = "pre function error, one input";

		final BiFunction<Department, Object[], Department> preFunctionOneInputError = (departmentIn, directives) -> {
			throw new IllegalArgumentException(errorMsg);
		};

		assertThatThrownBy(
				// when
				() -> facade.execute(
						department01, operation, preFunctionOneInputError,
						posFunctionOneInput, (department, directives) -> department01,
						errorFunctionOneInput))
				// then
				.isInstanceOf(BaseApplicationException.class)
				.hasRootCauseInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining(errorMsg);
	}

	@Test
	@Order(9)
	@DisplayName("Test execute operation, one input")
	void executeSingleInputOperationMainFunctionErrorTest() {
		// given
		final String errorMsg = "main function error, one input";

		final BiFunction<Department, Object[], Department> mainFunctionOneInputError = (
				departmentFinal, directives) -> {
			throw new IllegalArgumentException(errorMsg);
		};

		assertThatThrownBy(
				// when
				() -> facade.execute(
						department01, operation, preFunctionOneInput,
						posFunctionOneInput, mainFunctionOneInputError,
						errorFunctionOneInput))
				// then
				.isInstanceOf(BaseApplicationException.class)
				.hasRootCauseInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining(errorMsg);
	}

	@Test
	@Order(10)
	@DisplayName("Test execute pos operation, one input")
	void executeSingleInputOperationPosFunctionErrorTest() {
		// given
		final String errorMsg = "pos function error, one input";

		final BiFunction<Department, Object[], Department> posFunctionOneInputError = (
				departmentOut, directives) -> {
			throw new IllegalArgumentException(errorMsg);
		};

		assertThatThrownBy(
				// when
				() -> facade.execute(
						department01, operation, preFunctionOneInput,
						posFunctionOneInputError, (department, directives) -> department01,
						errorFunctionOneInput))
				// then
				.isInstanceOf(BaseApplicationException.class)
				.hasRootCauseInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining(errorMsg);
	}

	@Test
	@Order(11)
	@DisplayName("Test execute pre operation, two inputs")
	void executeDoubleInputOperationPreFunctionErrorTest() {
		// given
		final String errorMsg = "pre function error, two inputs";
		final var departmentDto = new DepartmenDto(department01.getName(), manager01.getName());

		final TriFunction<Department, Manager, Object[], Entry<Department, Manager>> preFunctionTwoInputsError = (
				departmentIn, managerIn, directives) -> {
			throw new IllegalArgumentException(errorMsg);
		};

		assertThatThrownBy(
				// when
				() -> facade.execute(
						department01, manager01, operation, preFunctionTwoInputsError, posFunctionTwoInputs,
						(departmentIn, managerIn, directives) -> departmentDto, errorFunctionTwoInputs))
				// then
				.isInstanceOf(BaseApplicationException.class)
				.hasRootCauseInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining(errorMsg);
	}

	@Test
	@Order(12)
	@DisplayName("Test execute operation, two inputs")
	void executeDoubleInputOperationMainFunctionErrorTest() {
		// given
		final String errorMsg = "main function error, two inputs";

		final TriFunction<Department, Manager, Object[], DepartmenDto> mainFunctionTwoInputsError = (
				departmentIn, managerIn, directives) -> {
			throw new IllegalArgumentException(errorMsg);
		};

		assertThatThrownBy(
				// when
				() -> facade.execute(
						department01, manager01, operation, preFunctionTwoInputs, posFunctionTwoInputs,
						mainFunctionTwoInputsError, errorFunctionTwoInputs))
				// then
				.isInstanceOf(BaseApplicationException.class)
				.hasRootCauseInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining(errorMsg);
	}

	@Test
	@Order(13)
	@DisplayName("Test execute pos operation, two inputs")
	void executeDoubleInputOperationPosFunctionErrorTest() {
		// given
		final String errorMsg = "pos function error, two inputs";
		final var departmentDto = new DepartmenDto(department01.getName(), manager01.getName());

		final BiFunction<DepartmenDto, Object[], DepartmenDto> posFunctionTwoInputsError = (
				departmentDtoIn, directives) -> {
			throw new IllegalArgumentException(errorMsg);
		};

		assertThatThrownBy(
				// when
				() -> facade.execute(
						department01, manager01, operation, preFunctionTwoInputs, posFunctionTwoInputsError,
						(departmentIn, managerIn, directives) -> departmentDto, errorFunctionTwoInputs))
				// then
				.isInstanceOf(BaseApplicationException.class)
				.hasRootCauseInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining(errorMsg);
	}

	@Test
	@Order(14)
	@DisplayName("Test compose bi functions with retrow first one")
	void composeBiFunctionsUntilThrowExceptionTest() {
		// given
		final var facade = new TestEntiyBaseFacade(i18nService, interfaceSecurityService, exceptionTranslatorService);

		final var functions = new ArrayList<FacadeFunctionOneArg<Department>>(getBiFunctions());

		final var functionThrow01 = new FacadeFunctionOneArg<Department>() {

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

		final var functionThrow02 = new FacadeFunctionOneArg<Department>() {

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
		assertThatThrownBy(() -> facade.compose(department01, functions))
				// then
				.isInstanceOf(IllegalStateException.class)
				.hasMessageContaining("State exception");
	}

	@Test
	@Order(15)
	@DisplayName("Test compose tri functions with retrow first one")
	void composeTriFunctionsUntilThrowExceptionTest() {
		// given
		final var facade = new TestEntiyBaseFacade(i18nService, interfaceSecurityService, exceptionTranslatorService);
		final var exception = new NullPointerException("null");

		final var functions = new ArrayList<FacadeFunctionTwoArgs<Exception, Department>>(getTriFunctions());
		final var functionThrow01 = new FacadeFunctionTwoArgs<Exception, Department>() {
			@Override
			public Exception apply(final Exception exception, final Department department, final Object[] directives) {
				throw new IllegalStateException("State exception");
			}

			@Override
			public boolean reTrowException() {
				return true;
			}
		};

		final var functionThrow02 = new FacadeFunctionTwoArgs<Exception, Department>() {
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
		assertThatThrownBy(() -> facade.compose(exception, department01, functions))
				// then
				.isInstanceOf(IllegalStateException.class)
				.hasMessageContaining("State exception");
	}
}
