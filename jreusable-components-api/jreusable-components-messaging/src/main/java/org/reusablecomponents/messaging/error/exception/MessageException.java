package org.reusablecomponents.messaging.error.exception;

public sealed interface MessageException permits DeadLetterException, RetryableException, RequeableException, DroppableException {

}
