package org.reusablecomponents.messaging.event;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

/**
 * Represents the time when an event occurred.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 * 
 * @param dateTime The date and time of the event.
 * @param zoneId   The time zone of the event.
 */
public record When(LocalDateTime dateTime, ZoneId zoneId) {

    /**
     * Constructor with validation.
     *
     * @param dateTime The date and time of the event.
     * @param zoneId   The time zone of the event.
     */
    public When {
        dateTime = Objects.nonNull(dateTime) ? dateTime : LocalDateTime.now();
        zoneId = Objects.nonNull(zoneId) ? zoneId : ZoneId.systemDefault();
    }

    /**
     * Default constructor setting current dateTime and system default zoneId.
     */
    public When() {
        this(null, null);
    }
}
