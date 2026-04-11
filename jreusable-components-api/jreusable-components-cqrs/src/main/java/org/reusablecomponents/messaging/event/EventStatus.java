package org.reusablecomponents.messaging.event;

/**
 * Enumeration representing the status of an event.
 */
public enum EventStatus {

    /**
     * Success status: operation was processed successfully
     */
    SUCCESS,

    /**
     * Pending status: operation is pending processing
     */
    PENDING,

    /**
     * Unknown status: operation status is unknown
     */
    UNKNOWN,

    /**
     * Warning status: operation was processed with warnings
     */
    WARNING,

    /**
     * Failure status: operation processing failed
     */
    FAILURE;

    /**
     * Get the name of the status.
     *
     * @return The name of the status.
     */
    public String getName() {
        return this.name();
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return this.getName();
    }
}
