package org.reusablecomponents.messaging.event;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.Objects;

/**
 * Represents the data involved in an event.
 */
public record What(String dataIn, String dataOut) {

    /**
     * Compact constructor to ensure non-null values.
     *
     * @param dataIn  The input data.
     * @param dataOut The output data.
     */
    public What {
        dataIn = Objects.nonNull(dataIn) ? dataIn : EMPTY;
        dataOut = Objects.nonNull(dataOut) ? dataOut : EMPTY;
    }

    /**
     * Default constructor setting empty dataIn and dataOut.
     */
    public What() {
        this(null, null);
    }
}
