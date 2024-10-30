package org.reusablecomponents.messaging.exception;

public sealed interface MessageException
        permits DeadLetterException, RetryableException, RequeableException, DroppableException {

}
