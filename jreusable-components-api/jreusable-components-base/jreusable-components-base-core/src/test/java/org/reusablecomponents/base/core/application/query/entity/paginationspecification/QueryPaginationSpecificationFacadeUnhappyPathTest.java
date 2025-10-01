package org.reusablecomponents.base.core.application.query.entity.paginationspecification;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.leftPad;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.application_example.application.query.entity.paged.DeparmentQueryPaginationSpecificationFacade;
import org.application_example.application.query.entity.paged.PageList;
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
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.executable.ExecutableValidator;

@Tag("unit")
@DisplayName("Test the QueryPaginationSpecificationFacade entity test, happy Path :) ")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class QueryPaginationSpecificationFacadeUnhappyPathTest {

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
	final DeparmentQueryPaginationSpecificationFacade defaultQueryFacade = new DeparmentQueryPaginationSpecificationFacade(
			defaultData);

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

		var i = 3;
		var total = 20;

		while (i++ < total) {
			final var counterString = leftPad("" + i, 2, "0");
			defaultData.add(new Department("x" + i, "Default " + counterString, "Sector " + counterString,
					manager));
		}
	}

	@AfterAll
	void tearDown() {
		// No requirements yet
		VALIDATOR_FACTORY.close();
	}

	// given
	Stream<Arguments> findOneByWithNullParamsData() {
		final var sort = (Comparator<Department>) Comparator.comparing(Department::getName).reversed();
		final Predicate<Department> spec = department -> equalsIgnoreCase(department.getName(), "Default 01");

		return Stream.of(
				Arguments.of(sort, null, "in2"),
				Arguments.of(null, spec, "in1"));
	}

	@Order(1)
	@ParameterizedTest(name = "Pos {index} : sort ''{0}'', specification ''{1}''")
	@MethodSource("findOneByWithNullParamsData")
	@DisplayName("Find one sorted with null sort")
	void findOneByWithNullParamsTest(final Comparator<Department> sort, final Predicate<Department> specification,
			final String parameter) {

		// when
		assertThatThrownBy(() -> defaultQueryFacade.findOneBy(sort, specification))
				// then
				.isInstanceOf(NullPointerException.class)
				.hasMessageContaining("Please pass a non-null '%s'", parameter);
	}

	@Order(2)
	@ParameterizedTest(name = "Pos {index} : sort ''{0}'', specification ''{1}''")
	@MethodSource("findOneByWithNullParamsData")
	@DisplayName("Find one sorted with null sort")
	void findOneByWithNullParamsBeanValidationTest(
			final Comparator<Department> sort,
			final Predicate<Department> specification) throws NoSuchMethodException, SecurityException {

		final var method = defaultQueryFacade.getClass()
				.getMethod("findOneBy", Object.class, Object.class, Object[].class);

		final var violations = EXECUTABLE_VALIDATOR
				.validateParameters(defaultQueryFacade, method,
						new Object[] { sort, specification, new Object[] {} });

		assertThat(violations)
				.hasSize(1)
				.extracting(t -> t.getPropertyPath().toString(), ConstraintViolation::getMessage)
				.containsAnyOf(
						tuple("findOneBy.arg0", "The object cannot be null"),
						tuple("findOneBy.arg1", "The object cannot be null"));
	}

	// =====================================================================================================================

	// given
	Stream<Arguments> findByWithNullParamsData() {
		final var pageable = new PageList<Department>(5, 0, defaultData);
		final Predicate<Department> spec = department -> equalsIgnoreCase(department.getName(), "Default 01");

		return Stream.of(
				Arguments.of(pageable, null, "in2"),
				Arguments.of(null, spec, "in1"));
	}

	@Order(3)
	@ParameterizedTest(name = "Pos {index} : pageable ''{0}'', specification ''{1}''")
	@MethodSource("findByWithNullParamsData")
	@DisplayName("Find one sorted with null sort")
	void findByWithNullParamsTest(
			final PageList<Department> pageable,
			final Predicate<Department> specification,
			final String parameter) {

		// when
		assertThatThrownBy(() -> defaultQueryFacade.findBy(pageable, specification))
				// then
				.isInstanceOf(NullPointerException.class)
				.hasMessageContaining("Please pass a non-null '%s'", parameter);
	}

	@Order(4)
	@ParameterizedTest(name = "Pos {index} : pageable ''{0}'', specification ''{1}''")
	@MethodSource("findByWithNullParamsData")
	@DisplayName("Find one sorted with null sort")
	void findByWithNullParamsBeanValidationTest(
			final PageList<Department> pageable,
			final Predicate<Department> specification) throws NoSuchMethodException, SecurityException {

		final var method = defaultQueryFacade.getClass()
				.getMethod("findBy", Object.class, Object.class, Object[].class);

		final var violations = EXECUTABLE_VALIDATOR
				.validateParameters(defaultQueryFacade, method,
						new Object[] { pageable, specification, new Object[] {} });

		assertThat(violations)
				.hasSize(1)
				.extracting(t -> t.getPropertyPath().toString(), ConstraintViolation::getMessage)
				.containsAnyOf(
						tuple("findBy.arg0", "The object cannot be null"),
						tuple("findBy.arg1", "The object cannot be null"));
	}
}
