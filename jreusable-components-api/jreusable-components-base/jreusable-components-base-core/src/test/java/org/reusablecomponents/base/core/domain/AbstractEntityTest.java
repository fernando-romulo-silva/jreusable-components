package org.reusablecomponents.base.core.domain;

import static com.github.jinahya.assertj.validation.ValidationAssertions.assertThatBean;
import static com.jparams.verifier.tostring.NameStyle.NAME;
import static java.text.MessageFormat.format;
import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static nl.jqno.equalsverifier.Warning.NONFINAL_FIELDS;
import static nl.jqno.equalsverifier.Warning.REFERENCE_EQUALITY;
import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.application_example.domain.Gender.FEMALE;
import static org.application_example.domain.Gender.MALE;
import static org.application_example.domain.Hobby.COOKING;
import static org.application_example.domain.Hobby.READING;
import static org.application_example.domain.Hobby.VIDEO_GAMMING;
import static org.application_example.domain.Hobby.WATCHING_MOVIES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import org.application_example.domain.Department;
import org.application_example.domain.Gender;
import org.application_example.domain.Hobby;
import org.application_example.domain.Manager;
import org.application_example.domain.Notification;
import org.application_example.domain.Person;
import org.application_example.domain.Project;
import org.application_example.infra.Utils;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.jparams.verifier.tostring.ToStringVerifier;

import jakarta.validation.ConstraintViolationException;
import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Test the {@link AbstractEntity} class.
 * 
 * @author Fernando Romulo da Silva
 */
