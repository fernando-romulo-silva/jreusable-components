package org.reusablecomponent.core.infra.messaging.event;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

public record When(LocalDateTime dateTime, ZoneId zoneId) {
    
    public When {
	dateTime = Objects.nonNull(dateTime) ? dateTime : LocalDateTime.now(); 
	zoneId = Objects.nonNull(zoneId) ? zoneId : ZoneId.systemDefault();
    }
    
    public When() {
	this(LocalDateTime.now(), ZoneId.systemDefault()); 
    }
}
