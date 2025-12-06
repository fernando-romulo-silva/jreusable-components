package org.reusablecomponents.base.core.application.base;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.stream.Stream;

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
import org.reusablecomponents.base.core.application.command.entity.function.save.ErrorSaveFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save.PosSaveFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save.PreSaveFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save.SaveFunction;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.util.function.operation.custom.CustomOperation1Args;
import org.reusablecomponents.base.core.infra.util.function.operation.custom.CustomOperation2Args;
import org.reusablecomponents.base.core.infra.util.function.operation.custom.CustomOperation3Args;
import org.reusablecomponents.base.core.infra.util.function.operation.custom.CustomOperation4Args;

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
		final var inNull = (Department) null;

		final PreSaveFunction<Department> preFunction = (department, directives) -> department;
		final PreSaveFunction<Department> preFunctionNull = null;

		final SaveFunction<Department, Department> mainFunction = (department, directives) -> department;
		final SaveFunction<Department, Department> mainFunctionNull = null;

		final PosSaveFunction<Department> posFunction = (department, directives) -> department;
		final PosSaveFunction<Department> posFunctionNull = null;

		final ErrorSaveFunction<Department> errorFunction = (exception, department, directives) -> exception;
		final ErrorSaveFunction<Department> errorFunctionNull = null;

		final Object[] directives = new Object[] {};
		final Object[] directivesNull = null;

		return Stream.of(
				Arguments.of(inNull, preFunction, mainFunction, posFunction, errorFunction, directives,
						msg.formatted("in")),
				Arguments.of(in, preFunctionNull, mainFunction, posFunction, errorFunction, directives,
						msg.formatted("preFunction")),
				Arguments.of(in, preFunction, mainFunctionNull, posFunction, errorFunction, directives,
						msg.formatted("mainFunction")),
				Arguments.of(in, preFunction, mainFunction, posFunctionNull, errorFunction, directives,
						msg.formatted("posFunction")),
				Arguments.of(in, preFunction, mainFunction, posFunction, errorFunctionNull, directives,
						msg.formatted("errorFunction")),
				Arguments.of(in, preFunction, mainFunction, posFunction, errorFunction, directivesNull,
						msg.formatted("directives")));
	}

	@Order(2)
	@ParameterizedTest(name = "Pos {index} : department ''{0}'', exception ''{1}''")
	@MethodSource("createInvalidExecuteData01")
	@DisplayName("Try to execute 01 invalid test")
	void invalidExecuteTest01(
			final Department in,
			final PreSaveFunction<Department> preFunction,
			final SaveFunction<Department, Department> mainFunction,
			final PosSaveFunction<Department> posFunction,
			final ErrorSaveFunction<Department> errorFunction,
			final Object[] directives,
			final String exceptionMessage) {
		// when
		assertThatThrownBy(
				() -> facade.execute(in,
						preFunction,
						mainFunction,
						posFunction,
						errorFunction,
						directives))
				// then
				.isInstanceOf(NullPointerException.class)
				.hasMessageContaining(exceptionMessage);
	}

	// given
	Stream<Arguments> createInvalidExecuteData02() {
		final var in1 = new Department("x1", "Development 01", "Technology", manager01);
		final var in1Null = (Department) null;

		final var in2 = new Manager("x3", "Business Happy");
		final var in2Null = (Manager) null;

		final CustomOperation3Args<Department, Manager, Object[], Department> preFunction = (
				department, manager, directives) -> department;
		final CustomOperation3Args<Department, Manager, Object[], Department> preFunctionNull = null;

		final CustomOperation2Args<Department, Object[], Department> posFunction = (
				department, directives) -> department;
		final CustomOperation2Args<Department, Object[], Department> posFunctionNull = null;

		final CustomOperation3Args<Department, Manager, Object[], Department> mainFunction = (
				department, manager, directives) -> department;
		final CustomOperation3Args<Department, Manager, Object[], Department> mainNull = null;

		final CustomOperation4Args<Department, Manager, Exception, Object[], Exception> errorFunction = (
				department, manager, exception, directives) -> exception;
		final CustomOperation4Args<Department, Manager, Exception, Object[], Exception> errorFunctionNull = null;

		final var directives = new Object[] {};
		final var directivesNull = (Object[]) null;

		return Stream.of(
				Arguments.of(in1Null, in2, preFunction, mainFunction, posFunction, errorFunction, directives,
						msg.formatted("in1")),
				Arguments.of(in1, in2Null, preFunction, mainFunction, posFunction, errorFunction, directives,
						msg.formatted("in2")),
				Arguments.of(in1, in2, preFunctionNull, mainFunction, posFunction, errorFunction, directives,
						msg.formatted("preFunction")),
				Arguments.of(in1, in2, preFunction, mainNull, posFunction, errorFunction, directives,
						msg.formatted("mainFunction")),
				Arguments.of(in1, in2, preFunction, mainFunction, posFunctionNull, errorFunction, directives,
						msg.formatted("posFunction")),
				Arguments.of(in1, in2, preFunction, mainFunction, posFunction, errorFunctionNull, directives,
						msg.formatted("errorFunction")),
				Arguments.of(in1, in2, preFunction, mainFunction, posFunction, errorFunction, directivesNull,
						msg.formatted("directives")));
	}

	@Order(3)
	@ParameterizedTest(name = "Pos {index} : department ''{0}'', exception ''{1}''")
	@MethodSource("createInvalidExecuteData02")
	@DisplayName("Try to execute 02 invalid test 2")
	void invalidExecuteTest02(
			final Department in1,
			final Manager in2,
			final CustomOperation3Args<Department, Manager, Object[], Department> preFunction,
			final CustomOperation3Args<Department, Manager, Object[], Department> mainFunction,
			final CustomOperation2Args<Department, Object[], Department> posFunction,
			final CustomOperation4Args<BaseException, Department, Manager, Object[], BaseException> errorFunction,
			final Object[] directives,
			final String exceptionMessage) {
		// when
		assertThatThrownBy(
				() -> facade.execute(in1, in2, preFunction, mainFunction, posFunction, errorFunction, directives))
				// then
				.isInstanceOf(NullPointerException.class)
				.hasMessageContaining(exceptionMessage);
	}

	// given
	Stream<Arguments> createInvalidExecuteData03() {
		final CustomOperation1Args<Object[], Object[]> preFunction = directives -> directives;
		final CustomOperation1Args<Object[], Object[]> preFunctionNull = null;

		final CustomOperation1Args<Object[], Department> mainFunction = directives -> department01;
		final CustomOperation1Args<Object[], Department> mainFunctionNull = null;

		final CustomOperation2Args<Department, Object[], Department> posFunction = (
				department, directives) -> department;
		final CustomOperation2Args<Department, Object[], Department> posFunctionNull = null;

		final CustomOperation2Args<BaseException, Object[], Exception> errorFunction = (
				exception, directives) -> exception;
		final CustomOperation2Args<BaseException, Object[], Exception> errorFunctionNull = null;

		final var directives = new Object[] {};
		final var directivesNull = (Object[]) null;

		return Stream.of(
				Arguments.of(preFunctionNull, mainFunction, posFunction, errorFunction, directives,
						msg.formatted("preFunction")),
				Arguments.of(preFunction, mainFunctionNull, posFunction, errorFunction, directives,
						msg.formatted("mainFunction")),
				Arguments.of(preFunction, mainFunction, posFunctionNull, errorFunction, directives,
						msg.formatted("posFunction")),
				Arguments.of(preFunction, mainFunction, posFunction, errorFunctionNull, directives,
						msg.formatted("errorFunction")),
				Arguments.of(preFunction, mainFunction, posFunction, errorFunction, directivesNull,
						msg.formatted("directives")));
	}

	@Order(4)
	@ParameterizedTest(name = "Pos {index} : department ''{0}'', exception ''{1}''")
	@MethodSource("createInvalidExecuteData03")
	@DisplayName("Try to execute 03 invalid test")
	void invalidExecuteTest03(
			final CustomOperation1Args<Object[], Object[]> preFunction,
			final CustomOperation1Args<Object[], Department> mainFunction,
			final CustomOperation2Args<Department, Object[], Department> posFunction,
			final CustomOperation2Args<BaseException, Object[], BaseException> errorFunction,
			final Object[] directives,
			final String exceptionMessage) {
		// when
		assertThatThrownBy(
				() -> facade.execute(
						preFunction,
						mainFunction,
						posFunction,
						errorFunction,
						directives))
				// then
				.isInstanceOf(NullPointerException.class)
				.hasMessageContaining(exceptionMessage);
	}

	@Test
	@Order(5)
	@DisplayName("Test execute pre operation error, no inputs")
	void executeNoInputOperationPreFunctionErrorTest() {
		// given
		assertThatThrownBy(
				// when
				() -> facade.execute(
						oneInputExceptionFunction03,
						oneInputFunction01,
						twoInputsFunction,
						twoInputsErrorFunction))
				// then
				.isInstanceOf(BaseException.class)
				.hasRootCauseInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining(ERROR_MSG);
	}

	@Test
	@Order(6)
	@DisplayName("Test execute no inputs")
	void executeNoInputOperationMainFunctionErrorTest() {
		// given
		assertThatThrownBy(
				// when
				() -> facade.execute(
						oneInput03Function,
						oneInputExceptionFunction01,
						twoInputsFunction,
						twoInputsErrorFunction))
				// then
				.isInstanceOf(BaseException.class)
				.hasRootCauseInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining(ERROR_MSG);
	}

	@Test
	@Order(7)
	@DisplayName("Test execute pos operation error, no inputs")
	void executeNoInputOperationPosFunctionErrorTest() {
		// given
		assertThatThrownBy(
				// when
				() -> facade.execute(
						oneInput03Function,
						oneInputFunction01,
						twoInputsExceptionFunction,
						twoInputsErrorFunction))
				// then
				.isInstanceOf(BaseException.class)
				.hasRootCauseInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining(ERROR_MSG);
	}

	@Test
	@Order(8)
	@DisplayName("Test execute pre one input")
	void executeSingleInputOperationPreFunctionErrorTest() {
		// given
		assertThatThrownBy(
				// when
				() -> facade.execute(
						department01,
						twoInputsExceptionFunction,
						twoInputsFunction,
						twoInputsFunction,
						threeInputsErrorFunction))
				// then
				.isInstanceOf(BaseException.class)
				.hasRootCauseInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining(ERROR_MSG);
	}

	@Test
	@Order(9)
	@DisplayName("Test execute one input")
	void executeSingleInputOperationMainFunctionErrorTest() {
		// given
		assertThatThrownBy(
				// when
				() -> facade.execute(
						department01,
						twoInputsFunction,
						twoInputsExceptionFunction,
						twoInputsFunction,
						threeInputsErrorFunction))
				// then
				.isInstanceOf(BaseException.class)
				.hasRootCauseInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining(ERROR_MSG);
	}

	@Test
	@Order(10)
	@DisplayName("Test execute pos one input")
	void executeSingleInputOperationPosFunctionErrorTest() {
		// given
		assertThatThrownBy(
				// when
				() -> facade.execute(
						department01,
						twoInputsFunction,
						twoInputsFunction,
						twoInputsExceptionFunction,
						threeInputsErrorFunction))
				// then
				.isInstanceOf(BaseException.class)
				.hasRootCauseInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining(ERROR_MSG);
	}

	// --------------------------------------------------------------------------------------------------

	@Test
	@Order(11)
	@DisplayName("Test execute pre two inputs")
	void executeDoubleInputOperationPreFunctionErrorTest() {
		// given
		assertThatThrownBy(
				// when
				() -> facade.execute(
						department01, manager01,
						threeInputsExceptionFunction,
						threeInputsFunction,
						twoInputsFunction,
						fourInputsErrorFunction))// then
				.isInstanceOf(BaseException.class)
				.hasRootCauseInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining(ERROR_MSG);
	}

	@Test
	@Order(12)
	@DisplayName("Test execute two inputs")
	void executeDoubleInputOperationMainFunctionErrorTest() {
		// given
		final String errorMsg = "main function error, two inputs";

		assertThatThrownBy(
				// when
				() -> facade.execute(
						department01, manager01,
						threeInputsFunction,
						threeInputsExceptionFunction,
						twoInputsFunction,
						fourInputsErrorFunction))
				// then
				.isInstanceOf(BaseException.class)
				.hasRootCauseInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining(ERROR_MSG);
	}

	@Test
	@Order(13)
	@DisplayName("Test execute pos two inputs")
	void executeDoubleInputOperationPosFunctionErrorTest() {
		// given
		assertThatThrownBy(
				// when
				() -> facade.execute(
						department01, manager01,
						threeInputsFunction,
						threeInputsFunction,
						twoInputsExceptionFunction,
						fourInputsErrorFunction))
				// then
				.isInstanceOf(BaseException.class)
				.hasRootCauseInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining(ERROR_MSG);
	}
}
