package org.reusablecomponent.core.domain;

import static java.text.MessageFormat.format;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.reusablecomponent.core.domain.Gender.MALE;
import static org.reusablecomponent.core.domain.Hobby.COOKING;
import static org.reusablecomponent.core.domain.Hobby.VIDEO_GAMMING;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

import jakarta.validation.ConstraintViolationException;

/**
 * Test the {@link AbstractEntiy} class on happy path.
 * 
 * @author Fernando Romulo da Silva
 */
@Tag("unit")
@DisplayName("Test the AbstractEntiy entity test, unhappy Path :( ")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class AbstractEntiyUnhappyPathTest extends AbstractAbstractEntiyTest {

    Stream<Arguments> checkEntityWithoutBuilderData() {
	
	final var id = "00001";
	final var name = "Development 01";
	final var sector = "Technology";
	
	// given
	return Stream.of(
			Arguments.of(EMPTY, name  , sector),
			Arguments.of(null , name  , sector),
			Arguments.of(id   , EMPTY , sector),
			Arguments.of(id   , null  , sector)
			// so on
	);
	
    }

    @ParameterizedTest(name = "Pos {index} : id ''{0}'', name ''{1}'', sector ''{2}'''")
    @MethodSource("checkEntityWithoutBuilderData")
    @Order(4)
    @DisplayName("Test entity without builder creation")
    void checkEntityWithoutBuilderTest(final String id, final String name, final String sector) {
	
	final var department = new Department(id, name, sector);
	
	final var violations = validator.validate(department);
	
	assertThat(violations).hasSize(1);
    }
    
    
    
    //---------------------------------------------------------------------------------------------
    
    Stream<Arguments> createEntityWithBuilderData() {
	
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
			Arguments.of(0L  , name , createdReason, score, gender, country, birthDate, hobbies),
			Arguments.of(null, name , createdReason, score, gender, country, birthDate, hobbies),
			Arguments.of(id  , EMPTY, createdReason, score, gender, country, birthDate, hobbies),
			Arguments.of(id  , null , createdReason, score, gender, country, birthDate, hobbies)
			// so on
	);
    }

    @ParameterizedTest(name = "Pos {index} : id ''{0}'', name ''{1}'', createdDate ''{2}'', createdReason ''{3}''")
    @MethodSource("createEntityWithBuilderData")
    @Order(2)
    @DisplayName("Test invalid entity with builder creation")
    void createEntityWithBuilderTest(final Long id, final String name, final String createdReason, final Integer score, final Gender gender, final String country, final LocalDate birthDate, final List<Hobby> hobbies) {

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
