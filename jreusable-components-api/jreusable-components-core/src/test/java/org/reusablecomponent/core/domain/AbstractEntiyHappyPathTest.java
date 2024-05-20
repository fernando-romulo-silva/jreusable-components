package org.reusablecomponent.core.domain;

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
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.application_example.domain.Department;
import org.application_example.domain.Gender;
import org.application_example.domain.Hobby;
import org.application_example.domain.Person;
import org.assertj.core.api.InstanceOfAssertFactories;
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
import org.reusablecomponent.core.util.AbstractValidatorTest;

import com.jparams.verifier.tostring.ToStringVerifier;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Test the {@link AbstractEntiy} class on happy path :).
 * 
 * @author Fernando Romulo da Silva
 */
@Tag("unit")
@DisplayName("Test the AbstractEntiy entity test, happy Path :) ")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class AbstractEntiyHappyPathTest extends AbstractValidatorTest {
     
    @Test
    @Order(1)
    //@Disabled("EqualsVerifier not support Java 21")
    @DisplayName("Test the equals And HashCode Contract")
    void tryEqualsAndHashCodeContractTest() { // NOPMD - JUnitTestsShouldIncludeAssert: EqualsVerifier already do it
	
	EqualsVerifier.forClass(Person.class)
			.suppress(NONFINAL_FIELDS, STRICT_INHERITANCE, REFERENCE_EQUALITY)
			.withOnlyTheseFields("id")
			.withPrefabValues(Long.class, 1L, 2L)
			.verify();
    }

    @Test
    @Order(2)
    @DisplayName("Test the toString method")
    void tryToStringTest() { // NOPMD - JUnitTestsShouldIncludeAssert: ToStringVerifier already do it
	ToStringVerifier.forClass(Person.class)
		.withClassName(NAME)
		.withFailOnExcludedFields(false)
		.verify();
    }

    @Test
    @Order(3)
    @DisplayName("Test the entity update")
    void tryToUpdateEntityTest() {
	
	// given
	final var department = new Department("00001", "Development 01", "Technology");
	
	// When
	department.update("Development 02", department.getSector());
	
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
    @DisplayName("Test entity without builder creation")
    void checkEntityWithoutBuilderTest() {

	// given
	final var id = "00001";
	final var name = "Development 01";
	final var sector = "Technology";
	
	// When
	final var department = new Department(id, name, sector);
	
	// then
	assertThat(department)
		.as("Check the update reason is not empty")
		.returns(true, Department::isPublishable)
		.returns(EMPTY, Department::getRealmId)
	;
	
	// perform beans validation
	assertThatBean(department)
	 	.isValid();
    }
    
    
    Stream<Arguments> createEntityWithBuilderData() {
	
	final var createdDate = LocalDateTime.now();
	
	// given
	return Stream.of(
		Arguments.of(33L, "Paul", createdDate, "New Person", 25, MALE, "Brazil", LocalDate.of(1980, 6, 23),  new ArrayList<>(asList(COOKING, VIDEO_GAMMING))),
		Arguments.of(25L, "Maira", createdDate, "Another Person", 25, FEMALE, "Panama", LocalDate.of(2000, 2, 15), new ArrayList<>(asList(READING, WATCHING_MOVIES)))
	);
    }

    @Order(5)
    @ParameterizedTest(name = "Pos {index} : id ''{0}'', name ''{1}'', createdDate ''{2}'', createdReason ''{3}''")
    @MethodSource("createEntityWithBuilderData")
    @DisplayName("Test entity with builder creation")
    void createEntityWithBuilderTest(final Long id, final String name, final LocalDateTime createdDate, final String createdReason, final Integer score, final Gender gender, final String country, final LocalDate birthDate, final List<Hobby> hobbies) {

	// when
	final var person = new Person.Builder().with($ -> {
	    $.id = id;
	    $.createdReason = createdReason; 
	    $.name = name;
	    $.score = score;
	    $.country = country;
	    $.birthDate = birthDate;
	    $.hobbies = hobbies;
	    $.gender = gender;
	}).build();

	updateValue(person, "createdDate", createdDate);
	
	final var msg = format("Check the name ''{0}'', createdDate ''{1}'' createdReason ''{2}'', score ''{3}'', gender ''{4}'', country ''{5}'', birthDate ''{6}'', and hobbies ''{7}''", name, createdDate, createdReason, score, gender, country, birthDate, hobbies);
	
	// then
	assertThat(person)
		.as(msg)
		.extracting("name", "createdDate", "createdReason")
		.containsExactly(name, createdDate, ofNullable(createdReason))
	;
	
	// perform beans validation
	assertThatBean(person).isValid();
    }
}
