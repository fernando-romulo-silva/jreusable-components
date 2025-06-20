package org.reusablecomponents.base.core.application.query.entity.specification;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.application_example.application.query.entity.nonpaged.DeparmentQuerySpecificationFacade;
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
import org.reusablecomponents.base.core.infra.exception.common.ElementNotFoundException;
import org.reusablecomponents.base.core.infra.exception.common.ElementWithIdNotFoundException;
import org.reusablecomponents.base.core.infra.exception.common.InvalidSpecificationException;
import org.reusablecomponents.base.core.infra.exception.common.UnexpectedException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.executable.ExecutableValidator;

@Tag("unit")
@DisplayName("Test the EntityQuerySpecificationFacade entity test, unhappy path :( ")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class QuerySpecificationFacadeUnhappyPathTest {

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
	final DeparmentQuerySpecificationFacade defaultQueryFacade = new DeparmentQuerySpecificationFacade(defaultData);

	Department department01;
	Department department02;
	Department department03;

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
		department03 = new Department("x3", "Default 03", "IT", manager);

		defaultData.addAll(List.of(department01, department02, department03));
	}

	@AfterAll
	void tearDown() {
		// No requirements yet
		VALIDATOR_FACTORY.close();
	}

	@Test
	@Order(1)
	@DisplayName("Find one by specification with null specification test")
	void findOneBySpecWithNullSpecTest() {

		// given
		final Predicate<Department> specNull = null;

		// when
		assertThatThrownBy(() -> defaultQueryFacade.findOneBySpec(specNull))
				// then
				.isInstanceOf(NullPointerException.class)
				.hasMessageContaining("The object '%s' cannot be null", "preSpecificationIn");
	}

	@Test
	@Order(2)
	@DisplayName("Find one by specification test with specification not found test")
	void findOneBySpecWithSpecNotFoundTest() {

		// given
		final Predicate<Department> spec = department -> equalsIgnoreCase(department.getName(), "Whatever");

		// when
		assertThatThrownBy(() -> defaultQueryFacade.findOneBySpec(spec))
				// then
				.isInstanceOf(ElementNotFoundException.class)
				.hasMessageContaining("The id 'spec' not found for 'Department' type");
	}

	@Test
	@Order(3)
	@DisplayName("Find by specification test with unexpected error test")
	void findOneBySpecWithUnexpectedErrorTest() {

		// given
		final Predicate<Department> spec = department -> equalsIgnoreCase(department.getName(), "Default 01");
		final var directives = new Object[] { "error" };

		// when
		assertThatThrownBy(() -> defaultQueryFacade.findOneBySpec(spec, directives))
				// then
				.isInstanceOf(UnexpectedException.class)
				.hasMessageContaining("Unexpecte error happened");
	}

	@Test
	@Order(4)
	@DisplayName("Find by specification with null specification test")
	void findBySpecWithNullSpecTest() {

		// given
		final Predicate<Department> specNull = null;

		// when
		assertThatThrownBy(() -> defaultQueryFacade.findBySpec(specNull))
				// then
				.isInstanceOf(NullPointerException.class)
				.hasMessageContaining("The object '%s' cannot be null", "preSpecificationIn");
	}

	@Test
	@Order(5)
	@DisplayName("Find by specification with unexpected error test")
	void findBySpecWithUnexpectedErrorTest() {

		// given
		final Predicate<Department> spec = department -> equalsIgnoreCase(department.getName(),
				"Department 01");
		final var directives = new Object[] { "error" };

		// when
		assertThatThrownBy(() -> defaultQueryFacade.findBySpec(spec, directives))
				// then
				.isInstanceOf(UnexpectedException.class)
				.hasMessageContaining("Unexpecte error happened");
	}

	@Test
	@Order(6)
	@DisplayName("Count by specification with null specification test")
	void countBySpecWithNullSpecTest() {

		// given
		final Predicate<Department> specNull = null;

		// when
		assertThatThrownBy(() -> defaultQueryFacade.countBySpec(specNull))
				// then
				.isInstanceOf(NullPointerException.class)
				.hasMessageContaining("The object '%s' cannot be null", "preSpecificationIn");
	}

	@Test
	@Order(7)
	@DisplayName("Count by specification with unexpected error test")
	void countBySpecWithUnexpectedErrorTest() {

		// given
		final Predicate<Department> spec = department -> equalsIgnoreCase(department.getName(),
				"Department 01");
		final var directives = new Object[] { "error" };

		// when
		assertThatThrownBy(() -> defaultQueryFacade.countBySpec(spec, directives))
				// then
				.isInstanceOf(UnexpectedException.class)
				.hasMessageContaining("Unexpecte error happened");
	}

	@Test
	@Order(8)
	@DisplayName("Exists by specification with null spec test")
	void existsBySpecWithNullSpecTest() {

		// given
		final Predicate<Department> specNull = null;

		// given
		assertThatThrownBy(() -> defaultQueryFacade.existsBySpec(specNull))
				// then
				.isInstanceOf(NullPointerException.class)
				.hasMessageContaining("The object '%s' cannot be null", "preSpecificationIn");
	}

	@Test
	@Order(9)
	@DisplayName("Exists by specification with unexpected error test")
	void existsBySpecWithUnexpectedErrorTest() {

		// given
		final Predicate<Department> spec = department -> equalsIgnoreCase(department.getName(),
				"Department 01");
		final var directives = new Object[] { "error" };

		// given
		assertThatThrownBy(() -> defaultQueryFacade.existsBySpec(spec, directives))
				// then
				.isInstanceOf(UnexpectedException.class)
				.hasMessageContaining("Unexpecte error happened");
	}

	Stream<Arguments> methodWithNullSpecBeanValidationData() {
		return Stream.of(
				Arguments.of("findOneBySpec"),
				Arguments.of("existsBySpec"),
				Arguments.of("findBySpec"),
				Arguments.of("countBySpec"));
	}

	@Order(10)
	@ParameterizedTest(name = "Pos {index} : method ''{0}''")
	@MethodSource("methodWithNullSpecBeanValidationData")
	@DisplayName("Find one by specification with null specification test")
	void methodWithNullSpecBeanValidationTest(final String methodSring)
			throws NoSuchMethodException, SecurityException {

		final var method = defaultQueryFacade.getClass()
				.getMethod(methodSring, Object.class, Object[].class);

		final var violations = EXECUTABLE_VALIDATOR
				.validateParameters(defaultQueryFacade, method, new Object[] { null, new Object[] {} });

		assertThat(violations)
				.hasSize(1)
				.extracting(t -> t.getPropertyPath().toString(), ConstraintViolation::getMessage)
				.containsExactlyInAnyOrder(tuple(methodSring + ".arg0", "The object cannot be null"));
	}
}
