package org.reusablecomponent.core.infra.messaging.event;

import java.time.LocalDateTime;
import java.util.TimeZone;

public record When(LocalDateTime dateTime, TimeZone timeZone) {

}
