package org.reusablecomponents.messaging.event;

import static com.jparams.verifier.tostring.NameStyle.NAME;
import static nl.jqno.equalsverifier.Warning.NONFINAL_FIELDS;
import static nl.jqno.equalsverifier.Warning.REFERENCE_EQUALITY;
import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import com.jparams.verifier.tostring.ToStringVerifier;

import nl.jqno.equalsverifier.EqualsVerifier;

@Tag("unit")
@DisplayName("Test the EntiyBaseFacade entity test, happy Path :)")
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class EventHappyTest {

    @Test
    @Order(1)
    @Disabled("EqualsVerifier not support Java 21")
    @DisplayName("Test the equals And HashCode Contract")
    void tryEqualsAndHashCodeContractTest() { // NOPMD - JUnitTestsShouldIncludeAssert: EqualsVerifier already do it

        EqualsVerifier.forClass(Event.class)
                .suppress(NONFINAL_FIELDS, STRICT_INHERITANCE, REFERENCE_EQUALITY)
                .withOnlyTheseFields("origin")
                .withPrefabValues(String.class, "origin1", "origin2")
                .verify();
    }

    @Test
    @Order(2)
    @DisplayName("Test the toString method")
    void tryToStringTest() { // NOPMD - JUnitTestsShouldIncludeAssert: ToStringVerifier already do it
        ToStringVerifier.forClass(Event.class)
                .withClassName(NAME)
                .withFailOnExcludedFields(false)
                .verify();
    }

    @Test
    @Order(3)
    @DisplayName("Test the publish operation")
    void createInvalidTest() {
        // given
        final var now = LocalDateTime.now();
        final var zoneId = ZoneId.systemDefault();

        final var event = assertDoesNotThrow(() -> new Event.Builder()
                .with($ -> {
                    $.origin = "origin";
                    $.status = EventStatus.SUCCESS;
                    $.when = new When(now, zoneId);
                    $.who = new Who("realm", "user", "session");
                    $.what = new What();
                    $.where = new Where("machine", "application", "version", "build", "descriptor");
                    $.why = new Why("reason");
                })
                .build());

        assertThat(event)
                .isNotNull()
                .hasFieldOrPropertyWithValue("origin", "origin")
                .hasFieldOrPropertyWithValue("status", EventStatus.SUCCESS)

                .extracting(t -> t.getWhen().dateTime(), t -> t.getWhen().zoneId())
                .containsExactly(now, zoneId);
        ;
    }
}
