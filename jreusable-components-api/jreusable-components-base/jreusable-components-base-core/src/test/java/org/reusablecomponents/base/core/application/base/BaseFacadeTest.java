package org.reusablecomponents.base.core.application.base;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.application_example.application.TestEntiyBaseFacade;
import org.application_example.domain.Department;
import org.application_example.domain.Manager;
import org.application_example.infra.DummySecurityService;
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
import org.reusablecomponents.base.core.application.command.entity.function.save.ErrorSaveFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save.PosSaveFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save.PreSaveFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save.SaveFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.count_all.CountAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.count_all.ErrorCountAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.count_all.PosCountAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.count_all.PreCountAllFunction;
import org.reusablecomponents.base.core.infra.exception.DefaultExceptionAdapterService;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.util.function.operation.custom.CustomOperation1Args;
import org.reusablecomponents.base.core.infra.util.function.operation.custom.CustomOperation2Args;
import org.reusablecomponents.base.core.infra.util.function.operation.custom.CustomOperation3Args;
import org.reusablecomponents.base.core.infra.util.function.operation.custom.CustomOperation4Args;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;

@Tag("unit")
@SuppressWarnings("null")
@DisplayName("Test the EntiyBaseFacade entity test")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class BaseFacadeTest {

	static final String ERROR_MSG = "Error Msg";
	static final String MSG = "Please pass a non-null '%s'";

	final InterfaceI18nService i18nService = (code, params) -> "translated!";
	final InterfaceSecurityService dummySecurityService = new DummySecurityService();
	final InterfaceExceptionAdapterService exceptionAdapterService = new DefaultExceptionAdapterService();
	final TestEntiyBaseFacade facade = new TestEntiyBaseFacade();

	Manager manager01;
	Manager manager02;
	Department department01;
	Department department02;

	final Consumer<Object[]> preFunctionNoInput = IO::println;

	final CustomOperation1Args<Object[], Department> oneInputFunction01 = directives -> department01;

	final CustomOperation1Args<Department, Department> oneInputFunction02 = department -> department;

	final CustomOperation1Args<Object[], Object[]> oneInput03Function = directives -> directives;

	final CustomOperation1Args<Object[], Department> oneInputExceptionFunction01 = directives -> {
		throw new IllegalArgumentException(ERROR_MSG);
	};

	final CustomOperation1Args<Department, Department> oneInputExceptionFunction02 = department -> {
		throw new IllegalArgumentException(ERROR_MSG);
	};

	final CustomOperation1Args<Object[], Object[]> oneInputExceptionFunction03 = directives -> {
		throw new IllegalArgumentException(ERROR_MSG);
	};

	final CustomOperation2Args<Department, Object[], Department> twoInputsFunction = (
			departmentIn, directives) -> departmentIn;

	final CustomOperation3Args<Department, Manager, Object[], Department> threeInputsFunction = (
			departmentIn, managerIn, directives) -> departmentIn;

	final CustomOperation2Args<Department, Object[], Department> twoInputsExceptionFunction = (
			departmentIn, directives) -> {
		throw new IllegalArgumentException(ERROR_MSG);
	};

	final CustomOperation3Args<Department, Manager, Object[], Department> threeInputsExceptionFunction = (
			departmentIn, managerIn, directives) -> {
		throw new IllegalArgumentException(ERROR_MSG);
	};

	final CustomOperation2Args<BaseException, Object[], BaseException> twoInputsErrorFunction = (
			exception, directives) -> exception;

	final CustomOperation3Args<BaseException, Department, Object[], BaseException> threeInputsErrorFunction = (
			exception, departmentIn, directives) -> exception;

	final CustomOperation4Args<BaseException, Department, Manager, Object[], BaseException> fourInputsErrorFunction = (
			exception, departmentIn, managerIn, directives) -> exception;

	// ------------------------------------------------------

	@BeforeEach
	void setUpEach() {
		manager01 = new Manager("x1", "Business Happy");
		manager02 = new Manager("x2", "Mister B");
		department01 = new Department("00001", "Development 01", "Technology", manager01);
		department02 = new Department("00002", "Development 02", "Technology", manager02);
	}

	@Test
	@Order(1)
	@DisplayName("Given constructor values, when creating facade, then values are set")
	void givenConstructorValues_whenCreatingFacade_thenValuesAreSet() {
		// given
		final var myExceptionAdapterService = exceptionAdapterService;
		final var myDummySecurityService = new DummySecurityService() {
			@Override
			public String getUserName() {
				return "Fernando";
			}
		};

		// when
		final var facade = assertDoesNotThrow(
				() -> new TestEntiyBaseFacade(i18nService, myDummySecurityService, myExceptionAdapterService));

		// then
		assertThat(facade)
				.satisfiesAnyOf(
						f -> {
							assertThat(f)
									.as(format(
											"Check the securityService, is not null and return the correct user name"))
									.extracting(TestEntiyBaseFacade::getSecurityService)
									.isNotNull()
									.extracting(InterfaceSecurityService::getUserName)
									.isEqualTo("Fernando");
						}, f -> {
							assertThat(f)
									.as(format("Check the exceptionAdapterService, and i18nService are not null"))
									.extracting("exceptionAdapterService", "i18nService")
									.doesNotContainNull();
						},
						f -> {
							assertThat(f)
									.extracting("entityClazz", "idClazz")
									.as(format("Check the entityClazz ''{0}'' and idClazz ''{1}''", Department.class,
											String.class))
									.containsExactly(Department.class, String.class);
						});
	}

	@Test
	@Order(2)
	@DisplayName("Given builder is null, when creating facade, then throw exception")
	void givenBuilderIsNull_whenCreatingFacade_thenThrowException() {
		assertThatThrownBy(() -> new BaseFacade<Department, String>(null))
				// then
				.isInstanceOf(NullPointerException.class)
				.hasMessageContaining("The object 'builder' cannot be null");

	}

	@Test
	@Order(3)
	@DisplayName("Given valid functions, when executing execute with no input, then return result")
	void givenValidFunctions_whenExecutingExecuteWithNoInput_thenReturnResult() {
		// given
		final PreCountAllFunction preFunction = (directives) -> directives;
		final CountAllFunction<Long> mainFunction = (directives) -> 1L;
		final PosCountAllFunction<Long> posFunction = (countResult, directives) -> countResult;
		final ErrorCountAllFunction errorFunction = (exception, directives) -> exception;

		// when
		final var result = assertDoesNotThrow(
				() -> facade.execute(
						preFunction,
						mainFunction,
						posFunction,
						errorFunction));

		// then
		assertThat(result).isEqualTo(1L);
	}

	@Test
	@Order(4)
	@DisplayName("Given valid functions, when executing execute with one input, then return result")
	void givenValidFunctions_whenExecutingExecuteWithOneInput_thenReturnResult() {
		// given
		final var in = new Department("x1", "Development 01", "Technology", manager01);

		final PreSaveFunction<Department> preFunction = (department, directives) -> department;
		final SaveFunction<Department, Department> mainFunction = (department, directives) -> {
			department.update("newName", "otherValue");
			return department;
		};
		final PosSaveFunction<Department> posFunction = (department, directives) -> department;
		final ErrorSaveFunction<Department> errorFunction = (exception, department, directives) -> exception;

		// when
		final var result = assertDoesNotThrow(
				() -> facade.execute(
						in,
						preFunction,
						mainFunction,
						posFunction,
						errorFunction));

		// then
		assertThat(result)
				.extracting(Department::getName, Department::getSector)
				.containsExactly("newName", "otherValue");
	}

	@Test
	@Order(5)
	@DisplayName("Given valid functions, when executing execute with two inputs, then return result")
	void givenValidFunctions_whenExecutingExecuteWithTwoInputs_thenReturnResult() {
		// given
		final var in1 = new Department("x1", "Development 01", "Technology", manager01);
		final var in2 = new Manager("x3", "Business Happy");

		final CustomOperation3Args<Department, Manager, Object[], Department> preFunction = (
				department, manager, directives) -> department;
		final CustomOperation3Args<Department, Manager, Object[], Department> mainFunction = (
				department, manager, directives) -> {
			department.update("newName", "otherValue");
			return department;
		};
		final CustomOperation2Args<Department, Object[], Department> posFunction = (
				department, directives) -> department;
		final CustomOperation4Args<BaseException, Department, Manager, Object[], BaseException> errorFunction = (
				exception, department, manager, directives) -> exception;

		// when
		final var result = assertDoesNotThrow(
				() -> facade.execute(in1, in2, preFunction, mainFunction, posFunction, errorFunction));

		// then
		assertThat(result)
				.extracting(Department::getName, Department::getSector)
				.containsExactly("newName", "otherValue");
	}

	// given
	Stream<Arguments> createInvalidExecuteNoInputData() {
		final PreCountAllFunction preFunction = (directives) -> directives;
		final PreCountAllFunction preFunctionNull = null;

		final CountAllFunction<Long> mainFunction = (directives) -> 1L;
		final CountAllFunction<Long> mainFunctionNull = null;

		final PosCountAllFunction<Long> posFunction = (countResult, directives) -> countResult;
		final PosCountAllFunction<Long> posFunctionNull = null;

		final ErrorCountAllFunction errorFunction = (exception, directives) -> exception;
		final ErrorCountAllFunction errorFunctionNull = null;

		final Object[] directives = new Object[] {};
		final Object[] directivesNull = null;

		return Stream.of(
				Arguments.of(preFunctionNull, mainFunction, posFunction, errorFunction, directives,
						MSG.formatted("preFunction")),
				Arguments.of(preFunction, mainFunctionNull, posFunction, errorFunction, directives,
						MSG.formatted("mainFunction")),
				Arguments.of(preFunction, mainFunction, posFunctionNull, errorFunction, directives,
						MSG.formatted("posFunction")),
				Arguments.of(preFunction, mainFunction, posFunction, errorFunctionNull, directives,
						MSG.formatted("errorFunction")),
				Arguments.of(preFunction, mainFunction, posFunction, errorFunction, directivesNull,
						MSG.formatted("directives")));
	}

	@Order(6)
	@ParameterizedTest(name = "Pos {index} : mainFunction ''{1}''")
	@MethodSource("createInvalidExecuteNoInputData")
	@DisplayName("Given invalid data, when executing execute with no input, then throw exception")
	void givenInvalidData_whenExecutingExecuteNoInput_thenThrowException(
			final PreCountAllFunction preFunction,
			final CountAllFunction<Long> mainFunction,
			final PosCountAllFunction<Long> posFunction,
			final ErrorCountAllFunction errorFunction,
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

	// given
	Stream<Arguments> createInvalidExecuteWithOneInputData() {
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
						MSG.formatted("in")),
				Arguments.of(in, preFunctionNull, mainFunction, posFunction, errorFunction, directives,
						MSG.formatted("preFunction")),
				Arguments.of(in, preFunction, mainFunctionNull, posFunction, errorFunction, directives,
						MSG.formatted("mainFunction")),
				Arguments.of(in, preFunction, mainFunction, posFunctionNull, errorFunction, directives,
						MSG.formatted("posFunction")),
				Arguments.of(in, preFunction, mainFunction, posFunction, errorFunctionNull, directives,
						MSG.formatted("errorFunction")),
				Arguments.of(in, preFunction, mainFunction, posFunction, errorFunction, directivesNull,
						MSG.formatted("directives")));
	}

	@Order(7)
	@ParameterizedTest(name = "Pos {index} : department ''{0}'', mainFunction ''{1}''")
	@MethodSource("createInvalidExecuteWithOneInputData")
	@DisplayName("Given invalid data, when executing execute with one input, then throw exception")
	void givenInvalidData_whenExecutingExecuteWithOneInput_thenThrowException(
			final Department in,
			final PreSaveFunction<Department> preFunction,
			final SaveFunction<Department, Department> mainFunction,
			final PosSaveFunction<Department> posFunction,
			final ErrorSaveFunction<Department> errorFunction,
			final Object[] directives,
			final String exceptionMessage) {
		// when
		assertThatThrownBy(
				() -> facade.execute(
						in,
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
	Stream<Arguments> createInvalidExecuteWithTwoInputsData() {
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
						MSG.formatted("in1")),
				Arguments.of(in1, in2Null, preFunction, mainFunction, posFunction, errorFunction, directives,
						MSG.formatted("in2")),
				Arguments.of(in1, in2, preFunctionNull, mainFunction, posFunction, errorFunction, directives,
						MSG.formatted("preFunction")),
				Arguments.of(in1, in2, preFunction, mainNull, posFunction, errorFunction, directives,
						MSG.formatted("mainFunction")),
				Arguments.of(in1, in2, preFunction, mainFunction, posFunctionNull, errorFunction, directives,
						MSG.formatted("posFunction")),
				Arguments.of(in1, in2, preFunction, mainFunction, posFunction, errorFunctionNull, directives,
						MSG.formatted("errorFunction")),
				Arguments.of(in1, in2, preFunction, mainFunction, posFunction, errorFunction, directivesNull,
						MSG.formatted("directives")));
	}

	@Order(8)
	@ParameterizedTest(name = "Pos {index} : department ''{0}'', mainFunction ''{1}''")
	@MethodSource("createInvalidExecuteWithTwoInputsData")
	@DisplayName("Given invalid data, when executing execute with two inputs, then throw exception")
	void givenInvalidData_whenExecutingExecuteWithTwoInputs_thenThrowException(
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
	Stream<Arguments> createInvalidExecuteWithThreeInputsData() {
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
						MSG.formatted("preFunction")),
				Arguments.of(preFunction, mainFunctionNull, posFunction, errorFunction, directives,
						MSG.formatted("mainFunction")),
				Arguments.of(preFunction, mainFunction, posFunctionNull, errorFunction, directives,
						MSG.formatted("posFunction")),
				Arguments.of(preFunction, mainFunction, posFunction, errorFunctionNull, directives,
						MSG.formatted("errorFunction")),
				Arguments.of(preFunction, mainFunction, posFunction, errorFunction, directivesNull,
						MSG.formatted("directives")));
	}

	@Order(9)
	@ParameterizedTest(name = "Pos {index} : department ''{0}'', exception ''{1}''")
	@MethodSource("createInvalidExecuteWithThreeInputsData")
	@DisplayName("Given invalid data, when executing execute with three inputs, then throw exception")
	void givenInvalidData_whenExecutingExecuteWithThreeInputs_thenThrowException(
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
	@Order(10)
	@DisplayName("Given pre operation with error, when executing execute with no input, then throw exception")
	void givenPreOperationWithError_thenExecuteNoInput_thenThrowException() {
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
	@Order(11)
	@DisplayName("Given main operation with error, when executing execute with no input, then throw exception")
	void givenMainOperationWithError_thenExecuteNoInput_thenThrowException() {
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
	@Order(12)
	@DisplayName("Given pos operation with error, when executing execute with no input, then throw exception")
	void givenPosOperationWithError_thenExecuteNoInput_thenThrowException() {
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
	@Order(13)
	@DisplayName("Given pre operation with error, when executing execute with one input, then throw exception")
	void givenPreOperationWithError_whenExecutingExecuteWithOneInput_thenThrowException() {
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
	@Order(14)
	@DisplayName("Given main operation with error, when executing execute with one input, then throw exception")
	void givenMainOperationWithError_whenExecutingExecuteWithOneInput_thenThrowException() {
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
	@Order(15)
	@DisplayName("Given pos operation with error, when executing execute with one input, then throw exception")
	void givenPosOperationWithError_whenExecutingExecuteWithOneInput_thenThrowException() {
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

	@Test
	@Order(16)
	@DisplayName("Given pre operation with error, when executing execute with two inputs, then throw exception")
	void givenPreOperationWithError_whenExecutingExecuteWithTwoInputs_thenThrowException() {
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
	@Order(17)
	@DisplayName("Given main operation with error, when executing execute with two inputs, then throw exception")
	void givenMainOperationWithError_whenExecutingExecuteWithTwoInputs_thenThrowException() {
		// given
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
	@Order(18)
	@DisplayName("Given pos operation with error, when executing execute with two inputs, then throw exception")
	void givenPosOperationWithError_whenExecutingExecuteWithTwoInputs_thenThrowException() {
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
