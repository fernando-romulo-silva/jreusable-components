package org.reusablecomponent.core.infra.messaging.event;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.Objects;

public record Who(String realm, String login, String session) {
    
    public Who {
	
	realm = Objects.nonNull(realm) ? realm : EMPTY;
	session = Objects.nonNull(session) ? session : EMPTY;
	
	if (Objects.isNull(login)) {
	    throw new IllegalArgumentException("The parameter 'login' cannot be null");
	}	
    }
}
