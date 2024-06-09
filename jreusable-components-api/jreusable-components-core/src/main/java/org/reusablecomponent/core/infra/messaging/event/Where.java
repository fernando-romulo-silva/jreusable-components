package org.reusablecomponent.core.infra.messaging.event;

import java.util.Objects;

public record Where(String application, String machine) {

    public Where {
	
	if (Objects.isNull(application)) {
	    throw new IllegalArgumentException("The parameter 'application' cannot be null");
	}
	
	if (Objects.isNull(machine)) {
	    throw new IllegalArgumentException("The parameter 'machine' cannot be null");
	}
    }
}
