package org.reusablecomponent.core.infra.messaging.event;

import java.time.LocalDateTime;
import java.time.ZoneId;

public record When(LocalDateTime dateTime, ZoneId zoneId) {

}
