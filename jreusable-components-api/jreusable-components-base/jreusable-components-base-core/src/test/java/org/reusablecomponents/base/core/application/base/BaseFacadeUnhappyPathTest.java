package org.reusablecomponents.base.core.application.base;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.AbstractMap;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import org.apache.commons.lang3.function.TriFunction;
import org.application_example.domain.Department;
import org.application_example.domain.Manager;
import org.junit.jupiter.api.BeforeAll;
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
import org.reusablecomponents.base.core.application.empty.EmptyFacade;
import org.reusablecomponents.base.core.infra.util.QuadFunction;
import org.reusablecomponents.base.core.infra.util.operation.CommandOperation;
import org.reusablecomponents.base.core.infra.util.operation.InterfaceOperation;

@Tag("unit")
@DisplayName("Test the EntiyBaseFacade entity test, unhappy Path :( ")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class BaseFacadeUnhappyPathTest {

	Manager defaultManager;

	EmptyFacade<Department, String> facade;

	String msg;

	@BeforeAll
	void setUpAll() {
		defaultManager = new Manager("x2", "Business Happy");
		facade = new EmptyFacade<>();
		msg = "Please pass a non-null '%s'";
	}

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
		final var in = new Department("x1", "Development 01", "Technology", defaultManager);
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

	@Order(1)
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
		final var in1 = new Department("x1", "Development 01", "Technology", defaultManager);
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

	@Order(2)
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

		final UnaryOperator<Object[]> preFunction = directives -> directives;
		final BiFunction<Department, Object[], Department> posFunction = (department, directives) -> department;
		final Function<Object[], Department> mainFunction = directives -> null;
		final BiFunction<Exception, Object[], Exception> errorFunction = (exception, directives) -> exception;

		final Object[] directives = new Object[] {};

		return Stream.of(
				Arguments.of(null, preFunction, posFunction, mainFunction, errorFunction,
						directives,
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

	@Order(3)
	@ParameterizedTest(name = "Pos {index} : department ''{0}'', exception ''{1}''")
	@MethodSource("createInvalidExecuteData03")
	@DisplayName("Try to execute 03 invalid test")
	void invalidExecuteTest03(
			final InterfaceOperation operation,
			final UnaryOperator<Object[]> preFunction,
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
}
