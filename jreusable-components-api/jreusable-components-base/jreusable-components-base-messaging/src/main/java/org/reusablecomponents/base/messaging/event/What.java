package org.reusablecomponents.base.messaging.event;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.Objects;

public record What(String dataIn, String dataOut) {
    
    public What {
	dataIn = Objects.nonNull(dataIn) ? dataIn : EMPTY; 
	dataOut = Objects.nonNull(dataOut) ? dataOut : EMPTY;
    }
}