@Tag("unit")
@DisplayName("Test the AbstractEntity entity test")
@SuppressWarnings("null")
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class AbstractEntityTest {

	static boolean updateValue(final Object object, final String fieldName, final Object fieldValue) {

		Class<?> clazz = object.getClass();

		while (clazz != null) {

			try {

				var field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				field.set(object, fieldValue);

				return true;

			} catch (final NoSuchFieldException ex) {
				clazz = clazz.getSuperclass();
			} catch (final Exception ex) {
				throw new IllegalStateException(ex);
			}
		}

		return false;
	}

	@BeforeEach
	void setUp() {
		Locale.setDefault(Locale.ENGLISH); // expecting english error messages
	}

	@AfterAll
	void tearDown() {
		Locale.setDefault(Locale.getDefault()); // restore the default locale
	}

	@Test
	@Order(1)
	// @Disabled("EqualsVerifier not support Java 21")
	@DisplayName("Test the equals And HashCode Contract")
	void givenEntity_whenEqualsAndHashCode_thenVerifyContract() { // NOPMD - JUnitTestsShouldIncludeAssert:
																	// EqualsVerifier already do it
		EqualsVerifier.forClass(Person.class)
				.suppress(NONFINAL_FIELDS, STRICT_INHERITANCE, REFERENCE_EQUALITY)
				.withOnlyTheseFields("id")
				.withPrefabValues(Long.class, 1L, 2L)
				.verify();
	}

	@Test
	@Order(2)
	@DisplayName("Test the toString method")
	void givenEntity_whenToString_thenReturnString() { // NOPMD - JUnitTestsShouldIncludeAssert: ToStringVerifier
														// already do it
		ToStringVerifier.forClass(Person.class)
				.withClassName(NAME)
				.withFailOnExcludedFields(false)
				.verify();
	}

	@Test
	@Order(3)
	@DisplayName("Test the entity update")
	void givenEntity_whenUpdate_thenUpdateEntity() {
		// given
		final var company = new Manager("x2", "Business Happy");
		final var department = new Department("00001", "Development 01", "Technology", company);

		// When
		assertDoesNotThrow(() -> department.update("Development 02", department.getSector()));

		final var createdDate = department.getCreatedDate();

		// then
		assertThat(department.getUpdatedDate())
				.isPresent()
				.get(InstanceOfAssertFactories.LOCAL_DATE_TIME)
				.as(format("Check the update date is before or equal to createdDate ''{0}''", createdDate))
				.isAfterOrEqualTo(createdDate);

		assertThat(department.getUpdatedReason())
				.isPresent()
				.get(InstanceOfAssertFactories.STRING)
				.as(format("Check the update reason is not empty"))
				.isNotBlank();
	}

	@Test
	@Order(4)
	void givenEntityWithoutValidator_whenCreate_thenNoExceptionRaised() {
		// given
		final var id = 10L;
		final var name = "Notification 01";
		final var description = "This is a description";
		final var dateTime = LocalDateTime.now();
		final var sequence = 10;

		// when
		final var notification = assertDoesNotThrow(() -> new Notification.Builder()
				.with($ -> {
					$.id = id;
					$.name = name;
					$.description = description;
					$.dateTime = dateTime;
					$.sequence = sequence;
				}).build());

		// then
		assertThat(notification.getId())
				.isEqualTo(id);
	}

	@Test
	@Order(5)
	@DisplayName("Test entity without builder creation")
	void givenEntityWithoutValidatorAndBuilder_whenCreate_thenNoExceptionRaised() {
		// given
		final var id = "00001";
		final var name = "Development 01";
		final var sector = "Technology";
		final var company = new Manager("x2", "Business Happy");

		// When
		final var department = assertDoesNotThrow(() -> new Department(id, name, sector, company));

		// then
		assertThat(department)
				.as("Check the update reason is not empty")
				.returns(true, Department::isPublishable)
				.returns(id, Department::getId)
				.returns(EMPTY, Department::getRealmId);

		// perform beans validation
		assertThatBean(department)
				.isValid();
	}

	Stream<Arguments> createEntityPersonValidData() {
		final var createdDate = LocalDateTime.now();
		// given
		return Stream.of(
				Arguments.of(33L, "Paul", createdDate, "New Person", 25, MALE, "Brazil", LocalDate.of(1980, 6, 23),
						new ArrayList<>(asList(COOKING, VIDEO_GAMMING))),
				Arguments.of(25L, "Maira", createdDate, "Another Person", 25, FEMALE, "Panama",
						LocalDate.of(2000, 2, 15), new ArrayList<>(asList(READING, WATCHING_MOVIES))));
	}

	@Order(6)
	@ParameterizedTest(name = "Pos {index} : id ''{0}'', name ''{1}'', createdDate ''{2}'', createdReason ''{3}''")
	@MethodSource("createEntityPersonValidData")
	@DisplayName("Test entity with builder creation")
	void givenValidValues_whenCreateWithValidatorAndBuilder_thenNoExceptionRaised(
			final Long id, final String name,
			final LocalDateTime createdDate,
			final String createdReason, final Integer score, final Gender gender, final String country,
			final LocalDate birthDate, final List<Hobby> hobbies) {

		// when
		final var person = assertDoesNotThrow(() -> new Person.Builder().with($ -> {
			$.id = id;
			$.createdReason = createdReason;
			$.name = name;
			$.score = score;
			$.country = country;
			$.birthDate = birthDate;
			$.hobbies = hobbies;
			$.gender = gender;
		}).build());

		updateValue(person, "createdDate", createdDate);

		final var msg = format(
				"Check the name ''{0}'', createdDate ''{1}'' createdReason ''{2}'', score ''{3}'', gender ''{4}'', country ''{5}'', birthDate ''{6}'', and hobbies ''{7}''",
				name, createdDate, createdReason, score, gender, country, birthDate, hobbies);

		// then
		assertThat(person)
				.as(msg)
				.extracting("name", "createdDate", "createdReason")
				.containsExactly(name, createdDate, createdReason);

		// perform beans validation
		assertThatBean(person).isValid();
	}

	Stream<Arguments> createEntityDepartmentInvalidData() {
		final var id = "00001";
		final var name = "Development 01";
		final var sector = "Technology";

		// given
		return Stream.of(
				Arguments.of(EMPTY, name, sector),
				Arguments.of(null, name, sector),
				Arguments.of(id, EMPTY, sector),
				Arguments.of(id, null, sector));

	}

	@Order(7)
	@ParameterizedTest(name = "Pos {index} : id ''{0}'', name ''{1}'', sector ''{2}'''")
	@MethodSource("createEntityDepartmentInvalidData")
	@DisplayName("Test entity without builder creation, without exception")
	void givenEntity_whenCreateWithInvalidValuesButNoValidator_thenNoExceptionRaised(
			final String id,
			final String name,
			final String sector) {

		final var company = new Manager("x2", "Business Happy");

		final var department = new Department(id, name, sector, company);

		final var violations = Utils.VALIDATOR.validate(department);

		assertThat(violations).hasSize(1);
	}

	Stream<Arguments> createEntityProjectInvalidData() {
		final var id = 1L;
		final var name = "XPTO";
		final var company = new Manager("x2", "Business Happy");

		final var department = new Department("00001", "Development 01", "Technology", company);

		// given
		return Stream.of(
				Arguments.of(null, name, department),
				Arguments.of(id, EMPTY, department),
				Arguments.of(id, null, null));

	}

	@Order(8)
	@ParameterizedTest(name = "Pos {index} : id ''{0}'', name ''{1}'', priority ''{2}'''")
	@MethodSource("createEntityProjectInvalidData")
	@DisplayName("Test entity without builder creation, with exception")
	void givenEntity_whenCreateWithInvalidValuesWithValidator_thenExceptionRaised(
			final Long id, final String name, final Department department) {

		// when
		assertThatThrownBy(() -> {

			new Project(id, name, department);

		}) // then
				.as(format("Check the invalid entity: id ''{0}'', name ''{1}'' ", id, name)) //
				.isInstanceOf(ConstraintViolationException.class);

	}

	Stream<Arguments> createEntityPersonInvalidData() {
		final var id = 33L;
		final var name = "Paul";
		final var createdReason = "New Person";
		final var score = 25;
		final var gender = MALE;
		final var country = "Brazil";
		final var birthDate = LocalDate.of(1980, 6, 23);
		final var hobbies = new ArrayList<>(asList(COOKING, VIDEO_GAMMING));

		// given
		return Stream.of(
				Arguments.of(0L, name, createdReason, score, gender, country, birthDate, hobbies),
				Arguments.of(null, name, createdReason, score, gender, country, birthDate, hobbies),
				Arguments.of(id, EMPTY, createdReason, score, gender, country, birthDate, hobbies),
				Arguments.of(id, null, createdReason, score, gender, country, birthDate, hobbies));
	}

	@Order(9)
	@ParameterizedTest(name = "Pos {index} : id ''{0}'', name ''{1}'', createdDate ''{2}'', createdReason ''{3}''")
	@MethodSource("createEntityPersonInvalidData")
	@DisplayName("Test invalid entity with builder creation")
	void givenEntity_whenCreateWithInvalidValuesWithBuilder_thenExceptionRaised(
			final Long id, final String name,
			final String createdReason, final Integer score,
			final Gender gender, final String country,
			final LocalDate birthDate, final List<Hobby> hobbies) {

		// when
		assertThatThrownBy(() -> {

			// try to create a person
			new Person.Builder().with($ -> {
				$.id = id;
				$.createdReason = createdReason;
				$.name = name;
				$.score = score;
				$.country = country;
				$.birthDate = birthDate;
				$.hobbies = hobbies;
				$.gender = gender;
			}).build();

		}) // then
				.as(format("Check the invalid entity: id ''{0}'', name ''{1}'' ", id, name)) //
				.isInstanceOf(ConstraintViolationException.class);
	}
}
