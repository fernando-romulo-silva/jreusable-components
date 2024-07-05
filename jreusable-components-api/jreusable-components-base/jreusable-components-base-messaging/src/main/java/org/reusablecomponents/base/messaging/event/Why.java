package org.reusablecomponents.base.messaging.event;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.Objects;

public record Why(String reason, String description) {
    
    public Why {
	
	description = Objects.nonNull(description) ? description : EMPTY;
	
	if (Objects.isNull(reason)) {
	    throw new IllegalArgumentException("The parameter 'reason' cannot be null");
	}	
    }
}
