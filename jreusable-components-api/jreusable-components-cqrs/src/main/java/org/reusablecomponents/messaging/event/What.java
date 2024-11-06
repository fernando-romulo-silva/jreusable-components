package org.reusablecomponents.messaging.event;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.Objects;

public record What(String dataIn, String dataOut, String status) {

    public What {
        dataIn = Objects.nonNull(dataIn) ? dataIn : EMPTY;
        dataOut = Objects.nonNull(dataOut) ? dataOut : EMPTY;
        status = Objects.nonNull(dataOut) ? dataOut : EMPTY;
    }
}
